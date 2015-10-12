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
        //Создаем автомобиль для записи в БД
        Categories categories1 = new Categories();
        categories1.setName("Citroen?");

        //Записываем в БД
        Categories categories = service.add(categories1);

        //Получние с БД Citroen?
        Categories categoriesFromDB = service.get(categories.getId());
        System.out.println(categoriesFromDB);
    }

    @Test
    public void testUpdate() throws Exception {
        //Создаем автомобиль для записи в БД
        Categories categories1 = new Categories();
        categories1.setName("Lambordshini");

        //Записываем в БД
        categories1 = service.add(categories1);

        categories1.setName("Lambo");

        //Обновляем
        service.update(categories1);

        //Получаем обновленую запись
        Categories categories2 = service.get(categories1.getId());
        System.out.println(categories2);
    }

    @Test
    public void testGetAll() {
        //Создаем автомобиль для записи в БД
        Categories categories1 = new Categories();
        categories1.setName("Lexus");

        //Создаем автомобиль для записи в БД
        Categories categories2 = new Categories();
        categories2.setName("Fiat");

        //Создаем автомобиль для записи в БД
        Categories categories3 = new Categories();
        categories3.setName("Porsche");

        //Сохраняем все авто
        service.add(categories1);
        service.add(categories2);
        service.add(categories3);

        //Получаем все авто с БД
        List<Categories> categories = service.getAll();

        //Выводим полученый список авто
        for (Categories c : categories) {
            System.out.println(c);
        }
    }

}
