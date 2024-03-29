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

            PopUpHide_category();

            var ingridDiv = $('#ingridDiv');
            var i = $('#ingridDiv').find('p').size() + 1;

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
    <title>Блюда</title>
</head>
<body>

<div id="header">
    <div id="logo">
        <h1>AuRe</h1>

        <h1>~БЛЮДА~</h1>
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

<h2>Список блюд</h2>
<table border="1" align="center">
    <thead>
    <tr>
        <th>Категория</th>
        <th>Наименование</th>
        <th>Цена</th>
        <th>Ингридиент:Кол-во</th>
    </thead>
    <tbody>
    <c:if test="${'admin'.equals(sessionScope.userType)}">
        <form action="${pageContext.request.contextPath}/products" method="get">
            <tr>
                <td><select name="categoryId" required>
                    <c:forEach var="category" items="${categoryList}">
                        <option value="${category.getId()}">${category.getName()}</option>
                    </c:forEach>
                </select></td>
                <td><input type="text" name="name" maxlength="255" required/></td>
                <td><input type="text" name="price" required/></td>

                <td>
                    <button id="add_ingrid_button" class="addProductButton">Еще...</button>
                    <div id="ingridDiv">

                        <p>
                            <select name="ingridientsId" id="selectIngrid" required>
                                <option selected value="default">Выберите ингридиент</option>
                                <c:forEach var="ingrid" items="${ingridList}">
                                    <option value="${ingrid.getId()}">${ingrid.getName()}</option>
                                </c:forEach>
                            </select>
                            <input type="text" name="quantity" required/>
                        </p>
                    </div>
                </td>
                <td>
                    <button type="submit" name="button" value="ADD PRODUCT" class="addProductButton">
                        ДОБАВИТЬ
                    </button>
                </td>
            </tr>
        </form>
    </c:if>
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
                <input type="hidden" name="id" value="${product.getId()}">
                <button type="submit" name="button" value="DELETE PRODUCT" class="deleteButton">УДАЛИТЬ
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<h2>Список категорий</h2>
<table border="1" align="center">
    <thead>
    <tr>
        <th>Категория</th>
    </thead>
    <tbody>
    <c:if test="${'admin'.equals(sessionScope.userType)}">
        <tr>
            <form action="${pageContext.request.contextPath}/products" method="get">
                <td><input type="text" name="categoryName" maxlength="255" required/></td>
                <td>
                    <button type="submit" name="button" value="ADD CATEGORY" class="addProductButton">
                        ДОБАВИТЬ
                    </button>
                </td>
            </form>
        </tr>
    </c:if>
    <c:forEach var="category" items="${categoryList}">
    <tr>
        <td>${category.getName()}</td>
        <td>
            <button type="button"
                    onclick="PopUpShow_category('${category.getId()}', '${category.getName()}')"
                    class="updateButton">ИЗМЕНИТЬ
            </button>
            <form>
                <input type="hidden" name="id" value="${category.getId()}">
                <button type="submit" name="button" value="DELETE CATEGORY" class="deleteButton">УДАЛИТЬ
                </button>
            </form>
        </td>
    </tr>
    </tbody>
    </c:forEach>
</table>

<%--Всплывающие окно--%>
<div class="b-popup" id="popup_category">
    <div class="b-popup-content">
        Изменение имени категории
        <form>
            <input id="id_c" type="hidden" name="id" required>
            <input id="categoryName" type="text" name="categoryName" maxlength="255" required/>
            <button type="submit" name="button" value="UPDATE CATEGORY" class="updateButton">UPDATE
            </button>
        </form>
        <a href="javascript:PopUpHide_category()">Отмена</a>
    </div>
</div>

</body>
</html>
