<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pageTitle" value="Recipes / Index" scope="application" />

<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class="container">
	<div class="row">
		<div class="col-xs-12 col-md-3">
			<form id="fix">
				<h4>
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					หมวดหมู่ตำรับอาหาร
				</h4>
				<c:forEach var="recipeCategory" items="${recipeCategories}">
					<div class="checkbox">
						<label> <input name="recipe_category_id" type="checkbox"
							value="${recipeCategory.recipe_category_id}"
							onclick="filter(this)"> ${recipeCategory.name}
						</label>
					</div>
				</c:forEach>
				<hr>
				<h4>
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					เครื่องครัว
				</h4>
				<c:forEach var="kitchenware" items="${kitchenwares}">
					<div class="checkbox">
						<label> <input name="kitchenware_id" type="checkbox"
							value="${kitchenware.kitchenware_id}" onclick="filter(this)">
							${kitchenware.name}
						</label>
					</div>
				</c:forEach>
				<script>
		    $('#fix').affix({
			offset : {
			    top : 0,
			    bottom : 0
			}
		    });
		    function filter(e) {
			var checked = $(':checked');
			console.log(checked);
			$('.recipe-panel').hide();
			for (var i = 0; i < checked.length; i++) {
			    var category = $('[data-recipe-category-'
				    + checked[i].value + ']');
			    console.log(category);
			    category.show();
			}
			if (checked.length == 0)
			    $('.recipe-panel').show();
		    }
		</script>
			</form>
		</div>

		<div class="list-group media col-xs-12 col-md-9">
			<div class="page-header">
				<h1>รายการตำรับอาหาร</h1>
			</div>
			<c:if test="${fn:length(recipes) == 0}">
				<h3>ไม่พบตำรับอาหารที่ค้นหา</h3>
			</c:if>
			<c:if test="${fn:length(recipes) != 0}">
				<c:forEach var="i" begin="0" end="${fn:length(recipes) - 1}">
					<a href="/recipes/show?id=${recipes[i].recipe_id}"
						<c:forEach var="aRecipeCategory" items="${recipesCategories[i]}">
											data-recipe-category-${aRecipeCategory.recipe_category_id}
										</c:forEach>
						<c:forEach var="recipeKitchenware" items="${recipeKitchenwares[i]}">
											data-kitchenware-category-${recipeKitchenware.kitchenware_id}
										</c:forEach>
						class="recipe-panel">
						<div class="panel panel-default">
							<div class="panel-body">
								<div class="row">
									<div class="col-xs-12 col-sm-6 col-md-4">
										<img src="${recipes[i].photo_url}" width="100%">
									</div>
									<div class="col-xs-12 col-sm-6 col-md-8">
										<h2>${recipes[i].name}</h2>
										<p><b>โดย</b> ${recipesUsers[i].username}</p>
										<p>
											<c:forEach begin="1" end="${recipes[i].averageRate}"
												var='star'>
												<span onclick="sentrate('${recipe.recipe_id}','${star}')"
													class='glyphicon glyphicon-star'
													style='color: gold; font-size: x-large;'></span>
											</c:forEach>
										</p>
										${recipes[i].description}
									</div>
								</div>
							</div>
							<div class="panel-footer">
								<b>หมวดหมู่:</b>
								<c:forEach var="aRecipeCategory" items="${recipesCategories[i]}">
											${aRecipeCategory.name} 
										</c:forEach>
							</div>
						</div>
					</a>

				</c:forEach>
			</c:if>
			<hr>
			<h3>ตำรับอาหารที่ขาดวัตถุดิบบางอย่าง</h3>
			<c:if test="${fn:length(recipesPartial) != 0}">
				<c:forEach var="i" begin="0" end="${fn:length(recipesPartial) - 1}">
					<c:if test="${fn:length(ingredientsPartial[i]) <= 15}">
						<div class="panel panel-default">
							<a href="/recipes/show?id=${recipesPartial[i].recipe_id}"
								<c:forEach var="aRecipeCategory" items="${recipesCategoriesPartial[i]}">
											data-recipe-category-${aRecipeCategory.recipe_category_id}
										</c:forEach>
								<c:forEach var="recipeKitchenware" items="${recipeKitchenwaresPartial[i]}">
											data-kitchenware-category-${recipeKitchenware.kitchenware_id}
										</c:forEach>
								class="recipe-panel">
								<div class="panel-body">
									<div class="row">
										<div class="col-xs-12 col-sm-6 col-md-4">
											<img src="${recipesPartial[i].photo_url}" width="100%">
										</div>
										<div class="col-xs-12 col-sm-6 col-md-8">
											<h2>${recipesPartial[i].name}</h2>
											<p><b>โดย</b> ${recipesPartialUsers[i].username}</p>
											<p>
												<c:forEach begin="1" end="${recipesPartial[i].averageRate}"
													var='star'>
													<span class='glyphicon glyphicon-star'
														style='color: gold; font-size: x-large;'></span>
												</c:forEach>
											</p>
											${recipesPartial[i].description}
										</div>
									</div>
								</div>
								<div class="panel-footer">
									<p><b>วัตถุดิบที่ขาด:</b>
									<c:forEach var="ingredientPartial"
										items="${ingredientsPartial[i]}">
							${ingredientPartial.name} 
						</c:forEach>
									</p><b>หมวดหมู่:</b>
									<c:forEach var="aRecipeCategory"
										items="${recipesCategoriesPartial[i]}">
											${aRecipeCategory.name} 
										</c:forEach>
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