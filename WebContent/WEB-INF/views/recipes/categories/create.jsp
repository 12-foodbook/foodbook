<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Recipes / Categories /  Create" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>

<form method="post">
	<input name="name"><br>
	<button>Create</button>
</form>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>