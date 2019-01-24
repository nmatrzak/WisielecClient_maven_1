var baseUrlEndpoint = "http://" + document.location.host + "/HangmanServer/ep";

function getEndpointUrl(epName) {
	return baseUrlEndpoint + "/" + epName;
}

function submit_operation(operation, data) {
	console.log("submit_operation: "+operation+"("+data+")")
	form = $("#main_form");
	$("#operation").val(operation);
	$("#data").val(data);
	form.submit(); 
	return true;	
}

function goto_page(page) {
	console.log("goto_page: "+page)
	submit_operation("goto_page",page)
}

function updateCurrentPage(currentPage) {
	console.log("updateCurrentPage")
	$("#currentPage").val(currentPage);	
}

function SendOperationToServer(oper, data) {
	console.log('SendOperationToServer: '+ data)//timeout()per)
	wsSendMessage(oper+"#"+data)
}

function isMessage(msg, oper) {	
	var op = oper + "#"
	return msg.substring(0, 4) == op;
}

function getMsgData(msg) {
	return msg.substring(4)
}

function getPlayerId() {
	var playerId = $("#playerId").val()
	console.log('playerId='+playerId)
	return playerId 
}


function getUserName() {
	var username = $("#username").val()
	return username 
}

function getUserNameConverted() {	
	return codePolishWordToWordWithSpecs(getUserName()) 
}

function setUserName(un) {
	$("#username").val(un )
}

function sendHello() {
	SendOperationToServer("hello", getPlayerId())
}

function sendByeBye() {
	SendOperationToServer("byebye", getPlayerId())
}

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

