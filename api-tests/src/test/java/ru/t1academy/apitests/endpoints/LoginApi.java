package ru.t1academy.apitests.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.t1academy.apitests.data.TestData;
import ru.t1academy.apitests.model.request.UserLogin;

import static io.restassured.RestAssured.given;
public class LoginApi extends RestApi {
    public LoginApi() {
        super(Urls.LOGIN);
        requestSpecification.contentType(ContentType.JSON);
    }
    public Response getToken(UserLogin userLogin) {
        return given(requestSpecification)
                .body(userLogin)
                .post();
    }
    public String getTokenString(UserLogin userLogin) {
        return getToken(userLogin)
                .jsonPath()
                .getString("access_token");
    }
    public String getDefaultToken() {
        return getToken(TestData.VALID_USER)
                .jsonPath()
                .getString("access_token");
    }
}
