package tests;

import Api.CategoryApi;
import database.CategoryDb;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pojo.CategoryPojo;

public class CategoryEntityTests extends BaseTests {
    private CategoryDb  db = new CategoryDb();
    private CategoryApi api;
    private int id = -1;
    private String rest_route = "/wp/v2/categories";

    @BeforeMethod
    public void setup() {
        api = new CategoryApi(requestSpec);
        id = db.createCategory(new CategoryPojo( 0,"name1", "decription", "category", "slug1"));
    }

    @Test
    public void getCategoryTest() {
        CategoryPojo category = api.getCategory(id);

        CategoryPojo categorydb = db.getCategoryById(category.getId());

        Assert.assertNotNull(category);
        Assert.assertNotNull(categorydb);
        Assert.assertEquals(category, categorydb);
    }

    @Test
    public void createCategoryTest() {
        String name = "name";
        String description = "desc";
        String slug = "slug";

        int category_id = api.createCategory(name, description, slug);

        Assert.assertTrue(category_id >= 0);
        CategoryPojo category = db.getCategoryById(category_id);

        Assert.assertNotNull(category);
        Assert.assertEquals(name, category.getName());
        Assert.assertEquals(description, category.getDescription());
        Assert.assertEquals(slug, category.getSlug());

        db.deleteCategoryById(category_id);
    }

    @Test
    public void updateCatergoryTest() {
        String name = "nameupdate";
        String description = "descupdate";
        String slug = "slugupdate";

        int category_id = api.updateCategory(id, name, description, slug);

        Assert.assertTrue(category_id >= 0);
        CategoryPojo category = db.getCategoryById(category_id);

        Assert.assertNotNull(category);
        Assert.assertEquals(id, category.getId());
        Assert.assertEquals(name, category.getName());
        Assert.assertEquals(description, category.getDescription());
        Assert.assertEquals(slug, category.getSlug());
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
