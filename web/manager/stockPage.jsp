<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            PopUpHide_ingrid();
        });
    </script>
    <title>Запас ингридиентов</title>
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

<h2>Ингридиентов на складе</h2>
<table border="1">
    <thead>
    <tr>
        <th>Название</th>
        <th>Количество</th>
        <th>Цена</th>
    </thead>
    <tbody>
    <tr>
        <form action="${pageContext.request.contextPath}/stock" method="get">
            <td><input type="text" name="name" maxlength="255" required/></td>
            <td><input type="text" name="quantity" required/></td>
            <td><input type="text" name="price" required/></td>
            <td>
                <button type="submit" name="button" value="ADD INGRIDIENT" class="addProductButton">
                    ДОБАВИТЬ
                </button>
            </td>
        </form>
    </tr>
    <c:forEach var="ingrid" items="${ingridList}">
    <tr>
        <td>${ingrid.getName()}</td>
        <td>${ingrid.getQuantity()}</td>
        <td>${ingrid.getPrice()}</td>
        <td>
            <button type="button"
                    onclick="PopUpShow_ingrid('${ingrid.getId()}', '${ingrid.getName()}', '${ingrid.getQuantity()}', '${ingrid.getPrice()}')"
                    class="updateButton">ИЗМЕНИТЬ
            </button>
            <form>
                <input type="hidden" name="id" value="${ingrid.getId()}">
                <button type="submit" name="button" value="DELETE INGRIDIENT" class="deleteButton">УДАЛИТЬ
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<%--Всплывающие окно--%>
<div class="b-popup" id="popup_ingrid">
    <div class="b-popup-content">
        Изменение ингридиента
        <form>
            <input id="id_c" type="hidden" name="id" required>
            <input id="name" type="text" name="name" maxlength="255" required/>
            <input id="quantity" type="text" name="quantity" required/>
            <input id="price" type="text" name="price" required/>
            <button type="submit" name="button" value="UPDATE INGRIDIENT" class="updateButton">UPDATE
            </button>
        </form>
        <a href="javascript:PopUpHide_ingrid()">Отмена</a>
    </div>
</div>

</body>
</html>
