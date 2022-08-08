<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>    

<% pageContext.setAttribute("newline", "\n"); %>
<c:set var="content" value="${fn:replace(boardVO.content,newline,'<br>') }"/>

    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 상세 화면</title>

<script src="/myproject_new/script/jquery-1.12.4.js"></script>
<script src="/myproject_new/script/jquery-ui.js"></script>


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
	
	$("#title").val( "제목입력" );
	
});

function fn_submit () {
	// ajax jquery coding
	// trim() -> 앞뒤 공백 제거 , java , jsp
	if($.trim($("#title").val()) == "" ) {
		alert("제목을 입력해주세요!");
		$("#title").focus();
		return false;
	}
	$("#title").val( $.trim($("#title").val()) );

	if($.trim($("#pass").val()) == "" ) {
		alert("암호를 입력해주세요!");
		$("#pass").focus();
		return false;
	}
	$("#pass").val( $.trim($("#pass").val()) );

	var formData = $("#frm").serialize();
	
	// ajax:비동기 전송 방식의 기능을 가지고 있는 jquery의 함수
	$.ajax({
		type:"POST",
		data:formData,
		url:"boardWriteSave.do",
		dataType:"text",  // 리턴 타입
		success: function(data) { // controller -> "ok","fail"
			if(data == "ok"){
				alert("저장완료");
				location="boardList.do";
			} else {
				alert("저장실패");
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
<table>
	<caption>게시판 상세</caption>
	<tr>
		<th width="20%">제목</th>
		<td width="80%">${boardVO.title }</td>
	</tr>
	<tr>
		<th>글쓴이</th>
		<td>${boardVO.name }</td>
	</tr>
	<tr>
		<th>내용</th>
		<td height="50">
		<%-- ${content} --%>
		${boardVO.content }
		</td>
	</tr>
	<tr>
		<th>등록일</th>
		<td>${boardVO.rdate }</td>
	</tr>
	<tr>
		<th colspan="2">
		<button type="button" onclick="location='boardList.do'">목록</button>
		<button type="button" onclick="location='boardModifyWrite.do?unq=${boardVO.unq}'">수정</button>
		<button type="button" onclick="location='passWrite.do?unq=${boardVO.unq}'">삭제</button>
		</th>
	</tr>
</table>
</form>
</body>
</html>