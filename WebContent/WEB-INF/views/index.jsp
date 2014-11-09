<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Index" scope="application"/>

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
				<a class="navbar-brand" href="index"> Foodbook <img
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
					<li><a href="users/create">register</a></li>
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
	<!-- Add Category View -->
	<!-- Content -->
	<div class="container">
		<!--Accordion  -->
		<!-- Ingredients will select-->
		<div
			class="ingresTab col-xs-12 col-xs-offset-0 col-sm-6 col-sm-offset-0">
			<ul class="nav nav-tabs">
				<c:forEach begin="0" end="${fn:length(ingredientCategories) - 1}" var="i">
					<li<c:if test="${i == 0}"> class="active"</c:if>><a href="#ingredient-category-${i}" data-toggle="tab">${ingredientCategories[i].name}</a></li>
				</c:forEach>
			</ul>
			<form method="post" action="/recipes/search-by-ingredient">
			<div id="myTabContent" class="tab-content">
				<c:forEach begin="0" end="${fn:length(ingredients) - 1}" var="j">
					<div class="tab-pane fade<c:if test="${j == 0}"> active in</c:if>" id="ingredient-category-${j}">
						<div class="checkboxcol">
							<c:forEach var="ingredient" items="${ingredients[j]}">
								<div class="checkbox">
									<label> <input type="checkbox" name="ingredient_id"
										value="${ingredient.ingredient_id}">${ingredient.name}
									</label>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:forEach>
			</div>
			<!--Ingredients Selected  --><!-- 
			<div class="selectedIngres">
				<span class="label label-success">เนื้อไก่</span> <span
					class="label label-success">องุ่น</span> <span
					class="label label-success">พริก</span>
			</div> -->
			<button class="btn btn-primary btn-lg searchButt">ค้นหาเมนู</button>
			</form>
		</div>
		<!--Search ingres button  -->

		<!-- Foodbook Description -->
		<div
			class="jumbotron FoodbookDes col-xs-12 col-xs-offset-0 col-sm-6 col-sm-offset-0">
			<h1>
				Foodbook <img class="jumboImg" alt="Brand"
					src="/test content/logo/logo_1.png">
			</h1>
			<p>This is Foodbook.</p>
		</div>

	</div>

<%-- <c:out value="${pageTitle}"/> --%>


<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>