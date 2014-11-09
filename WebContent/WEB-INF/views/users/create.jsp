<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Users / Create" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>


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