package org.meiskalt7.crud;

import org.meiskalt7.entity.TimeRange;

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
}
