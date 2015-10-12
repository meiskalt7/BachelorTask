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
<form action="mailto:vasya@mail.ru">
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
    <td><input type="text" /></td>
    <td><input type="text" /></td>
    <td><button>Найти</button></td>
    </tbody>
</table>
</form>
</body>
</html>
