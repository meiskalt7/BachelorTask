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

<h1>Login Page</h1>

<h2>Signup Details</h2>

<form action="${pageContext.request.contextPath}/loginCheck" method="get"><br/>Username:
    <input type="username" name="username" required> <br/>Password:
    <input type="password" name="password" required> <br/>
    <input type="submit" value="Submit">
</form>

<c:if test="${sessionScope.userType != null}">
    <h1>Logout was done successfully.</h1>
</c:if>

<% if (session.getAttribute("username") != null) {
    session.removeAttribute("username");
}
    session.invalidate(); %>
${errorMessage}
</body>
</html>