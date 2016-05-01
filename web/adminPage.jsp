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
    <title>Администрирование</title>
</head>
<body>
<div id="header">
    <div id="logo">
        <h2>AuRe: Страница администратора(продукты, категории, работники)</h2>
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


<h2>Добавить продукт</h2>

<div class="wrapper">
    <form action="${pageContext.request.contextPath}/admin" method="get">
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
            <td>
                <button type="submit" value="Добавить товар"/>
                <a class="addProductButton">Добавить товар</a></td>
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
                <button type="submit" name="productId" value="${product.getId()}"><a class="deleteButton">DELETE</a>
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>
<h2>Добавить категорию</h2>

<form action="${pageContext.request.contextPath}/admin" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Категория:</th>
        </thead>
        <tbody>
        <td><input type="text" name="categoryName" maxlength="255"/></td>
        <td>
            <button type="submit" value="Добавить категорию"/>
            <a class="addProductButton">Добавить категорию</a></td>
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
                <button type="submit" name="categoryId" value="${category.getId()}"><a class="deleteButton">DELETE</a>
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>
<h2>Добавить работника</h2>

<form action="${pageContext.request.contextPath}/admin" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Фамилия:</th>
            <th>Имя:</th>
            <th>Отчество:</th>
            <th>Заработная плата:</th>
        </thead>
        <tbody>
        <td><input type="text" name="surname" maxlength="255"/></td>
        <td><input type="text" name="name" maxlength="255"/></td>
        <td><input type="text" name="patronymic" maxlength="255"/></td>
        <td><input type="text" name="wage" maxlength="255"/></td>
        <td>
            <button type="submit" value="Добавить работника"/>
            <a class="addProductButton">Добавить работника</a></td>
        </tbody>
    </table>
</form>
<h2>Список работников ресторана</h2>
<table border="1">
    <thead>
    <th>Фамилия</th>
    <th>Имя</th>
    <th>Отчество</th>
    <th>Заработная плата</th>
    <th>Удалить</th>
    </thead>
    <tbody>
    <c:forEach var="employee" items="${employeeList}">
    <tr>
        <td>${employee.getSurname()}</td>
        <td>${employee.getName()}</td>
        <td>${employee.getPatronymic()}</td>
        <td>${employee.getWage()}</td>
        <td>
            <form>
                <button type="submit" name="employeeId" value="${employee.getId()}"><a class="deleteButton">DELETE</a>
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<h2>Добавить смену</h2>

<form action="${pageContext.request.contextPath}/admin" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Начало:</th>
            <th>Конец:</th>
        </thead>
        <tbody>
        <td><input type="time" name="start" maxlength="255"/></td>
        <td><input type="time" name="finish" maxlength="255"/></td>
        <td>
            <button type="submit" value="Добавить"/>
            <a class="addProductButton">Добавить</a></td>
        </tbody>
    </table>
</form>
<h2>Список смен</h2>
<table border="1">
    <thead>
    <th>Фамилия</th>
    <th>Имя</th>
    </thead>
    <tbody>
    <c:forEach var="timerange" items="${timerangeList}">
    <tr>
        <td>${timerange.getStart()}</td>
        <td>${timerange.getFinish()}</td>
        <td>
            <form>
                <button type="submit" name="timerangeId" value="${timerange.getId()}"><a class="deleteButton">DELETE</a>
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>
${errorMessage}
</body>
</html>