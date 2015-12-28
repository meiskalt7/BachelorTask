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

<div id="header">
    <div id="logo">
        <h2>МЕНЮ РЕСТОРАНА:Страница администрирования</h2>
    </div>
    <div id="menu">
        <ul>
            <li class="li"><a href="${pageContext.request.contextPath}/AdminPage">To Admin Page</a></li>
            <li class="li"><a href="${pageContext.request.contextPath}/Pricelist">To Pricelist</a></li>
        </ul>
        <br class="clearfix"/>
    </div>
</div>


<h2>Добавить продукт</h2>

<div class="wrapper">
<form action="${pageContext.request.contextPath}/AdminPage" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Категория:</th>
            <th>Наименование:</th>
            <th>Цена:</th>
        </thead>
        <tbody>
        <td><select name="categoryId">
            <c:forEach var="category" items="${categoryList}">
                <option value="${category.getId()}">${category.getName()}</option>
            </c:forEach>
        </select></td>
        <td><input type="text" name="name" maxlength="255"/></td>
        <td><input type="text" name="price"/></td>
        <td><button type="submit" value="Добавить товар"/><a class="addProductButton">Добавить товар</a></td>
        </tbody>
    </table>
</form>
</div>
<h2>Список продуктов</h2>
<table border="1">
    <thead>
    <tr>
        <th>Категория</th>
        <th>Наименование</th>
        <th>Цена</th>
        <th>Удалить</th>
    </thead>
    <tbody>
    <c:forEach var="product" items="${productsList}">
    <tr>
        <td>${product.getCategory().getName()}</td>
        <td>${product.getName()}</td>
        <td>${product.getPrice()}</td>
        <td>
            <form>
                <button type="submit" name="productId" value="${product.getId()}"><a class="deleteButton">DELETE</a></button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>
<h2>Добавить категорию</h2>
<form action="${pageContext.request.contextPath}/AdminPage" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Категория:</th>
        </thead>
        <tbody>
        <td><input type="text" name="categoryName" maxlength="255"/></td>
        <td><button type="submit" value="Добавить категорию"/><a class="addProductButton">Добавить категорию</a></td>
        </tbody>
    </table>
</form>
<h2>Список категорий</h2>
<table border="1">
    <thead>
    <tr>
        <th>Категория</th>
        <th>Удалить</th>
    </thead>
    <tbody>
    <c:forEach var="category" items="${categoryList}">
    <tr>
        <td>${category.getName()}</td>
        <td>
            <form>
                <button type="submit" name="categoryId" value="${category.getId()}"><a class="deleteButton">DELETE</a></button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>
${errorMessage}
</body>
</html>