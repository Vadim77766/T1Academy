package ru.t1academy.apitests.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.t1academy.apitests.data.TestData;
import ru.t1academy.apitests.model.request.UserRegistration;
import static io.restassured.RestAssured.given;

public class RegisterApi extends BaseApi {
    public RegisterApi() {
        super(Urls.REGISTER);
        requestSpecification.contentType(ContentType.JSON);
    }
    public Response registerUser(String name, String pass) {
        return registerUser(new UserRegistration(name, pass));
    }
    public Response registerUser(UserRegistration userRegistration) {
        return given(requestSpecification)
                .body(userRegistration)
                .post();
    }
    public Response registerDefaultUser() {
        return registerUser(TestData.USER_REGISTRATION);
    }
}
