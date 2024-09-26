package ru.t1academy.apitests.services;

import io.restassured.http.ContentType;
import ru.t1academy.apitests.data.TestData;
import ru.t1academy.apitests.model.Message;
import static io.restassured.RestAssured.given;

public class RegisterService extends BaseService{
    public RegisterService() {
        super("register");
        requestSpecification.contentType(ContentType.JSON);
    }
    public void registerUser() {
        Message m = given()
                .body(TestData.USER_REGISTRATION)
                .post()
                .then()
                .extract().as(Message.class);
    }
}
