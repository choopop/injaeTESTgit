<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>guestbook_insert.jsp</title>
</head>
<body>
<main>
<form name='frm_guestbook' class='frm_guestbook_insert' method='post'>
	<h4>새 게시물 작성</h4>
	<span>작성자(아이디)</span><input name='id' type="text">
	<br/>
	<span>작성일</span><input name='nal' id='nal' type="date">
	<br/>
	<textarea name='doc' rows="6" cols="50" ></textarea>
	
	<span>암호</span><input type='text' name='pwd' >
	<div>
		<input type='button' value='작성' class='btnGuestbookSave'>
		<input type='button' value='취소'>
	</div>
</form>
<hr/>
<hr/>
<hr/>
	
</main>
<script>
document.getElementById('nal').valueAsDate = new Date();
</script>

</body>
</html>