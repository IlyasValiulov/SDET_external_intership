package Api;

import extension.ProjectProperties;
import io.restassured.specification.RequestSpecification;
import pojo.NavigationPojo;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class NavigationApi {
    private final RequestSpecification requestSpec;
    private final String rest_route;

    public NavigationApi(RequestSpecification spec) {
        requestSpec = spec;
        try {
            rest_route = ProjectProperties.getProperty("navigation_rest_route");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public NavigationPojo getNavigation(int id) {
        return given()
                .spec(requestSpec)
                .queryParam("rest_route", rest_route + "/" + id)
            .when()
                .get()
            .then()
                .statusCode(200)
                .extract()
                .as(NavigationPojo.class);
    }

    public int createNavigation(String slug, String status, String password, String title, String content) {
        return given()
                .spec(requestSpec)
                .queryParam("rest_route", rest_route)
                .queryParam("slug", slug)
                .queryParam("status", status)
                .queryParam("password", password)
                .queryParam("title", title)
                .queryParam("content", content)
           .when()
                .post()
           .then()
                .statusCode(201)
                .extract()
                .path("id");
    }

    public int updateNavigation(int id, String slug, String status, String password, String title, String content) {
        return given()
                .spec(requestSpec)
                .queryParam("rest_route", rest_route + "/" + id)
                .queryParam("slug", slug)
                .queryParam("status", status)
                .queryParam("password", password)
                .queryParam("title", title)
                .queryParam("content", content)
            .when()
                .post()
            .then()
                .statusCode(200)
                .extract()
                .path("id");
    }

    public void deleteNavigation(int id) {
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
