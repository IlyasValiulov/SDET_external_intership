package tests;

import Api.NavigationApi;
import database.NavigationDb;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojo.NavigationPojo;

import java.util.Date;

public class NavigationEntityTests extends BaseTests {
    private final NavigationDb db = new NavigationDb();
    private NavigationApi api;
    private int id;

    @BeforeMethod
    public void setup() {
        api = new NavigationApi(requestSpec);
        id = db.createNavigation(new NavigationPojo(0, new Date().toString(), new Date().toString(), new Date().toString(), new Date().toString(),
                                                "", "slug", "publish", "wp_navigation", "", "title", "content"));
    };

    @Test
    public void getNavigationTest() {
        NavigationPojo navigation = api.getNavigation(id);

        NavigationPojo navigationdb = db.getNavigationById(navigation.getId());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(navigation);
        softAssert.assertNotNull(navigationdb);
        softAssert.assertEquals(navigation, navigationdb);
        softAssert.assertAll();
    }

    @Test
    public void createNavigationTest() {
        String slug = "slug";
        String status = "publish";
        String password = "";
        String title = "title";
        String content = "content";

        int navigation_id = api.createNavigation(slug, status, password, title, content);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(navigation_id >= 0);
        NavigationPojo navigation = db.getNavigationById(navigation_id);

        softAssert.assertNotNull(navigation);
        softAssert.assertEquals(slug, navigation.getSlug());
        softAssert.assertEquals(status, navigation.getStatus());
        softAssert.assertEquals(password, navigation.getPassword());
        softAssert.assertEquals(title, navigation.getTitle());
        softAssert.assertEquals(content, navigation.getContent());
        softAssert.assertAll();

        db.deleteNavigationById(navigation_id);
    }

    @Test
    public void updateNavigationTest() {
        String slug = "slug";
        String status = "publish";
        String password = "";
        String title = "title";
        String content = "content";

        int navigation_id = api.updateNavigation(id, slug, status, password, title, content);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(navigation_id >= 0);
        NavigationPojo navigation = db.getNavigationById(navigation_id);

        softAssert.assertNotNull(navigation);
        softAssert.assertEquals(slug, navigation.getSlug());
        softAssert.assertEquals(status, navigation.getStatus());
        softAssert.assertEquals(password, navigation.getPassword());
        softAssert.assertEquals(title, navigation.getTitle());
        softAssert.assertEquals(content, navigation.getContent());
        softAssert.assertAll();
    }

    @Test
    public void deleteNavigationTest() {
        api.deleteNavigation(id);

        NavigationPojo navigation = db.getNavigationById(id);
        Assert.assertNull(navigation);
    }

    @AfterMethod
    public void teardown() {
        db.deleteNavigationById(id);
        id = -1;
    }
}
