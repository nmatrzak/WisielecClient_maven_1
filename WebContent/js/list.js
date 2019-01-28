updateCurrentPage("list")

$("#messageWait").hide();
$("#dialog-disconnect").hide();

var timerCounterFrom = 10;
var timerCounter = timerCounterFrom;

/*
 * wyslanie ws -em komunikatu rozlaczenia
 * @author Piotr Podgorski  
 * 
 * */
function do_disconnect() {
	sendByeBye()
	submit_operation("disconnect", getPlayerId())
}

/*
 * po nacisnieciu przycisku disconnect uruchomienie okna dialogowego
 * @author Piotr Podgorski 
 * */
function buttonDisconnectClicked() {
	console.log("disconnect: " + name)
	$("#dialog-disconnect").dialog({
		resizable : false,
		height : "auto",
		width : 400,
		modal : true,
		buttons : {
			"YES" : function() {
				$(this).dialog("close");
				do_disconnect();
			},
			No : function() {
				$(this).dialog("close");
			}
		}
	});
}

/*
 * Pokazanie uzytkownika i jego punktow
 * @author Piotr Podgorski 
 * @param user - uzytkownik json
 * */
function showUser(user) {
	console.log("showUser: " + user);
	var un = user.name
	$("#user").html("Me (" + un + ")");
	$("#user_points").html(user.points);
	$("#user_wins").html(user.countWins);
	$("#user_losts").html(user.countLosts);
}

/*
 * Odswieza dane gracza
 * @author Piotr Podgorski 
 * @param player - uzytkownik json
 * */
function refershPlayer(player) {
	console.log("refershPlayer");
	showUser(player)
}
/*
 * funkcja inicjacji paska postepu
 * @author Piotr Podgorski 
 * */
function initProgress() {
	var t = ""
	for (i = 1; i <= timerCounterFrom; i++) {
		t += "<span id='prg" + i
				+ "' class='prg_element'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>";
	}
	$("#progress").html(t);
}
// zainicjowanie paska postepu
initProgress()

/*
 * Wyswietlenie paska postepu zgodnie ze stanem licznika
 * @author Piotr Podgorski 
 * */
function showProgress() {
	for (i = 1; i <= timerCounterFrom; i++) {
		if (i < timerCounterFrom - timerCounter + 1) {
			$("#prg" + i).show();
		} else {
			$("#prg" + i).hide();
		}
	}
}

/*
 * Wstawia liste (html) do dokumentu strony w elemenci o id = list
 * @author Piotr Podgorski 
 * */
function showList(l) {
	console.log("showList: ");
	$("#list").html(l)
}

/*
 * Funkcja sprawdza czy "uzytkownik" jest niewidoczny
 * @author Piotr Podgorski
 * @param user - uzytkownik 
 * */
function isInvisible(user) {
	return user.status == 'INVISIBLE'
}

/*
 * Pobiera liste uztykownikow z serwera i odswieza ja na stronie
 * @author Piotr Podgorski
 * */
function refreshList() {
	console.log("refreshList");
	$.ajax({
		type : "GET",
		url : getEndpointUrl("players"),
		success : function(data) {
			console.log("data successed: ") // + data)

			var b = 0
			var list = addComputerToList(b)

			var username = getUserName()
			var playerId = getPlayerId()
			console.log("current user name: " + username + " with id: "+playerId) // + data)

			b = 1
			jQuery.each(data, function(index, itemData) {
				if ((itemData.playerId != playerId) && !isInvisible(itemData)) {
					if (index > 0) {
						list += addToList(itemData, b)
					}
					b = 1 - b
				} else {
					refershPlayer(itemData)
				}
			});
			showList(list)
		}
	})
}

/*
 * Funkcja sprawdza liczniki i w zaleznosci od ich stanu odswieza strone
 * @author Piotr Podgorski
 * 
 * */
function timeToRefresh() {
	console.log("timeToRefresh: " + timerCounter);
	timerCounter--;
	if (timerCounter <= 0) {
		timerCounter = timerCounterFrom;
		sendAlive()
		refreshList();
	}
}

/*
 * Funkcja timeout do odswiezenia listy 
 * @author Piotr Podgorski
 * 
 * */
function timeout() {
	setTimeout(function() {
		showProgress()
		timeToRefresh()
		timeout();
	}, 500);
}

/*
 * Uruchomienie funkcja timeout do odswiezenia listy 
 * @author Piotr Podgorski
 *
 * */
timeout()

/*
 * Po przejsciu na strone "zameldowanie sie serwerowi" z nowym id sessji 
 * @author Piotr Podgorski
 * 
 * */
function wsOnOpen() {
	console.log("wsOnOpen")
	sendHello()
	refreshList()
}

/*
 * Obsluga zdarzen przychodzacych z serwera - odpowiednia reakcja na nie (komunikacja websocket) 
 * @author Piotr Podgorski
 * @param msg - komunikat
 * */
function wsOnMessage(msg) {
	console.log("wsOnMessage: " + msg)
	if (msg == 'goto_guess') {
		submit_operation("goto_page", "guess")
		return;
	}
	if (msg == 'goto_word') {
		submit_operation("goto_page", "word")
		return;
	}
	if (msg == 'refresh_player_list') {
		refreshList()
		return;
	}	
}

/*
 * Spradzenie czy przeciwnik jest "dostepny" do grania
 * @author Piotr Podgorski
 * @param opponentId - id przeciwnika (0 - gdy wybrano gre z komputerem)
 * */
function playerAliveCanPlay(opponentId) {
	var urlGetPlayer = getEndpointUrl("players") + "/byId/" + opponentId // getUserName()
	$.ajax({
		type : "GET",
		url : urlGetPlayer,
		success : function(data) {
			console.log("data = " + data)
			var json = $.parseJSON(data);
			console.log("data.status = " + json.status)
			if (json.status == 'CREATED') {
				submit_operation("playGame", opponentId)
			} else {
				alert('Opponent is busy or unaviable!')
			}
		},
		error : function(request, status, error) {
			alert(request.responseText);
		}
	})
}

/*
 * Wybiera uztykownika do grania i przechodi na odpowiednia strone
 * @author Piotr Podgorski
 * @param opponentId - id przeciwnika (0 - gdy wybrano gre z komputerem)
 * */
function playWith(opponentId) {	
	if (opponentId == 0) {
		submit_operation("playGame", 0)
	} else 
		{
			playerAliveCanPlay(opponentId)
		}	
}
