updateCurrentPage("guess")

/*
 * wyslanie wybranej litery formularzem do serwera
 * @author Piotr Podgorski
 * @param letter - litera
 * @param id - id elementu HTML 
 * */
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
}

/*
 * wybarkowane slowo jako element HTML
 * @author Piotr Podgorski 
 * @param gappedWord - wybrakowane slowo
 * @return element HTML
 * */
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


/*
 * wstawia "wybrakowane" slowo do opowiedniego elementu strony
 * @author Piotr Podgorski 
 * @param gappedWord - wybrakowane slowo
 * @return element HTML
 * */
function printGappedWord(word) {
	$("#word_lettered").html(getGappedWord(word))
}

if (gappedWord != null) {
	printGappedWord(gappedWord)
}

/*
 * timeout no wywolanie funkcji alive 
 * */
function timeout() {
	setTimeout(function() {
		sendAlive()
		timeout() 
	}, 5000);
}

timeout()