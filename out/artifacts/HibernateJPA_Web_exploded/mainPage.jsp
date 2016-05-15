<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 17.04.2016
  Time: 11:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="css/index.css" rel="stylesheet" type="text/css">
    <link href="css/accordion-menu.css" rel="stylesheet" type="text/css"/>
    <script src="js/accordion-menu.js" type="text/javascript"></script>
    <script src="js/float-panel.js"></script>
    <title>Главная</title>
</head>
<body>
<div id="menu" class="float-panel">
    <div id="accordion">
        <ul>
            <li>
                <div>Меню сайта</div>
                <ul>
                    <li class="li"><a href="${pageContext.request.contextPath}/main">Главная страница</a></li>
                    <li class="li"><a href="${pageContext.request.contextPath}/pricelist">Прайс-лист</a></li>
                    <li class="li"><a href="${pageContext.request.contextPath}/cart">Заказ</a></li>
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
            </li>
            <li>
                <c:if test="${'admin'.equals(sessionScope.userType) || 'manager'.equals(sessionScope.userType) || 'waiter'.equals(sessionScope.userType)}">
                    <div>Waiter</div>
                    <ul>
                        <li class="li"><a href="${pageContext.request.contextPath}/orders">Заказы</a></li>
                        <li class="li"><a href="${pageContext.request.contextPath}/reservations">Брони</a></li>
                    </ul>
                </c:if>
            </li>
            <li>
                <c:if test="${'admin'.equals(sessionScope.userType) || 'manager'.equals(sessionScope.userType)}">
                    <div>Manager</div>
                    <ul>
                        <li class="li"><a href="${pageContext.request.contextPath}/workshift">Смена</a></li>
                    </ul>
                </c:if>
            </li>
            <li>
                <c:if test="${'admin'.equals(sessionScope.userType)}">
                    <div>Administrator</div>
                    <ul>
                        <li class="li"><a href="${pageContext.request.contextPath}/admin">Администрирование</a></li>
                        <li class="li"><a href="${pageContext.request.contextPath}/statistics">Статистика</a></li>
                    </ul>
                </c:if>
            </li>
        </ul>
    </div>
</div>
<c:if test="${sessionScope.userType != null}">
    ${sessionScope.userType}
    <h2></h2> <br/> <br/> <br/><br/><br/><br/><br/> <a href="logout.jsp">Logout</a>
</c:if>
</body>
</html>
