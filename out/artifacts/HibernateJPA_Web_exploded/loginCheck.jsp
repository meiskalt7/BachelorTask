<%@ page import="org.meiskalt7.entity.Employee" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: Eiskalt
  Date: 03.05.2016
  Time: 15:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>
</head>
<body>
<%
    String username = request.getParameter("username");
    String password = request.getParameter("password");

    if (username != null && password != null) {
        List<Employee> employeeList = (List<Employee>) request.getAttribute("employeeList");

        for (Employee employee : employeeList) {
            if (username.equals(employee.getUsername()) && password.equals(employee.getPassword())) {
                session.setAttribute("userType", employee.getUserType().getType());
                session.setAttribute("userId", employee.getId());
                response.sendRedirect("mainPage.jsp");
                break;
            }
        }
    }

    if (!response.isCommitted())
        response.sendRedirect("login.jsp");
%>
</body>
</html>
