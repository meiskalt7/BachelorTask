package org.meiskalt7.testing;

import org.junit.Test;
import org.meiskalt7.crud.ProductsService;
import org.meiskalt7.entity.Products;

import java.util.List;

/**
 * @author Eiskalt on 12.10.2015.
 */
public class ProductsServiceTest {
    ProductsService service = new ProductsService();

    @Test
    public void testSaveRecord() throws Exception {
        Products products1 = new Products();
        products1.setName("Keyboard");

        Products products = service.add(products1);

        System.out.println(products);
    }

    @Test
    public void testDeleteRecord() throws Exception {
        Products products1 = new Products();
        products1.setName("Printer");

        Products products = service.add(products1);

        service.delete(products.getId());
    }

    @Test
    public void testSelect() throws Exception {
        //������� ���������� ��� ������ � ��
        Products products1 = new Products();
        products1.setName("Citroen?");

        //���������� � ��
        Products products = service.add(products1);

        //�������� � �� Citroen?
        Products productsFromDB = service.get(products.getId());
        System.out.println(productsFromDB);
    }

    @Test
    public void testUpdate() throws Exception {
        //������� ���������� ��� ������ � ��
        Products products1 = new Products();
        products1.setName("Lambordshini");

        //���������� � ��
        products1 = service.add(products1);

        products1.setName("Lambo");

        //���������
        service.update(products1);

        //�������� ���������� ������
        Products products2 = service.get(products1.getId());
        System.out.println(products2);
    }

    @Test
    public void testGetAll() {
        //������� ���������� ��� ������ � ��
        Products products1 = new Products();
        products1.setName("Lexus");

        //������� ���������� ��� ������ � ��
        Products products2 = new Products();
        products2.setName("Fiat");

        //������� ���������� ��� ������ � ��
        Products products3 = new Products();
        products3.setName("Porsche");

        //��������� ��� ����
        service.add(products1);
        service.add(products2);
        service.add(products3);

        //�������� ��� ���� � ��
        List<Products> products = service.getAll();

        //������� ��������� ������ ����
        for (Products c : products) {
            System.out.println(c);
        }
    }
}
