<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pageTitle" value="Ingredients / Index" scope="application" />

<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class="container">
	<div class="row">
		<div class="col-xs-12 col-md-2">
			<form id="fix">
				<b><span class="glyphicon glyphicon-leaf" aria-hidden="true"></span> หมวดหมู่วัตถุดิบ</b>
				<c:forEach var="ingredientCategory" items="${ingredientCategories}">
					<div class="checkbox">
						<label> <input name="ingredient_category_id"
							type="checkbox"
							value="${ingredientCategory.ingredient_category_id}">
							${ingredientCategory.name}
						</label>
					</div>
				</c:forEach>
			</form>
			
			<script>
				$('#fix').affix({
					offset : {
						top : 0,
						bottom : 0
					}
				});
			</script>
		</div>
		
		<div class="col-xs-12 col-md-10">
		<div class="page-header">
			<h1><span class="glyphicon glyphicon-leaf" aria-hidden="true"></span> วัตถุดิบ</h1>
		</div>
			<form action="/ingredients/index" method="get">
				<table id="cateTable" class="table table-striped">
					<!-- ingredients -->
					<thead>
						<td>รูปวัตถุดิบ</td>
						<td align="center">ชื่อวัตถุดิบ</td>
						<td align="center">แคลลอรี่/หน่วย</td>
						<td align="center">ลบวัตถุดิบ</td>
						<td align="center">ประเภทวัตถุดิบ</td>
						<td align="center"></td>
					</thead>
					<c:forEach var="i" begin="0" end="${fn:length(ingredients)-1}">
						<tr>
							<!-- photo -->
							<td id='ingrePhoto_${ingredients[i].photo_url}'><img
								src="${ingredients[i].photo_url}" alt="ingredient photo"
								width="150px" height="100px" /></td>
							<!-- ingredient -->
							<td align="center" id='cateValue_${ingredients[i].name}'
								value="${ingredients[i].name}">${ingredients[i].name}</td>
							<!-- calories -->
							<td align="center" class='col-md-4' id='cal_${ingredients[i].calorie}'>
								${ingredients[i].calorie} kcal/${ingredients[i].unit}</td>
							<!-- delete -->
							<td align="center"><input id='ingredient_id'
								name="ingredient_id" type="checkbox"
								value="${ingredients[i].ingredient_id}" />
							</td>
							<td align="center"><c:forEach var="ingredientCategory"
									items="${ingredients[i].ingredient_categories}">
								${ingredientCategory.name} 
							</c:forEach></td>
							<td id="editButt_${ingredients[i].name}"><a
								onclick="editCate('${ingredients[i].ingredient_id}','editButt_${ingredients[i].name}','cateValue_${ongredients[i].name}','${ingredients[i].name}')"
								class="btn btn-default col-md-11">แก้ไขวัตถุดิบ</a></td>
						</tr>
					</c:forEach>
				</table>
			</form>
			
			<script type="text/javascript">
				
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
					var url = "/ingredients/edit?id="+cateid+"&&name="+catename;
					

					xmlhttp.open("POST",url,true);
					xmlhttp.onreadystatechange=function(){
						location.reload();
					};
					xmlhttp.send();
				}
			</script>
			
			<button type="button" class="btn btn-danger col-md-2"
				data-toggle="modal" data-target="#myModal">ลบวัตถุดิบ</button>
			<!-- Modal -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
				aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<h4 class="modal-title" id="myModalLabel">ลบวัตถุดิบ</h4>
						</div>
						<div class="modal-body">ต้องการลบวัตถุดิบหรือไม่?</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default"
								data-dismiss="modal">ยกเลิก</button>
							<input onclick='sendValue()' type="submit" class="btn btn-danger"
								value='ลบ'>
								
						</div>
					</div>
				</div>
			</div>
			
			<!-- button add -->

			<button onclick="addCate()" id="add" class="btn btn-default col-md-2">เพิ่มวัตถุดิบ</button>
			<script>
			
			var isAddCate =true;
				function addCate(){
					if(isAddCate){
					isAddCate =false;
					
					var table = document.getElementById('cateTable');
					var row = table.insertRow(${fn:length(ingredients)+1});
				    var cell1 = row.insertCell(0);
				    var cell2 = row.insertCell(1);
				    var cell3 = row.insertCell(2);
				    var cell4 = row.insertCell(3);
				    var cell5 = row.insertCell(4);
				    var cell6 = row.insertCell(5);
				 
					
				    cell1.innerHTML = '<tr><td><div align="center"><input name="photo" class="form-control" type="file"onchange="fileChanged(this)"> <input name="photo_url" class="form-control" type="hidden"></div></td>';
				    cell2.innerHTML = '<td><div align="center"><input class="col-md-7 col-md-offset-2" name="name" placeholder="วัตถุดิบ" /></div></td>';
				    cell3.innerHTML = '<td><div align="center"><input class="col-md-2 col-md-offset-4" name="calorie" /><input class="col-md-2 col-md-offset-0" name="unit"/></div></td>';
				    cell4.innerHTML = '<td><div align="center"><input type="checkbox" value="" /></div></td>';
				    cell5.innerHTML = '<td><div align="center"><select class="col-md-4 col-md-offset-2" name="name" placeholder="วัตถุดิบ"><option></option></select></div></td>';
				    cell6.innerHTML = '<td><div align="center"><a href="/ingredients/index"class="btn btn-default col-md-7">ยกเลิก</a></td>'+
				    '<td><button class="btn btn-success col-md-4" type="submit">+</button></div></td></tr>'; 
					
					//alert("333");
					}
				}
				function sendValue(){
					
					var catsend = [];
					xmlhttp = new XMLHttpRequest();	
					var url = "/ingredients/delete?ingredient_id=";
					var category = document.getElementsByName('ingredient_id');
					
					for(var i=0;i<category.length;i++){
						if(category[i].checked){
							if(catsend.length==0){
								url +=''+category[i].value;
							}
							else{
								url +='&&ingredient_id='+category[i].value;
							}
						catsend[catsend.length]=category[i].value;
						}
					}
					
					//var url = "/ingredients/delete?ingredient_id=";
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