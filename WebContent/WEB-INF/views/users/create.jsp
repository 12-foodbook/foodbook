<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Users / Create" scope="application"/>

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
	<!-- form insert  -->
	<div class="container-fluid">
		<!--Category Accordion  -->
		<div class="row">
			<form class="form-horizontal col-sm-6 col-sm-offset-3" method="post">
				<fieldset>
					<legend>สมัครสมาชิก</legend>
					<div class="form-group">
						<label for="inputFoodName" class="col-lg-2 control-label">ชื่อผู้ใช้</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" id="inputUsername"
								name="username" placeholder="ชื่อผู้ใช้">
						</div>
					</div>
					<div class="form-group">
						<label for="inputVideoLink" class="col-lg-2 control-label">รหัสผ่าน</label>
						<div class="col-lg-10">
							<input name="password" type="password" class="form-control" id="inputPassword"
								 placeholder="รหัสผ่าน">
						</div>
					</div>
					<div class="form-group">
						<label for="inputVideoLink" class="col-lg-2 control-label">ยืนยันรหัสผ่าน</label>
						<div class="col-lg-10">
							<input name="confirm_password" type="password" class="form-control"
								id="inputConfirmPassword"
								placeholder="ยืนยันรหัสผ่าน">
						</div>
					</div>

					<div class="form-group">
						<div class="col-lg-10 col-lg-offset-2">
							<button type="submit" class="btn btn-primary">สมัครสมาชิก</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>


<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>