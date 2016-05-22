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
    <link href="../css/index.css" rel="stylesheet" type="text/css">
    <link href="../css/popup.css" rel="stylesheet" type="text/css">
    <link href="../css/accordion-menu.css" rel="stylesheet" type="text/css"/>
    <script src="../js/accordion-menu.js" type="text/javascript"></script>
    <script src="../js/popup.js" type="text/javascript"></script>
    <script src="../js/float-panel.js"></script>
    <script type="text/javascript" src="../js/jquery-2.2.3.min.js"></script>
    <script type="text/javascript">

        $(document).ready(function () {
            PopUpHide_timeRange();
        });
    </script>
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

<c:if test="${'admin'.equals(sessionScope.userType)}">
    <h2>Добавить смену</h2>

    <form action="${pageContext.request.contextPath}/workshift" method="get">
        <table border="0">
            <thead>
            <tr>
                <th>Начало</th>
                <th>Конец</th>
            </thead>
            <tbody>
            <td><input type="time" name="start" maxlength="255" required/></td>
            <td><input type="time" name="finish" maxlength="255" required/></td>
            <td>
                <button type="submit" name="button" value="ADD TIMERANGE" class="addProductButton"/>
                Добавить
            </td>
            </tbody>
        </table>
    </form>
</c:if>

<h2>Список смен</h2>
<table border="1">
    <thead>
    <th>Начало</th>
    <th>Конец</th>
    </thead>
    <tbody>
    <%-- jsp:useBean изучить --%>
    <c:forEach var="timerange" items="${timerangeList}" varStatus="loop">
    <tr>
        <td>${timerange.getStart()}</td>
        <td>${timerange.getFinish()}</td>
        <td>
            <button type="button"
                    onclick="PopUpShow_timeRange('${timerange.getId()}', '${timerangeList.get(id).getStart().toString().substring(0,5)}', '${timerangeList.get(id).getFinish().toString().substring(0,5)}')"
                    class="updateButton">CHANGE
            </button>
            <form action="${pageContext.request.contextPath}/workshift" method="get">
                <input type="hidden" name="id" value="${timerange.getId()}">
                <button type="submit" name="button" value="DELETE TIMERANGE" class="deleteButton">DELETE
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<div class="b-popup" id="popup_timeRange">
    <div class="b-popup-content">
        Изменение работы смены
        <form action="${pageContext.request.contextPath}/workshift" method="get">
            <input id="id" type="hidden" name="id" required>
            <input id="start" type="time" name="start" maxlength="255" required/>
            <input id="finish" type="time" name="finish" maxlength="255" required/>
            <button type="submit" name="button" value="UPDATE TIMERANGE" class="updateButton">UPDATE
            </button>
        </form>
        <a href="javascript:PopUpHide_timeRange()">Отмена</a>
    </div>
</div>

</body>
</html>
