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
							value="${kitchenware.kitchenware_id}" onclick="filter2(this)">
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
		    function filter2(e) {
			var checked = $(':checked');
			console.log(checked);
			$('.recipe-panel').hide();
			for (var i = 0; i < checked.length; i++) {
			    var category = $('[data-kitchenware-category-'
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
					<div
						<c:forEach var="aRecipeCategory" items="${recipesCategories[i]}">
											data-recipe-category-${aRecipeCategory.recipe_category_id}
										</c:forEach>
						<c:forEach var="recipeKitchenware" items="${recipeKitchenwares[i]}">
											data-kitchenware-category-${recipeKitchenware.kitchenware_id}
										</c:forEach>
						class="recipe-panel">
						<a href="/recipes/show?id=${recipes[i].recipe_id}">
							<div class="panel panel-default">
								<div class="panel-body">
									<div class="row">
										<div class="col-xs-12 col-md-4">
											<img src="${recipes[i].photo_url}" width="100%">
										</div>
										<div class="col-xs-12 col-md-5">
											<h2>${recipes[i].name}</h2>
											<p>
												<b>โดย</b>
												<c:if test="${recipes[i].is_moderator_id}">${recipesModerators[i].username}</c:if>
												<c:if test="${!recipes[i].is_moderator_id}">${recipesUsers[i].username}</c:if>
											</p>
											<p>
												<c:forEach begin="1" end="${recipes[i].averageRate}"
													var='star'>
													<span class='glyphicon glyphicon-star'
														style='color: gold; font-size: x-large;'></span>
												</c:forEach>
											</p>
											${recipes[i].description}
										</div>
										<div class="col-xs-12 col-md-3">
											<c:if test="${param.id == recipeUser.user_id}">
												<a href="/recipes/edit?id=${recipes[i].recipe_id}"
													class="btn btn-default btn-block">แก้ไข</a>
												<br>
												<form action="/recipes/delete" method="post"
													style='margin-left: 2%'>
													<input type="hidden" name="recipe_id"
														value="${recipes[i].recipe_id}">
													<!-- Button trigger modal -->
													<button type="button" class="btn btn-danger btn-block"
														data-toggle="modal"
														data-target="#myModal_${recipes[i].name}">ลบ</button>

													<!-- Modal -->
													<div class="modal fade" id="myModal_${recipes[i].name}"
														tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
														aria-hidden="true">
														<div class="modal-dialog">
															<div class="modal-content">
																<div class="modal-header">
																	<button type="button" class="close"
																		data-dismiss="modal">
																		<span aria-hidden="true">&times;</span><span
																			class="sr-only">Close</span>
																	</button>
																	<h4 class="modal-title" id="myModalLabel">Delete
																		Recipe</h4>
																</div>
																<div class="modal-body">คุณแน่ใจใช่ไหมว่าจะลบ
																	${recipes[i].name}?</div>
																<div class="modal-footer">
																	<button type="button" class="btn btn-default"
																		data-dismiss="modal">Cancel</button>
																	<input type="submit" class="btn btn-danger"
																		value='Delete'>
																</div>
															</div>
														</div>
													</div>
												</form>
												<form class="col-md-4" method="post" accept-charset="UTF-8"
													action="/favorites/create">
													<input type="hidden" name="recipe_id"
														value="${recipes[i].recipe_id}">
												</form>
											</c:if>
										</div>
									</div>
								</div>
								<div class="panel-footer">
									<b>หมวดหมู่:</b>
									<c:forEach var="aRecipeCategory"
										items="${recipesCategories[i]}">
											${aRecipeCategory.name} 
										</c:forEach>
								</div>
							</div>
						</a>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
</div>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />