package org.meiskalt7.testing;

import org.junit.Test;
import org.meiskalt7.crud.CategoryService;
import org.meiskalt7.entity.Category;

import java.util.List;

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

    @Test
    public void testSelect() throws Exception {
        //Создаем автомобиль для записи в БД
        Category category1 = new Category();
        category1.setName("Citroen?");

        //Записываем в БД
        Category category = service.add(category1);

        //Получние с БД Citroen?
        Category categoryFromDB = service.get(category.getId());
        System.out.println(categoryFromDB);
    }

    @Test
    public void testUpdate() throws Exception {
        //Создаем автомобиль для записи в БД
        Category category1 = new Category();
        category1.setName("Lambordshini");

        //Записываем в БД
        category1 = service.add(category1);

        category1.setName("Lambo");

        //Обновляем
        service.update(category1);

        //Получаем обновленую запись
        Category category2 = service.get(category1.getId());
        System.out.println(category2);
    }

    @Test
    public void testGetAll() {
        //Создаем автомобиль для записи в БД
        Category category1 = new Category();
        category1.setName("Lexus");

        //Создаем автомобиль для записи в БД
        Category category2 = new Category();
        category2.setName("Fiat");

        //Создаем автомобиль для записи в БД
        Category category3 = new Category();
        category3.setName("Porsche");

        //Сохраняем все авто
        service.add(category1);
        service.add(category2);
        service.add(category3);

        //Получаем все авто с БД
        List<Category> categories = service.getAll();

        //Выводим полученый список авто
        for (Category c : categories) {
            System.out.println(c);
        }
    }

}
