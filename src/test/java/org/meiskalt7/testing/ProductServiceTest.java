package org.meiskalt7.testing;

import org.junit.Test;
import org.meiskalt7.crud.ProductService;
import org.meiskalt7.entity.Product;

import java.util.List;

/**
 * @author Eiskalt on 12.10.2015.
 */
public class ProductServiceTest {
    ProductService service = new ProductService();

    @Test
    public void testSaveRecord() throws Exception {
        Product product1 = new Product();
        product1.setName("Keyboard");

        Product product = service.add(product1);

        System.out.println(product);
    }

    @Test
    public void testDeleteRecord() throws Exception {
        Product product1 = new Product();
        product1.setName("Printer");

        Product product = service.add(product1);

        service.delete(product.getId());
    }

    @Test
    public void testSelect() throws Exception {
        //������� ���������� ��� ������ � ��
        Product product1 = new Product();
        product1.setName("Citroen?");

        //���������� � ��
        Product product = service.add(product1);

        //�������� � �� Citroen?
        Product productFromDB = service.get(product.getId());
        System.out.println(productFromDB);
    }

    @Test
    public void testUpdate() throws Exception {
        Product product1 = new Product();
        product1.setName("Lambordshini");

        product1 = service.add(product1);

        product1.setName("Lambo");

        service.update(product1);

        Product product2 = service.get(product1.getId());
        System.out.println(product2);
    }

    @Test
    public void testGetAll() {
        Product product1 = new Product();
        product1.setName("Lexus");

        Product product2 = new Product();
        product2.setName("Fiat");

        Product product3 = new Product();
        product3.setName("Porsche");

        service.add(product1);
        service.add(product2);
        service.add(product3);

        List<Product> products = service.getAll();

        for (Product c : products) {
            System.out.println(c);
        }
    }
}
