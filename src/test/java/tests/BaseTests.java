package tests;

import extension.ProjectProperties;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class BaseTests {
    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setRequestSpecification() throws IOException {
        String base_url = ProjectProperties.getProperty("base_url");
        String username = ProjectProperties.getProperty("username");
        String password = ProjectProperties.getProperty("password");

        RestAssured.defaultParser = Parser.JSON;
        requestSpec = given()
                .contentType(ContentType.JSON)
                .baseUri(base_url)
                .auth().preemptive().basic(username, password);
    }
}
