<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<body>
<table border="">
	<tr>
		<td colspan="2">
			<jsp:include page="inc/top.jsp"/>
		</td>
	</tr>
	<tr>
		<td><jsp:include page="inc/menu.jsp"/></td>
		<td><jsp:include page="bb/${main }"/></td>
	</tr>
	<tr>
		<td colspan="2">
			<jsp:include page="inc/bottom.jsp"/>
		</td>
	</tr>
</table>