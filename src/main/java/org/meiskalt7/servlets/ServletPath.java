package org.meiskalt7.servlets;

enum ServletPath {
    PRICELIST("/pricelist"), ORDERS("/orders"), RESERVATIONS("/reservations"), PRODUCTS("/products"), STOCK("/stock"), WORKSHIFT("/workshift"), HALL("/hall"), ADMIN("/admin");

    private String path;

    ServletPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
