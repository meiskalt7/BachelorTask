package org.meiskalt7.servlets;

import org.meiskalt7.crud.EmployeeService;
import org.meiskalt7.crud.OrderService;
import org.meiskalt7.entity.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@WebServlet(name = "statisticPage")
public class StatisticPage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        OrderService orderService = OrderService.getInstance();
        EmployeeService employeeService = EmployeeService.getInstance();

        List<Order> endedOrders = new ArrayList<>();
        ArrayList<String> employeeAndWageList = null;
        ArrayList<String> ingridientAndCostList = null;
        ArrayList<Product> productList = new ArrayList<>();
        double wageSum = 0;
        double ingridientSum = 0;
        double rent = 0;
        double salesSum = 0;
        double costs = 0;

        if (req.getParameter("button") != null) {
            String button[] = req.getParameter("button").split(" ");

            Operation operation = Operation.valueOf(button[0]);
            Entity entity = Entity.valueOf(button[1]);
            Calendar calendar = Calendar.getInstance();
            int month = Integer.parseInt(req.getParameter("month"));

            switch (operation) {
                case READ:

                    switch (entity) {
                        case BALANCE:
                            //Доходы(продажи) - расходы(З/П, Ингридиенты, Аренда)
                            salesSum = getSalesSum(orderService, productList);

                            employeeAndWageList = new ArrayList<>();
                            wageSum = getWageSum(employeeService, employeeAndWageList, wageSum);

                            ingridientAndCostList = new ArrayList<>();
                            ingridientSum = getIngridientSum(orderService, ingridientAndCostList, ingridientSum);

                            rent = 5000;

                            costs += wageSum + ingridientSum + rent;
                            break;

                        case INCOME:
                            salesSum = getSalesSum(orderService, productList);
                            break;
                        case COSTS:
                            employeeAndWageList = new ArrayList<>();
                            wageSum = getWageSum(employeeService, employeeAndWageList, wageSum);

                            ingridientAndCostList = new ArrayList<>();
                            ingridientSum = getIngridientSum(orderService, ingridientAndCostList, ingridientSum);

                            rent = 5000;

                            costs += wageSum + ingridientSum + rent;
                            break;
                        case ORDER:

                            for (Order order : orderService.getAll()) {
                                if (order.isEnded()) {
                                    calendar.setTime(order.getDatetime());
                                    if (month == -1 || calendar.get(Calendar.MONTH) == month)
                                        endedOrders.add(order);
                                }
                            }
                            break;
                    }
                    break;
            }
        }

        req.setAttribute("orderList", endedOrders);
        req.setAttribute("rent", rent);
        req.setAttribute("wageSum", wageSum);
        req.setAttribute("salesSum", salesSum);
        req.setAttribute("ingridientSum", ingridientSum);
        req.setAttribute("costs", costs);
        req.setAttribute("ingridientAndCostList", ingridientAndCostList);
        req.setAttribute("employeeAndWageList", employeeAndWageList);
        req.setAttribute("productList", productList);

        RequestDispatcher rd = getServletContext()
                .getRequestDispatcher("/admin/statisticPage.jsp");
        rd.forward(req, resp);
    }

    private double getIngridientSum(OrderService orderService, ArrayList<String> ingridientAndCostList, double ingridientSum) {
        for (Order order : orderService.getAll()) {
            if (order.isEnded()) {
                for (Orderlist orderlist : order.getOrderlists()) {
                    for (Composition composition : orderlist.getProduct().getIngridients()) {
                        ingridientAndCostList.add(composition.getIngridient().getName() + ":" + composition.getIngridient().getPrice());
                        ingridientSum += orderlist.getQuantity() * (composition.getIngridient().getPrice() * composition.getRequired());
                    }
                }
            }
        }
        return ingridientSum;
    }

    private double getWageSum(EmployeeService employeeService, ArrayList<String> employeeAndWageList, double wageSum) {
        for (Employee employee : employeeService.getAll()) {
            employeeAndWageList.add(employee.getSurname() + " " + employee.getName() + ":" + employee.getWage());
            wageSum += employee.getWage();
        }
        return wageSum;
    }

    private double getSalesSum(OrderService orderService, ArrayList<Product> productList) {
        double salesSum = 0;
        for (Order order : orderService.getAll()) {
            if (order.isEnded()) {
                for (Orderlist orderlist : order.getOrderlists()) {
                    productList.add(orderlist.getProduct());
                    salesSum += orderlist.getProduct().getPrice() * orderlist.getQuantity();
                }
            }
        }
        return salesSum;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
