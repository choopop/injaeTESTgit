<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel='stylesheet' href='css/guestbook.css'>
<title>guestbook/guestbook_select.jsp</title>
<script defer src="js/guestbook.js"></script>
</head>
<body>
<%@include file="guestbook_insert.jsp" %>


<form class="frm_gb_search" method='post'>
	<input type ='text' name='serial' value='${gVo.sno}'>
	<input type ='text' name='nowPage' value='${gVo.nowPage}'>
	<input type ='text' name='findStr' value='${gVo.findStr}'>
	<input type='button' class='btnSearch' value = '검색'>
</form>

<br/>
<div id='guestsbook_list'>
	<div class='guestbook_items'>
		<c:forEach var='i'  items='${list}'>
			<div class='item'>
				<form name='frm_guestbook' class='frm_guestbook' method='post'>
					<div class='btnzone'>
						<input type='button' class='btnUpdate' value='수정' onclick='gb.modifyView(this.form)'/>
						<input type='button' class='btnDelete' value='X' onclick='gb.modalView(this.form)'/>
					</div>
					<br/>
					<label>작성자</label>
					<input type = 'text' name='id' value='${i.id }'>
					<label>작성일</label>
					<output>${i.nal }</output><br/>
					
					<textarea rows="6" cols="50" name='doc' readonly>${i.doc}</textarea>
					<br/>
					<div class = 'updateZone'>
						<label>암호</label>
						<input type='password' name='pwd' value='${i.pwd }'>
						<input type='button' value='수정' class='btnGuestbookUpdate' onclick='gb.update(this.form)'/>
						<input type='button' value='취소' class='btnGuestbookUpdate' onclick='gb.modifyCancel(this.form)'/>
					</div>
					
					<input type='hidden' name='sno' value='${i.sno}'>
				
				</form>
			</div>
		</c:forEach>
	</div>
	
	<!-- <div class='guestbook_btn'>
		<input type='button' value='&lt' class='btnPrev'>&lt -> 왼쪽괄호 
		<input type='button' value='&gt' class='btnNext'>&gt -> 오른쪽괄호 
	</div> -->
	<div class='btnZone'>
      <c:if test="${gVo.startPage>1}">
         <input type='button' value='맨첨' class='btnFirst' onclick ='move(1)' />
         <input type='button' value='이전' class='btnPrev'  onclick ='move(${gVo.startPage-1})'/>
      </c:if>
      
      <c:forEach var='i' begin='${gVo.startPage}' end='${gVo.endPage}'>
         <input type='button' value='${i}' class='btnMove' onclick ='move(${i})'/>
      </c:forEach>
   
      <c:if test="${gVo.totPage>gVo.endPage }">
         <input type='button' value='다음' class='btnNext' onclick ='move(${gVo.endPage+1})'/>
         <input type='button' value='맨끝' class='btnLast' onclick ='move(${gVo.totPage})'/>
      </c:if>
         
   </div>

	<!-- 투명 div -> modal -->
	<div id='modal'>
		<div id='content'>
			<span>암호를 입력하세요</span>
			<input type='button' id='btnClose' value='X'/>
			<input type='password' id='pwd'/>
			<input type='button' value='확인' id='btnCheck'>
		</div>
	</div>
	
</div>



</body>
</html>