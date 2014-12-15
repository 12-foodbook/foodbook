<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Ingredients / Index" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>
	
<form action="/ingredients/index" method="get">
<div class="container">
	<div class="row">
	<div class="page-header" >
		<h1>รายการตำรับอาหาร</h1>
	</div>
		<div class="col-xs-12 col-md-4">
			<form>
				<label>หมวดหมู่วัตถุดิบ</label>
				<c:forEach var="ingredientCategory" items="${ingredientCategories}">
					<div class="checkbox">
						<label> <input name="ingredient_category_id" type="checkbox"
							value="${ingredientCategory.ingredient_category_id}">
							${ingredientCategory.name}
						</label>
					</div>
				</c:forEach>
			</form>
		</div>
		<div class="col-xs-12 col-md-8 col-md-offset-4">
			<table id="cateTable" class="table table-striped" ><!-- ingredients -->
				<thead><td >รูปรายการอาหาร</td><td align="center">รายการอาหาร</td><td align="center">แคลลอรี่/หน่วย</td><td align="center">ลบรายการ</td><td align="center"></td></thead>
				<c:forEach var="i" begin="0" end="${fn:length(ingredients)-1}">
					<tr>
						<!-- photo -->
						<td id='ingrePhoto_${ingredients[i].photo_url}'>
							<img src="${ingredients[i].photo_url}" alt="ingredient photo" width="150px" height="100px" />
						</td>
						<!-- ingredient -->
						<td align="center" id='cateValue_${ingredients[i].name}' value="${ingredients[i].name}">
							${ingredients[i].name}
						</td>
						<!-- calories -->
						<td align="center" id='cal_${ingredients[i].calorie}'>
							${ingredients[i].calorie} kcal/${ingredients[i].unit}
						</td>
						<!-- delete -->
						<td align="center">
							<input id='ingredient_id' name="ingredient_id" type="checkbox" value="${ingredients[i].ingredient_id}" />
						</td>
						<td>
							<c:forEach var="ingredientCategory" items="${ingredients[i].ingredient_categories}">
								${ingredientCategory.name} 
							</c:forEach>
						</td>
					</tr>	
				</c:forEach>
			</table>
		</div>
	</div>		
</div>
</form>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>