<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel='stylesheet' type='text/css' 
		href='css/common.css?v=<%=new java.util.Date().getTime()%>'>
<link rel="icon" type="image/svg+xml" href="imgs/logo.svg">
<meta charset="UTF-8">
<c:choose>
	<c:when test=""></c:when>
</c:choose>
<title>Caramel HOMEcchiato</title>
</head>
<body>
<h3></h3>
<div id="content">
<tiles:insertAttribute name="content"/>
</div>
</body>
</html>