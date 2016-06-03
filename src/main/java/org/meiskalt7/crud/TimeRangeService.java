package org.meiskalt7.crud;

import org.meiskalt7.entity.TimeRange;
import org.meiskalt7.servlets.Entity;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.util.List;

public class TimeRangeService extends Service<TimeRange> {

    private static TimeRangeService timeRangeService;

    private TimeRangeService() {
    }

    public static synchronized TimeRangeService getInstance() {
        if (timeRangeService == null) {
            timeRangeService = new TimeRangeService();
        }
        return timeRangeService;
    }

    public TimeRange get(int id) {
        return em.find(TimeRange.class, id);
    }

    public List<TimeRange> getAll() {
        return (List<TimeRange>) em.createQuery("SELECT t FROM TimeRange t").getResultList();
    }

    public void deleteAll() {
        for (TimeRange emp : getAll()) {
            delete(emp.getId());
        }
    }

    @Override
    public void create(HttpServletRequest request) {
        Service timeRangeService = Service.getService(Entity.TIMERANGE);

        Time start = Time.valueOf(request.getParameter("start") + ":00");
        Time finish = Time.valueOf(request.getParameter("finish") + ":00");
        timeRangeService.add(new TimeRange(start, finish));
    }

    @Override
    public void update(HttpServletRequest request, int id) {
        Service timeRangeService = Service.getService(Entity.TIMERANGE);

        TimeRange timeRange = (TimeRange) timeRangeService.get(id);
        Time start = Time.valueOf(request.getParameter("start") + ":00");
        Time finish = Time.valueOf(request.getParameter("finish") + ":00");
        timeRange.setStart(start);
        timeRange.setFinish(finish);
        timeRangeService.update(timeRange);
    }
}
