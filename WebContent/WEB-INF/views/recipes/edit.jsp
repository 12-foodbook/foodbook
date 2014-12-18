<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pageTitle" value="Recipes / Edit" scope="application" />

<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class="container">


	<div class="page-header">
		<h1>
			<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
			สร้างตำรับอาหาร
		</h1>
	</div>

	<form method="post" accept-charset="UTF-8" class="form-horizontal"
		role="form">
		<input name="id" value="${recipe.recipe_id}" type="hidden">
		<div class="row">
			<div class="col-xs-12 col-md-6">
				<div class="form-group">
					<label for="name" class="col-sm-4 control-label label-create">ชื่อรายการอาหาร</label>
					<div class="col-sm-8">
						<input name="name" placeholder="name" id="name"
							class="form-control" value="${recipe.name}" required>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-4 control-label label-create">คำอธิบายรายการอาหาร</label>
					<div class="col-sm-8">
						<textarea name="description">${recipe.description}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-4 control-label label-create">หมวดรายการอาหาร</label>
					<div class="col-sm-8">
						<c:forEach var='i' begin="0"
							end="${fn:length(recipeCategories) - 1}">
							<div class='col-sm-3'>
								<c:set var="found" value="false" />
								<c:forEach var="recipesCategory" items="${recipesCategories}">
									<c:if
										test="${recipeCategories[i].recipe_category_id == recipesCategory.recipe_category_id}">
										<c:set var="found" value="true" />
									</c:if>
								</c:forEach>
								<input type="checkbox" name="recipe_category_id"
									<c:if test="${found}">checked</c:if>
									value="${recipeCategories[i].recipe_category_id}"> <label>
									${recipeCategories[i].name} </label>
							</div>
						</c:forEach>
					</div>
				</div>
				<div class="form-group">
					<label for="name" class="col-sm-4 control-label label-create">เครื่องครัว</label>
					<div class="col-sm-8">
						<c:forEach var='i' begin="0"
							end="${fn:length(kitchenwaresAll) - 1}">
							<div class='col-sm-4'>
								<c:set var="found" value="false" />
								<c:forEach var="kitchenware" items="${kitchenwares}">
									<c:if
										test="${kitchenwaresAll[i].kitchenware_id == kitchenware.kitchenware_id}">
										<c:set var="found" value="true" />
									</c:if>
								</c:forEach>
								<input type="checkbox" name="kitchenware_id"
									<c:if test="${found}">checked</c:if>
									value="${kitchenwaresAll[i].kitchenware_id}"> <label>
									${kitchenwaresAll[i].name} </label>
							</div>
						</c:forEach>
					</div>
				</div>
				<hr>
				<div class="form-group">
					<label class="col-sm-4 control-label label-create">รูปภาพหน้าปก</label>
					<div class="col-sm-8">
						<input name="photo" class="form-control" type="file"
							onchange="fileChanged(this)"> <input name="photo_url"
							class="form-control" type="hidden" value="${recipe.photo_url}">
					</div>
				</div>
				<div class="form-group">
					<label for="video_url" class="col-sm-4 control-label label-create">วิดิโอวิธีการประกอบอาหาร</label>
					<div class="col-sm-8">
						<input id='video_you' onblur="checkLink()" name="video_url"
							placeholder="video_url" id="video_url" class="form-control"
							value="${recipe.video_url}">
						<script type="text/javascript">
			    function checkLink() {
				var tem = document.getElementById('video_you').value;
				var tem2 = tem.replace('watch?v=', 'embed/');
				document.getElementById('video_you').value = tem2;

			    }
			</script>
					</div>
				</div>

				<div id="recipe-steps">
					<c:forEach var="i" begin="0" end="${fn:length(recipeSteps) - 1}">
						<hr>
						<img src="../img/remove_red.png" alt="remove" width="22px" height="22px" style="cursor:pointer"/>'
						<div class="form-group">
							<label class="col-sm-4 control-label label-create">หัวข้อขั้นตอน</label>
							<div class="col-sm-8">
								<input name="step_title" class="form-control"
									value="${recipeSteps[i].title}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label label-create">รายละเอียดขั้นตอน</label>
							<div class="col-sm-8">
								<textarea name="step_description" class="form-control" rows="5">${recipeSteps[i].description}</textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-4 control-label label-create">รูปภาพประกอบขั้นตอน</label>
							<div class="col-sm-8">
								<input name="step_photo" class="form-control" type="file"
									onchange="fileChanged(this)"> <input
									name="step_photo_url" class="form-control" type="hidden" value="${recipeStepPhotos[i][0].photo_url}">
							</div>
						</div>
					</c:forEach>
				</div>
				<!-- button -->
				<div class="row">
					<div class="col-xs-12">
						<button id="add-step-button"
							class="btn btn-default btn-lg btn-block" type="button">
							<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
							เพิ่มขั้นตอน
						</button>
					</div>
				</div>
			</div>

			<!-- ingredients -->

			<div class="col-xs-12 col-md-6 fixed-div-ingres">
				<ul class="nav nav-tabs">
					<c:forEach begin="0" end="${fn:length(ingredientCategories) - 1}"
						var="i">
						<li <c:if test="${i == 0}"> class="active"</c:if>><a
							href="#ingredient-category-${i}" data-toggle="tab">${ingredientCategories[i].name}</a></li>
					</c:forEach>
				</ul>
				<div class="row">
					<div id="myTabContent" class="tab-content">

						<c:forEach begin="0" end="${fn:length(ingredients) - 1}" var="j">
						${ingredient.ingredient_id}
							<div
								class="tab-pane fade<c:if test="${j == 0}"> active in</c:if>"
								id="ingredient-category-${j}">
								<div class="checkboxcol col-sm-12">

									<c:forEach var="ingredient" items="${ingredients[j]}">
										<c:set var="eqing" value='true' />
										<c:forEach var='recipeIngredient' items="${recipeIngredients}">

											<c:if test="${recipeIngredient.name==ingredient.name}">
												<div class=' col-sm-6'>
													<c:set var="eqing" value='false' />
													<input id='${ingredient.name}' checked="checked"
														onclick='showNext(this)' type="checkbox"
														name="ingredient_id" value="${ingredient.ingredient_id}">
													<label style='cursor: pointer' for='${ingredient.name}'>
														${ingredient.name} </label> <input name="ingredient_amount"
														class="form-control" value='${recipeIngredient.amount}' />
												</div>
											</c:if>


										</c:forEach>
										<c:if test="${eqing eq 'true'}">
											<div class=' col-sm-6'>

												<input data-unit="${ingredient.unit}"
													id='${ingredient.name}' onclick='showNext(this)'
													type="checkbox" name="ingredient_id"
													value="${ingredient.ingredient_id}"> <label
													style='cursor: pointer' for='${ingredient.name}'>
													${ingredient.name} </label>
											</div>
										</c:if>


									</c:forEach>
								</div>
							</div>
						</c:forEach>
					</div>
				</div>
				<!-- TAG -->
				<div class='row'>
					<h3>
						<div id='tagDiv' class='col-sm-6'></div>
					</h3>
				</div>
			</div>

		</div>
		<!-- button -->
		<div class="row">
			<div class="col-xs-12 col-md-6">
				<button type="submit" id="create-button"
					class="btn btn-success btn-lg btn-block">
					<span class="glyphicon glyphicon-ok" aria-hidden="true"></span>
					แก้ไขตำรับอาหาร
				</button>
			</div>
		</div>


	</form>

