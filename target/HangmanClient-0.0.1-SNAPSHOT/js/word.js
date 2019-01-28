updateCurrentPage("word")

/*
 * Funkcja wysyla hello do serwera - aktualizacja sesji - id gracza
 * @author Piotr Podgorski
 * 
 * */
function wsOnOpen() {
	console.log("wsOnOpen")
	sendHello()
}

/*
 * Funkcja aktualizuje stan gry na www
 * @author Piotr Podgorski
 * @param game - obiekt gry
 * 
 * */
function updateGame(game) {
	console.log("updateGame")	
	printTheWord(game.theWord,game.usedLetters)
	printUsedLetters(game.usedLetters)
	if (game.gameStatus=="END") {
		$("#button_end_game").hide();
		if (game.winner== getUserName()) {
			$("#winBox").show();			
		} else {			
			$("#lostBox").show();
		}
		setTimeout(function() {
			   goto_page("list")
			}, 5000);
	}
}

/*
 * Funkcja pobiera z serwera stan gry i aktualizuje strone www
 * @author Piotr Podgorski
 * 
 * */
function updateWordAndGameState() {
	console.log("updateWordAndGameState")
	var id = getPlayerId()
	ep = getEndpointUrl("game") + "/gameByPlayerId/" + id
	$.ajax({
		type : "GET",
		url : ep,
		success : function(data) {
			console.log("data successed: " + data)
			obj = jQuery.parseJSON( data );			
			updateGame(obj)
		}
	})
}

/*
 * Funkcja  przyjmuje z serwera gry informacje o wyslaniu znaku przez przeciwnika, aktualizuje stan gry
 * @author Piotr Podgorski
 * @param msg- dane z websocket
 * */
function wsOnMessage(msg) {
	console.log("wsOnMessage: " + msg)
	if (msg == "letter") {
		updateWordAndGameState()
	}  
}

/*
 * Funkcja  sprawdza wprowadzone slowo przez uzytkownika i wysyla je do serwera w celu akutalizajci stanu gry
 * @author Piotr Podgorski
 * 
 * */
function wordEntered() {
	var word = $("#word_input").val()
	if (word == "" || word.length < 4) {
		alert("A word cann`t be empty and has to have minimum 4 letters!")
		return false;
	}
	var letters = /^[A-Za-zĄĆĘŁŃÓŚŻŹąćęłńóśżź]+$/;
	if (!word.match(letters)) {
		alert("You can use only letters, no digits and special characters!")
		return false;
	}
	var conv = codePolishWordToWordWithSpecs(word)
	console.log("encWord: "+word + " > "+conv);
	submit_operation("update_word", conv)
	return true;
}

/*
 * Funkcja  generuje kod html do wyswietlania na stronie z odpowiednim stylowaniem liter zgadywanego wyrazu
 * @author Piotr Podgorski
 * @param theWord - zgadywane slowo
 * @param used - wszystkie uzyte litery przez przeciwnika
 * */
function getTheWord(theWord, used) {
	console.log("getTheWod: "+theWord+" < "+used)
	var t = ""
	var c = ""
	for (i = 0; i < theWord.length; i++) {
		var c = theWord.charAt(i)
		var cl = ""
		if (used.indexOf(c) !== -1) {
			cl = "_hit"
		} 
		t += "<span class='letter" + cl + "'>" + c + "</span>"
	}
	console.log("getTheWod="+t)
	return t;
}

/*
 * Funkcja  wyswietla slowo (html) w odpowiednim elemencie strony ( id word_lettered )
 * @author Piotr Podgorski
 * @param theWord - zgadywane slowo
 * @param used - wszystkie uzyte litery przez przeciwnika
 * */
function printTheWord(theWord, used) {	 
	var _theWord = decodeWordWithSpecsToPolishWord(theWord)
	var _used = decodeWordWithSpecsToPolishWord(used)
	$("#word_lettered").html(getTheWord(_theWord, _used))
}

/*
 * Funkcja  wyswietla wszystkie uzytke litery (html) w odpowiednim elemencie strony ( id word_lettered )
 * @author Piotr Podgorski
 * @param letters - wszystkie uzyte litery przez przeciwnika
 * */
function printUsedLetters(letters) {
	var _letters = decodeWordWithSpecsToPolishWord(letters)	
	var t = ""
	for (i = 0; i < _letters.length; i++) {
	  var c = _letters.charAt(i)
	  t += "<span class='letter'>" + c + "</span>"
	}
	console.log("printUsedLetters="+t)
	$("#letters").html(t)
}

/*
 * Funkcja wysyla do serwera informacje o tym ze uzytkownik wciaz jest w "grze"
 * @author Piotr Podgorski 
 * 
 * */
function timeout() {
	setTimeout(function() {
		sendAlive()
		timeout() 
	}, 5000);
}

timeout()

$("#word").focus()

printTheWord(theWord, lettersUsed)

/*
 * Ustawienie fokusa na element i przejecie zdarzenia nacisniecia enter - obsluga "start"
 * */
var input = document.getElementById("word_input");
// Execute a function when the user releases a key on the keyboard
if (input != null) {
	input.addEventListener("keyup", function(event) {
		// Cancel the default action, if needed
		event.preventDefault();
		// Number 13 is the "Enter" key on the keyboard
		if (event.keyCode === 13) {
			wordEntered()
		}
	});
}

/*
 * wylaczenie domyslnego dzialania na zdarzeniu
 * */
$(document).ready(function() {
	$(window).keydown(function(event) {
		if (event.keyCode == 13) {
			event.preventDefault();
			return false;
		}
	});
});