<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pageTitle" value="Recipes / Edit" scope="application" />

<jsp:include page="/WEB-INF/views/layouts/header.jsp" />

<div class="container">

	<div class="page-header">
		<h1>แก้ไขตำรับอาหาร <small>${recipe.name}</small></h1>
	</div>

	<form method="post" class="form-horizontal" role="form">
		<input name="id" value="${recipe.recipe_id}" type="hidden">
		<div class="row">
			<div class="col-xs-12 col-md-8">
				<div class="form-group">
					<label for="name" class="col-sm-2 control-label">ชื่อรายการอาหาร</label>
					<div class="col-sm-10">
						<input name="name" placeholder="name" id="name" class="form-control" value="${recipe.name}">
					</div>
				</div>
				<div class="form-group">
					<label for="video_url" class="col-sm-2 control-label">วิดิโอวิธีการประกอบอาหาร</label>
					<div class="col-sm-10">
						<input name="video_url" placeholder="video_url" id="video_url" class="form-control" value="${recipe.video_url}">
					</div>
				</div>
				
				<div id="recipe-steps">
					<c:forEach var="recipeStep" items="${recipeSteps}">
						<hr>
						<div class="form-group">
							<label class="col-sm-2 control-label">หัวข้อขั้นตอน</label>
							<div class="col-sm-10">
								<input name="step_title" class="form-control" value="${recipeStep.title}">
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">รายละเอียดขั้นตอน</label>
							<div class="col-sm-10">
								<textarea name="step_description" class="form-control" rows="5">${recipeStep.description}</textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">รูปภาพประกอบขั้นตอน</label>
							<div class="col-sm-10">
								<input name="step_photo" class="form-control" type="file" onchange="fileChanged(this)">
								<input name="step_photo_url" class="form-control" type="hidden">
							</div>
						</div>
					</c:forEach>
				</div>
				
			</div>
			
			<div class="col-xs-12 col-md-4">
				<c:forEach begin="0" end="${fn:length(ingredientCategories) - 1}" var="i">
					<div class="form-group">
						<label class="col-sm-4 control-label">${ingredientCategories[i].name}</label>
					</div>
					<c:forEach begin="0" end="${fn:length(ingredients[i]) - 1}" var="j">
						<c:set var="contains" value="false" />
						<c:forEach var="recipeIngredient" items="${recipeIngredients}">
							<c:if test="${recipeIngredient.ingredient_id eq ingredients[i][j].ingredient_id}">
								<c:set var="contains" value="true" />
							</c:if>
						</c:forEach>
						<div class="form-group">
							<div class="col-sm-8">
								<div class="checkbox">
								  <label>
								    <input name="ingredient_id" type="checkbox" onclick="toggleAmount(this)" value="${ingredients[i][j].ingredient_id}"<c:if test="${contains}"> checked</c:if>>
								    <span>${ingredients[i][j].name}</span>
								    <c:if test="${contains}"><input name="ingredient_amount" class="form-control" placeholder="เช่น 5 ชิ้น" value=""></c:if>
								  </label>
								</div>
							</div>
						</div>
					</c:forEach>
				</c:forEach>
			</div>
		</div>
		
		<div class="row">
			<div class="col-xs-12 col-md-6">
				<button id="add-step-button" class="btn btn-default btn-lg btn-block" type="button">เพิ่มขั้นตอน</button>
			</div>
			<div class="col-xs-12 col-md-6">
				<button type="submit" id="create-button" class="btn btn-success btn-lg btn-block">แก้ไขตำรับอาหาร</button>
			</div>
		</div>
		
	</form>

</div>

<script>
	var $createRecipeForm = $('#create-recipe');
	var $recipeSteps = $('#recipe-steps');
	var recipeStepHtml = '<hr><div class="form-group"><label class="col-sm-2 control-label">หัวข้อขั้นตอน</label><div class="col-sm-10"><input name="step_title" class="form-control" value="${recipeStep.title}"></div></div><div class="form-group"><label class="col-sm-2 control-label">รายละเอียดขั้นตอน</label><div class="col-sm-10"><textarea name="step_description" class="form-control" rows="5">${recipeStep.description}</textarea></div></div><div class="form-group"><label class="col-sm-2 control-label">รูปภาพประกอบขั้นตอน</label><div class="col-sm-10"><input name="step_photo" class="form-control" type="file" onchange="fileChanged(this)"><input name="step_photo_url" class="form-control" type="hidden"></div></div>';
	console.log(recipeStepHtml);
	var $addStepButton = $('#add-step-button');
	var $createButton = $('#create-button');

	// $recipeSteps.append(recipeStepHtml);

	$addStepButton.click(function() {
		$recipeSteps.append(recipeStepHtml);
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
	
	var amountHtml = '<input name="ingredient_amount" class="form-control" placeholder="เช่น 5 ชิ้น">'
	
	function toggleAmount(input) {
		if (input.checked) $(input).next().after(amountHtml);
		else $(input).next().next().remove();
	}
</script>

<!--
<form method="post" id="create-recipe">
	<input name="name" placeholder="name" value="${recipe.name}"><br>
	<input name="video_url" placeholder="video_url"
		value="${recipe.video_url}"><br>
	<c:forEach begin="0" end="${fn:length(ingredientCategories) - 1}"
		var="i">
		${ingredientCategories[i].name}<br>
		<c:forEach begin="0" end="${fn:length(ingredients[i]) - 1}" var="j">
			<c:set var="contains" value="false" />
			<c:forEach var="recipeIngredient" items="${recipeIngredients}">
				<c:if
					test="${recipeIngredient.ingredient_id eq ingredients[i][j].ingredient_id}">
					<c:set var="contains" value="true" />
				</c:if>
			</c:forEach>
			<input name="ingredient_id" type="checkbox"
				value="${ingredients[i][j].ingredient_id}"
				<c:if test="${contains}"> checked</c:if>>
			${ingredients[i][j].name}<br>
		</c:forEach>
	</c:forEach>
	<div id="recipe-steps">
		<c:forEach var="recipeStep" items="${recipeSteps}">
			<div class="recipe-step">
				<input name="step_title" placeholder="step_title"
					value="${recipeStep.title}"><br> <input
					name="step_description" placeholder="step_description"
					value="${recipeStep.description}"><br> <input
					type="file" onchange="fileChanged(this)">
			</div>
		</c:forEach>
	</div>
	<button id="add-step-button" type="button">Add Step</button>
	<button id="create-button" type="submit">Create</button>
</form>

<script>
	var $createRecipeForm = $('#create-recipe');
	var $recipeSteps = $('#recipe-steps');
	var recipeStepHtml = ''
			+ '<div class="recipe-step">'
			+ '<input name="step_title" placeholder="step_title"><br>'
			+ '<input name="step_description" placeholder="step_description"><br>'
			+ '<input type="file" onchange="fileChanged(this)">'
			+ '<input name="step_photo_url" type="hidden">' + '</div>';
	console.log(recipeStepHtml);
	var $addStepButton = $('#add-step-button');
	var $createButton = $('#create-button');

	$recipeSteps.append(recipeStepHtml);

	$addStepButton.click(function() {
		$recipeSteps.append(recipeStepHtml);
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
-->

<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />