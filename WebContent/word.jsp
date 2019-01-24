<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dto.GameDto, dto.PlayerDto, utils.WordCodeDecode" %>
<%@include file="includes/header.jsp" %>

<script>
<% 
  PlayerDto player = request.getAttribute("player")!=null?(PlayerDto)request.getAttribute("player"):null;
  GameDto game = (request.getAttribute("game")!=null)?(GameDto)request.getAttribute("game"):new GameDto();
  boolean waitForWord = "WAIT_FOR_WORD".equals(game.getGameStatus());  
  boolean endGame = "END".equals(game.getGameStatus());
  boolean winGame = false;
  
  out.println("var waitForWord="+waitForWord); 
  String lettersUsed = "";
  String theWord = "";
  String opponentName = "";
  if (endGame) {
	  System.out.println("word.jsp: winner="+game.getWinner());
	  String winner = WordCodeDecode.decodeWordWithSpecsToPolishWord(game.getWinner()); 
	  winGame = player.getName().equals(winner);
  }  
  if (game!=null) {
	  theWord = WordCodeDecode.decodeWordWithSpecsToPolishWord((game.getTheWord()!=null)?game.getTheWord():"");
	  out.println("var theWord='"+theWord+"'");
	  lettersUsed = WordCodeDecode.decodeWordWithSpecsToPolishWord((game.getUsedLetters()!=null)?game.getUsedLetters():"");
	  out.println("var lettersUsed='"+lettersUsed+"'");
	  opponentName = WordCodeDecode.decodeWordWithSpecsToPolishWord(game.getPlayerGuessName());
  }
%>
</script>
<input type="hidden" id="username" name="username" value="<%=player.getName()%>" />
<input type="hidden" id="playerId" name="playerId" value="<%=player.getPlayerId() %>" />

<div id="userdata">
    <div class="user_name">Ja (<%= player.getName() %>)</div>
	<div class="user_points">
		<span class="label">points:</span> <span class="value"><%=  player.getPoints() %></span>
	</div>
	<div class="user_wins">
		<span class="label">wins:</span> <span class="value"><%=  player.getCountWins() %></span>
	</div>
	<div class="user_losts">
		<span class="label">losts:</span> <span class="value"><%=  player.getCountLosts() %></span>
	</div>
	<div class="user_opponent">
 	  <span class="label">vs: </span><span class="opponent_name"><%= opponentName %></span>
	</div>
</div>

<div id="pict_hangman"><img src="img/funny.jpg" height="300px" /></div>
<br /><br />
<% if (waitForWord) { %>
<div id="enter_word">Enter your word: <input name="word" id="word_input" placeholder="enter a word" style="text-transform:uppercase" value="" />&nbsp;&nbsp;<button id="btnEnterWord" onClick="wordEntered()" type="button" form="main_form" >Send</button></div>
<% } %>


<div id="word">
  <div class="word_label">Your word:</div>
  <div id="word_lettered"> </div>
</div>

<div id="used_letters">
  <div class="word_label">Letters used by opponent:</div>
  <div id="letters"> </div>
</div>

<div id="winBox"> You won. Congratulations! </div>
<div id="lostBox"> You lost!</div>
<div id="button_end_game" style="text-align: center; margin-top: 30px;"><!--  button type="button" onClick="guess_end_game()">END GAME</button--> </div>

<!-- div id="dialog-confirm" title="End of the game?">
  <p><span class="ui-icon ui-icon-alert" style="float:left; margin:12px 12px 20px 0;"></span>Are you sure?</p>
</div -->

<script>
//$("#dialog-confirm").hide();
$("#winBox").hide();
$("#lostBox").hide();
//$("#opponent_end_game").hide();

if (waitForWord) {
  $("#word").hide();
}
</script>

<script src="js/word.js"></script>
<%@include file="includes/footer.jsp" %>