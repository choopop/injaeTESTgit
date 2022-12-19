<%@page import="kr.jobtc.board.BoardVo"%>
<%@page import="java.util.List"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix='c' uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel='stylesheet' href='css/index.css' >
<link rel='icon' href='images/icon2.png'>
<script src="https://code.jquery.com/jquery-3.6.1.min.js"></script>
<script defer src='js/index.js'></script>
</head>
<body>
<main>
<div id='index'> 
	<header>
		<div class='main_title'>
			<img src ='images/icon2.png'>
		</div>
		
		<nav>
			<a href="/">HOME</a>
			<a href='#' class='btnGuestBook'>방명록</a>
			<a href='#' class='btnBoard'>게시판</a>
		</nav>
	</header>
	<div id ='sectionlayout'>
		<div id='section'>
			<div id='sectionright' class='guestbook'>
			
			</div>
			<div id='sectionleft'  class='board'>
			</div>
		</div>
	</div>
</div>
</main>
</body>
</html>