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

<h2>Список чеков(свернуть, развернуть)</h2>

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

<h2>Сумма всех чеков за месяц(выбор месяца, либо всего)</h2>

<form action="${pageContext.request.contextPath}/statistics" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Месяц:</th>
        </thead>
        <tbody>
        <td><select name="categoryId" required>
            <c:forEach var="category" items="${categoryList}">
                <option value="${category.getId()}">${category.getName()}</option>
            </c:forEach>
        </select></td>
        <td>
            <button type="submit" name="button" value="add product" class="addProductButton"/>
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
    </thead>
    <tbody>
    <c:forEach var="product" items="${productsList}">
    <tr>
        <td>${product.getCategory().getName()}</td>
        <td>${product.getName()}</td>
        <td>${product.getPrice()}</td>
        <td>
            <c:forEach var="ingridient" items="${product.getIngridients()}">
                <p>${ingridient.getIngridient().getName()} :
                        ${ingridient.getRequired()} </p>
            </c:forEach>
        </td>
        <td>
            <form>
                <input type="hidden" name="productId" value="${product.getId()}">
                <button type="submit" name="button" value="delete product" class="deleteButton">DELETE
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<h2>Сумма всех расходов за месяц(выбор месяца, либо всего)</h2>

<form action="${pageContext.request.contextPath}/statistics" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Месяц:</th>
        </thead>
        <tbody>
        <td><select name="categoryId" required>
            <c:forEach var="category" items="${categoryList}">
                <option value="${category.getId()}">${category.getName()}</option>
            </c:forEach>
        </select></td>
        <td>
            <button type="submit" name="button" value="add product" class="addProductButton"/>
            Показать
        </td>
        </tbody>
    </table>
</form>

<table border="1">
    <thead>
    <tr>
        <th>З/П</th>
        <th>Ингридиенты</th>
        <th>Аренда</th>
        <th>Итого</th>
    </thead>
    <tbody>
    <c:forEach var="product" items="${productsList}">
    <tr>
        <td>${product.getCategory().getName()}</td>
        <td>${product.getName()}</td>
        <td>${product.getPrice()}</td>
        <td>
            <c:forEach var="ingridient" items="${product.getIngridients()}">
                <p>${ingridient.getIngridient().getName()} :
                        ${ingridient.getRequired()} </p>
            </c:forEach>
        </td>
        <td>
            <form>
                <input type="hidden" name="productId" value="${product.getId()}">
                <button type="submit" name="button" value="delete product" class="deleteButton">DELETE
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<h2>Сумма всех доходов за месяц(выбор месяца, либо всего)</h2>

<form action="${pageContext.request.contextPath}/statistics" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Месяц:</th>
        </thead>
        <tbody>
        <td><select name="categoryId" required>
            <c:forEach var="category" items="${categoryList}">
                <option value="${category.getId()}">${category.getName()}</option>
            </c:forEach>
        </select></td>
        <td>
            <button type="submit" name="button" value="add product" class="addProductButton"/>
            Показать
        </td>
        </tbody>
    </table>
</form>

<table border="1">
    <thead>
    <tr>
        <th>Продукты</th>
    </thead>
    <tbody>
    <c:forEach var="product" items="${productsList}">
    <tr>
        <td>${product.getCategory().getName()}</td>
        <td>${product.getName()}</td>
        <td>${product.getPrice()}</td>
        <td>
            <c:forEach var="ingridient" items="${product.getIngridients()}">
                <p>${ingridient.getIngridient().getName()} :
                        ${ingridient.getRequired()} </p>
            </c:forEach>
        </td>
        <td>
            <form>
                <input type="hidden" name="productId" value="${product.getId()}">
                <button type="submit" name="button" value="delete product" class="deleteButton">DELETE
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<h2>Баланс за месяц(выбор месяца, либо всего)</h2>

<form action="${pageContext.request.contextPath}/statistics" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Месяц:</th>
        </thead>
        <tbody>
        <td><select name="categoryId" required>
            <c:forEach var="category" items="${categoryList}">
                <option value="${category.getId()}">${category.getName()}</option>
            </c:forEach>
        </select></td>
        <td>
            <button type="submit" name="button" value="add product" class="addProductButton"/>
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
    <c:forEach var="product" items="${productsList}">
    <tr>
        <td>${product.getCategory().getName()}</td>
        <td>${product.getName()}</td>
        <td>${product.getPrice()}</td>
        <td>
            <c:forEach var="ingridient" items="${product.getIngridients()}">
                <p>${ingridient.getIngridient().getName()} :
                        ${ingridient.getRequired()} </p>
            </c:forEach>
        </td>
        <td>
            <form>
                <input type="hidden" name="productId" value="${product.getId()}">
                <button type="submit" name="button" value="delete product" class="deleteButton">DELETE
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<h2>Потребность в официантах</h2>
12 столиков - 3 столика на официанта, 4 официнта
Идеально: 3 столика - 1 официант на смену
(кол-во столов/3)*2 = идеал

<h2>Статистика уникальных IP</h2>

//Яндекс метрика

</body>
</html>
