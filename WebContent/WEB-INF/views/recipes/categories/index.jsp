<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Categories / Index" scope="application" />
<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class='container'>
	<div class="row">
	<div class="page-header">
		<h1>หมวดหมู่อาหาร</h1>
	</div>
		<div class="col-md-10 col-md-offset-1"><!-- set table -->
	
		<form action='/recipes/categories/create' method="post">
			<table id="cateTable" class="table table-striped"><!-- recipe category -->
				<thead><td align="center">#</td><td align="center">หมวดหมู่อาหาร</td><td align="center">ลบหมวดหมู่</td><td></td></thead>
				<c:forEach var="i" begin="0" end="${fn:length(recipeCategories)-1}">
					
					<tr id='row_${recipeCategories[i].name}' onmouseout="hide_visible('${recipeCategories[i].name}')" onmouseover="show_visible('${recipeCategories[i].name}')">
						<td align="center" >${i+1}</td>
						<td align="center" id='cateValue_${recipeCategories[i].name}' value="${recipeCategories[i].name}">${recipeCategories[i].name}</td>
						<td align="center"><input id='recipe_category_id' name="recipe_category_id" type="checkbox" value="${recipeCategories[i].recipe_category_id}" /></td>
						<td id="editButt_${recipeCategories[i].name}"><a id="a_${recipeCategories[i].name}" style='visibility:hidden' onclick="editCate('${recipeCategories[i].recipe_category_id}','editButt_${recipeCategories[i].name}','cateValue_${recipeCategories[i].name}','${recipeCategories[i].name}')" class="btn btn-default col-md-6" >แก้ไขหมวดหมู่</a></td>						
					</tr>	
				</c:forEach>
			</table></form>
			
			<!-- edit -->
			<script type="text/javascript">
			
				function show_visible(id){
					document.getElementById('a_'+id).style.visibility='visible';
				}
				
				function hide_visible(id){
					
					document.getElementById('a_'+id).style.visibility='hidden';
				}
				
				function editCate(id,butt,catefield,val){
					//alert("333");
					var table = document.getElementById('cateTable');
				    var cell2 = val;
				    document.getElementById(catefield).innerHTML='<input id="newcateValue" value='+cell2+' />';
				    document.getElementById(butt).innerHTML='<a onclick="sending('+id+')" id="editButt" class="btn btn-success col-md-6" >ยืนยัน</a>';
				    //alert(cell3);

				}
				function sending(id){
					var catsend = [];
					xmlhttp = new XMLHttpRequest();	
					var cateid = id;
					var catename = document.getElementById('newcateValue').value;
					var url = "/recipes/categories/edit?id="+cateid+"&&name="+catename;
					

					xmlhttp.open("POST",url,true);
					xmlhttp.onreadystatechange=function(){
						location.reload();
					};
					xmlhttp.send();
				}
			</script>
			
			<!-- button delete -->
			<button type="button" class="btn btn-danger col-md-2" data-toggle="modal" data-target="#myModal">ลบหมวดหมู่</button>
			<!-- Modal -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
								</button>
								<h4 class="modal-title" id="myModalLabel">ลบหมวดหมู่</h4>
							</div>
							<div class="modal-body">ต้องการลบหมวดหมู่หรือไม่?</div>
							<div class="modal-footer">
							<button type="button" class="btn btn-default"data-dismiss="modal">ยกเลิก</button>
							<input onclick='sendValue()' type="submit" class="btn btn-danger" value='ลบ'>
						</div>
					</div>
				</div>
			</div>
		
		<!-- button add -->
		
			<button  onclick="addCate()" id="add" class="btn btn-default col-md-2" >เพิ่มหมวดหมู่</button>
			<script>
			
			var isAddCate =true;
				function addCate(){
					if(isAddCate){
					isAddCate =false;
					
					var table = document.getElementById('cateTable');
					var row = table.insertRow(${fn:length(recipeCategories)+1});
				    var cell1 = row.insertCell(0);
				    var cell2 = row.insertCell(1);
				    var cell3 = row.insertCell(2);
				    var cell4 = row.insertCell(3);
				 
					
				    cell1.innerHTML = '<tr><td><div align="center">${fn:length(recipeCategories)+1}</div></td>';
				    cell2.innerHTML = '<td><div align="center"><input class="col-md-4 col-md-offset-4" name="name" placeholder="หมวดหมู่อาหาร" /></div></td>';
				    cell3.innerHTML = '<td><div align="center"><input type="checkbox" value="" /></div></td>';
				    cell4.innerHTML = '<td><div align="center"><a href="/recipes/categories/index"class="btn btn-default col-md-3">ยกเลิก</a></td>'+
				    '<td><button class="btn btn-success col-md-4" type="submit">เพิ่มหมวดหมู่</button></div></td></tr>'; 
					
					//alert("333");
					}
				}
				function sendValue(){
					
					var catsend = [];
					xmlhttp = new XMLHttpRequest();	
					var url = "/recipes/categories/delete?recipe_category_id=";
					var category = document.getElementsByName('recipe_category_id');
					
					for(var i=0;i<category.length;i++){
						if(category[i].checked){
							if(catsend.length==0){
								url +=''+category[i].value;
							}
							else{
								url +='&&recipe_category_id='+category[i].value;
							}
						catsend[catsend.length]=category[i].value;
						}
					}
					
					//var url = "/recipes/categories/delete?recipe_category_id=";
					xmlhttp.open("POST",url,true);
					xmlhttp.onreadystatechange=function(){
						location.reload();
					};
					xmlhttp.send();
					//alert(category);
				}
				
				
			</script>
			
		</div>
	</div>
</div>	
<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />