<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Recipes / Index" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>


	<div class="row">
	<!-- ************************************************************************************************* -->
	<!-- Contextual Class -->
	<div class="list-group col-sm-2 col-sm-offset-1" >
		<a href="#" class="list-group-item">หมวด 1</a> 
		<a href="#" class="list-group-item">หมวด 2</a> 
		<a href="#" class="list-group-item">หมวด 3</a> 
		<a href="#"class="list-group-item">หมวด 4</a>
	</div>
	<!-- *************************************************************************************************** -->
	<!-- detial -->
	<div class="col-sm-9">
	
	
	<div class="row media">
		<a class="media-left media-middle col-sm-2 thumbnail" href="#"> 
			<img src="/test content/scone.jpg"alt="scone">
		</a>
		<div class="media-body col-sm-3">
			<h4 class="media-heading">${recipe.name}</h4>
				sdlferhvdsjldfjfvdfbvjhdsvcwvdyvweycvweuycveycveywucv
			wecbwbcwhjecvbdgjvsdgcvhegcvweghcvweghdvcdcdhsgcvsdg
			<!-- rating -->
		<div class="row">
		<div class="rating col-sm-8">
			<form action="send_rating">
				<h3>
					<span>&#x2605;</span><span>&#x2605;</span><span>&#x2605;</span><span>&#x2605;</span><span>&#x2605;</span>
				</h3>
			</form>
		</div>
		<button type="button" class="btn btn-primary col-sm-4 FavButt">Favorite</button>
		</div>
		</div>
	</div>
	
	</div>
	
	</div>

<c:forEach var="recipe" items="${recipes}">
	<a herf="/recipes/show?id=${recipe.id}">${recipe.name}</a><br>
</c:forEach>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>