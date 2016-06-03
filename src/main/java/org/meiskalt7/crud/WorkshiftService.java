package org.meiskalt7.crud;

import org.meiskalt7.entity.Employee;
import org.meiskalt7.entity.TimeRange;
import org.meiskalt7.entity.Workshift;
import org.meiskalt7.servlets.Entity;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class WorkshiftService extends Service<Workshift> {

    private static WorkshiftService workshiftService;

    private WorkshiftService() {
    }

    public static synchronized WorkshiftService getInstance() {
        if (workshiftService == null) {
            workshiftService = new WorkshiftService();
        }
        return workshiftService;
    }

    public Workshift get(int id) {
        return em.find(Workshift.class, id);
    }

    public List<Workshift> getAll() {
        return (List<Workshift>) em.createQuery("SELECT w FROM Workshift w").getResultList();
    }

    public void deleteAll() {
        for (Workshift workshift : getAll()) {
            delete(workshift.getId());
        }
    }

    @Override
    public void create(HttpServletRequest request) {
        final Service employeeService = Service.getService(Entity.EMPLOYEE);
        Service timeRangeService = Service.getService(Entity.TIMERANGE);

        final String[] employeeId = request.getParameterValues("employee");
        List<Employee> employees = new ArrayList<Employee>() {{
            for (String anEmployeeId : employeeId) {
                add((Employee) employeeService.get(Integer.parseInt(anEmployeeId)));
            }
        }};
        Date date = Date.valueOf(request.getParameter("date"));
        int timerangeId = Integer.parseInt(request.getParameter("timerange"));
        TimeRange timeRange = (TimeRange) timeRangeService.get(timerangeId);
        Workshift workshift = new Workshift(date, timeRange, employees);
        add(workshift);
        for (Employee emp : employees) {
            emp.getWorkshifts().add(workshift);
            employeeService.update(emp);
        }
    }

    @Override
    public void update(HttpServletRequest request, int id) {

    }
}
