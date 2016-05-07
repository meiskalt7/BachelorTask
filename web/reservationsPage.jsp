<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 17.04.2016
  Time: 12:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="index.css" rel="stylesheet" type="text/css">
    <title>Брони</title>
</head>
<body>
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
    <h2>Administrator</h2>
    <ul>
        <li class="li"><a href="${pageContext.request.contextPath}/admin">Администрирование</a></li>
        <li class="li"><a href="${pageContext.request.contextPath}/statistics">Статистика</a></li>
    </ul>
    <br class="clearfix"/>
</div>

<h2>Список броней</h2>
<table border="1">
    <thead>
    <th>ФИО</th>
    <th>Телефон</th>
    <th>Время</th>
    <th>Столик</th>
    </thead>
    <tbody>
    <c:forEach var="reservation" items="${reservationList}">
    <tr>
        <td>${reservation.getName()}</td>
        <td>${reservation.getPhone()}</td>
        <td>${reservation.getDatetime()}</td>
        <td>${reservation.getTable().getType()}</td>
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

</body>
</html>
