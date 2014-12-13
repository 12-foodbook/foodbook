<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Foodbook / ${pageTitle}</title>
	<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="/css/fontface.css">
	<link rel="stylesheet" type="text/css" href="/css/foodbook.css">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="/js/bootstrap.min.js"></script>
</head>
<body>

	<div id="fb-root"></div>
	<script>(function(d, s, id) {
	  var js, fjs = d.getElementsByTagName(s)[0];
	  if (d.getElementById(id)) return;
	  js = d.createElement(s); js.id = id;
	  js.src = "//connect.facebook.net/en_US/sdk.js#xfbml=1&appId=1589281751292732&version=v2.0";
	  fjs.parentNode.insertBefore(js, fjs);
	}(document, 'script', 'facebook-jssdk'));</script>

	<c:if test="${not empty alert}">
		<div class="container">
			<div class="alert alert-${alert.type}" role="alert">
				<button type="button" class="close" data-dismiss="alert">
					<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
				</button>
				${alert.message}
			</div>
		</div>
	</c:if>
	
	<nav class="navbar navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">
					<span class="glyphicon glyphicon-book" aria-hidden="true"></span>
					Foodbook
				</a>
			</div>
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<form class="navbar-form navbar-left" role="search">
					<a href="/" class="btn btn-success">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						ค้นหาตำรับอาหารจากวัตถุดิบ
					</a>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${empty user}">
						<li><a href="/users/authenticate"><span class="glyphicon glyphicon-log-in" aria-hidden="true"></span> เข้าสู่ระบบ</a></li>
						<li><a href="/users/create"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> สมัครสมาชิก</a></li>
					</c:if>
					<c:if test="${!empty user}">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-toggle="dropdown">
								<span class="glyphicon glyphicon-user" aria-hidden="true"></span>
								${user.username}
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="/favorites/index">
									<span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
									รายการโปรด
								</a></li>
								<li class="divider"></li>
								<li><a href="/recipes/index">
									<span class="glyphicon glyphicon-cutlery" aria-hidden="true"></span>
									ตำรับอาหารของฉัน
								</a></li>
								<li><a href="/recipes/create">
									<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
									สร้างตำรับอาหาร
								</a></li>
								<li class="divider"></li>
								<li><a href="/users/deauthenticate">
									<span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
									ออกจากระบบ
								</a></li>
							</ul></li>
					</c:if>
					<c:if test="${!empty moderator}">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><span class="glyphicon glyphicon-user" aria-hidden="true"></span> ${moderator.username} <span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#">สมาชิก</a></li>
								<li class="divider"></li>
								<li><a href="#">ตำรับอาหาร</a></li>
								<li><a href="/recipes/categories/index">หมวดหมู่รายการอาหาร</a></li>
								<li><a href="#">เพิ่มวัตถุดิบ</a></li>
								<li><a href="#">สร้างตำรับอาหาร</a></li>
								<li class="divider"></li>
								<li><a href="/moderators/deauthenticate">ออกจากระบบ</a></li>
							</ul></li>
					</c:if>
				</ul>
				<form class="navbar-form navbar-right" role="search" action="/recipes/search-by-name" accept-charset="UTF-8">
					<div class="form-group">
						<input type="text" class="form-control" name="query" placeholder="เช่น ข้าวผัด" value="${param.query}">
					</div>
					<button type="submit" class="btn btn-default">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						ค้นหา
					</button>
				</form>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>