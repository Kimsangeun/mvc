<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script>
<c:if test="${msg !=null }">
	alert("${msg}");
</c:if>

function fileDelete(){
	if(confirm('파일을 삭제하시겠습니까?\n삭제된 파일은 복구할수 없습니다'))
	{
		var frm = document.frm;
		frm.action="FileDelete";
		frm.submit();
	}
	
}
</script>
<form name="frm" action="ModifyReg" method="post" enctype="multipart/form-data">
	<input type="hidden" name="id" value="${data.id }">
	<input type="hidden" name="page" value="${param.page }">
	<table border="">
		<tr>
			<td>제목</td>
			<td><input type="text" name="title" value="${data.title }"></td>
		</tr>
		<tr>
			<td>작성자</td>
			<td><input type="text" name="pname" value="${data.pname}"></td>
		</tr>
		<tr>
			<td>암호확인</td>
			<td><input type="text" name="pw"></td>
		</tr>

<c:choose>
<c:when test="${data.seq==0 }">
		<tr>
			<td>파일</td>
			<td>
				<c:choose>
					<c:when test="${data.upfile !='' }">
						${data.upfile }
						<input type="hidden" name="upfile" value="${data.upfile }">
						<input type="button" value="파일삭제" onclick="fileDelete()" >
					</c:when>
					<c:otherwise>
						<input type="file" name="upfile">
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
</c:when>
<c:otherwise>
	<input type="hidden" name="upfile"  value="">
</c:otherwise>		
</c:choose>		
		<tr>
			<td>내용</td>
			<td><textarea name="content" cols="30" rows="5" >${data.content}</textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="수정하기">
				<a href="ModifyForm?id=${data.id }&page=${param.page }">초기화</a>
				<!-- <input type="reset" value="초기화"> -->
				<a href="Detail?id=${data.id }&page=${param.page }">뒤로</a>
				<a href="List">목록으로</a>
			</td>
		</tr>
	</table>
</form>
