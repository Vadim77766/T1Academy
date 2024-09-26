package ru.t1academy.apitests.services;

import io.restassured.http.ContentType;
import ru.t1academy.apitests.data.TestData;
import static io.restassured.RestAssured.given;
public class LoginService extends BaseService{
    public LoginService() {
        super("login");
        requestSpecification.contentType(ContentType.JSON);
    }
    public String getToken() {
        return given()
                .body(TestData.VALID_USER)
                .post()
                .then()
                .extract().response().jsonPath().getString("access_token");
    }
}
