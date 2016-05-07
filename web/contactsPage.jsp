<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 17.04.2016
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="index.css" rel="stylesheet" type="text/css">
    <title>Контакты</title>
</head>
<body>

<div id="header">
    <div id="logo">
        <h2>AuRe: Контакты</h2>
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
        <h2>Administrator</h2>
        <ul>
            <li class="li"><a href="${pageContext.request.contextPath}/admin">Администрирование</a></li>
            <li class="li"><a href="${pageContext.request.contextPath}/statistics">Статистика</a></li>
        </ul>
        <br class="clearfix"/>
    </div>
</div>

<h2>The Golden Spruce</h2>
The Golden Spruce - ресторан, который..., а так же..., к тому же...
<h2>Адрес ресторана</h2>
ул. Ленина 17
(карту бы вставить)
<h2>Телефон</h2>
8-888-888-8888
<h2>Ешь в Golden Spruce! Живи в удовольствие!</h2>

</body>
</html>
