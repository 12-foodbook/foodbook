<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Recipes / Index" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>
<!-- menu bar -->
	<nav class="navbar navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#"> Foodbook <img
					class="logo_brand" alt="Brand" src="/test content/logo/logo_1.png">
				</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">หมวดหมู่ <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
							<li class="divider"></li>
							<li><a href="#">One more separated link</a></li>
						</ul></li>
				</ul>
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Search</button>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="#">ชื่อ นามสกุล</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">ตั้งค่า <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	<div class="space-nav">asd</div>
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
			<img src="test content/scone.jpg"alt="scone">
		</a>
		<div class="media-body col-sm-3">
			<h4 class="media-heading">Scone</h4>
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
			
	<div class="row media">
		<a class="media-left media-middle col-sm-2 thumbnail" href="#"> 
			<img src="test content/scone.jpg"alt="scone">
		</a>
		<div class="media-body col-sm-3">
			<h4 class="media-heading">Scone</h4>
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
	<div class="row media">
		<a class="media-left media-middle col-sm-2 thumbnail" href="#"> 
			<img src="test content/scone.jpg"alt="scone">
		</a>
		<div class="media-body col-sm-3">
			<h4 class="media-heading">Scone</h4>
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
	<div class="row media">
		<a class="media-left media-middle col-sm-2 thumbnail" href="#"> 
			<img src="test content/scone.jpg"alt="scone">
		</a>
		<div class="media-body col-sm-3">
			<h4 class="media-heading">Scone</h4>
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
	<div class="row media">
		<a class="media-left media-middle col-sm-2 thumbnail" href="#"> 
			<img src="test content/scone.jpg"alt="scone">
		</a>
		<div class="media-body col-sm-3">
			<h4 class="media-heading">Scone</h4>
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
	<div class="row media">
		<a class="media-left media-middle col-sm-2 thumbnail" href="#"> 
			<img src="test content/scone.jpg"alt="scone">
		</a>
		<div class="media-body col-sm-3">
			<h4 class="media-heading">Scone</h4>
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