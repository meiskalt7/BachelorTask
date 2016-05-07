<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 17.04.2016
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="index.css" rel="stylesheet" type="text/css">
    <title>Статистика</title>
</head>
<body>
<div id="header">
    <div id="logo">
        <h2>AuRe: Статистика ресторана</h2>
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

<h2>Список чеков(свернуть, развернуть)</h2>

<h2>Сумма всех чеков за месяц(выбор месяца)</h2>

<h2>Сумма всех расходов за месяц</h2>

<h2>Баланс за месяц(выбор месяца, либо всего)</h2>

<h2>Потребность в официантах</h2>

<h2>Статистика уникальных IP</h2>

</body>
</html>
