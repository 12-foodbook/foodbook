<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Recipes / Create" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>

<form method="post" id="create-recipe">
	<input name="name"><br>
	<input name="video_url"><br>
	<div id="recipe-steps">
	</div>
	<button id="add-step-button" type="button">Add Step</button>
	<button id="create-button" type="submit">Create</button>
</form>

<script>
var $createRecipeForm = $('#create-recipe');
var $recipeSteps = $('#recipe-steps');
var recipeStepHtml = '' +
'<div class="recipe-step">' +
'<input name="step_title"><br>' +
'<input name="step_description"><br>' +
'<input type="file" onchange="fileChanged(this)">' +
'<input name="step_photo_url" type="hidden">' +
'</div>';
console.log(recipeStepHtml);
var $addStepButton = $('#add-step-button');
var $createButton = $('#create-button');

$recipeSteps.append(recipeStepHtml);

$addStepButton.click(function () {
	$recipeSteps.append(recipeStepHtml);
});

$createRecipeForm.submit(function(event){
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
		beforeSend: function(xhr, settings) {
			$createButton.attr('disabled', true);
		},
		success : function(result) {
			console.log(result);
			var id = result.data.id;
			var url = 'https://i.imgur.com/' + id + '.png';
			$stepPhotoUrlInput.attr('value', url);
			console.log($stepPhotoUrlInput);
			$createButton.attr('disabled', false);
		},
		error : function(result) {
			console.log(result);
		}
	});
}
</script>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>