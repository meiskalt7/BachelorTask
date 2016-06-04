package org.meiskalt7.crud;


import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.meiskalt7.servlets.Entity;
import org.mockito.Mockito;

import javax.servlet.http.HttpServletRequest;

public class CategoryServiceTest {

    private CategoryService categoryService;
    private String categoryName;
    private HttpServletRequest request;

    @Before
    public void init() {
        categoryService = (CategoryService) Service.getService(Entity.CATEGORY);
        categoryName = "TestCategory";
        request = Mockito.mock(HttpServletRequest.class);
    }

    @Test
    public void testCreate() {
        Mockito.when(request.getParameter("categoryName")).thenReturn(categoryName);

        categoryService.create(request);

        Assert.assertNotNull(categoryService.getId(categoryName));
    }

    @Test
    public void testUpdate() {
        Mockito.when(request.getParameter("categoryName")).thenReturn(categoryName + " Updated");

        int id = categoryService.getId(categoryName);

        categoryService.update(request, id);

        Assert.assertEquals(categoryName + " Updated", categoryService.get(id).getName());
    }

    @Test
    public void testDelete() {
        int id = categoryService.getId(categoryName + " Updated");
        categoryService.delete(id);

        Assert.assertNull(categoryService.getId(categoryName + " Updated"));
    }
}
