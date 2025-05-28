package tests;

import Api.CategoryApi;
import database.CategoryDb;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojo.CategoryPojo;

import java.util.ArrayList;
import java.util.List;

public class CategoryEntityTests extends BaseTests {
    private final CategoryDb  db = new CategoryDb();
    private CategoryApi api;
    private int id;

    @BeforeMethod
    public void setup() {
        api = new CategoryApi(requestSpec);
        id = db.createCategory(new CategoryPojo(0, "name1", "description", "category", "slug1", 0, "", 0, new ArrayList<>()));
    };

    @Test
    public void getCategoryTest() {
        CategoryPojo category = api.getCategory(id);

        CategoryPojo categorydb = db.getCategoryById(category.getId());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(category);
        softAssert.assertNotNull(categorydb);
        softAssert.assertEquals(category, categorydb);
        softAssert.assertAll();
    }

    @Test
    public void createCategoryTest() {
        String name = "name";
        String description = "desc";
        String slug = "slug";
        int count = 0;
        String link = "";
        int parent = 0;
        List<String> meta = new ArrayList<>();

        int category_id = api.createCategory(name, description, slug);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(category_id >= 0);
        CategoryPojo category = db.getCategoryById(category_id);

        softAssert.assertNotNull(category);
        softAssert.assertEquals(name, category.getName());
        softAssert.assertEquals(description, category.getDescription());
        softAssert.assertEquals(slug, category.getSlug());
        softAssert.assertEquals(count, category.getCount());
        softAssert.assertEquals(link, category.getLink());
        softAssert.assertEquals(parent, category.getParent());
        softAssert.assertEquals(meta, category.getMeta());
        softAssert.assertAll();

        db.deleteCategoryById(category_id);
    }

    @Test
    public void updateCatergoryTest() {
        String name = "nameupdate";
        String description = "descupdate";
        String slug = "slugupdate";
        int count = 0;
        String link = "";
        int parent = 0;
        List<String> meta = new ArrayList<>();

        int category_id = api.updateCategory(id, name, description, slug);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(category_id >= 0);
        CategoryPojo category = db.getCategoryById(category_id);

        softAssert.assertNotNull(category);
        softAssert.assertEquals(name, category.getName());
        softAssert.assertEquals(description, category.getDescription());
        softAssert.assertEquals(slug, category.getSlug());
        softAssert.assertEquals(count, category.getCount());
        softAssert.assertEquals(link, category.getLink());
        softAssert.assertEquals(parent, category.getParent());
        softAssert.assertEquals(meta, category.getMeta());
        softAssert.assertAll();
    }

    @Test
    public void deleteCategoryTest() {
        api.deleteCategory(id);

        CategoryPojo categorydb = db.getCategoryById(id);
        Assert.assertNull(categorydb);
    }

    @AfterMethod
    public void teardown() {
        db.deleteCategoryById(id);
        id = -1;
    }
}
