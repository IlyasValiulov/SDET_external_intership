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
    private final String BASE_URL = "http://localhost:8000/index.php";
    protected RequestSpecification requestSpec;

    @BeforeClass
    public void setRequestSpecification() throws IOException {
        String username = ProjectProperties.getProperty("username");
        String password = ProjectProperties.getProperty("password");

        RestAssured.defaultParser = Parser.JSON;
        requestSpec = given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .auth().preemptive().basic(username, password);
    }
}
