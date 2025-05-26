package Api;

import extension.ProjectProperties;
import io.restassured.specification.RequestSpecification;
import pojo.TagPojo;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class TagApi {
    private final RequestSpecification requestSpec;
    private final String rest_route;

    public TagApi(RequestSpecification spec) {
        requestSpec = spec;
        try {
            rest_route = ProjectProperties.getProperty("tag_rest_route");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TagPojo getTag(int id) {
        return given()
                .spec(requestSpec)
                .queryParam("rest_route", rest_route + "/" + id)
            .when()
                .get()
            .then()
                .statusCode(200)
                .extract()
                .as(TagPojo.class);
    }

    public int createTag(String name, String description, String slug) {
        return given()
                .spec(requestSpec)
                .queryParam("rest_route", rest_route)
                .queryParam("name", name)
                .queryParam("description", description)
                .queryParam("slug", slug)
            .when()
                .post()
            .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    public int updateTag(int id, String name, String description, String slug) {
        return given()
                .spec(requestSpec)
                .queryParam("rest_route", rest_route + "/" + id)
                .queryParam("name", name)
                .queryParam("description", description)
                .queryParam("slug", slug)
            .when()
                .post()
            .then()
                .statusCode(200)
                .extract()
                .path("id");
    }

    public void deleteTag(int id) {
        given()
                .spec(requestSpec)
                .queryParam("rest_route", rest_route + "/" + id)
                .queryParam("force", true)
            .when()
                .delete()
            .then()
                .statusCode(200);
    }
}
