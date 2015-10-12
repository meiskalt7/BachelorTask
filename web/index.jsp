<%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 12.10.2015
  Time: 10:58
  To change this template use File | Settings | File Templates.
  <jsp:forward page="/Pricelist"></jsp:forward>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Прайс-лист</title>
</head>
<body>
Прайс-лист
<form action="${pageContext.request.contextPath}/Pricelist" method="get">
    <table border="0">
        <thead>
        <tr>
            <th>Категория:</th>
            <th>Наименование:</th>
            <th>Цена от:</th>
            <th>Цена до:</th>
        </thead>
        <tbody>
        <td><input type="text" name = "category" maxlength = "255"/></td>
        <td><input type="text" name = "name" maxlength = "255"/></td>
        <td><input type="text" name = "priceFrom"/></td>
        <td><input type="text" name = "priceTo"/></td>
        <td><input type="submit" value="Найти"/></td>
        </tbody>
    </table>
</form>
<table border="1">
    <thead>
    <tr>
        <th>Категория</th>
        <th>Наименование</th>
        <th>Цена</th>
    </thead>
    <tbody>
    <td>Текст</td>
    <td>Текст</td>
    <td>Текст</td>
    <tr>
        <td>Текст</td>
        <td>Текст</td>
        <td>Текст</td>
    <tr>
        <td>Текст</td>
        <td>Текст</td>
        <td>Текст</td>
    </tbody>
</table>
</body>
</html>