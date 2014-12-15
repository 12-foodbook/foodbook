<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pageTitle" value="Recipes / Index" scope="application" />

<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class="container">
	<div class="page-header">
		<h1>รายการตำรับอาหาร</h1>
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
				<a href="/recipes/show?id=${recipe.recipe_id}">
					<div class="panel panel-default">
						<div class="panel-body">
							<div class="row">
								<div class="col-xs-12 col-sm-6 col-md-4">
									<img src="${recipe.photo_url}" width="100%">
								</div>
								<div class="col-xs-12 col-sm-6 col-md-8">
									<h2>${recipe.name}</h2>
									${recipe.averageRate}
								</div>
							</div>
						</div>
					</div>
				</a>
				<%-- <a href="/recipes/show?id=${recipe.recipe_id}" class="media-left media-top col-md-3">
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
						</h3>
					</form>
					<form class="col-md-4" method="post" accept-charset="UTF-8"
						action="/favorites/create">
						<input type="hidden" name="recipe_id" value="${recipe.recipe_id}">
					</form>
				</div> --%>
			</c:forEach>
			<h3>ขาดบางอย่าง</h3>
			<hr>
			<c:if test="${fn:length(recipesPartial) != 0}">
			<c:forEach var="i" begin="0" end="${fn:length(recipesPartial) - 1}">
				<div class="panel panel-default">
					<a href="/recipes/show?id=${recipesPartial[i].recipe_id}">
						<div class="panel-body">
							<div class="row">
								<div class="col-xs-12 col-sm-6 col-md-4">
									<img src="${recipesPartial[i].photo_url}" width="100%">
								</div>
								<div class="col-xs-12 col-sm-6 col-md-8">
									<h2>${recipesPartial[i].name}</h2>
									${recipesPartial[i].averageRate}
								</div>
							</div>
						</div>
					</a>
				</div>
				<%-- <a href="/recipes/show?id=${recipesPartial[i].recipe_id}" class="media-left media-top col-md-3">
					<img src="${recipesPartial[i].photo_url}" style="width:80%;height:80%" alt="image">
				</a>

				<div class="media-body col-md-10">
					<h4 class="media-heading col-md-6">
						<a href="/recipes/show?id=${recipesPartial[i].recipe_id}" class="media-left media-top">
							<h2>${recipesPartial[i].name}</h2>
						</a>
					</h4>
					<form action="/rates" accept-charset="UTF-8" method="post">
						<input type="hidden" value="${recipesPartial[i].recipe_id}" name="recipe_id">
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
							<span onclick="sentrate('${recipesPartial[i].recipe_id}','1')">&#x2605;</span>
							<span onclick="sentrate('${recipesPartial[i].recipe_id}','2')">&#x2605;</span>
							<span onclick="sentrate('${recipesPartial[i].recipe_id}','3')">&#x2605;</span>
							<span onclick="sentrate('${recipesPartial[i].recipe_id}','4')">&#x2605;</span>
							<span onclick="sentrate('${recipesPartial[i].recipe_id}','5')">&#x2605;</span>
							${rate}
						</h3>
					</form>
					<form class="col-md-4" method="post" accept-charset="UTF-8"
						action="/favorites/create">
						<input type="hidden" name="recipe_id" value="${recipesPartial[i].recipe_id}">
					</form>
					<h3>ขาด</h3>
					<c:forEach var="ingredientPartial" items="${ingredientsPartial[i]}">
						${ingredientPartial.name}<br>
					</c:forEach>
				</div> --%>
			</c:forEach>
			</c:if>
		</div>
	</div>
</div>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />