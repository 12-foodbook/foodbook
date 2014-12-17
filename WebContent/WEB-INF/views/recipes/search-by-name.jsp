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
			<c:if test="${fn:length(recipes) != 0}">
				<c:forEach var="i" begin="0" end="${fn:length(recipes) - 1}">
					<a href="/recipes/show?id=${recipes[i].recipe_id}"
						<c:forEach var="aRecipeCategory" items="${recipesCategories[i]}">
											data-recipe-category-${aRecipeCategory.recipe_category_id}
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
										โดย ${recipesUsers[i].username}<br>
										${recipes[i].averageRate}<br> ${recipes[i].description}
										<c:forEach var="aRecipeCategory"
											items="${recipesCategories[i]}">
											${aRecipeCategory.name} 
										</c:forEach>
										<c:forEach begin="1" end="${recipes[i].averageRate}"
											var='star'>
											<span onclick="sentrate('${recipe.recipe_id}','${star}')"
												class='glyphicon glyphicon-star'
												style='color: gold; font-size: x-large;'></span>
										</c:forEach>
									</div>
								</div>
							</div>
						</div>
					</a>
				</c:forEach>
			</c:if>
		</div>
	</div>
</div>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />