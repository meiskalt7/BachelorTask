package org.meiskalt7.testing;

import org.junit.Test;
import org.meiskalt7.crud.CategoriesService;
import org.meiskalt7.entity.Categories;

import java.util.List;

/**
 * @author Eiskalt on 12.10.2015.
 */
public class CategoriesServiceTest {

    CategoriesService service = new CategoriesService();

    @Test
    public void testSaveRecord() throws Exception {
        Categories categories1 = new Categories();
        categories1.setName("Keyboard");

        Categories categories = service.add(categories1);

        System.out.println(categories);
    }

    @Test
    public void testDeleteRecord() throws Exception {
        Categories categories1 = new Categories();
        categories1.setName("Printer");

        Categories categories = service.add(categories1);

        service.delete(categories.getId());
    }

    @Test
    public void testSelect() throws Exception {
        //������� ���������� ��� ������ � ��
        Categories categories1 = new Categories();
        categories1.setName("Citroen?");

        //���������� � ��
        Categories categories = service.add(categories1);

        //�������� � �� Citroen?
        Categories categoriesFromDB = service.get(categories.getId());
        System.out.println(categoriesFromDB);
    }

    @Test
    public void testUpdate() throws Exception {
        //������� ���������� ��� ������ � ��
        Categories categories1 = new Categories();
        categories1.setName("Lambordshini");

        //���������� � ��
        categories1 = service.add(categories1);

        categories1.setName("Lambo");

        //���������
        service.update(categories1);

        //�������� ���������� ������
        Categories categories2 = service.get(categories1.getId());
        System.out.println(categories2);
    }

    @Test
    public void testGetAll() {
        //������� ���������� ��� ������ � ��
        Categories categories1 = new Categories();
        categories1.setName("Lexus");

        //������� ���������� ��� ������ � ��
        Categories categories2 = new Categories();
        categories2.setName("Fiat");

        //������� ���������� ��� ������ � ��
        Categories categories3 = new Categories();
        categories3.setName("Porsche");

        //��������� ��� ����
        service.add(categories1);
        service.add(categories2);
        service.add(categories3);

        //�������� ��� ���� � ��
        List<Categories> categories = service.getAll();

        //������� ��������� ������ ����
        for (Categories c : categories) {
            System.out.println(c);
        }
    }

}