</div>

<script type="text/javascript" src="/js/tinymce/tinymce.min.js"></script>
<script type="text/javascript">
    tinymce.init({
	selector : "textarea",
	menubar : false
    });
</script>

<script>
    var $createRecipeForm = $('#create-recipe');
    var $recipeSteps = $('#recipe-steps');
    var recipeStepHtml = '<hr><div class="form-group"><label class="col-sm-4 control-label">หัวข้อขั้นตอน</label><div class="col-sm-8"><input name="step_title" class="form-control" value="${recipeStep.title}"></div></div><div class="form-group"><label class="col-sm-4 control-label">รายละเอียดขั้นตอน</label><div class="col-sm-8"><textarea name="step_description" class="form-control" rows="5">${recipeStep.description}</textarea></div></div><div class="form-group"><label class="col-sm-4 control-label">รูปภาพประกอบขั้นตอน</label><div class="col-sm-8"><input name="step_photo" class="form-control" type="file" onchange="fileChanged(this)"><input name="step_photo_url" class="form-control" type="hidden"></div></div>';
    console.log(recipeStepHtml);
    var temp = 1;
    var $addStepButton = $('#add-step-button');
    var $createButton = $('#create-button');

    // $recipeSteps.append(recipeStepHtml);

    $addStepButton
	    .click(function() {
		var recipeStepHtml = '<div id="step_'+temp+'"><hr><img src="../img/remove_red.png" alt="remove" onclick='
			+ 'remove_step('
			+ '"step_'
			+ temp
			+ '"'
			+ ')'
			+ ' width="22px" height="22px" style="cursor:pointer"/>'
			+ '<div class="form-group"><label class="col-sm-4 control-label">หัวข้อขั้นตอน</label><div class="col-sm-8"><input name="step_title" class="form-control" value="${recipeStep.title}"></div></div>'
			+ '<div class="form-group"><label class="col-sm-4 control-label">รายละเอียดขั้นตอน</label><div class="col-sm-8"><textarea name="step_description" class="form-control" rows="5">${recipeStep.description}</textarea></div></div>'
			+ '<div class="form-group"><label class="col-sm-4 control-label">รูปภาพประกอบขั้นตอน</label><div class="col-sm-8"><input name="step_photo" class="form-control" type="file" onchange="fileChanged(this)"><input name="step_photo_url" class="form-control" type="hidden"></div></div></div>';
		console.log(recipeStepHtml);
		$recipeSteps.append(recipeStepHtml);
		tinymce.init({
		    selector : "textarea",
		    menubar : false
		});
	    });

    function remove_step(temp) {
	$('#' + temp).remove();
    }

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

    var amountHtml = '<input name="ingredient_amount" class="form-control" placeholder="เช่น 5 unit"/>'
    function showNext(input) {
	$("#" + input.id).click(toggleAmount(input), showStatus(input));
    }
    function toggleAmount(input) {
	console.log(input);
	if (input.checked)
	    $(input).next().after(
		    amountHtml.replace('unit', $(input).data('unit')));
	else
	    $(input).next().next().remove();
    }

    //Copy from Index
    var checknum = 0;
    var numrow = 0;
    function showStatus(input) {
	var ingname = input.id;
	var tagHTML = '<span title="Remove" id="label-selected_' + ingname
		+ '" class="label label-info" style="margin-left:2%;">'
		+ ingname;
	tagHTML += '</span>';
	if (document.getElementById(ingname).checked) {
	    $("#tagDiv").append(tagHTML);
	    checknum++;
	    if (checknum % 5 == 0) {
		$("#tagDiv").append('<div id="space"><br></div>');
		numrow++;
	    }
	} else {
	    $('#label-selected_' + ingname + '').remove();
	    $(input).next().next().remove();
	    if (checknum < 5 * numrow) {
		$('#space').remove();
		numrow--;
	    }
	    checknum--;

	}
    }
    function del_select(temid) {
	document.getElementById(temid.innerHTML).checked = false;
	if (checknum < 5 * numrow) {
	    $('#space').remove();
	    numrow--;
	}
	checknum--;

	$("#" + temid.id).remove();
    }
</script>


<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />