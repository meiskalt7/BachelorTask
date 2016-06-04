<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link href="../css/index.css" rel="stylesheet" type="text/css">
    <link href="../css/accordion-menu.css" rel="stylesheet" type="text/css"/>
    <script src="../js/accordion-menu.js" type="text/javascript"></script>
    <script src="../js/float-panel.js"></script>
    <title>Прайс-лист</title>
</head>
<body>
<div id="header">
    <div id="logo">
        <h1>AuRe</h1>

        <h1>~Меню Ресторана~</h1>
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

<h2>Поиск по меню</h2>
<form action="${pageContext.request.contextPath}/pricelist" method="get">
    <table border="0" align="center">
        <thead>
        <tr>
            <th>Категория</th>
            <th>Наименование</th>
            <th>Цена от</th>
            <th>Цена до</th>
        </thead>
        <tbody>
        <td><select name="categoryId" required>
            <option selected value="all">Все</option>
            <c:forEach var="category" items="${categoryList}">
                <option value="${category.getId()}">${category.getName()}</option>
            </c:forEach>
        </select></td>
        <td><input type="text" name="name" maxlength="255"/></td>
        <td><input type="text" name="priceFrom"/></td>
        <td><input type="text" name="priceTo"/></td>
        <td>
            <button type="submit" name="button" value="READ PRODUCT" class="addProductButton">
                НАЙТИ
            </button>
        </td>
        </tbody>
    </table>
    <input type="hidden" name="userId" value="${sessionScope.userId}">
</form>

<form action="${pageContext.request.contextPath}/pricelist" method="get">
    <%-- выбор заказа(если выбран заказ, то блокируем выбор столика) --%>
    <div align="center">
        <td><select name="orderId">
        <option selected value="new">--Новый заказ--</option>
        <c:forEach var="order" items="${orderList}">
            <option value="${order.getId()}">${order.getTable().getId()}
                (${order.getTable().getType()}) ${order.getDatetime()}</option>
        </c:forEach>
        <option value="">--Прочие--</option>
    </select></td>
    <%-- выбор столика для заказа(сначала свои, потом чужие) --%>
    Столики:
    <td><select name="tableId" required>
        <option selected value="">--Назначенные--</option>
        <c:forEach var="table" items="${waiterTableList}">
            <option value="${table.getId()}">${table.getNumber()} (${table.getType()})</option>
        </c:forEach>
        <option value="">--Прочие--</option>
    </select></td>
    </div>
    <table border="1" align="center">
        <thead>
        <tr>
            <th>Категория</th>
            <th>Наименование</th>
            <th>Цена</th>
            <th>Количество</th>
            <th>Заказать</th>
        </thead>
        <tbody>
        <c:forEach var="product" items="${productsList}">
        <tr>
            <td>${product.getCategory().getName()}</td>
            <td>${product.getName()}</td>
            <td>${product.getPrice()}</td>
            <td><input type="number" name="quantity"/></td>
            <input type="hidden" name="productId" value="${product.getId()}">

            <td>
                <button type="submit" name="button" value="TO_CART" class="addProductButton">
                    В корзину
                </button>
            </td>
        </tr>
        </tbody>
        </c:forEach>
    </table>
    <input type="hidden" name="userId" value="${sessionScope.userId}">
    ${errorMessage}
</form>
</body>
</html>