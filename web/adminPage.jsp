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

<h2>Добавить продукт</h2>
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
        <td><input type="submit" value="Добавить товар"/></td>
        </tbody>
    </table>
</form>
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
                <button type="submit" name="productId" value="${product.getId()}">delete</button>
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
        <td><input type="submit" value="Добавить категорию"/></td>
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
                <button type="submit" name="productId" value="${category.getId()}">delete</button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>
${errorMessage}
</body>
</html>