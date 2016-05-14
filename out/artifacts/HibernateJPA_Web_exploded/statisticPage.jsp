<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 17.04.2016
  Time: 10:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="index.css" rel="stylesheet" type="text/css">
    <link href="accordion-menu.css" rel="stylesheet" type="text/css"/>
    <script src="accordion-menu.js" type="text/javascript"></script>
    <script src="float-panel.js"></script>
    <title>Статистика</title>
</head>
<body>
<div id="header">
    <div id="logo">
        <h2>AuRe: Статистика ресторана</h2>
    </div>
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
</div>

<h2>Сумма чеков за месяц(выбор месяца, либо всего)</h2>

<form action="${pageContext.request.contextPath}/statistics" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Месяц:</th>
            <th></th>
        </thead>
        <tbody>
        <td><select name="month" size="1">
            <option value="-1">Все месяцы</option>
            <option value="0">Январь</option>
            <option value="1">Февраль</option>
            <option value="2">Март</option>
            <option value="3">Апрель</option>
            <option value="4">Май</option>
            <option value="5">Июнь</option>
            <option value="6">Июль</option>
            <option value="7">Август</option>
            <option value="8">Сентябрь</option>
            <option value="9">Октябрь</option>
            <option value="10">Ноябрь</option>
            <option value="11">Декабрь</option>
        </select></td>
        <td>
            <button type="submit" name="button" value="READ ORDER" class="addProductButton"/>
            Показать
        </td>
        </tbody>
    </table>
</form>

<table border="1">
    <thead>
    <tr>
        <th>Столик</th>
        <th>Время заказа</th>
        <th>Продукты:Стоимость</th>
        <th>Итого</th>
        <th></th>
        <th></th>
    </thead>
    <c:set var="totalAll" value="${0}"/>
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
                <c:set var="totalAll" value="${totalAll + total}"/>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/orders" method="get">
                    <input type="hidden" name="id" value="${order.getId()}">
                    <button type="submit" name="button" value="delete order" class="deleteButton">DELETE
                    </button>
                </form>
            </td>
        </tr>
        </tbody>
    </c:forEach>
    <tfoot>
    <tr>
        <td colspan="3">Общий счет:</td>
        <td>${totalAll}</td>
    </tr>
    </tfoot>

</table>

<h2>Сумма всех расходов за месяц(выбор месяца, либо всего)</h2>

<form action="${pageContext.request.contextPath}/statistics" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Месяц:</th>
        </thead>
        <tbody>
        <td><select name="month" size="1">
            <option value="-1">Все месяцы</option>
            <option value="0">Январь</option>
            <option value="1">Февраль</option>
            <option value="2">Март</option>
            <option value="3">Апрель</option>
            <option value="4">Май</option>
            <option value="5">Июнь</option>
            <option value="6">Июль</option>
            <option value="7">Август</option>
            <option value="8">Сентябрь</option>
            <option value="9">Октябрь</option>
            <option value="10">Ноябрь</option>
            <option value="11">Декабрь</option>
        </select></td>
        <td>
            <button type="submit" name="button" value="READ COSTS" class="addProductButton"/>
            Показать
        </td>
        </tbody>
    </table>
</form>


<table border="1" style="display: inline-block; border: 1px solid; float: left; ">
    <thead>
    <tr>
        <th>Вид расхода</th>
        <th>З/П</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="employeeAndWage" items="${employeeAndWageList}">
        <tr>
            <td></td>
            <td>${employeeAndWage}</td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td>Общий счет:</td>
        <td>${wageSum}</td>
    </tr>
    </tfoot>
</table>

<table border="1">
    <thead>
    <tr>
        <th>Ингридиенты</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ingridientAndCost" items="${ingridientAndCostList}">
        <tr>
            <td>${ingridientAndCost}</td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td>${ingridientSum}</td>
    </tr>
    </tfoot>
</table>

<table border="1">
    <thead>
    <tr>
        <th>Аренда</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td rowspan="42">${rent}</td>
    </tr>
    </tbody>
    <tfoot>
    <tr>
        <td>${costs}</td>
    </tr>
    </tfoot>
</table>

<h2>Сумма всех доходов за месяц(выбор месяца, либо всего)</h2>

<form action="${pageContext.request.contextPath}/statistics" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Месяц:</th>
        </thead>
        <tbody>
        <td><select name="month" size="1">
            <option value="-1">Все месяцы</option>
            <option value="0">Январь</option>
            <option value="1">Февраль</option>
            <option value="2">Март</option>
            <option value="3">Апрель</option>
            <option value="4">Май</option>
            <option value="5">Июнь</option>
            <option value="6">Июль</option>
            <option value="7">Август</option>
            <option value="8">Сентябрь</option>
            <option value="9">Октябрь</option>
            <option value="10">Ноябрь</option>
            <option value="11">Декабрь</option>
        </select></td>
        <td>
            <button type="submit" name="button" value="READ INCOME" class="addProductButton"/>
            Показать
        </td>
        </tbody>
    </table>
</form>

<table border="1">
    <thead>
    <tr>
        <th>Продукт</th>
        <th>Цена</th>
        <th>Количество проданных</th>
        <th>Итого</th>
    </thead>
    <tbody>
    <c:forEach var="product" items="${productList}">
        <tr>
            <td>${product.getName()}</td>
            <td>${product.getPrice()}</td>
            <td>${order.getQuantity()}</td>
            <td>${product.getPrice()}</td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td>Общий счет:</td>
        <td>${salesSum}</td>
    </tr>
</table>

<h2>Баланс за месяц(выбор месяца, либо всего)</h2>

<form action="${pageContext.request.contextPath}/statistics" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Месяц:</th>
        </thead>
        <tbody>
        <td><select name="month" size="1">
            <option value="-1">Все месяцы</option>
            <option value="0">Январь</option>
            <option value="1">Февраль</option>
            <option value="2">Март</option>
            <option value="3">Апрель</option>
            <option value="4">Май</option>
            <option value="5">Июнь</option>
            <option value="6">Июль</option>
            <option value="7">Август</option>
            <option value="8">Сентябрь</option>
            <option value="9">Октябрь</option>
            <option value="10">Ноябрь</option>
            <option value="11">Декабрь</option>
        </select></td>
        <td>
            <button type="submit" name="button" value="READ BALANCE" class="addProductButton"/>
            Показать
        </td>
        </tbody>
    </table>
</form>

<table border="1">
    <thead>
    <tr>
        <th>Доходы</th>
        <th>Расходы</th>
        <th>Баланс</th>
    </thead>
    <tbody>
    <tr>
        <td>${salesSum}</td>
        <td>${costs}</td>
        <td>${salesSum - costs}</td>
    </tr>
    </tbody>
</table>

<h2>Потребность в официантах</h2>
12 столиков - 3 столика на официанта, 4 официнта
Идеально: 3 столика - 1 официант на смену
(кол-во столов/3)*2 = идеал

<h2>Статистика уникальных IP</h2>

//Яндекс метрика

</body>
</html>
