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
	<!-- menu bar -->
	<nav class="navbar navbar-fixed-top" role="navigation">
		<div class="container">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/">Foodbook</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">หมวดหมู่ <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">ของทอด</a></li>
							<li><a href="#">ของหวาน</a></li>
						</ul></li>
				</ul>
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="เช่น ข้าวผัด">
					</div>
					<button type="submit" class="btn btn-default">ค้นหา</button>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${empty user}">
						<li><a href="/users/authenticate">เข้าสู่ระบบ</a></li>
						<li><a href="/users/create">สมัครสมาชิก</a></li>
					</c:if>
					<c:if test="${!empty user}">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">${user.username} <span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="/favorites/index">ตำรับอาหารโปรด</a></li>
								<li class="divider"></li>
								<li><a href="/recipes/index">ตำรับอาหารของฉัน</a></li>
								<li><a href="/recipes/create">สร้างตำรับอาหาร</a></li>
								<li class="divider"></li>
								<li><a href="/users/deauthenticate">ออกจากระบบ</a></li>
							</ul></li>
					</c:if>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>