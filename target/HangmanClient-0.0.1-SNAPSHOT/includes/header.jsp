<%@ page language="java" contentType="text/html; UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hangman (c) Piotr & Norbert</title>
<link rel="stylesheet" type="text/css" href="css/main.css" />
<link rel="stylesheet" type="text/css" href="css/index.css" />
<link rel="stylesheet" type="text/css" href="css/guess.css" />
<link rel="stylesheet" type="text/css" href="css/word.css" />
<link rel="stylesheet" type="text/css" href="css/list.css" />
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<!-- link rel="stylesheet" href="/resources/demos/style.css"-->
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script src="js/utils.js"></script>
<script src="js/main.js"></script>
</head>
<body>
	<div id="content">
		<div id="title">Hangman</div>
		<form name="main_form" id="main_form" method="POST" action="/HangmanClient/pageDispach">
		  <input type="hidden" name="operation" id="operation" value="oper1" />   	      
          <input type="hidden" name="data" id="data" value="data1" />		
          <input type="hidden" id="currentPage" name="currentPage" value="" />
 
  


		  