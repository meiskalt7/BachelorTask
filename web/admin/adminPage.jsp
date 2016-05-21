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
    <link href="../css/index.css" rel="stylesheet" type="text/css">
    <link href="../css/popup.css" rel="stylesheet" type="text/css">
    <link href="../css/accordion-menu.css" rel="stylesheet" type="text/css"/>
    <script src="../js/accordion-menu.js" type="text/javascript"></script>
    <script src="../js/popup.js" type="text/javascript"></script>
    <script src="../js/float-panel.js"></script>
    <title>Администрирование</title>
    <script type="text/javascript" src="../js/jquery-2.2.3.min.js"></script>

    <script type="text/javascript">

        $(document).ready(function () {

            PopUpHide_timeRange();
            PopUpHide_employee();
            PopUpHide_category();

            var ingridDiv = $('#ingridDiv');
            var i = $('#ingridDiv p').size() + 1;

            $(document).on('click', '#delete_ingrid_button' + i, function () {
                alert('Удалим элемент' + i);
                if (i > 0) {
                    i--;
                    alert('Удалим элемент' + i);
                    jQuery('#selectIngrid' + i).remove();
                }
                return false;
            });

            $("#add_ingrid_button").click(function () {
                alert('добавлен элемент' + i);
                jQuery('#delete_ingrid_button' + (i - 1)).remove();
                $('<p id="selectIngrid' + i + '"><select name="ingridientsId" required><option selected value="default">Выберите ингридиент</option>' + '<c:forEach var="ingrid" items="${ingridList}"><option value="${ingrid.getId()}">${ingrid.getName()}</option></c:forEach></select><input type="text" name="quantity"/><button id="delete_ingrid_button' + i + '" type="button" class="deleteButton">x</button></p>').appendTo(ingridDiv);
                i++;
                return false;
            });
        });

    </script>
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

<h2>Добавить работника</h2>

<form action="${pageContext.request.contextPath}/admin" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Фамилия</th>
            <th>Имя</th>
            <th>Отчество</th>
            <th>Заработная плата</th>
            <th>Юзер</th>
            <th>Пароль</th>
            <th>Тип юзера</th>
        </thead>
        <tbody>
        <td><input type="text" name="surname" maxlength="255" required/></td>
        <td><input type="text" name="name" maxlength="255" required/></td>
        <td><input type="text" name="patronymic" maxlength="255" required/></td>
        <td><input type="text" name="wage" maxlength="255" required/></td>
        <td><input type="username" name="username" maxlength="255" required/></td>
        <td><input type="password" name="password" maxlength="255" required/></td>
        <td><select name="userTypeId" required>
            <c:forEach var="userType" items="${userTypeList}">
                <option value="${userType.getId()}">${userType.getType()}</option>
            </c:forEach>
        </select></td>
        <td>
            <button type="submit" name="button" value="ADD EMPLOYEE" class="addProductButton"/>
            Добавить работника
        </td>
        </tbody>
    </table>
</form>

<h2>Список работников ресторана</h2>
<table border="1">
    <thead>
    <th>Фамилия</th>
    <th>Имя</th>
    <th>Отчество</th>
    <th>Заработная плата</th>
    <th>Юзер</th>
    <th>Пароль</th>
    <th>Тип юзера</th>
    <th>Удалить</th>
    </thead>
    <tbody>
    <c:forEach var="employee" items="${employeeList}">
    <tr>
        <td>${employee.getSurname()}</td>
        <td>${employee.getName()}</td>
        <td>${employee.getPatronymic()}</td>
        <td>${employee.getWage()}</td>
        <td>${employee.getUsername()}</td>
        <td>${employee.getPassword()}</td>
        <td>${employee.getUserType().getType()}</td>
        <td>
            <button type="button"
                    onclick="PopUpShow_employee('${employee.getId()}', '${employee.getSurname()}', '${employee.getName()}', '${employee.getPatronymic()}', '${employee.getWage()}', '${employee.getUsername()}', '${employee.getPassword()}', '${employee.getUserType().getId()}')"
                    class="updateButton">CHANGE
            </button>
            <form>
                <input type="hidden" name="employeeId" value="${employee.getId()}">
                <button type="submit" name="button" value="DELETE EMPLOYEE" class="deleteButton">DELETE
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<%--Всплывающие окно--%>
<div class="b-popup" id="popup_employee">
    <div class="b-popup-content">
        Изменение данных о работнике
        <form>
            <input id="id_e" type="hidden" name="id" required>
            <input id="surname" type="text" name="surname" maxlength="255" required/>
            <input id="name" type="text" name="name" maxlength="255" required/>
            <input id="patronymic" type="text" name="patronymic" maxlength="255" required/>
            <input id="wage" type="text" name="wage" maxlength="255" required/>
            <input id="username" type="username" name="username" maxlength="255" required/>
            <input id="password" type="password" name="password" maxlength="255" required/>
            <select id="type" name="userTypeId" required>
                <c:forEach var="userType" items="${userTypeList}">
                    <option value="${userType.getId()}">${userType.getType()}</option>
                </c:forEach>
            </select>
            <button type="submit" name="button" value="UPDATE EMPLOYEE" class="updateButton">UPDATE
            </button>
        </form>
        <a href="javascript:PopUpHide_employee()">Отмена</a>
    </div>
</div>

<h2>Добавить смену</h2>

<form action="${pageContext.request.contextPath}/admin" method="get">
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
            <form>
                <input type="hidden" name="timerangeId" value="${timerange.getId()}">
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
        <form>
            <input id="id" type="hidden" name="id" required>
            <input id="start" type="time" name="start" maxlength="255" required/>
            <input id="finish" type="time" name="finish" maxlength="255" required/>
            <button type="submit" name="button" value="UPDATE TIMERANGE" class="updateButton">UPDATE
            </button>
        </form>
        <a href="javascript:PopUpHide_timeRange()">Отмена</a>
    </div>
</div>
${errorMessage}
</body>
</html>