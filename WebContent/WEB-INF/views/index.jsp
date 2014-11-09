<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Index" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>

<!-- menu bar -->

	<c:forEach begin="0" end="${fn:length(ingredientCategories) - 1}" var="i">
		${ingredientCategories[i].name}<br>
		<c:forEach begin="0" end="${fn:length(ingredients[i]) - 1}" var="j">
			<input name="ingredient_id" type="checkbox" value="${ingredients[i][j].ingredient_id}"> ${ingredients[i][j].name}<br>
		</c:forEach>
		<hr>
	</c:forEach>

	<nav class="navbar navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index"> Foodbook <img
					class="logo_brand" alt="Brand" src="/test content/logo/logo_1.png">
				</a>
			</div>

			<!-- Collect the nav links, forms, and other content for toggling -->
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">หมวดหมู่ <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
							<li class="divider"></li>
							<li><a href="#">One more separated link</a></li>
						</ul></li>
				</ul>
				<form class="navbar-form navbar-left" role="search">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search">
					</div>
					<button type="submit" class="btn btn-default">Search</button>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="users/create">register</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">ตั้งค่า <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li><a href="#">Separated link</a></li>
						</ul></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>
	<!-- Add Category View -->
	<!-- Content -->
	<div class="container">
		<!--Accordion  -->
		<div
			class="panel-group col-xs-6 col-xs-offset-3 col-sm-2 col-sm-offset-0"
			id="accordion">
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseOne"> หมวดที่ 1 </a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse in">

					<div class="panel-body">
						<a href="#">test</a> <a href="#">test</a> <a href="#">test</a> <a
							href="#">test</a>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseTwo"> หมวดที่ 2 </a>
					</h4>
				</div>
				<div id="collapseTwo" class="panel-collapse collapse">
					<div class="panel-body">Anim pariatur cliche reprehenderit,
						</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading">
					<h4 class="panel-title">
						<a data-toggle="collapse" data-parent="#accordion"
							href="#collapseThree"> หมวดที่ 3 </a>
					</h4>
				</div>
				<div id="collapseThree" class="panel-collapse collapse">
					<div class="panel-body"></div>
				</div>
			</div>
		</div>
		<!-- Ingredients will select-->
		<div
			class="ingresTab col-xs-12 col-xs-offset-0 col-sm-4 col-sm-offset-0">
			<ul class="nav nav-tabs">
				<li class="active"><a href="#เนื้อ" data-toggle="tab">เนื้อ</a></li>
				<li><a href="#ผลไม้" data-toggle="tab">ผลไม้</a></li>
				<li><a href="#ผัก" data-toggle="tab">ผัก</a></li>
				<li><a href="#เครื่องปรุงรส" data-toggle="tab">เครื่องปรุงรส</a></li>
				<li><a href="#อื่นๆ" data-toggle="tab">อื่นๆ</a></li>
			</ul>
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade active in" id="เนื้อ">
					<div class="checkboxcol">
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
					</div>
					<div class="checkboxcol">
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
					</div>
					<div class="checkboxcol">
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="meats"
								value="เนื้อไก่">เนื้อไก่
							</label>
						</div>
					</div>
				</div>
				<div class="tab-pane fade" id="ผลไม้">
					<div class="checkboxcol">
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="องุ่น">องุ่น
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="องุ่น">องุ่น
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="สับปะรด">องุ่น
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="องุ่น">องุ่น
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="องุ่น">องุ่น
							</label>
						</div>
					</div>
					<div class="checkboxcol">
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="แอปเปิล">แอปเปิล
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="แอปเปิล">แอปเปิล
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="แอปเปิล">แอปเปิล
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="แอปเปิล">แอปเปิล
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="แอปเปิล">แอปเปิล
							</label>
						</div>
					</div>
					<div class="checkboxcol">
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="องุ่น">องุ่น
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="องุ่น">องุ่น
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="สับปะรด">องุ่น
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="องุ่น">องุ่น
							</label>
						</div>
						<div class="checkbox">
							<label> <input type="checkbox" name="fruits"
								value="องุ่น">องุ่น
							</label>
						</div>
					</div>
				</div>
				<div class="tab-pane fade" id="ผัก">
					<div class="checkboxcol">
						<div class="checkbox">
							<label> <input type="checkbox" name="vegetables"
								value="ผักบุ้ง">ผักบุ้ง
							</label>
						</div>


						<div class="checkbox">
							<label> <input type="checkbox" name="vegetables"
								value="ผักบุ้ง">ผักบุ้ง
							</label>
						</div>


						<div class="checkbox">
							<label> <input type="checkbox" name="vegetables"
								value="ผักบุ้ง">ผักบุ้ง
							</label>
						</div>
					</div>

				</div>
				<div class="tab-pane fade" id="เครื่องปรุงรส">
					<div class="checkboxcol">
						<div class="checkbox">
							<label> <input type="checkbox" name="seasoning"
								value="ผักบุ้ง">พริก
							</label>
						</div>
					</div>
				</div>
				<div class="tab-pane fade" id="อื่นๆ">
					<div class="checkboxcol">
						<div class="checkbox">
							<label> <input type="checkbox" name="other"
								value="แป้งอเนกประสงค์">แป้งอเนกประสงค์
							</label>
						</div>
					</div>
				</div>
			</div>
			<!--Ingredients Selected  -->
			<div class="selectedIngres">
				<span class="label label-success">เนื้อไก่</span> <span
					class="label label-success">องุ่น</span> <span
					class="label label-success">พริก</span>
			</div>
			<a href="#" class="btn btn-primary btn-lg searchButt">ค้นหาเมนู</a>
		</div>
		<!--Search ingres button  -->

		<!-- Foodbook Description -->
		<div
			class="jumbotron FoodbookDes col-xs-12 col-xs-offset-0 col-sm-6 col-sm-offset-0">
			<h1>
				Foodbook <img class="jumboImg" alt="Brand"
					src="/test content/logo/logo_1.png">
			</h1>
			<p>This is Foodbook.</p>
		</div>

	</div>

<%-- <c:out value="${pageTitle}"/> --%>


<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>