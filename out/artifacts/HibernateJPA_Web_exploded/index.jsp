<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 12.10.2015
  Time: 10:58
  To change this template use File | Settings | File Templates.
  <jsp:forward page="/pricelist"></jsp:forward>
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
        <h2>AuRe: Прайс-лист</h2>
    </div>
    <div id="menu">
        <h2>Меню сайта</h2>
        <ul>
            <li class="li"><a href="${pageContext.request.contextPath}/main">Главная страница</a></li>
            <li class="li"><a href="${pageContext.request.contextPath}/pricelist">Прайс-лист</a></li>
            <li class="li"><a href="${pageContext.request.contextPath}/cart">Заказ</a></li>
        </ul>
        <ul>
            <li class="li"><a href="${pageContext.request.contextPath}/reservations">Бронирование</a></li>
            <li class="li"><a href="${pageContext.request.contextPath}/contacts">Контакты</a></li>
            <li class="li"><a href="${pageContext.request.contextPath}/login">Вход в систему</a></li>
        </ul>
        <h2>Waiter</h2>
        <ul>
            <li class="li"><a href="${pageContext.request.contextPath}/orders">Заказы</a></li>
            <li class="li"><a href="${pageContext.request.contextPath}/reservations">Брони</a></li>
        </ul>
        <h2>Manager</h2>
        <ul>
            <li class="li"><a href="${pageContext.request.contextPath}/workshift">Смена</a></li>
        </ul>
        <h2>Administrator</h2>
        <ul>
            <li class="li"><a href="${pageContext.request.contextPath}/admin">Администрирование</a></li>
            <li class="li"><a href="${pageContext.request.contextPath}/statistics">Статистика</a></li>
        </ul>
        <br class="clearfix"/>
    </div>
</div>

<form action="${pageContext.request.contextPath}/pricelist" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Категория</th>
            <th>Наименование</th>
            <th>Цена от</th>
            <th>Цена до</th>
        </thead>
        <tbody>
        <td><select name="categoryId" required>
            <option selected value="all">Все</option>
            <c:forEach var="category" items="${categoryList}">
                <option value="${category.getId()}">${category.getName()}</option>
            </c:forEach>
        </select></td>
        <td><input type="text" name="name" maxlength="255"/></td>
        <td><input type="text" name="priceFrom"/></td>
        <td><input type="text" name="priceTo"/></td>
        <td><input type="submit" value="Найти"/></td>
        </tbody>
    </table>
</form>

<form action="${pageContext.request.contextPath}/pricelist" method="get">
    <table border="1">
        <thead>
        <tr>
            <th>Категория</th>
            <th>Наименование</th>
            <th>Цена</th>
            <th>Количество</th>
            <th>Заказать</th>
        </thead>
        <tbody>
        <c:forEach var="product" items="${productsList}">
        <tr>
            <td>${product.getCategory().getName()}</td>
            <td>${product.getName()}</td>
            <td>${product.getPrice()}</td>
            <td><input type="number" name="quantity"/></td>
            <input type="hidden" name="productId" value="${product.getId()}">
            <td>
                <button type="submit" name="button" value="add cart"><a class="addProductButton">В
                    корзину</a>
                </button>
            </td>
        </tr>
        </tbody>
        </c:forEach>
    </table>
    ${errorMessage}
</form>
</body>
</html>