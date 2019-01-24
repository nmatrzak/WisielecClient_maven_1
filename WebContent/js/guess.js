updateCurrentPage("guess")

//function do_guess_end_game() {
//	console.log("end_game");
//	submit_operation("end_game", letter);
//}
//
//function guess_end_game() {
//	$("#dialog-confirm").dialog({
//		resizable : false,
//		height : "auto",
//		width : 400,
//		modal : true,
//		buttons : {
//			"YES" : function() {
//				$(this).dialog("close");
//				do_guess_end_game();
//			},
//			No : function() {
//				$(this).dialog("close");
//			}
//		}
//	});
//}

function guess_letter(letter, id) {
	console.log("letter pressed: " + letter)
	$('#' + id).attr("disabled", true);
	submit_operation("letter", letter);
}

function wsOnOpen() {
	console.log("wsOnOpen")
	sendHello()
}

function wsOnMessage(data) {
	if (data == "word_updated") {
		submit_operation("word_updated", "")
	} 
//	else if (msg == "opponnent_end_game") {
//		$("#opponent_end_game").show();
//		setTimeout(function() {
//			goto_page("list")
//		}, 5000);
//	}
}

function getGappedWord(gappedWord) {
	console.log("showGappedWord: " + gappedWord)
	var t = ""
	var c = ""
	for (i = 0; i < gappedWord.length; i++) {
		var c = gappedWord.charAt(i)
		if (c == "_") {
			t += "<span class='letter_blanked'>?</span>"
		} else {
			t += "<span class='letter_hit'>" + c + "</span>"
		}
	}
	return t;
}


function printGappedWord(word) {
	$("#word_lettered").html(getGappedWord(word))
}

if (gappedWord != null) {
	printGappedWord(gappedWord)
}

function timeout() {
	setTimeout(function() {
		sendAlive()
		timeout() 
	}, 5000);
}

timeout()