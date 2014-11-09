<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Recipes / Create" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>


	<!-- form insert  -->
	<div class="container-fluid">
		<div class="row">
			<form class="form-horizontal col-xs-12 col-xs-offset-0 col-sm-6 col-sm-offset-3">
				<fieldset>
					<legend>เพิ่มสูตรอาหาร</legend>
					<div class="form-group">
						<label for="inputFoodName" class="col-lg-2 control-label">ชื่ออาหาร</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" id="inputFoodName"
								placeholder="ชื่ออาหาร">
						</div>
					</div>
					<div class="form-group">
						<label for="inputVideoLink" class="col-lg-2 control-label">video
							link</label>
						<div class="col-lg-10">
							<input type="text" class="form-control" id="inputVideoLink"
								placeholder="video link example: http://www.youtube.com/watch?v=6YZlFdTIdzM">
						</div>
					</div>
					<div class="form-group">
						<label for="inputVideoLink" class="col-lg-2 control-label">รูปประกอบ</label>
						<div class="col-lg-10">
							<input id="input-1" type="file" class="file" multiple="true">
						</div>

					</div>
					<div class="form-group">
						<label for="inputIngredients" class="col-lg-2 control-label">วัตถุดิบ</label>
						<div class="col-lg-10">
							<textarea class="form-control" rows="3" id="inputIngredients"></textarea>
						</div>
					</div>
					<div id="FoodProcedure" class="form-group">
						<label for="inputFoodProcedure" class="col-lg-2 control-label">ขั้นตอนการทำ</label>
						<div class="col-lg-10">
						<input type='button' value='Add Step' id='addButton'>
<input type='button' value='Remove Step' id='removeButton'>
						<script type="text/javascript">
 
$(document).ready(function(){
 
    var counter = 1;
 
    $("#addButton").click(function () {
 
	 if(counter>10){
            alert("Only 10 textboxes allow");
            return false;
	}
 
	var newTextBoxDiv = $(document.createElement('div'))
	     .attr("id", 'TextBoxDiv' + counter);
 
	newTextBoxDiv.after().html('<label>Step #'+ counter + ' : </label>' +
	      '<textarea class="form-control" rows="3" id="inputFoodProcedure' + counter + '" value="" ></textarea>');
 
	newTextBoxDiv.appendTo("#FoodProcedure .col-lg-10");
 
 
	counter++;
     });
 
     $("#removeButton").click(function () {
	if(counter==1){
          alert("No more textbox to remove");
          return false;
       }   
 
	counter--;
 
        $("#TextBoxDiv" + counter).remove();
 
     });
  });
</script>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-10 col-lg-offset-2">
							<button class="btn btn-default">Cancel</button>
							<button type="submit" class="btn btn-primary">Submit</button>
						</div>
					</div>
				</fieldset>
			</form>
		</div>
	</div>


<form method="post" id="create-recipe">
	<input name="name" placeholder="name"><br>
	<input name="video_url" placeholder="video_url"><br>
	<c:forEach begin="0" end="${fn:length(ingredientCategories) - 1}" var="i">
		${ingredientCategories[i].name}
		<c:forEach begin="0" end="${fn:length(ingredients[i]) - 1}" var="j">
			<input name="ingredient_id" type="checkbox" value="${ingredients[i][j].ingredient_id}"> ${ingredients[i][j].name}
			<input name="ingredient_amount" placeholder="ingredient_amount">
		</c:forEach>
	</c:forEach>
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
'<input name="step_title" placeholder="step_title"><br>' +
'<input name="step_description" placeholder="step_description"><br>' +
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

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>