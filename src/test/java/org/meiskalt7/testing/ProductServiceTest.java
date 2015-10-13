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
        //Создаем автомобиль для записи в БД
        Product product1 = new Product();
        product1.setName("Citroen?");

        //Записываем в БД
        Product product = service.add(product1);

        //Получние с БД Citroen?
        Product productFromDB = service.get(product.getId());
        System.out.println(productFromDB);
    }

    @Test
    public void testUpdate() throws Exception {
        //Создаем автомобиль для записи в БД
        Product product1 = new Product();
        product1.setName("Lambordshini");

        //Записываем в БД
        product1 = service.add(product1);

        product1.setName("Lambo");

        //Обновляем
        service.update(product1);

        //Получаем обновленую запись
        Product product2 = service.get(product1.getId());
        System.out.println(product2);
    }

    @Test
    public void testGetAll() {
        //Создаем автомобиль для записи в БД
        Product product1 = new Product();
        product1.setName("Lexus");

        //Создаем автомобиль для записи в БД
        Product product2 = new Product();
        product2.setName("Fiat");

        //Создаем автомобиль для записи в БД
        Product product3 = new Product();
        product3.setName("Porsche");

        //Сохраняем все авто
        service.add(product1);
        service.add(product2);
        service.add(product3);

        //Получаем все авто с БД
        List<Product> products = service.getAll();

        //Выводим полученый список авто
        for (Product c : products) {
            System.out.println(c);
        }
    }
}
