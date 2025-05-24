package Api;

import extension.ProjectProperties;
import io.restassured.specification.RequestSpecification;
import pojo.CategoryPojo;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class CategoryApi {
    private final RequestSpecification requestSpec;
    private final String rest_route;

    public CategoryApi(RequestSpecification spec) {
        requestSpec = spec;
        try {
            rest_route = ProjectProperties.getProperty("category_rest_route");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public CategoryPojo getCategory(int id) {
        return given()
                .spec(requestSpec)
                .queryParam("rest_route", rest_route + "/" + id)
            .when()
                .get()
            .then()
                .statusCode(200)
                .extract()
                .as(CategoryPojo.class);
    }

    public int createCategory(String name, String description, String slug) {
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

    public int updateCategory(int id, String name, String description, String slug) {
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

    public void deleteCategory(int id) {
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
