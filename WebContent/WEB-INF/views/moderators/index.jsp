<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Categories / Index" scope="application" />
<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class='container'>
	<div class="row">
	<div class="page-header">
		<h1>ผู้ดูแล</h1>
	</div>
		<div class="col-md-10 col-md-offset-1"><!-- set table -->
	
		<form action='/moderators/delete' method="post">
			<table id="cateTable" class="table table-striped"><!-- recipe category -->
				<thead><td align="center">#</td><td align="center">ผู้ดูแล</td><td align="center">ลบดูแล</td><td></td></thead>
				<c:forEach var="i" begin="0" end="${fn:length(moderators)-1}">
					
					<tr>
						<td align="center" >${i+1}</td>
						<td align="center" id='cateValue_${moderators[i].username}' value="${moderators[i].username}">${moderators[i].username}</td>
						<td align="center"><input id='moderator_id' name="moderator_id" type="checkbox" value="${moderators[i].moderator_id}" /></td>
						<td></td>
					</tr>	
				</c:forEach>
			</table>
			
			
			
			<!-- button delete -->
			<button type="button" class="btn btn-danger col-md-2" data-toggle="modal" data-target="#myModal">ลบผู้ดูแล</button>
			<!-- Modal -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">ลบดูแล</h4>
							</div>
							<div class="modal-body">ต้องการลบดูแลหรือไม่?</div>
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