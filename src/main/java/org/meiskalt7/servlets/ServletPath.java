package org.meiskalt7.servlets;

import java.util.HashMap;
import java.util.Map;

enum ServletPath {
    PRICELIST("/pricelist"), ORDERS("/orders"), RESERVATIONS("/reservations"), PRODUCTS("/products"), STOCK("/stock"), WORKSHIFT("/workshift"), HALL("/hall"), ADMIN("/admin");

    private static final Map<String, ServletPath> map;

    static {
        map = new HashMap<>();
        for (ServletPath sp : ServletPath.values()) {
            map.put(sp.getPath(), sp);
        }
    }

    private String path;

    ServletPath(String path) {
        this.path = path;
    }

    public static ServletPath getByPath(String path) {
        return map.get(path);
    }

    public String getPath() {
        return path;
    }
}
