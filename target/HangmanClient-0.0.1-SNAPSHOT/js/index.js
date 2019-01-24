updateCurrentPage("index")

function hideMessage() {
	console.log("hideMessage")
	$("#message").hide()
}

function showUserCreated() {
	console.log("showUserCreated")
	$("#message").removeClass("error").html("User created! Wait ...").show();
	setTimeout(function() {
		hideMessage();
//		alert(getUserNameConverted())		
		submit_operation("setPlayerName", getUserNameConverted())
	}, 500);
}

function showError() {
	console.log("showError")
	$("#message").addClass("error").html(
			"User creation failed! Try again. Try with another name.").show();
}

function checkPlayerAndGo(name) {
	console.log("checkPlayerAndGo: " + name);
	var urlGetUser = getEndpointUrl("players") + "/" + name
	$.ajax({
		type : "POST",
		url :  urlGetUser,
		success : function(data) {
			if (data!="USER EXIST!") {
			   showUserCreated()
			} else {
				showError()
			}
		}
	}).fail(function() {
		showError()
	})
}

function buttonStartClicked() {
	console.log("buttonStartClicked")
	username = getUserName()
	
	if (username=="") {
		alert("Enter player name!")
		return false;
	}		
	if (username=="COMPUTER") {
		alert("Enter another player name!")
		return false;
	}
	
	checkPlayerAndGo(username)	
	return true;
}

function wsOnOpen() {
	console.log("wsOnOpen")
}


function wsOnMessage(msg) {
	console.log("wsOnMessage: "+msg)
}

$("#username").focus()

var input = document.getElementById("username");
//Execute a function when the user releases a key on the keyboard
input.addEventListener("keyup", function(event) {
	// Cancel the default action, if needed		
	event.preventDefault();	
	// Number 13 is the "Enter" key on the keyboard
	if (event.keyCode === 13) {
		buttonStartClicked()
	}
});

$(document).ready(function() {
	  $(window).keydown(function(event){
	    if(event.keyCode == 13) {
	      event.preventDefault();
	      return false;
	    }
	  });
});