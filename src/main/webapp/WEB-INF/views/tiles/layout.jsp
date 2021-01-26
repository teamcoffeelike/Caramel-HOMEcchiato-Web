<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel='stylesheet' type='text/css' 
		href='css/common.css?v=<%=new java.util.Date().getTime()%>'>
<link rel="icon" type="image/svg+xml" href="imgs/logo.svg">
<meta charset="UTF-8">
<title>Caramel HOMEcchiato</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/js/all.min.js"></script>
</head>
<body>
<tiles:insertAttribute name="header"/>
<div id="content">
<tiles:insertAttribute name="content"/>
</div>
</body>
</html>