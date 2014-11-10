<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Foodbook / ${pageTitle}</title>
<link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/css/fontface.css">
<link rel="stylesheet" type="text/css" href="/css/Foodbook_theme.css">
<script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
</head>
<body>

<c:if test="${not empty alert}">
	<div class="alert alert-${alert.type}" role="alert">${alert.message}</div>
</c:if>
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
				<a class="navbar-brand" href="/index"> Foodbook <img
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
							<li><a href="#">ของทอด</a></li>
							<li class="divider"></li>
							<li><a href="#">ของหวาน</a></li>
							
						</ul></li>
				</ul>
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Search</button>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<c:if test="${empty user}"><li><a href="/users/authenticate">Login</a></li>
					<li><a href="/users/create">สมัครสมาชิก</a></li></c:if>
					<c:if test="${!empty user}"><li><a href="#">${user.username}</a></li></c:if>
					
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">ตัวเลือก <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">Favorite</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>