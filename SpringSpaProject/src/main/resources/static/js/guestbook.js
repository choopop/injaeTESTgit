/**
 * 
 */

 (gb = function(){
 

	 
	 $('.btnSearch').on('click',function(){
		let frm = $('.frm_gb_search')[0];
		frm.nowPage.value = 1;
		let param = $(frm).serialize();
		$('#section').load('/guestbook/guestbook_select',param);
	 });
	 
	 
	 move = function(nowPage){
		    let frm = $('.frm_gb_search')[0];
		    frm.nowPage.value = nowPage;
		    let param = $(frm).serialize();
		    $.post('/guestbook/guestbook_select', param, function(data){
		      $('#section').html(data);
		    })
		}
		
	/*방명록 추가--------------------------------------*/	
	$('.btnGuestbookSave').on('click',function(){
		let frm = $('.frm_guestbook_insert')[0];
		let param = $(frm).serialize();
		$.post('/guestbook/guestbook_insert', param,function(msg){
			if(msg !='') alert(msg);
		
			frm = $('.frm_gb_search')[0];
			param = $(frm).serialize();
			$('#section').load('/guestbook/guestbook_select',param);
		
		})
	});	
	
	
	var savedoc="";
	//guestbook_select의 수정버튼을누르면 css로 가려진 updateZone 암호부분이 보여짐
	gb.modifyView = function(frm){
		if(savedoc==""){
			savedoc=frm.doc.value;
			let div = frm.querySelector('.updateZone');		
			div.style.visibility='visible';
			frm.doc.readOnly=false;
		}else{alert("수정은 한번에 1건씩만 가능합니다.");}
	}
	
	
	gb.modifyCancel = function(frm){
		frm.doc.value = savedoc;
		let div = frm.querySelector('.updateZone');
		div.style.visibility='hidden';
		savedoc="";
		frm.doc.readOnly=true;
	}	
	/*modal box------------------------------------*/
	
	$('#btnClose').on('click',function(){
		$('#modal').css('display','none');
	});
	
	
	var delForm;	//가급적 변수선언은 맨위꼭대기에다가 하는게 좋다.
	
	gb.modalView=function(frm){
		delForm = frm;
		$('#modal').css('display','block');
	}
	
	
	/*방명록 삭제--------------------------------------*/
	
	$('#btnCheck').on('click',function(){
		delForm.pwd.value = $('#pwd').val();
		
		let frm = delForm;
		let param = $(frm).serialize();
		$.post('/guestbook/guestbook_delete', param,function(msg){
			if(msg !='') alert(msg);
			
			frm = $('.frm_gb_search')[0];
			param = $(frm).serialize();
			$('#section').load('/guestbook/guestbook_select',param);
		})
	});
	
	/*방명록 수정--------------------------------------*/
	
	gb.update = function(frm){
		let param = $(frm).serialize();
		
		$.post('/guestbook/guestbook_update', param,function(msg){
			if(msg !='') alert(msg);
			
			frm = $('.frm_gb_search')[0];
			param = $(frm).serialize();
			$('#section').load('/guestbook/guestbook_select',param);
		})
	};
	
	
	
	
	
	
	
	
})();