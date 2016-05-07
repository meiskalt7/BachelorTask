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
    <title>Смена</title>
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
            <li class="li"><a href="${pageContext.request.contextPath}/reservation">Бронирование</a></li>
            <li class="li"><a href="${pageContext.request.contextPath}/contacts">Контакты</a></li>
            <li class="li">
                <a href="${pageContext.request.contextPath}/login">
                    <c:choose>
                        <c:when test="${sessionScope.userType == null}">
                            Вход в систему
                        </c:when>
                        <c:otherwise>
                            Выход из системы
                        </c:otherwise>
                    </c:choose>
                </a>
            </li>
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
        <c:if test="${sessionScope.userType == 'Max'}">
            <h2>Administrator</h2>
            <ul>
                <li class="li"><a href="${pageContext.request.contextPath}/admin">Администрирование</a></li>
                <li class="li"><a href="${pageContext.request.contextPath}/statistics">Статистика</a></li>
            </ul>
        </c:if>
        <br class="clearfix"/>
    </div>
</div>

<h2>Добавить ингридиент</h2>

<div class="wrapper">
    <form action="${pageContext.request.contextPath}/workshift" method="get">
        <table border="0">
            <thead>
            <tr>
                <th>Название:</th>
                <th>Количество:</th>
            </thead>
            <tbody>
            <td><input type="text" name="name" maxlength="255" required/></td>
            <td><input type="text" name="quantity" required/></td>
            <td>
                <button type="submit" value="Добавить"/>
                <a class="addProductButton">Добавить</a></td>
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
        <td>${ingrid.getName()}</td>
        <td>${ingrid.getQuantity()}</td>
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
    <form action="${pageContext.request.contextPath}/workshift" method="get">
        <table border="0">
            <thead>
            <tr>
                <th>Работник</th>
                <th>День</th>
                <th>Время</th>
            </thead>
            <tbody>
            <td><select multiple name="employee" size="5" required>
                <c:forEach var="employee" items="${employeeList}">
                    <option value="<c:out value='${employee.getId()}'/>"><c:out value='${employee.getName()}'/> <c:out
                            value='${employee.getSurname()}'/></option>
                </c:forEach>
            </select></td>
            <td><input type="date" name="date" required/></td>
            <td><select name="timerange" size="1" required>
                <c:forEach var="timerange" items="${timerangeList}">
                    <option value="<c:out value='${timerange.getId()}'/>"><c:out value='${timerange.getStart()}'/> -
                        <c:out
                                value='${timerange.getFinish()}'/></option>
                </c:forEach>
            </select></td>
            <td>
                <button type="submit" name="button" value="add workshift"/>
                <a class="addProductButton">Добавить</a>
            </td>
            </tbody>
        </table>
    </form>
</div>
<h2>Список смен</h2>

<table border="1">
    <thead>
    <tr>
        <th>День</th>
        <th>Время</th>
        <th>Работник</th>
        <th>Удалить</th>
    </tr>
    <c:forEach var="workshift" items="${workshiftList}">
        <tr>

            <th><c:out value='${workshift.getDate()}'/></th>
            <td><c:forEach var="timerange" items="${timerangeList}">
                <c:if test="${workshift.getTimerange_id() == timerange.getId()}">
                    ${timerange.getStart()} - ${timerange.getFinish()}
                </c:if>
            </c:forEach></td>
            <td><select id="numbers" size="10">
                <c:forEach items='${workshift.getEmployees()}' var='employee'>
                    <option>
                        <c:out value='${employee.getName()}'/> <c:out value='${employee.getSurname()}'/>
                    </option>
                </c:forEach>
            </select></td>
            <td>
                <form>
                    <button type="submit" name="workshiftId" value="${workshift.getId()}"><a
                            class="deleteButton">DELETE</a>
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </thead>
</table>

<h2>Добавить к столику официанта</h2>

<div class="wrapper">
    <form action="${pageContext.request.contextPath}/workshift" method="get">
        <table border="0">
            <thead>
            <tr>
                <th>Работник</th>
                <th>Столики</th>
            </thead>
            <tbody>
            <td><select name="employee" size="1" required>
                <c:forEach var="employee" items="${employeeList}">
                    <option value="<c:out value='${employee.getId()}'/>"><c:out value='${employee.getName()}'/> <c:out
                            value='${employee.getSurname()}'/></option>
                </c:forEach>
            </select></td>
            <td><select multiple name="tables" size="6" required>
                <c:forEach var="table" items="${tableList}">
                    <option value="<c:out value='${table.getId()}'/>"><c:out value='${table.getId()}'/> (<c:out
                            value='${table.getType()}'/>)
                    </option>
                </c:forEach>
            </select></td>
            <td>
                <button type="submit" value="Добавить"/>
                <a class="addProductButton">Добавить</a></td>
            </tbody>
        </table>
    </form>
</div>
<h2>Список официантов-столиков</h2>
<table border="1">
    <thead>
    <tr>
        <th>Работник</th>
        <th>Столики</th>
        <th>Место</th>
    </thead>
    <tbody>
    <c:forEach var="employee" items="${employeeList}">
        <tr>
            <form>
                <td>
                    <c:out value='${employee.getName()}'/> <c:out value='${employee.getSurname()}'/>
                </td>
                <td><select multiple name="tableId" size="6">
                    <c:forEach var="table" items="${employee.getTables()}">
                        <option value="<c:out value='${table.getId()}'/>">${table.getId()} + ${table.getType()})
                        </option>
                    </c:forEach>
                </select></td>
                <td>
                    <input type="hidden" name="id" value="${employee.getId()}">
                    <button type="submit" name="button" value="delete employee"><a class="deleteButton">DELETE</a>
                    </button>
                </td>
            </form>
        </tr>
    </c:forEach>
    </tbody>
</table>

<h2>Добавить столик</h2>

<div class="wrapper">
    <form action="${pageContext.request.contextPath}/workshift" method="get">
        <table border="0">
            <thead>
            <tr>
                <th>Тип</th>
            </thead>
            <tbody>
            <td><input type="text" name="table" required/></td>
            <td>
                <button type="submit" value="Добавить"/>
                <a class="addProductButton">Добавить</a></td>
            </tbody>
        </table>
    </form>
</div>
<h2>Список столиков</h2>
<table border="1">
    <thead>
    <tr>
        <th>Номер</th>
        <th>Тип</th>
    </thead>
    <tbody>
    <c:forEach var="table" items="${tableList}">
    <tr>
        <td>${table.getId()}</td>
        <td>${table.getType()}</td>
        <td>
            <form action="${pageContext.request.contextPath}/workshift" method="get">
                <input type="hidden" name="id" value="${table.getId()}">
                <button type="submit" name="button" value="delete table"><a class="deleteButton">DELETE</a>
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

</body>
</html>
