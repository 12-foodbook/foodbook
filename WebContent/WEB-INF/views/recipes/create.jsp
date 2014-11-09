<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="pageTitle" value="Recipes / Create" scope="application" />

<jsp:include page="/WEB-INF/views/layouts/header.jsp" />
<div class="container-fluid">
	<div class="row">
		<form method="post" id="create-recipe"
			class="form-horizontal col-xs-12 col-xs-offset-0 col-sm-6 col-sm-offset-3">
			<fieldset>
				<legend>เพิ่มสูตรอาหาร</legend>
				<div class="form-group">
					<label for="inputFoodName" class="col-lg-2 control-label">ชื่ออาหาร</label>
					<div class="col-lg-10">
						<input type="text" class="form-control" id="inputFoodName"
							name="name" placeholder="name">
					</div>
				</div>
				<div class="form-group">
					<label for="inputVideoLink" class="col-lg-2 control-label">video
						link</label>
					<div class="col-lg-10">
						<input type="text" class="form-control" id="inputVideoLink"
							name="video_url"
							placeholder="video link example: http://www.youtube.com/watch?v=6YZlFdTIdzM">
					</div>
				</div>
				<div class="form-group">
					<label for="inputIngredients" class="col-lg-2 control-label">วัตถุดิบ</label>
					<div class="col-lg-10">
						<c:forEach begin="0" end="${fn:length(ingredientCategories) - 1}"
							var="i">
		${ingredientCategories[i].name}
		<c:forEach begin="0" end="${fn:length(ingredients[i]) - 1}" var="j">
								<input name="ingredient_id" type="checkbox"
									value="${ingredients[i][j].ingredient_id}"> ${ingredients[i][j].name}
			<input name="ingredient_amount" placeholder="ingredient_amount">
							</c:forEach>
						</c:forEach>
					</div>
				</div>
				
				<div id="recipe-steps" class="form-group">
				<label for="inputVideoLink" class="col-lg-2 control-label">ขั้นตอนการทำ</label>
				</div>
				
				<button id="add-step-button" type="button">Add Step</button>
				<button id="create-button" type="submit" class="btn btn-primary col-lg-2 col-lg-offset-10">Create</button>
			</fieldset>
		</form>
	</div>
</div>
<script>
	var $createRecipeForm = $('#create-recipe');
	var $recipeSteps = $('#recipe-steps');
	var recipeStepHtml = ''
			+ '<div class="recipe-step col-lg-10 col-lg-offset-2">'
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

<jsp:include page="/WEB-INF/views/layouts/footer.jsp" />