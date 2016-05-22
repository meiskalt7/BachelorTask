<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../css/index.css" rel="stylesheet" type="text/css">
    <link href="../css/accordion-menu.css" rel="stylesheet" type="text/css"/>
    <script src="../js/accordion-menu.js" type="text/javascript"></script>
    <script src="../js/float-panel.js"></script>
    <title>Брони</title>
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
                    <option value="<c:out value='${table.getId()}'/>"><c:out value='${table.getNumber()}'/> (<c:out
                            value='${table.getType()}'/>)
                    </option>
                </c:forEach>
            </select></td>
            <td>
                <button type="submit" name="button" value="ADD RESERVATION" class="addProductButton"/>
                Забронировать
            </td>
            </tbody>
        </table>
    </form>
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
                <input type="hidden" name="id" value="${reservation.getId()}">
                <button type="submit" name="button" value="DELETE RESERVATION" class="deleteButton">DELETE
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

</body>
</html>
