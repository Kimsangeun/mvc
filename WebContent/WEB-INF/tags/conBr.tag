<%@ tag language="java" body-content="scriptless" pageEncoding="UTF-8"%>
<jsp:doBody var="con" scope="page"/>
<%

	String con = (String)jspContext.getAttribute("con");
//jspContext : pageContext가 제공하는 setAttribute(), getAttribute()메서드를 그대로 제공하며,각 속성과 관련된 작업을 처리한다.
//con변수에 저장한 tag안의 내용을 jspContext.getAttribute()메소드를 통해 불러와서 con변수에 대입했다.
	String res = con.trim().replaceAll("\n", "<br>");
//처음과 끝의 공백제거 후 엔터를 br로 바꾼다.
%>
<%=res %>
