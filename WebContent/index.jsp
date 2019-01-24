<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<%@include file="includes/header.jsp" %>
<div id="start">
  <div class="label">Enter your name:</div>
  <div class="input_name"><input type="text" id="username" name="username" size=20 placeholder="Your name" value=""  /></div>
  <div class="button"><button type="button" form="main_form" onClick="buttonStartClicked();" id="btn_start">Press to START</button></div>  
  <div class="message" id="message"></div>  
</div>

<input type="hidden" name="errormsg_jsp" value="<%= request.getAttribute("errormsg") %>">

<script src="js/index.js"></script>
<%@include file="includes/footer.jsp" %>