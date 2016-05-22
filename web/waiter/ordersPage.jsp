<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../css/index.css" rel="stylesheet" type="text/css">
    <link href="../css/accordion-menu.css" rel="stylesheet" type="text/css"/>
    <script src="../js/accordion-menu.js" type="text/javascript"></script>
    <script src="../js/float-panel.js"></script>
    <title>Заказы</title>
</head>
<body>

<div id="header">
    <div id="logo">
        <h1>AuRe: Страница администратора</h1>
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

<h2>Список заказов</h2>
<table border="1">
    <thead>
    <tr>
        <th>Столик</th>
        <th>Время заказа</th>
        <th>Блюда:Стоимость</th>
        <th>Итого</th>
        <th></th>
        <th></th>
    </thead>
    <c:forEach var="order" items="${orderList}">
        <tbody>
        <tr>
            <td>${order.getTable().getNumber()} ${order.getTable().getType()}</td>
            <td>${order.getDatetime()}</td>
            <td><c:forEach var="orderlist" items="${order.getOrderlists()}">
                <p>${orderlist.getProduct().getName()} :
                        ${orderlist.getProduct().getPrice()} </p>
            </c:forEach></td>
            <td>
                <c:set var="total" value="${0}"/>
                <c:forEach var="orderlist" items="${order.getOrderlists()}">
                    <c:set var="total" value="${total + orderlist.getProduct().getPrice()}"/>
                </c:forEach>
                    ${total}
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/orders" method="get">
                    <input type="hidden" name="id" value="${order.getId()}">
                    <button type="submit" name="button" value="DELETE ORDER" class="deleteButton">DELETE
                    </button>
                </form>
            </td>
            <td>
                <c:choose>
                    <c:when test="${order.isEnded() != true}">
                        <form action="${pageContext.request.contextPath}/orders" method="get">
                            <input type="hidden" name="id" value="${order.getId()}">
                            <button type="submit" name="button" value="UPDATE ORDER" class="deleteButton">ЗАКРЫТЬ ЗАКАЗ
                            </button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" name="button" value="" class="deleteButton" disabled>Закрыт
                        </button>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
        </tbody>
    </c:forEach>
</table>

</body>
</html>
