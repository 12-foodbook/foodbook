<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Recipes/Show" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>

<c:out value="${recipe.recipe_id}"/>
<c:out value="${recipe.name}"/>
<c:out value="${recipe.video_link}"/>
<c:out value="${recipe.user_id}"/>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>