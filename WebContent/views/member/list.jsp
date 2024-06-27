<%-- 

1. 디렉티트(Directive)에 대하여...
 
JSP페이지에 대한 설정정보를 지정할때 사용된다. (page, taglib, include 등)

'<%@'로 시작하고 그 뒤에 디렉티브 이름이 오고 필요에 따라 속성명이 올 수 있으며, 마지막 '%>'로 끝난다

 ex) <%@ 디렉티브이름 속성명="속성값" ... %> 
 
2.스크립트 요소에 대하여...

 JSP에서 문서의 내용을 동적으로 생성하기 위해 사용된다.
 
 - 표현식(Expression): 값을 출력결과에 포함시키고자 할 때 사용된다. ex) <%=값%>
 - 스크립트릿(Scriptlet): 자바코드를 작성할 때 사용한다. ex) <% 자바코드들...%>
 - 선언부(Declaration): 스크립트릿이나 표션식에서 사용할 수 있는 메서드를 작성할 때 사용한다. ex) <%! ~~~ %>
  
3. JSP 기본객체왕 영역(SCOPE)

 - PAGE 영역 : 하나의 JSP페이지를 처리할때 사용되는 영역 => pageContext
 - REQUEST 영역 : 하나의 HTTP요청을 처리할때 사용되는 영역 => request
 - SESSION 영역 : 하나의 HTTP요청을 처리할때 사용되는 영역 => session
 - APPLICATION 영역 : 하나의 HTTP요청을 처리할때 사용되는 영역 => application

--%>


<%@page import="org.apache.catalina.tribes.membership.McastService"%>
<%@page import="kr.or.ddit.member.vo.MemberVO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- 스크립트릿영역으로 자바코드 작성가능하다 -->
<%
	List<MemberVO> memList = (List<MemberVO>) request.getAttribute("memList");
	/* String msg =(String)request.getAttribute("msg") ==null ? "" : 
		(String)request.getAttribute("msg");
	*/
	String msg =session.getAttribute("msg") ==null ? "" :
		(String)session.getAttribute("msg");
	//request에 저장한게 리다이렉트로 두번 요청하므로 안나오니깐 session에 저장해줬다
	
	session.removeAttribute("msg"); //한번 사용한 데이터 삭제하기...
	

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원목록</title>
<style>
table{
  border: 5px solid pink;
  text-align: center;
  width: 80%;
  table-layout: fixed;
}
</style>
</head>
<body>
	<table border="1">
		<tr>
			<th>ID</th>
			<th>이름</th>
			<th>전화번호</th>
			<th>주소</th>
		</tr>
		
<%
	int memSize = memList.size();
	if(memSize > 0) {
		for(MemberVO mv : memList) {
			
		
%>
		<tr>
			<%-- <td><% out.print(mv.getMemId()); %></td> --%>
			<td><%=mv.getMemId() %></td>
			<td> <a href="<%=request.getContextPath() %>/member/detail.do?memId=<%=mv.getMemId() %>"><%=mv.getMemName() %></a></td>
			<td><%=mv.getMemTel() %></td>
			<td><%=mv.getMemAddr() %></td>
		</tr>
<%
		}  // for문
	}else{
%>
		<tr>
			<td colspan="4">회원정보가 존재하지 않습니다.</td>
		</tr>
<%
	} //if문
%>
		
	<tr align="center">
		<td colspan="4"><a href="<%=request.getContextPath() %>/member/insert.do">[회원 등록]</a></td>
		<!-- 브라우저 관점에서 하는거라서 servletExam이 앞에 들어가야한다 -->
	</tr>

	</table>
<%
	if(msg.equals("SUCCESS")){
%>
<script>
	alert("정상적으로 처리되었습니다.");
</script>
<%		
	}

%>

</body>
</html>