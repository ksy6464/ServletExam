<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>신규회원 등록</title>
</head>
<body>
	<!-- <form action="/ServletExam/member/insert.do" method="post"> -->
	<form action="<%=request.getContextPath() %>/member/insert.do" method="post" enctype="multipart/form-data">
	<!-- 하드코딩이니깐 바꿔주자 -->
		<table>
			<tr>
				<td>I D:</td>
				<td><input type="text" name="memId" value=""></td>
			</tr>
			<tr>
				<td>이름:</td>
				<td><input type="text" name="memName" value=""></td>
			</tr>
			<tr>
				<td>전화번호:</td>
				<td><input type="text" name="memTel" value=""></td>
			</tr>
			<tr>
				<td>주소:</td>
				<td><textarea name="memAddr" ></textarea></td>
			</tr>
			<tr>
				<td>첨부파일</td>
				<td> <input type="file" name="atchFile" multiple="multiple"> </td>
				
			</tr>
		</table>
		<input type="submit" value="회원 등록">
	</form>
</body>
</html>