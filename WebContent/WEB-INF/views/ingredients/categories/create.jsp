<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="Ingredients / Categories / Create" scope="application"/>

<jsp:include page="/WEB-INF/views/layouts/header.jsp"/>

<div class='container'>
	<script type="text/javascript">
	window.onload = window.location.href = "/ingredients/categories/index";
	</script>
</div>

<jsp:include page="/WEB-INF/views/layouts/footer.jsp"/>