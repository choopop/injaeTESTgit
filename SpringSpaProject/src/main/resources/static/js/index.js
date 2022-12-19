/**
 * 
 */
 /* 테스트로 만들어본 셀렉
 $('.btnBoard').on('click',function(){
  console.log("연결");
 	$('#section').load('/board/board_select');
 });
 */
 
 
 $('.btnBoard').on('click',function(){
  console.log("연결");
 	$('#section').load('/board/board_select2');
 });
 
 $('.btnGuestBook').on('click',function(){
 console.log("연결");
 $('#section').load('/guestbook/guestbook_select'); //매핑정보를전달하는것일뿐 경로가아님 *서블릿 URL패턴과 같은 역할 ; GuestbookController에 있는 @RequestMapping("/guestbook/guestbook_select)을 호출
 }); 
 
 /*방명록 최근 10개*/
 $('#sectionright').load('/guestbook/guestbook10');
 
 /*게시물 최근 10개*/
 $('#sectionleft').load('/board/board10');