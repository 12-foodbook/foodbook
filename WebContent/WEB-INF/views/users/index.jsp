<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Categories / Index" scope="application" />
<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class='container'>
	<div class="row">
	<div class="page-header">
		<h1>ผู้ใช้</h1>
	</div>
		<div class="col-md-10 col-md-offset-1"><!-- set table -->
	
		<form action='/users/delete' method="post">
			<table id="cateTable" class="table table-striped"><!-- recipe category -->
				<thead><td align="center">#</td><td align="center">ผู้ใช้</td><td align="center">ลบผู้ใช้</td><td></td></thead>
				<c:forEach var="i" begin="0" end="${fn:length(users)-1}">
					
					<tr>
						<td align="center" >${i+1}</td>
						<td align="center" id='cateValue_${users[i].username}' value="${users[i].username}">${users[i].username}</td>
						<td align="center"><input id='user_id' name="user_id" type="checkbox" value="${users[i].user_id}" /></td>
						<td></td>
					</tr>	
				</c:forEach>
			</table>
			
			
			
			<!-- button delete -->
			<button type="button" class="btn btn-danger col-md-2" data-toggle="modal" data-target="#myModal">ลบผู้ใช้</button>
			<!-- Modal -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">ลบผู้ใช้</h4>
							</div>
							<div class="modal-body">ต้องการลบผู้ใช้หรือไม่?</div>
							<div class="modal-footer">
							<button type="button" class="btn btn-default"data-dismiss="modal">ยกเลิก</button>
							<input onclick='sendValue()' type="submit" class="btn btn-danger" value='ลบ'>
						</div>
					</div>
				</div>
			</div>
		</form>
		
		<!-- button add -->
		
			
			
		</div>
	</div>
</div>	
<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />