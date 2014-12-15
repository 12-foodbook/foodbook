<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Index" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>

<div class="container">
	<div class="ingresTab col-xs-12 col-xs-offset-0 col-sm-6 col-sm-offset-0">
		<div class="page-header">
			<h1>
				<span class="glyphicon glyphicon-leaf" aria-hidden="true"></span>
				เลือกวัตถุดิบ
			</h1>
		</div>
		<ul class="nav nav-tabs">
			<c:forEach begin="0" end="${fn:length(ingredientCategories) - 1}" var="i">
				<li<c:if test="${i == 0}"> class="active"</c:if>><a href="#ingredient-category-${i}" data-toggle="tab">${ingredientCategories[i].name}</a></li>
			</c:forEach>
		</ul>
		<form method="post" action="/recipes/search-by-ingredient">
		<div id="myTabContent" class="tab-content">
			<c:forEach begin="0" end="${fn:length(ingredients) - 1}" var="j">
				<div class="tab-pane fade<c:if test="${j == 0}"> active in</c:if>" id="ingredient-category-${i}">
				<div class="row">
					<div id="myTabContent" class="tab-content">
					
						<c:forEach begin="0" end="${fn:length(ingredients) - 1}" var="j">
							<div
								class="tab-pane fade<c:if test="${j == 0}"> active in</c:if>"
								id="ingredient-category-${j}">
								<div class="checkboxcol col-sm-12">
									<c:forEach var="ingredient" items="${ingredients[j]}">
									<div class=' col-sm-4'>
										<input id='${ingredient.name}' onclick='showStatus("${ingredient.name}")' type="checkbox"name="ingredient_id" value="${ingredient.ingredient_id}">
										<label style='cursor:pointer' for='${ingredient.name}'>
										${ingredient.name}
										</label>
									</div>

									</c:forEach>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
			
					
				</div>
			</c:forEach>
			<!-- TAG -->
			<div class='row'>
				<h3><div id='tagDiv' class='col-sm-6'></div></h3>
				</div>
				<button class="btn btn-success btn-lg btn-block searchButt col-sm-12">
						<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
						ค้นหาตำรับอาหาร
					</button>
		</div>
		
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

<script type="text/javascript">
var checknum = 0;
var numrow=0;
function showStatus(ingname){
	var tagHTML ='<span onclick="del_select(this)" id="label-selected_'+ingname+'" class="label label-info" style="margin-left:2%;cursor:pointer">'+ingname+'</span>';
	if(document.getElementById(ingname).checked){
		$( "#tagDiv" ).append(tagHTML);	
		checknum++;
		if(checknum%5==0){$( "#tagDiv" ).append('<div id="space"><br></div>');
		numrow++;
		}
	}
	else{
		$( '#label-selected_'+ingname+'' ).remove();
		
		if(checknum<5*numrow){$( '#space' ).remove();numrow--;}
		checknum--;
		
	}
}
function del_select(temid){
	document.getElementById(temid.innerHTML).checked=false;
	if(checknum<5*numrow){$( '#space' ).remove();numrow--;}
	checknum--;
	
	$("#"+temid.id).remove();
}
</script>
<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>