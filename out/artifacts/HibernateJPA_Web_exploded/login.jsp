<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="css/index.css" rel="stylesheet" type="text/css">
    <link href="css/accordion-menu.css" rel="stylesheet" type="text/css"/>
    <script src="js/accordion-menu.js" type="text/javascript"></script>
    <script src="js/float-panel.js"></script>
    <title>Вход в систему</title>
</head>
<body>
<div id="header">
    <div id="logo">
        <h1>AuRe</h1>
        <h1>~Авторизация~</h1>
    </div>
    <div id="menu" class="float-panel">
        <div id="accordion">
            <ul>
                <li>
                    <div>Меню сайта</div>
                    <ul>
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
                            <li class="li"><a href="${pageContext.request.contextPath}/pricelist">Меню ресторана</a>
                            </li>
                            <li class="li"><a href="${pageContext.request.contextPath}/orders">Заказы</a></li>
                            <li class="li"><a href="${pageContext.request.contextPath}/reservations">Брони</a></li>
                        </ul>
                    </c:if>
                </li>
                <li>
                    <c:if test="${'admin'.equals(sessionScope.userType) || 'manager'.equals(sessionScope.userType)}">
                        <div>Manager</div>
                        <ul>
                            <li class="li"><a href="${pageContext.request.contextPath}/products">Блюда</a></li>
                            <li class="li"><a href="${pageContext.request.contextPath}/stock">Ингридиенты</a></li>
                            <li class="li"><a href="${pageContext.request.contextPath}/workshift">Расписание</a></li>
                            <li class="li"><a href="${pageContext.request.contextPath}/hall">Зал</a></li>
                        </ul>
                    </c:if>
                </li>
                <li>
                    <c:if test="${'admin'.equals(sessionScope.userType)}">
                        <div>Administrator</div>
                        <ul>
                            <li class="li"><a href="${pageContext.request.contextPath}/admin">Работники</a></li>
                            <li class="li"><a href="${pageContext.request.contextPath}/statistics">Статистика</a></li>
                        </ul>
                    </c:if>
                </li>
            </ul>
        </div>
    </div>
</div>

<br/>
<br/>
<h2>Signup Details</h2>
<div>
    <form action="${pageContext.request.contextPath}/loginCheck" method="get">
        <div id="text"><br/>Username:
            <input type="username" name="username" required>
            <br/> Password:
            <input type="password" name="password" required> <br/>
            <button type="submit" name="button" value="Sumbit" class="addProductButton">
                Войти
            </button>
        </div>
    </form>
</div>

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