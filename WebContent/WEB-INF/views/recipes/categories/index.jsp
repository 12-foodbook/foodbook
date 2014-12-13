<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Categories / Index" scope="application" />
<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class='container'>
	<div class="row">
		<div class=".col-md-8.col-md-offset-2">
	<form action="/recipes/categories/index" method="post">
			<table class="table table-condensed">
				<tr ><td align="center">#</td><td align="center">หมวดหมู่อาหาร</td><td align="center">ลบหมวดหมู่</td></tr>
				<c:forEach var="i" begin="0" end="${fn:length(recipeCategories)-1}">
					<c:if test="${(i+1)%2 != 0}">
						<tr class="active">
							<td align="center" >${i+1}</td>
							<td align="center"> ${recipeCategories[i].name}</td>
							<td align="center"><input name="recipe_category_id" type="checkbox" value="${recipeCategories[i].recipe_category_id}" /></td>
						</tr>
					</c:if>
					<c:if test="${(i+1)%2 == 0}">
						<tr>
							<td align="center" >${i+1}</td>
							<td align="center"> ${recipeCategories[i].name}</td>
							<td align="center"><input name="recipe_category_id" type="checkbox" value="${recipeCategories[i].recipe_category_id}" /></td>
						</tr>
					</c:if>
					
				</c:forEach>
			</table>
			<div class=".col-md-offset-0">
				
					<a href="/recipes/categories/index" class="btn btn-default" >เพิ่มหมวดหมู่</a>
				
				
					<input type="submit" class="btn btn-danger" value='ลบหมวดหมู่'/>
				</form>
			</div>
		</div>
	</div>
</div>


	

	
<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />