<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 12.10.2015
  Time: 10:58
  To change this template use File | Settings | File Templates.
  <jsp:forward page="/Pricelist"></jsp:forward>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="index.css" rel="stylesheet" type="text/css">
    <title>Прайс-лист</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/AdminPage">To Admin Page</a> <br> <a href="login.jsp">Login</a>
Прайс-лист
<form action="${pageContext.request.contextPath}/Pricelist" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Категория:</th>
            <th>Наименование:</th>
            <th>Цена от:</th>
            <th>Цена до:</th>
        </thead>
        <tbody>
        <td><input type="text" name="category" maxlength="255"/></td>
        <td><input type="text" name="name" maxlength="255"/></td>
        <td><input type="text" name="priceFrom"/></td>
        <td><input type="text" name="priceTo"/></td>
        <td><input type="submit" value="Найти"/></td>
        </tbody>
    </table>
</form>
<table border="1">
    <thead>
    <tr>
        <th><a href="?sort=category">Категория</a></th>
        <th><a href="?sort=name">Наименование</a></th>
        <th><a href="?sort=price">Цена</a></th>
    </thead>
    <tbody>
    <c:forEach var="product" items="${productsList}">
    <tr>
        <td>${product.getCategory().getName()}</td>
        <td>${product.getName()}</td>
        <td>${product.getPrice()}</td>
    </tr>
    </tbody>
    </c:forEach>
</table>
${errorMessage}
</body>
</html>