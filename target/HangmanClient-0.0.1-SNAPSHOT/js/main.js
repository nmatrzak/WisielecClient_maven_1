var baseUrlEndpoint = "http://" + document.location.host + "/HangmanServer/ep";

/*
 * Funkcja zwraca bazowy url do endpointa na serwerze
 * @author Piotr Podgorski
 * @param epName - nazwa endpointa domenowego
 * */
function getEndpointUrl(epName) {
	return baseUrlEndpoint + "/" + epName;
}

/*
 * Funkcja wymusza przejscie na formularzu www
 * @author Piotr Podgorski
 * 
 * */
function submit_operation(operation, data) {
	console.log("submit_operation: "+operation+"("+data+")")
	form = $("#main_form");
	$("#operation").val(operation);
	$("#data").val(data);
	form.submit(); 
	return true;	
}

/*
 * Funkcja wymusza przejsce do strony www
 * @author Piotr Podgorski
 * @page - nazwa strony
 * 
 * */
function goto_page(page) {
	console.log("goto_page: "+page)
	submit_operation("goto_page",page)
}

/*
 * Funkcja akutalizauje nazwa aktulanej strony (na elemencie html)
 * @author Piotr Podgorski
 * 
 * */
function updateCurrentPage(currentPage) {
	console.log("updateCurrentPage")
	$("#currentPage").val(currentPage);	
}

/*
 * Funkcja wysyla websocketem informacje do serwera
 * @author Piotr Podgorski
 * @param oper - nazwana akcja / operacja
 * @param data - dane zwiazanez operacja
 * */
function SendOperationToServer(oper, data) {
	console.log('SendOperationToServer: '+ data)//timeout()per)
	wsSendMessage(oper+"#"+data)
}

/*
 * Funkcja zwraca informacje czy przyslana informacja w websocket jest komenda podana w parametrze
 * @author Piotr Podgorski
 * @param msg- dane z websocket
 * @param oper - sprawdzana operacja
 * */
function isMessage(msg, oper) {	
	var op = oper + "#"
	return msg.substring(0, 4) == op;
}

/*
 * Funkcja zwraca dane z message od serwera z websocket
 * @author Piotr Podgorski
 * @param msg- dane z websocket
 * */
function getMsgData(msg) {
	return msg.substring(4)
}

/*
 * Funkcja zwarac id uzytkownika zapisana na elemencie html
 * @author Piotr Podgorski
 * 
 * */
function getPlayerId() {
	var playerId = $("#playerId").val()
	console.log('playerId='+playerId)
	return playerId 
}


/*
 * Funkcja zwraca nazwe uzytkownika na elemencie html (input)
 * @author Piotr Podgorski
 * 
 * */
function getUserName() {
	var username = $("#username").val()
	return username 
}

/*
 * Funkcja zwraca nazwe uzytkownika po konwersji diaktrycznej
 * @author Piotr Podgorski
 * 
 * */
function getUserNameConverted() {	
	return codePolishWordToWordWithSpecs(getUserName()) 
}

/*
 * Funkcja ustawia nazwe uzytkownika na elemencie html
 * @author Piotr Podgorski
 * 
 * */
function setUserName(un) {
	$("#username").val(un )
}

/*
 * Funkcja wysyla do serwera, ze "player" o id zaczyna swoja aktywnosc pod nowa sesja
 * @author Piotr Podgorski
 * 
 * */
function sendHello() {
	SendOperationToServer("hello", getPlayerId())
}

/*
 * Funkcja wysyla do serwera, ze "player" konczy swoja aktywnosc w grze
 * @author Piotr Podgorski
 * 
 * */
function sendByeBye() {
	SendOperationToServer("byebye", getPlayerId())
}

/*
 * Funkcja wysyla do serwera, ze "player" jest aktywny (strona)
 * @author Piotr Podgorski
 * 
 * */
function sendAlive() {
	var id = getPlayerId()	
	console.log('sendAlive: playerId='+id )
	$.ajax({
		type : "GET",
		url : getEndpointUrl("players") + "/alive/" +id,
		success : function(data) {
			console.log("alive successed: ") // + data)
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
			console.log("alive error: ") // + data)
        }       
	})
}

