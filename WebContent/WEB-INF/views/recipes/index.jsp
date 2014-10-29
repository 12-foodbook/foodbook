<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Recipes/Index" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>

<c:forEach var="recipe" items="${recipes}">
	${recipe.name}<br>
</c:forEach>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>