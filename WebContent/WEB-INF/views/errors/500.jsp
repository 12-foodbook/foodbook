<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Errors / 500" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>

<div class="container">
	<div class="page-header">
		<h1><span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span> Something Went Wrong D:</h1>
	</div>
	<center><img src="/img/men-in-black-neuralizer.jpg"></center>
</div>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>