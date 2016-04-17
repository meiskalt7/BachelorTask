<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 16.04.2016
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="index.css" rel="stylesheet" type="text/css">
    <title>Manager Page</title>
</head>
<body>

<div id="header">
    <div id="logo">
        <h2>AuRe: Страница менеджера смены</h2>
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

<h2>Добавить ингридиент</h2>

<div class="wrapper">
    <form action="${pageContext.request.contextPath}/ManagerPage" method="get">
        <table border="0">
            <thead>
            <tr>
                <th>Название:</th>
                <th>Количество:</th>
            </thead>
            <tbody>
            <td><input type="text" name="name" maxlength="255"/></td>
            <td><input type="text" name="quantity"/></td>
            <td>
                <button type="submit" value="Добавить ингридиент"/>
                <a class="addProductButton">Добавить ингридиент</a></td>
            </tbody>
        </table>
    </form>
</div>
<h2>Список ингридиентов</h2>
<table border="1">
    <thead>
    <tr>
        <th>Название</th>
        <th>Количество</th>
        <th>Удалить</th>
    </thead>
    <tbody>
    <c:forEach var="ingrid" items="${ingridList}">
    <tr>
        <td>${ingrid.getCategory().getName()}</td>
        <td>${ingrid.getName()}</td>
        <td>${ingrid.getPrice()}</td>
        <td>
            <form>
                <button type="submit" name="ingridId" value="${ingrid.getId()}"><a class="deleteButton">DELETE</a>
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<h2>Добавить смену</h2>

<div class="wrapper">
    <form action="${pageContext.request.contextPath}/ManagerPage" method="get">
        <table border="0">
            <thead>
            <tr>
                <th>Работник</th>
                <th>Время</th>
            </thead>
            <tbody>
            <td><input type="text" name="name" maxlength="255"/></td>
            <td><input type="text" name="quantity"/></td>
            <td>
                <button type="submit" value="Добавить ингридиент"/>
                <a class="addProductButton">Добавить ингридиент</a></td>
            </tbody>
        </table>
    </form>
</div>
<h2>Список смен</h2>
<table border="1">
    <thead>
    <tr>
        <th>Работник</th>
        <th>Время</th>
        <th>Удалить</th>
    </thead>
    <tbody>
    <c:forEach var="ingrid" items="${ingridList}">
    <tr>
        <td>${ingrid.getCategory().getName()}</td>
        <td>${ingrid.getName()}</td>
        <td>${ingrid.getPrice()}</td>
        <td>
            <form>
                <button type="submit" name="ingridId" value="${ingrid.getId()}"><a class="deleteButton">DELETE</a>
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<h2>Добавить столик</h2>

<div class="wrapper">
    <form action="${pageContext.request.contextPath}/ManagerPage" method="get">
        <table border="0">
            <thead>
            <tr>
                <th>Работник</th>
                <th>Столик</th>
            </thead>
            <tbody>
            <td><input type="text" name="name" maxlength="255"/></td>
            <td><input type="text" name="quantity"/></td>
            <td>
                <button type="submit" value="Добавить ингридиент"/>
                <a class="addProductButton">Добавить ингридиент</a></td>
            </tbody>
        </table>
    </form>
</div>
<h2>Список столиков</h2>
<table border="1">
    <thead>
    <tr>
        <th>Работник</th>
        <th>Столик</th>
        <th>Удалить</th>
    </thead>
    <tbody>
    <c:forEach var="ingrid" items="${ingridList}">
    <tr>
        <td>${ingrid.getCategory().getName()}</td>
        <td>${ingrid.getName()}</td>
        <td>${ingrid.getPrice()}</td>
        <td>
            <form>
                <button type="submit" name="ingridId" value="${ingrid.getId()}"><a class="deleteButton">DELETE</a>
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

</body>
</html>
