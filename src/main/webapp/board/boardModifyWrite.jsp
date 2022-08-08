<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 등록 화면</title>

<script src="/egovsample/script/jquery-1.12.4.js"></script>
<script src="/egovsample/script/jquery-ui.js"></script>


</head>
<style>
body{
	font-size:9pt;
}
button {
	font-size:9pt;
}
table {
	width:600px;
	border-collapse:collapse;
	
}
th,td {
	border:1px solid #cccccc;
	padding:3px;
}
.input1 {
	width:98%;
}
.textarea {
	width:98%;
	height:70px;
}
</style>

<script>
$(function(){
	
//	$("#title").val( "제목입력" );
	
});

function fn_submit () {
	// ajax jquery coding
	// trim() -> 앞뒤 공백 제거, java, jsp
	// document.frm.title.value == ""
	// $("#title").val() == ""
	// $.trim("  aabbcc  ") ==> $.trim($("#title").val()) == ""
	
	if($.trim($("#title").val()) == "" ) {
		alert("제목을 입력해주세요!");
		// document.frm.title.focus();
		$("#title").focus();
		return false;
	}
	
	// 앞,뒤 공백 제거
	$("#title").val( $.trim($("#title").val()) );

	if($.trim($("#pass").val()) == "" ) {
		alert("암호를 입력해주세요!");
		$("#pass").focus();
		return false;
	}
	
	$("#pass").val( $.trim($("#pass").val()) );

	var formData = $("#frm").serialize();
	
	// ajax:비동기 전송 방식(깜빡 거림이 없음)의 기능을 가지고 있는 jquery의 함수
	$.ajax({
		/* 전송 전 세팅 */
		type:"POST",
		data:formData,
		url:"boardModifySave.do",
		dataType:"text",  // 리턴 타입

		/* 전송 후 세팅 */
		success: function(result) { // controller -> 1
			if(result == "1"){
				alert("저장완료");
				location="boardList.do";
			} else if(result == "-1"){
				alert("암호가 일치하지 않습니다.");
			} else {
				alert("저장실패\n관리자에게 연락해 주세요.");
			}
		},
		error: function(){ // 장애 발생
			alert("오류발생");
		}
	});
}

</script>
<body>
<form id="frm">

<input type="hidden" name="unq" value="${boardVO.unq }" >

<table>
	<caption>게시판 수정 화면</caption>
	<tr>
		<th width="20%"><label for="title">제목</label></th>
		<td width="80%"><input type="text" name="title" id="title" class="input1" value="${boardVO.title }"></td>
		
	</tr>
	<tr>
		<th><label for="pass">암호</label></th>
		<td><input type="password" name="pass" id="pass"></td>
	</tr>
	<tr>
		<th>글쓴이</th>
		<td><input type="text" name="name" id="name" value="${boardVO.name }"></td>
	</tr>
	<tr>
		<th>내용</th>
		<td><textarea name="content" id="content" class="textarea">${boardVO.content }</textarea></td>
	</tr>
	<tr>
		<th colspan="2">
		<button type="submit" onclick="fn_submit();return false;">저장</button>
		<button type="reset">취소</button>
		</th>
	</tr>
</table>
</form>
</body>
</html>