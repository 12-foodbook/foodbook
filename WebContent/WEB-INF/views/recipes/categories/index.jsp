<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Categories / Index" scope="application" />
<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class='container'>
	<div class="row">
		<div class=".col-md-8.col-md-offset-2">
		<form action="/recipes/categories/delete" method="post">
			<table class="table table-striped"><!-- recipe category -->
				<thead><td align="center">#</td><td align="center">หมวดหมู่อาหาร</td><td align="center">ลบหมวดหมู่</td></thead>
				<c:forEach var="i" begin="0" end="${fn:length(recipeCategories)-1}">
					<tr>
						<td align="center" >${i+1}</td>
						<td align="center"> ${recipeCategories[i].name}</td>
						<td align="center"><input name="recipe_category_id" type="checkbox" value="${recipeCategories[i].recipe_category_id}" /></td>
					</tr>	
				</c:forEach>
			</table>
			<!-- button -->
			<a href="/recipes/categories/index" class="btn btn-default" >เพิ่มหมวดหมู่</a>
			<button type="button" class="btn btn-danger"
							data-toggle="modal" data-target="#myModal">ลบหมวดหมู่</button>

						<!-- Modal -->
						<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
							aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">
											<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
										</button>
										<h4 class="modal-title" id="myModalLabel">ลบหมวดหมู่</h4>
									</div>
									<div class="modal-body">ต้องการลบหมวดหมู่หรือไม่?</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">ยกเลิก</button>
										<input type="submit" class="btn btn-danger" value='ลบ'>
									</div>
								</div>
							</div>
						</div>
		</form>
		</div>
	</div>
</div>	
<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />