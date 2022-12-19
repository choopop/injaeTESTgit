<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%> 
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>guestbook10</title>
</head>
<body>
<label>최신방명록10</label>
<hr/>
<c:forEach var='i'  items='${list}'>
${i.nal} ${i.id} 님의 게시글 : 
	<c:choose>
	        <c:when test="${fn:length(i.doc) gt 9}">
		        <c:out value="${fn:substring(i.doc, 0, 9)}...">
		        </c:out>
	        </c:when>
	        <c:otherwise>
	      		<c:out value="${i.doc}">
	        	</c:out>
	        </c:otherwise>
	</c:choose>
<br/>	
</c:forEach>
</body>
</html>