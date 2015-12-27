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
<a href="index.jsp">To Pricelist</a> <a href="login.jsp">Login</a> <br>
Добавить продукт(выбор категории, ввод названия продукта и его цены)
<form action="${pageContext.request.contextPath}/Pricelist" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Категория:</th>
            <th>Наименование:</th>
            <th>Цена:</th>
        </thead>
        <tbody>
        <td><select>
            <c:forEach var="product" items="${productsList}">
                <option>${product.getCategory().getName()}</option>
                >
            </c:forEach>
        </select></td>
        <td><input type="text" name="name" maxlength="255"/></td>
        <td><input type="text" name="priceFrom"/></td>
        <td><input type="submit" value="Добавить товар"/></td>
        </tbody>
    </table>
</form>
Список продуктов(удалить, изменить)
<table border="1">
    <thead>
    <tr>
        <th>Категория</th>
        <th>Наименование</th>
        <th>Цена</th>
        <th>Изменить</th>
        <th>Удалить</th>
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
Добавить категорию(введение название категории)
<form action="${pageContext.request.contextPath}/Pricelist" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Категория:</th>
        </thead>
        <tbody>
        <td><input type="text" name="name" maxlength="255"/></td>
        <td><input type="submit" value="Добавить категорию"/></td>
        </tbody>
    </table>
</form>
Список категорий(удалить, изменить)
<table border="1">
    <thead>
    <tr>
        <th>Категория</th>
        <th>Изменить</th>
        <th>Удалить</th>
    </thead>
    <tbody>
    <c:forEach var="product" items="${productsList}">
    <tr>
        <td>${product.getCategory().getName()}</td>
    </tr>
    </tbody>
    </c:forEach>
</table>
${errorMessage}
</body>
</html>