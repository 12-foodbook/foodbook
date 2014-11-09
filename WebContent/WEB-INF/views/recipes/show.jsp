<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Recipes / Show" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>

	
	<!-- **************************************************************************** -->
	<!-- video link Edit-->
	<div class="container">
	<div class="media">
		<h1 class="media-heading videoHead">${recipe.name}</h1>

		<div class="embed-responsive embed-responsive-16by9">
			<iframe src="${recipe.video_url}"></iframe>
		</div>
	</div>
	<!-- other content -->

	<div class="row well">
		<h2>${recipe.name}</h2>
		<!-- picture -->
		<div id="carousel-example-generic"
			class="carousel slide col-xs-6 col-xs-offset-3 col-sm-2 col-sm-offset-1"
			data-ride="carousel">
			<!-- Wrapper for slides -->
			<div class="carousel-inner">
				<div class="item active">
					<img src="/test content/scone2.jpg" alt="...">
					<div class="carousel-caption">
						<center>${recipe.name}</center>
					</div>
				</div>
				<div class="item">
					<img src="/test content/scone.jpg" alt="...">
					<div class="carousel-caption">
						<center>${recipe.name}</center>
					</div>
				</div>
			</div>
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#carousel-example-generic" data-slide-to="0"
					class="active"></li>
				<li data-target="#carousel-example-generic" data-slide-to="1"></li>
			</ol>

			<!-- Controls -->
			<a class="left carousel-control" href="#carousel-example-generic"
				role="button" data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left"></span>
			</a> <a class="right carousel-control" href="#carousel-example-generic"
				role="button" data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right"></span>
			</a>
		</div>
		<!-- <img class="media-img" src="test content/scone.jpg"> -->
		<!-- description -->
		<div class="Ingres col-xs-12 col-xs-offset-0 col-sm-2 col-sm-offset-1">
			<h3>Ingredients</h3>
			<ol>
				<c:forEach var="ingredient" items="${ingredients}">
					<li>${ingredient.name}</li>
				</c:forEach>
			</ol>
		</div>
		<div class="Ingres col-xs-12 col-xs-offset-0 col-sm-2 col-sm-offset-1">
			<h3>How to</h3>
			<ol>
				<c:forEach var="recipeStep" items="${recipeSteps}">
					<li>${recipeStep.title} ${recipeStep.description}</li>
				</c:forEach>
			</ol>
		</div>
		<!-- rating -->
		<div class="rating col-xs-6 col-xs-offset-2 col-sm-6 col-sm-offset-0">
			<form action="/rates" method="post">
			<!-- test send recipe id -->
			<input type="hidden" value="${recipe.recipe_id}" name="recipe_id">
				<h1>
				<script>
				function sentrate(recipe_id,rate) {
					$.post('/rates', {'recipe_id':recipe_id,'rate':rate}, function (data) {
						console.log(data);
					});
				}
					</script>
					<span onclick="sentrate('${recipe.recipe_id}','5')">&#x2605;</span><span onclick="sentrate('${recipe.recipe_id}','4')">&#x2605;</span><span onclick="sentrate('${recipe.recipe_id}','3')">&#x2605;</span><span onclick="sentrate('${recipe.recipe_id}','2')">&#x2605;</span><span onclick="sentrate('${recipe.recipe_id}','1')">&#x2605;</span>
				</h1>
			</form>
		</div>
	</div>
</div>


<c:out value="${recipe.recipe_id}"/>
<c:out value="${recipe.name}"/>
<c:out value="${recipe.video_url}"/>
<c:out value="${recipe.user_id}"/>


<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>