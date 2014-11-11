<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" value="Users / Authenticate" scope="application" />

<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class="container">

	<div class="page-header">
		<h1>เข้าสู่ระบบ</h1>
	</div>
	
	<div class="row">
		<div class="col-xs-12 col-md-6 col-md-offset-3">
			<form class="form-horizontal" role="form">
				<div class="form-group">
					<label for="username" class="col-sm-2 control-label">ชื่อผู้ใช้</label>
					<div class="col-sm-10">
						<input type="email" class="form-control" id="username" placeholder="เช่น stevejobs">
					</div>
				</div>
				<div class="form-group">
					<label for="password" class="col-sm-2 control-label">รหัสผ่าน</label>
					<div class="col-sm-10">
						<input type="password" class="form-control" id="password" placeholder="เช่น secret1@#$">
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-12">
						<button type="submit" class="btn btn-success btn-lg btn-block">เข้าสู่ระบบ</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	
</div>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />