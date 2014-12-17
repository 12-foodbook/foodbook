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

			<c:if test="${fn:length(recipes) != 0}">
				<c:forEach var="i" begin="0" end="${fn:length(recipes) - 1}">
					<a href="/recipes/show?id=${recipes[i].recipe_id}"
						class="recipe-panel">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row">
									<div class="col-xs-12 col-sm-6 col-md-4">
										<img src="${recipes[i].photo_url}" width="100%">
									</div>
									<div class="col-xs-12 col-sm-6 col-md-8">
										<h2>${recipes[i].name}</h2>
										โดย ${recipeUsers[i].username}<br>
										${recipes[i].averageRate}<br> ${recipes[i].description}
										<c:forEach begin="1" end="${recipes[i].averageRate}"
											var='star'>
											<span onclick="sentrate('${recipe.recipe_id}','${star}')"
												class='glyphicon glyphicon-star' style='color: gold;font-size:x-large;'></span>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</a>
					
				</c:forEach>
			</c:if>
			<h3>ตำรับอาหารที่ขาดวัตถุดิบบางอย่าง</h3>
			<hr>
			<c:if test="${fn:length(recipesPartial) != 0}">
				<c:forEach var="i" begin="0" end="${fn:length(recipesPartial) - 1}">
					<c:if
						test="${fn:length(ingredientsPartial[i]) <= 5}">
						<div class="panel panel-default">
							<a href="/recipes/show?id=${recipesPartial[i].recipe_id}"
								class="recipe-panel">
								<div class="panel-body">
									<div class="row">
										<div class="col-xs-12 col-sm-6 col-md-4">
											<img src="${recipesPartial[i].photo_url}" width="100%">
										</div>
										<div class="col-xs-12 col-sm-6 col-md-8">
											<h2>${recipesPartial[i].name}</h2>
											โดย ${recipesPartialUsers[i].username}<br>
											${recipesPartial[i].averageRate}<br>
											<c:forEach begin="1" end="${recipes[i].averageRate}"
											var='star'>
											<span onclick="sentrate('${recipe.recipe_id}','${star}')"
												class='glyphicon glyphicon-star' style='color: gold;font-size:x-large;'></span>
										</c:forEach><br>
										 <b>วัตถุดิบที่ขาด:</b>
											<c:forEach var="ingredientPartial"
												items="${ingredientsPartial[i]}">
							${ingredientPartial.name} 
						</c:forEach>
										</div>
									</div>
								</div>
							</a>
						</div>
					</c:if>
				</c:forEach>
			</c:if>
		</div>
	</div>
</div>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />