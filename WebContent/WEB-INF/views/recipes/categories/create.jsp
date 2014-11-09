<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Recipes / Categories / Create" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>


	<div class="space-nav">asd</div>
<div class="container">
	<!--Category Accordion  -->
	<div class="row">
		<!-- Category Add form --> 
		<form method="post" class="form-horizontal col-xs-12">
		<fieldset>
			<legend>เพิ่มหมวดอาหาร</legend>
			<div class="form-group">
				<label for="name" class="col-lg-2 control-label">ชื่อหมวดอาหาร</label>
				<div class="col-lg-10">
					<input type="text" class="form-control" id="name" name="name"
						placeholder="ชื่อหมวดอาหาร">
				</div>
			</div>
			<div class="form-group">
				<div class="col-lg-10 col-lg-offset-2">
					<button type="submit" class="btn btn-primary">Submit</button>
				</div>
			</div>
		</fieldset>
	</form>
	</div>
	</div>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>