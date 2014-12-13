<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="Recipes / Index" scope="application" />

<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class="container">
	<div class="page-header">
		<h1><span class="glyphicon glyphicon-heart" aria-hidden="true"></span> รายการตำรับอาหาร</h1>
	</div>
	<div class="row">
		<div class="col-xs-12 col-md-4">
			<form>
				<label>หมวดหมู่ตำรับอาหาร</label>
				<c:forEach var="recipeCategory" items="${recipeCategories}">
					<div class="checkbox">
						<label> <input name="recipe_category_id" type="checkbox"
							value="${recipeCategory.recipe_category_id}">
							${recipeCategory.name}
						</label>
					</div>
				</c:forEach>
			</form>
		</div>

		<div class="list-group media col-xs-12 col-md-8">
			<c:forEach var="recipe" items="${recipes}">
				<a href="/recipes/show?id=${recipe.recipe_id}" class="media-left media-top col-md-3">
					<img src="${recipe.photo_url}" style="width:80%;height:80%" alt="image">
				</a>

				<div class="media-body col-md-10">
					<h4 class="media-heading col-md-6">
						<a href="/recipes/show?id=${recipe.recipe_id}" class="media-left media-top">
							<h2>${recipe.name}</h2>
						</a>
					</h4>
					<form action="/rates" accept-charset="UTF-8" method="post">
						<input type="hidden" value="${recipe.recipe_id}" name="recipe_id">
						<h3>
							<script>
								function sentrate(recipe_id, rate) {
									$.post('/rates', {
										'recipe_id' : recipe_id,
										'rate' : rate
									}, function(data) {
										console.log(data);
									});
								}
							</script>
							<span onclick="sentrate('${recipe.recipe_id}','1')">&#x2605;</span>
							<span onclick="sentrate('${recipe.recipe_id}','2')">&#x2605;</span>
							<span onclick="sentrate('${recipe.recipe_id}','3')">&#x2605;</span>
							<span onclick="sentrate('${recipe.recipe_id}','4')">&#x2605;</span>
							<span onclick="sentrate('${recipe.recipe_id}','5')">&#x2605;</span>
							${rate}
						</form>
						<form action="/favorites/delete" method="post"class='col-md-2' style='margin-left:2%'>
							<!-- button delete -->
							<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-minus-sign" aria-hidden="true"></span> ลบรายการโปรด</button>
							<input name="recipe_id" value="${recipe.recipe_id}" type="hidden">
							<!-- Modal -->
						<div class="modal fade" id="myModal" tabindex="-1" role="dialog"aria-labelledby="myModalLabel" aria-hidden="true">
							<div class="modal-dialog">
							<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">ลบรายการโปรด</h4>
							</div>
							<div class="modal-body">ต้องการลบรายการโปรดหรือไม่?</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"data-dismiss="modal">ยกเลิก</button>
								<input type="submit" class="btn btn-danger" value='ลบ'>
							</div>
							</div>
							</div>
						</div>
						</form>
							
						</h3>
						
					<form class="col-md-4" method="post" accept-charset="UTF-8"
						action="/favorites/create">
						<input type="hidden" name="recipe_id" value="${recipe.recipe_id}">
					</form>
				</div>
			</c:forEach>
		</div>
	</div>
</div>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />