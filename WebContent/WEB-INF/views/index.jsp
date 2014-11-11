<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Index" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>


	<!-- Add Category View -->
	<!-- Content -->
	<div class="container">
		<!--Accordion  -->
		<!-- Ingredients will select-->
		<div
			class="ingresTab col-xs-12 col-xs-offset-0 col-sm-6 col-sm-offset-0">
			<div class="page-header">
				<h1>เลือกวัตถุดิบ</h1>
			</div>
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
			<button class="btn btn-success btn-lg btn-block searchButt">ค้นหาตำรับอาหาร</button>
			</form>
		</div>
		
		<div class="panel panel-default col-xs-12 col-xs-offset-0 col-sm-6 col-sm-offset-0"">
		  <div class="panel-body">
		    <div align="center">
				<img src="/img/foodbook_black.png">
				<h1>Foodbook</h1>
				<h3>ระบบค้นหาตำรับอาหารจากวัตถุดิบที่มีอยู่</h3>
			</div>
		  </div>
		</div>

	</div>

<%-- <c:out value="${pageTitle}"/> --%>


<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>