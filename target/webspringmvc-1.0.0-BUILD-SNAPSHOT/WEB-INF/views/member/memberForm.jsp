<%@page import="kr.or.nextit.member.service.impl.MemberServiceImpl"%>
<%@page import="kr.or.nextit.member.service.MemberService"%>
<%@page import="kr.or.nextit.member.model.Member"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css">

<title>Insert title here</title>
<c:if test="${isError}">
<script type="text/javascript">
	alert("${message}");
	history.pushState(null, "", "{pageContext.request.contextPath}/member/memberForm?type=${param.type}")
</script>
</c:if>

<script type="text/javascript">


/*
 * 회원 등록 함수
 */
function fn_save(type){
	
	if(!validate()){
		return;
	}
	
	var frm = document.memberForm;
	
	<c:if test="${param.type == 'I'}">
	frm.action = "memberInsert";
	</c:if>
	
	<c:if test="${param.type != 'I'}">
	frm.action = "memberUpdate";
	</c:if>
	
	/* frm.action = "memberInsert.jsp";
	
	if(type == 'U'){		
		frm.action = "memberUpdate.jsp";
	} */
	
	frm.submit();	
}

function validate(){
	
	var frm = document.memberForm;
	
	if(frm.mem_id.value == ""){
		alert("아이디를 입력하세요.");
		frm.mem_id.focus();
		return false;
	}
	
	if(frm.mem_name.value == ""){
		alert("이름을 입력하세요.");
		frm.mem_name.focus();
		return false;
	}
	
	if(frm.mem_pwd.value == "" || frm.mem_pwd_confirm.value == ""){		
		alert("비밀번호를 입력하세요.");		
		return false;
	}else{
		if(frm.mem_pwd.value != frm.mem_pwd_confirm.value){
			alert("비밀번호가 일치하지 않습니다.");
			return false;
		}
	}
	
	if(frm.mem_birth.value == ""){
		alert("생년월일을 입력하세요.");
		frm.mem_birth.focus();
		return false;
	}
	if(frm.mem_phone.value == ""){
		alert("전화번호를 입력하세요.");
		frm.mem_phone.focus();
		return false;
	}
	if(frm.mem_email.value == ""){
		alert("이메일을 입력하세요.");
		frm.mem_email.focus();
		return false;
	}
	if(frm.mem_zipcode.value == ""){
		alert("우편번호를 입력하세요.");
		frm.mem_zipcode.focus();
		return false;
	}
	if(frm.mem_addr_master.value == ""){
		alert("기본주소를 입력하세요.");
		frm.mem_addr_master.focus();
		return false;
	}
	if(frm.mem_addr_detail.value == ""){
		alert("상세주소를 입력하세요.");
		frm.mem_addr_detail.focus();
		return false;
	}
	
	return true;	
}

/*
 * 회원 목록 이동 함수
 */
function fn_list(){
	location.href="memberList";
}

</script>

</head>
<body>

<div class="container">

	<c:if test="${param.type != 'U'}">
		<h2>회원가입</h2>
	</c:if>
	
	<c:if test="${param.type == 'U'}">
		<h2>회원수정</h2>
	</c:if>
	
	<form name="memberForm" method="post">
		
		<table class="table table-bordered">
			<tr>
				<td>아이디</td>
				<td><input type="text" name="mem_id" size="20" value="${member.mem_id}"> 
				<button type="button" class="btn btn-primary">중복체크</button>
				8~20자 내의 영문,숫자 조합
				
				</td>
			</tr>
			<tr>
				<td>이름</td>
				<td><input type="text" name="mem_name" size="20" value="${member.mem_name}"> 실명을 입력하세요</td>
			</tr>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="mem_pwd" size="20"> 8~20자 내의 영문,숫자 조합</td>
			</tr>
			<tr>
				<td>비밀번호확인</td>
				<td><input type="password" name="mem_pwd_confirm" size="20"> 8~20자 내의 영문,숫자 조합</td>
			</tr>
			
			<tr>
				<td>생년월일</td>
				<td><input type="text" name="mem_birth" size="20" value="${member.mem_birth}"> 예) 19931231</td>
			</tr>
			<tr>
				<td>전화번호</td>
				<td><input type="text" name="mem_phone" size="20" value="${member.mem_phone}"> '-' 없이 입력</td>
			</tr>
			<tr>
				<td>이메일</td>
				<td><input type="text" name="mem_email" size="20" value="${member.mem_email}"></td>
			</tr>
			<tr>
				<td>주소</td>
				<td>
					<p><input type="text" name="mem_zipcode" size="6" value="${member.mem_zipcode}">
					<button type="button" class="btn btn-primary">우편번호검색</button>
					</p>
					<p><input type="text" name="mem_addr_master" size="50" value="${member.mem_addr_master}"></p>
					<p><input type="text" name="mem_addr_detail" size="50" value="${member.mem_addr_detail}"></p>				
				</td>
			</tr>
			
			<tr>
				<td colspan="2">
				
					<c:if test="${param.type != 'U'}">
						<input type="button" value="가입하기" class="btn btn-default" onclick="fn_save('I')">
					</c:if>
					
					<c:if test="${param.type == 'U'}">
						<input type="button" value="수정하기" class="btn btn-default" onclick="fn_save('U')">
					</c:if>
					
					<input type="reset" value="취소" class="btn btn-default">
					<input type="button" value="목록" class="btn btn-default" onclick="fn_list()">
				</td>
			</tr>
			
		
		</table>
		
	</form>

</div>

</body>
</html>








