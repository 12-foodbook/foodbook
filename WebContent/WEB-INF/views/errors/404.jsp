<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Errors / 404" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>

<div class="container">
	<div class="page-header">
		<h1><span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span> Not Found</h1>
	</div>
</div>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>