<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 21.05.2016
  Time: 14:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Расписание</title>
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
                            <li class="li"><a href="${pageContext.request.contextPath}/products">Блюда</a></li>
                            <li class="li"><a href="${pageContext.request.contextPath}/admin">Работники</a></li>
                            <li class="li"><a href="${pageContext.request.contextPath}/workshift">Расписание</a></li>
                            <li class="li"><a href="${pageContext.request.contextPath}/statistics">Статистика</a></li>
                        </ul>
                    </c:if>
                </li>
            </ul>
        </div>
    </div>
</div>

<h2>Добавить расписание</h2>

<div class="wrapper">
    <form action="${pageContext.request.contextPath}/workshift" method="get">
        <table border="0">
            <thead>
            <tr>
                <th>Работник</th>
                <th>День</th>
                <th>Время</th>
            </thead>
            <tbody>
            <td><select multiple name="employee" size="5" required>
                <c:forEach var="employee" items="${employeeList}">
                    <option value="<c:out value='${employee.getId()}'/>"><c:out value='${employee.getName()}'/> <c:out
                            value='${employee.getSurname()}'/></option>
                </c:forEach>
            </select></td>
            <td><input type="date" name="date" required min="${today}" value="${today}"/></td>
            <td><select name="timerange" size="1" required>
                <c:forEach var="timerange" items="${timerangeList}">
                    <option value="<c:out value='${timerange.getId()}'/>"><c:out value='${timerange.getStart()}'/> -
                        <c:out
                                value='${timerange.getFinish()}'/></option>
                </c:forEach>
            </select></td>
            <td>
                <button type="submit" name="button" value="ADD WORKSHIFT" class="addProductButton"/>
                Добавить
            </td>
            </tbody>
        </table>
    </form>
</div>
<h2>Расписание</h2>

<table border="1">
    <thead>
    <tr>
        <th>День</th>
        <th>Время</th>
        <th>Работник</th>
        <th>Удалить</th>
    </tr>
    <c:forEach var="workshift" items="${workshiftList}">
        <tr>

            <th><c:out value='${workshift.getDate()}'/></th>
            <td><c:forEach var="timerange" items="${timerangeList}">
                <c:if test="${workshift.getTimerange_id() == timerange.getId()}">
                    ${timerange.getStart()} - ${timerange.getFinish()}
                </c:if>
            </c:forEach></td>
            <td><select id="numbers" size="10">
                <c:forEach items='${workshift.getEmployees()}' var='employee'>
                    <option>
                        <c:out value='${employee.getName()}'/> <c:out value='${employee.getSurname()}'/>
                    </option>
                </c:forEach>
            </select></td>
            <td>
                <form>
                    <input type="hidden" name="id" value="${workshift.getId()}">
                    <button type="submit" name="button" value="DELETE WORKSHIFT" class="deleteButton">DELETE
                    </button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </thead>
</table>

</body>
</html>
