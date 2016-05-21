<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 21.05.2016
  Time: 14:15
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
            PopUpHide_table();
        });
    </script>
    <title>Title</title>
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

<h2>Добавить столик</h2>

<div class="wrapper">
    <form action="${pageContext.request.contextPath}/workshift" method="get">
        <table border="0">
            <thead>
            <tr>
                <th>Номер столика</th>
                <th>Тип</th>
            </thead>
            <tbody>
            <td><input type="text" name="number" required/></td>
            <td><input type="text" name="table" required/></td>
            <td>
                <button type="submit" name="button" value="ADD TABLE" class="addProductButton"/>
                Добавить
            </td>
            </tbody>
        </table>
    </form>
</div>

<h2>Список столиков</h2>
<table border="1">
    <thead>
    <tr>
        <th>Номер</th>
        <th>Тип</th>
    </thead>
    <tbody>
    <c:forEach var="table" items="${tableList}">
    <tr>
        <td>${table.getNumber()}</td>
        <td>${table.getType()}</td>
        <td>

            <button type="button"
                    onclick="PopUpShow_table('${table.getId()}', '${table.getNumber()}', '${table.getType()}')"
                    class="updateButton">CHANGE
            </button>
            <form action="${pageContext.request.contextPath}/workshift" method="get">
                <input type="hidden" name="id" value="${table.getId()}">
                <button type="submit" name="button" value="DELETE TABLE" class="deleteButton">DELETE
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<%--Всплывающие окно--%>
<div class="b-popup" id="popup_table">
    <div class="b-popup-content">
        Изменение стола
        <form>
            <input id="id_t" type="hidden" name="id" required>
            <input id="number" type="text" name="number" required/>
            <input id="table" type="text" name="table" required/>
            <button type="submit" name="button" value="UPDATE INGRIDIENT" class="updateButton">UPDATE
            </button>
        </form>
        <a href="javascript:PopUpHide_table()">Отмена</a>
    </div>
</div>

<h2>Добавить к столику официанта</h2>

<div class="wrapper">
    <form action="${pageContext.request.contextPath}/workshift" method="get">
        <table border="0">
            <thead>
            <tr>
                <th>Работник</th>
                <th>Столики</th>
            </thead>
            <tbody>
            <td><select name="employee" size="1" required>
                <c:forEach var="employee" items="${employeeList}">
                    <option value="<c:out value='${employee.getId()}'/>"><c:out value='${employee.getName()}'/> <c:out
                            value='${employee.getSurname()}'/></option>
                </c:forEach>
            </select></td>
            <td><select multiple name="tables" size="6" required>
                <c:forEach var="table" items="${tableList}">
                    <option value="<c:out value='${table.getId()}'/>"><c:out value='${table.getNumber()}'/> (<c:out
                            value='${table.getType()}'/>)
                    </option>
                </c:forEach>
            </select></td>
            <td>
                <button type="submit" name="button" value="ADD TABLES_EMPLOYEES" class="addProductButton"/>
                Добавить
            </td>
            </tbody>
        </table>
    </form>
</div>
<h2>Список официантов-столиков</h2>
<table border="1">
    <thead>
    <tr>
        <th>Работник</th>
        <th>Столики</th>
        <th>Место</th>
    </thead>
    <tbody>
    <c:forEach var="employee" items="${employeeList}">
        <tr>
            <form>
                <td>
                    <c:out value='${employee.getName()}'/> <c:out value='${employee.getSurname()}'/>
                </td>
                <td><select multiple name="tableId" size="6">
                    <c:forEach var="table" items="${employee.getTables()}">
                        <option value="<c:out value='${table.getId()}'/>">${table.getNumber()} + ${table.getType()})
                        </option>
                    </c:forEach>
                </select></td>
                <td>
                    <input type="hidden" name="id" value="${employee.getId()}">
                    <button type="submit" name="button" value="DELETE EMPLOYEE" class="deleteButton">DELETE
                    </button>
                </td>
            </form>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
