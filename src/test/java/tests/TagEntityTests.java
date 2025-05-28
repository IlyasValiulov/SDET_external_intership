package tests;

import Api.TagApi;
import database.TagDb;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojo.TagPojo;

import java.util.ArrayList;
import java.util.List;

public class TagEntityTests extends BaseTests {
    private final TagDb db = new TagDb();
    private TagApi api;
    private int id;

    @BeforeMethod
    public void setup() {
        api = new TagApi(requestSpec);
        id = db.createTag(new TagPojo(0, "name1", "description", 0, "", "slug1", "category", new ArrayList<>()));
    };

    @Test
    public void getTagTest() {
        TagPojo tag = api.getTag(id);

        TagPojo tagdb = db.getTagById(tag.getId());

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(tag);
        softAssert.assertNotNull(tagdb);
        softAssert.assertEquals(tag, tagdb);
        softAssert.assertAll();
    }

    @Test
    public void createTagTest() {
        String name = "name";
        String description = "desc";
        int count = 0;
        String link = "";
        String slug = "slug";
        String taxonomy = "category";
        List<String> meta = new ArrayList<>();

        int tag_id = api.createTag(name, description, slug);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(tag_id >= 0);
        TagPojo tag = db.getTagById(tag_id);

        softAssert.assertNotNull(tag);
        softAssert.assertEquals(name, tag.getName());
        softAssert.assertEquals(description, tag.getDescription());
        softAssert.assertEquals(count, tag.getCount());
        softAssert.assertEquals(link, tag.getLink());
        softAssert.assertEquals(slug, tag.getSlug());
        softAssert.assertEquals(taxonomy, tag.getTaxonomy());
        softAssert.assertEquals(meta, tag.getMeta());
        softAssert.assertAll();

        db.deleteTagById(tag_id);
    }

    @Test
    public void createTagDbTest() {
        String name = "name";
        String description = "desc";
        int count = 0;
        String link = "";
        String slug = "slug";
        String taxonomy = "category";
        List<String> meta = new ArrayList<>();
        int id = db.createTag(TagPojo.builder().id(0).name(name).description(description).count(count).link(link).slug(slug).taxonomy(taxonomy).meta(meta).build());

        TagPojo tagdb = db.getTagById(id);
        TagPojo tag = api.getTag(id);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(tag);
        softAssert.assertEquals(tagdb.getName(), tag.getName());
        softAssert.assertEquals(tagdb.getDescription(), tag.getDescription());
        softAssert.assertEquals(tagdb.getCount(), tag.getCount());
        softAssert.assertEquals(tagdb.getLink(), tag.getLink());
        softAssert.assertEquals(tagdb.getSlug(), tag.getSlug());
        softAssert.assertEquals(tagdb.getTaxonomy(), tag.getTaxonomy());
        softAssert.assertEquals(tagdb.getMeta(), tag.getMeta());
        softAssert.assertAll();

        db.deleteTagById(id);
    }

    @Test
    public void updateTagTest() {
        String name = "nameupd";
        String description = "descupd";
        int count = 0;
        String link = "";
        String slug = "slugupd";
        String taxonomy = "categoryupd";
        List<String> meta = new ArrayList<>();

        int tag_id = api.updateTag(id, name, description, slug);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(tag_id >= 0);
        TagPojo tag = db.getTagById(tag_id);

        softAssert.assertNotNull(tag);
        softAssert.assertEquals(name, tag.getName());
        softAssert.assertEquals(description, tag.getDescription());
        softAssert.assertEquals(count, tag.getCount());
        softAssert.assertEquals(link, tag.getLink());
        softAssert.assertEquals(slug, tag.getSlug());
        softAssert.assertEquals(taxonomy, tag.getTaxonomy());
        softAssert.assertEquals(meta, tag.getMeta());
        softAssert.assertAll();
    }

    @Test
    public void deleteTagTest() {
        api.deleteTag(id);

        TagPojo tagdb = db.getTagById(id);
        Assert.assertNull(tagdb);
    }

    @AfterMethod
    public void teardown() {
        db.deleteTagById(id);
        id = -1;
    }
}
