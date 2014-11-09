<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Users / Authenticate" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>

	<div class="space-nav">asd</div>

<!-- form insert  -->
	<div class="container-fluid">
		<!--Category Accordion  -->
		<div class="row">
			<form class="form-horizontal col-sm-6 col-sm-offset-3" method="post">
		<fieldset>
			<legend>เข้าสู่ระบบ</legend>
			<div class="form-group">
				<label for="inputUsername" class="col-lg-2 control-label">ชื่อผู้ใช้</label>
				<div class="col-lg-10">
					<input type="text" class="form-control" id="inputUsername" name="username"
						placeholder="username">
				</div>
			</div>
			<div class="form-group">
				<label for="inputPassword" class="col-lg-2 control-label">รหัสผ่าน</label>
				<div class="col-lg-10">
					<input type="password" class="form-control" id="inputPassword" name="password"
						placeholder="password">
				</div>
			</div>

			<div class="form-group">
				<div class="col-lg-10 col-lg-offset-2">
					<button type="submit" class="btn btn-primary">เข้าสู่ระบบ</button>
				</div>
			</div>
		</fieldset>
	</form>
		</div>
	</div>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>