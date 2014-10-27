<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Recipes/Show" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>

	<!-- menu bar -->
	<div class="navbar navbar-fixed-top">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-responsive-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">
			<img class="logo" src="/test content/logo/logo_1.png"></a>
		</div>
		<div class="navbar-collapse collapse navbar-responsive-collapse">
			<ul class="nav navbar-nav">
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">หมวดหมู่ <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Action</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li class="divider"></li>
						<li class="dropdown-header">Dropdown header</li>
						<li><a href="#">Separated link</a></li>
						<li><a href="#">One more separated link</a></li>
					</ul></li>
			</ul>
			<form class="navbar-form navbar-left" role="search">
				<div class="form-group">
					<input type="text" class="form-control col-lg-8" placeholder="Search">
				</div>
				<button type="submit" class="btn btn-default">ค้นหา</button>
			</form>
			<ul class="nav navbar-nav navbar-right">
				<li><a href="#">ชื่อ นามสกุล</a></li>
				<li class="dropdown"><a href="#" class="dropdown-toggle"
					data-toggle="dropdown">ตั้งค่า <b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a href="#">Action</a></li>
						<li><a href="#">Another action</a></li>
						<li><a href="#">Something else here</a></li>
						<li class="divider"></li>
						<li><a href="#">Separated link</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
	<!-- video link Edit-->
	<h1 class="media-heading">${recipe.name}</h1>
	<section class="row">
		<div class="span6">
			<div class="flex-video widescreen">
				<iframe src="${recipe.video_url}"
					frameborder="0" allowfullscreen=""></iframe>
			</div>
		</div>
	</section>

	<!-- Jammy video
<div class="media" >
<div class="media-body">
    <h4 class="media-heading">Video Head</h4>
  </div>
    <video class="media-object" controls autoplay>
    <source src="test content/Scones.mp4" type="video/mp4">
    </video>
    </div> -->
	<!-- other content -->
	<div class="well">
		<h2>${recipe.name}</h2>
		<!-- picture -->
		<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
  
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
    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
  </ol>

  <!-- Controls -->
  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left"></span>
  </a>
  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right"></span>
  </a>
</div>
		<!-- <img class="media-img" src="test content/scone.jpg"> -->
		<!-- description -->
		<div class="Ingres">
		<h3 >Ingredients</h3>
		<ol>
			<li>asdkklnsasnd</li>
			<li>dafdgsfbfs</li>
			<li>gsdgffgfd</li>
			<li>gsdgsggsfg</li>
			<li>sdgffbgdfg</li>
		</ol>
	</div>
	<div class="Ingres">
		<h3 >How to</h3>
		<ol>
			<li>asdkklnsasnd</li>
			<li>dafdgsfbfs</li>
			<li>gsdgffgfd</li>
			<li>gsdgsggsfg</li>
			<li>sdgffbgdfg</li>
		</ol>
	</div>
	</div>
	<!-- rating -->
	<div class="rating">
		<span>&#x2605;</span><span>&#x2605;</span><span>&#x2605;</span><span>&#x2605;</span><span>&#x2605;</span>
	</div>


<c:out value="${recipe.recipe_id}"/>
<c:out value="${recipe.name}"/>
<c:out value="${recipe.video_url}"/>
<c:out value="${recipe.user_id}"/>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>