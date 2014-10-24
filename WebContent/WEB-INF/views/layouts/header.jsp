<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Foodbook / <c:out value="${pageTitle}"/></title>
<link rel="stylesheet" href="/css/bootstrap.min.css" type="text/css" />
</head>
<body>

<c:out value="${alert}"/>
<c:set var="alert" scope="session" value=""/>