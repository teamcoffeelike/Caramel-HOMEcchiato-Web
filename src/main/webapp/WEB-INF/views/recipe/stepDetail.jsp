<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${recipe.title}</title>
</head>
<body>
	${recipe.title} ${recipe.id} ${recipe.category} ${recipe.coverImage} ${recipe.author.name} ${recipe.ratings}<br>
	${step.image} ${step.step} ${step.text}
</body>
</html>