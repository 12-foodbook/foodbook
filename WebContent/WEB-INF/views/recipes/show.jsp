<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pageTitle" value="Recipes / Show" scope="application" />

<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div id="fb-root"></div>
<script>
    (function(d, s, id) {
	var js, fjs = d.getElementsByTagName(s)[0];
	if (d.getElementById(id))
	    return;
	js = d.createElement(s);
	js.id = id;
	js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&appId=1589281751292732&version=v2.0";
	fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
</script>

<div class="container">

	<div class="page-header">
		<h1>${recipe.name}
			<small>โดย <a href="/recipes/user?id=${recipeUser.user_id}">${recipeUser.username}</a></small>
		</h1>
	</div>

	<div class="row">
		<div class="col-xs-12 col-md-8">

			<c:if test="${!empty recipe.video_url}">
				<div class="embed-responsive embed-responsive-16by9">
					<iframe src="${recipe.video_url}"></iframe>
				</div>

				<hr>
			</c:if>

			<img src="${recipe.photo_url}" width="100%" />

			<hr>

			<c:forEach var="i" begin="0" end="${fn:length(recipeSteps) - 1}">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">${i+1}. ${recipeSteps[i].title}</h3>
					</div>
					<div class="panel-body">
						<p>${recipeSteps[i].description}</p>
						<c:forEach var="recipeStepPhoto" items="${recipeStepPhotos[i]}">
							<img src="${recipeStepPhoto.photo_url}" width="100%">
						</c:forEach>
					</div>
				</div>
			</c:forEach>

			<div class="fb-comments"
				data-href="<%=request.getRequestURL() + "?" + request.getQueryString()%>"
				data-colorscheme="light" data-width="100%"></div>

		</div>

		<div class="col-xs-12 col-md-4">
			<table class="table table-bordered">
				<tr>
					<th>วัตถุดิบ</th>
					<th>ปริมาณที่ใช้</th>
					<th>แคลลอรี่</th>
				</tr>
				<c:set var="totalCalorie" value="0" />
				<c:forEach var="ingredient" items="${ingredients}">
					<c:set var="totalCalorie"
						value="${totalCalorie + (ingredient.calorie * ingredient.amount)}" />
					<tr>
						<td>${ingredient.name}</td>
						<td>${ingredient.amount} ${ingredient.unit}</td>
						<td>${ingredient.calorie}</td>
					</tr>
				</c:forEach>
				<tr>
					<td colspan="2"><strong>แคเรอรี่รวม</strong></td>
					<td>${totalCalorie}</td>
				</tr>
			</table>
			<c:if test="${!empty user}">
				<h1>VOTE</h1>
				<form action="/rates" method="post">
					<input type="hidden" name="recipe_id" value="${recipe.recipe_id}">
					<div class="btn-group" role="group">
						<input class="btn btn-default" type="submit" name="rate" value="1">
						<input class="btn btn-default" type="submit" name="rate" value="2">
						<input class="btn btn-default" type="submit" name="rate" value="3">
						<input class="btn btn-default" type="submit" name="rate" value="4">
						<input class="btn btn-default" type="submit" name="rate" value="5">
					</div>
				</form>

				<form method="post" accept-charset="UTF-8"
					action="/favorites/create">
					<input type="hidden" name="recipe_id" value="${recipe.recipe_id}"><br>
					<button class="btn btn-lg btn-block btn-danger">เพิ่มในรายการโปรด</button>
				</form>
			</c:if>
			<br>
			<h3>RATING AVERAGE</h3>
			<c:forEach begin="1" end="${recipe.averageRate}" var='star'>
				<span onclick="sentrate('${recipe.recipe_id}','${star}')"
					class='glyphicon glyphicon-star'
					style='color: gold; font-size: x-large;'></span>
			</c:forEach>
		</div>
	</div>

</div>
<%-- 	
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
<c:out value="${recipe.user_id}"/> --%>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />