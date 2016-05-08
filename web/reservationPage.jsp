<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 17.04.2016
  Time: 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="index.css" rel="stylesheet" type="text/css">
    <title>Reservation Page</title>
</head>
<body>

<div id="header">
    <div id="logo">
        <h2>AuRe: Страница бронирования столиков</h2>
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
        <c:if test="${'admin'.equals(sessionScope.userType) || 'manager'.equals(sessionScope.userType) || 'waiter'.equals(sessionScope.userType)}">
            <h2>Waiter</h2>
            <ul>
                <li class="li"><a href="${pageContext.request.contextPath}/orders">Заказы</a></li>
                <li class="li"><a href="${pageContext.request.contextPath}/reservations">Брони</a></li>
            </ul>
        </c:if>
        <c:if test="${'admin'.equals(sessionScope.userType) || 'manager'.equals(sessionScope.userType)}">
            <h2>Manager</h2>
            <ul>
                <li class="li"><a href="${pageContext.request.contextPath}/workshift">Смена</a></li>
            </ul>
        </c:if>
        <c:if test="${'admin'.equals(sessionScope.userType)}">
            <h2>Administrator</h2>
            <ul>
                <li class="li"><a href="${pageContext.request.contextPath}/admin">Администрирование</a></li>
                <li class="li"><a href="${pageContext.request.contextPath}/statistics">Статистика</a></li>
            </ul>
        </c:if>
        <br class="clearfix"/>
    </div>
</div>

<h2>Забронировать столик</h2>

<div class="wrapper">
    <form action="${pageContext.request.contextPath}/reservation" method="get">
        <table border="0">
            <thead>
            <tr>
                <th>ФИО</th>
                <th>Телефон(9XX-XXX-XXXX)</th>
                <th>Время</th>
                <th>Столик</th>
            </thead>
            <tbody>
            <td><input type="text" name="name" maxlength="255" required/></td>
            <td><input type="tel" name="phone" maxlength="255" pattern="9[0-9]{2}-[0-9]{3}-[0-9]{4}" required/></td>
            <td><input type="datetime-local" name="time" maxlength="255" required/></td>
            <td><select name="tableId">
                <c:forEach var="table" items="${tableList}">
                    <option value="<c:out value='${table.getId()}'/>"><c:out value='${table.getId()}'/> (<c:out
                            value='${table.getType()}'/>)
                    </option>
                </c:forEach>
            </select></td>
            <td>
                <button type="submit" name="button" value="add reservation" class="addProductButton"/>
                Забронировать
            </td>
            </tbody>
        </table>
    </form>
</div>
${resultMessage}
</body>
</html>
