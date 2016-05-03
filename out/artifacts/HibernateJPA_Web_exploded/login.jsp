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

<h1>Login Page</h1>

<h2>Signup Details</h2>

<form action="${pageContext.request.contextPath}/loginCheck" method="get"><br/>Username:
    <input type="username" name="username"> <br/>Password:
    <input type="password" name="password"> <br/>
    <input type="submit" value="Submit">
</form>

<c:if test="${sessionScope.username != null}">
    <h1>Logout was done successfully.</h1>
</c:if>

<% if (session.getAttribute("username") != null) {
    session.removeAttribute("username");
}
    session.invalidate(); %>
${errorMessage}
</body>
</html>