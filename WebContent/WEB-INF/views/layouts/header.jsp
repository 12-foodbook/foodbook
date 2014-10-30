<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Foodbook / ${pageTitle}</title>
<link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css" />
<link rel="stylesheet" href="/css/bootstrap_Foodbooktheme.css" type="text/css" />
</head>
<body>

<c:if test="${not empty alert}">
	<div class="alert alert-${alert.type}" role="alert">${alert.message}</div>
</c:if>