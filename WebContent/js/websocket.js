var wsUri = "ws://" + document.location.host + "/HangmanServer/play";

console.log(wsUri);

/*
 * obsluga websocketu - przypisanie funkcji do zdarzen
 * */
var websocket = new WebSocket(wsUri);

console.log("connected");

websocket.onerror = function(evt) {
	console.log('websocket.onerror')
	wsOnError(evt)
};

function wsOnError(evt) {
	console.log("wsOnError="+evt.data);
}

websocket.onopen = function(evt) {
	console.log('websocket.onopen')
	wsOnOpen()
};


websocket.onmessage = function(evt) {
	console.log('websocket.onmessage')
	wsOnMessage(evt.data)
};


/*
 * wysylka informacji websocketem
 */
function wsSendMessage(msg) {
	console.log('sendMessage: '+ msg)
	websocket.send(msg)	
}


