package org.meiskalt7.testing;

import org.junit.Test;
import org.meiskalt7.crud.CategoryService;
import org.meiskalt7.entity.Category;

/**
 * @author Eiskalt on 12.10.2015.
 */
public class CategoryServiceTest {

    CategoryService service = new CategoryService();

    @Test
    public void testSaveRecord() throws Exception {
        Category category1 = new Category();
        category1.setName("Keyboard");

        Category category = service.add(category1);

        System.out.println(category);
    }

    @Test
    public void testDeleteRecord() throws Exception {
        Category category1 = new Category();
        category1.setName("Printer");

        Category category = service.add(category1);

        service.delete(category.getId());
    }
/*
    @Test
    public void testSelect() throws Exception {
        //������� ���������� ��� ������ � ��
        Category category1 = new Category();
        category1.setName("Citroen?");

        //���������� � ��
        Category category = service.add(category1);

        //�������� � �� Citroen?
        Category categoryFromDB = service.get(category.getId());
        System.out.println(categoryFromDB);
    }

    @Test
    public void testUpdate() throws Exception {
        //������� ���������� ��� ������ � ��
        Category category1 = new Category();
        category1.setName("Lambordshini");

        //���������� � ��
        category1 = service.add(category1);

        category1.setName("Lambo");

        //���������
        service.update(category1);

        //�������� ���������� ������
        Category category2 = service.get(category1.getId());
        System.out.println(category2);
    }

    @Test
    public void testGetAll() {
        //������� ���������� ��� ������ � ��
        Category category1 = new Category();
        category1.setName("Lexus");

        //������� ���������� ��� ������ � ��
        Category category2 = new Category();
        category2.setName("Fiat");

        //������� ���������� ��� ������ � ��
        Category category3 = new Category();
        category3.setName("Porsche");

        //��������� ��� ����
        service.add(category1);
        service.add(category2);
        service.add(category3);

        //�������� ��� ���� � ��
        List<Category> categories = service.getAll();

        //������� ��������� ������ ����
        for (Category c : categories) {
            System.out.println(c);
        }
    }
*/
}
