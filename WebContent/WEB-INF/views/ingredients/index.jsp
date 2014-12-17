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
				<h4>
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					หมวดหมู่วัตถุดิบ
				</h4>
				<c:forEach var="ingredientCategory" items="${ingredientCategories}">
					<div class="checkbox">
						<label> <input name="ingredient_category_id"
							type="checkbox"
							value="${ingredientCategory.ingredient_category_id}"
							onclick="filter(this)"> ${ingredientCategory.name}
						</label>
					</div>
				</c:forEach>
				<script>
		    $('#fix').affix({
			offset : {
			    top : 0,
			    bottom : 0
			}
		    });
		    function filter(e) {
			var checked = $(':checked');
			console.log(checked);
			$('#cateTable tbody tr').hide();
			for (var i = 0; i < checked.length; i++) {
			    var category = $('[data-ingredient-category-'
				    + checked[i].value + ']');
			    console.log(category);
			    category.show();
			}
			if (checked.length == 0)
			    $('#cateTable tbody tr').show();
		    }
		</script>
			</form>
		</div>

		<div class="col-xs-12 col-md-10">
			<div class="page-header">
				<h1>
					<span class="glyphicon glyphicon-leaf" aria-hidden="true"></span>
					วัตถุดิบ
				</h1>
			</div>
			<form action="/ingredients/create" method="get">
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
					<tbody>
						<c:forEach var="i" begin="0" end="${fn:length(ingredients)-1}">
							<tr
								<c:forEach var="aIngredientCategory" items="${ingredientsCategories[i]}">
											data-ingredient-category-${aIngredientCategory.ingredient_category_id}
										</c:forEach>>
								<!-- photo -->
								<td id='ingrePhoto_${ingredients[i].ingredient_id}'
									value="${ingredients[i].photo_url}"><img
									src="${ingredients[i].photo_url}" alt="ingredient photo"
									width="150px" height="100px" /></td>
								<!-- ingredient -->
								<td align="center" id='cateValue_${ingredients[i].name}'
									value="${ingredients[i].name}">${ingredients[i].name}</td>
								<!-- calories -->
								<td align="center" class='col-md-4'
									id='${ingredients[i].calorie}'>${ingredients[i].calorie}
									kcal/${ingredients[i].unit}</td>
								<!-- delete -->
								<td align="center"><input id='ingredient_id'
									name="ingredient_id" type="checkbox"
									value="${ingredients[i].ingredient_id}" /></td>
								<td align="center" id='cat_select_${ingredients[i].name}'><c:forEach
										var="ingredientCategory"
										items="${ingredients[i].ingredient_categories}">
								${ingredientCategory.name}
							</c:forEach></td>
								<td id="editButt_${ingredients[i].name}"><a
									onclick="domulti('${ingredients[i].ingredient_id}','editButt_${ingredients[i].name}','cateValue_${ingredients[i].name}','${ingredients[i].name}','${ingredients[i].calorie }','${ingredients[i].unit }','cat_select_${ingredients[i].name}','${ingredients[i].photo_url}')"
									class="btn btn-default col-md-11">แก้ไขวัตถุดิบ</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</form>

			<script type="text/javascript">
				function domulti(id,butt,catefield,name,kcal,unit,cate,photo){
					
					$('#editButt_'+name).click(editCate(id,butt,catefield,name,kcal,unit,cate,photo));
					
				}
			
				function editCate(id,butt,catefield,name,kcal,unit,cate,photo){
					
					var table = document.getElementById('cateTable');
					document.getElementById('ingrePhoto_'+id).innerHTML='<img src="'+photo+'" alt="ingredient photo"width="150px" height="100px" /><input  name="photo" class="form-control" id="oldPhoto" type="file"onchange="fileChanged(this)"> <input id="newPhoto" name="photo_url" class="form-control" type="hidden">';
				    document.getElementById(catefield).innerHTML='<input class="col-md-8 col-md-offset-2" id="newcateValue" value='+name+' />';				    
				    document.getElementById(kcal).innerHTML='<input class="col-md-2 col-md-offset-4" id="newCalorie" value='+kcal+' /><input class="col-md-3 col-md-offset-0" id="newUnit" value='+unit+' />';
				    document.getElementById(cate).innerHTML='<select class="col-md-10 col-md-offset-1" id="newCateg"><c:forEach var="Categories" items="${ingredientCategories}"><option selected="selected" value="${Categories.ingredient_category_id}">${Categories.name}</option></c:forEach></select>';
				    document.getElementById(butt).innerHTML='<a onclick="sending('+id+')" id="editButt" class="btn btn-success col-md-7" >ยืนยัน</a>';
				    

				}
				function sending(id){
					var catsend = [];
					xmlhttp = new XMLHttpRequest();	
					var cateid = id;
					var catename = document.getElementById('newcateValue').value;
					var cat_calorie = document.getElementById('newCalorie').value;
					var cat_Unit = document.getElementById('newUnit').value;
					var cat_Photo =document.getElementById('newPhoto');
					var newCateg = $('#newCateg').find(":selected").attr('value');
					var url = "/ingredients/edit";
					
					if (cat_Photo == null || cat_Photo.value == '') {
						cat_Photo = $('#ingrePhoto_'+id).attr('value');
						console.log(cat_Photo);
					} else {
						cat_Photo = $('#newPhoto').attr('value');
						console.log(cat_Photo);
					}
					console.log(newCateg);
					$.post(url, {id: cateid, name: catename, photo_url: cat_Photo, calorie: cat_calorie, unit: cat_Unit, ingredient_category_id: newCateg}, function(data) {
						window.location = '/ingredients/index';
					});

					//xmlhttp.open("POST",url,true);
					/* xmlhttp.onreadystatechange=function(){
						location.reload();
					}; */
					//xmlhttp.send();
					
				}
				
				var $createRecipeForm = $('#create-recipe');
				var $recipeSteps = $('#recipe-steps');
				var temp = 1;
				var $addStepButton = $('#add-step-button');
				var $createButton = $('#editButt');
				// $recipeSteps.append(recipeStepHtml);

				$addStepButton.click(function() {
					var recipeStepHtml = '<div id="step_'+temp+'"><hr><img src="../img/remove_red.png" alt="remove" onclick='+'remove_step('+'"step_'+temp+'"'+')'+' width="22px" height="22px"/>'+
					'<div class="form-group"><label class="col-sm-4 control-label">หัวข้อขั้นตอน</label><div class="col-sm-8"><input name="step_title" class="form-control" value="${recipeStep.title}"></div></div>'+
					'<div class="form-group"><label class="col-sm-4 control-label">รายละเอียดขั้นตอน</label><div class="col-sm-8"><textarea name="step_description" class="form-control" rows="5">${recipeStep.description}</textarea></div></div>'+
					'<div class="form-group"><label class="col-sm-4 control-label">รูปภาพประกอบขั้นตอน</label><div class="col-sm-8"><input name="step_photo" class="form-control" type="file" onchange="fileChanged(this)"><input name="step_photo_url" class="form-control" type="hidden"></div></div></div>';
					console.log(recipeStepHtml);
					$recipeSteps.append(recipeStepHtml);
					temp++;
					tinymce.init({
					    selector: "textarea",
					    menubar : false
					 });
				});

				$createRecipeForm.submit(function(event) {
					console.log("submit");
				});

				var $stepPhotoUrlInput;

				function fileChanged(input) {
					$stepPhotoUrlInput = $($(input).next()[0]);
					console.log($stepPhotoUrlInput);
					readImage(input);
				}

				function readImage(input) {
					for (var i = 0; i < input.files.length; i++) {
						var fr = new FileReader();
						fr.onload = uploadImage;
						fr.readAsDataURL(input.files[i]);
					}
				}

				function uploadImage(e) {
					var data = e.target.result.split(',')[1];
					$.ajax({
						url : 'https://api.imgur.com/3/image',
						method : 'POST',
						headers : {
							Authorization : 'Client-ID 104715d58c6294d',
							Accept : 'application/json'
						},
						data : {
							image : data,
							type : 'base64'
						},
						beforeSend : function(xhr, settings) {
							$('input[type=file]').attr('disabled', true);
							$createButton.attr('disabled', true);
						},
						success : function(result) {
							console.log(result);
							var id = result.data.id;
							var url = 'https://i.imgur.com/' + id + '.png';
							$stepPhotoUrlInput.attr('value', url);
							console.log($stepPhotoUrlInput);
							$('input[type=file]').attr('disabled', false);
							$createButton.attr('disabled', false);
						},
						error : function(result) {
							console.log(result);
						}
					});
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
				    cell2.innerHTML = '<td><div align="center"><input class="col-md-8 col-md-offset-2" name="name" placeholder="วัตถุดิบ" /></div></td>';
				    cell3.innerHTML = '<td><div align="center"><input class="col-md-2 col-md-offset-4" name="calorie" /><input class="col-md-2 col-md-offset-0" name="unit"/></div></td>';
				    cell4.innerHTML = '<td><div align="center"><input type="checkbox" value="" /></div></td>';
				    cell5.innerHTML = '<td><div align="center"><select name="ingredient_category_id" class="col-md-10 col-md-offset-1" id="newCateg"><c:forEach var="Categories" items="${ingredientCategories}"><option selected="selected" value="${Categories.ingredient_category_id}">${Categories.name}</option></c:forEach></select></div></td>';
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