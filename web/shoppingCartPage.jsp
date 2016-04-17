<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 17.04.2016
  Time: 12:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="index.css" rel="stylesheet" type="text/css">
    <title>Title</title>
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

</body>
</html>
