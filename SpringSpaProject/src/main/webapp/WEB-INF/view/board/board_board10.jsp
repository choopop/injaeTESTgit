<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board_board10.jsp</title>
</head>
<body>
게시판 최신 10개<hr/>
<c:forEach var='vo' items="${list}">
	<b>${vo.id}</b>
	<span>${vo.subject}</span> 
	<br/>
</c:forEach>
<script>

</script>
</body>
</html>