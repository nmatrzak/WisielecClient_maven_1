<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@include file="includes/header.jsp"%>
<%@ page import=" utils.WordCodeDecode, dto.PlayerDto" %>
<% 
PlayerDto player = request.getAttribute("player")!=null?(PlayerDto)request.getAttribute("player"):null;

%>
<input type="hidden" id="username" name="username" value="<%=player.getName()%>" />
<input type="hidden" id="playerId" name="playerId" value="<%=player.getPlayerId() %>" />
        
<div id="userdata">
	<div class="user_name" id="user">Me</div>
	<div class="user_points">
		<span class="label">points:</span> <span class="value" id="user_points">20</span>
	</div>
	<div class="user_wins">
		<span class="label">wins:</span> <span class="value" id="user_wins">30</span>
	</div>
	<div class="user_losts">
		<span class="label">losts:</span> <span class="value" id="user_losts">10</span>
	</div>
</div>

<script>
function divClassValue(cl, valname, val) {
	return "<div class='"+cl+"'><span class='label'>"+valname+":</span> <span class='value'>"+val+"</span></div>"
}

function addToList(item, u) {
	var t = "<div class='user"+u+"'><div class='user_name'>"+item.name+"</div>";
	t += divClassValue("user_points", "points", item.points)
	t += divClassValue("user_wins", "wins", item.countWins)
	t += divClassValue("user_losts", "losts", item.countLosts) 
	 
	t += "<div class='user_buttons'><button type='button' form='main_form' onClick='playWith("+item.playerId+")' "
	if (item.status=='PLAYING') t += " disabled"		
	t += ">Play</button></div>"
	t += "</div>";
	return t;
}

function addComputerToList(u) {
	var t = "<div class='user"+u+"'><div class='computer_name'>COMPUTER</div>";
	t += divClassValue("user_points", "", "")
	t += divClassValue("user_wins", "", "")
	t += divClassValue("user_losts", "", "")
	t += "<div class='user_buttons'><button type='button' form='main_form' onClick='playWith(0)'>Play</button></div>"
	t += "</div>";
	return t;
}
</script>

<div id="progress">
</div>

<div id="list">	
</div>

<div id="messageWait">
WAIT ...	
</div>

<div id="controlsbox">
	<button name="btn_disconnect" id="btn_disconnect"
		onClick="buttonDisconnectClicked(); return false">Disconnect</button>
</div>

<div id="dialog-disconnect" title="Disconnect">
	<p>
		<span class="ui-icon ui-icon-alert"
			style="float: left; margin: 12px 12px 20px 0;"></span>Are you sure?
	</p>
</div>

<script src="js/list.js"></script>
<%@include file="includes/footer.jsp"%>