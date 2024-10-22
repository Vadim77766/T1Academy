package ru.t1academy.apitests.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.t1academy.apitests.assertions.BasicAssert;
import ru.t1academy.apitests.data.TestData;
import ru.t1academy.apitests.endpoints.LoginApi;
import ru.t1academy.apitests.endpoints.RegisterApi;

import static io.restassured.RestAssured.given;

public class AuthTests {

    private static RegisterApi registerApi;
    private static LoginApi loginApi;
    @BeforeAll
    static public void installSpecs() {
        registerApi = new RegisterApi();
        registerApi.registerDefaultUser();
        loginApi = new LoginApi();

    }

    @Tag("smoke")
    @Test
    @DisplayName("Проверка авторизации незарегистрированного пользователя")
    public void testUnregisteredUserAuth() {
        Response response = given(loginApi.build())
                .body(TestData.USER_NOT_EXISTING)
                .post();
        BasicAssert.assertThat(response)
                .hasStatusCode(401)
                .containsField("message")
                .fieldIsEqual("message","Invalid credentials");
    }

    @Test
    @DisplayName("Проверка авторизации пользователя, зарегистрированного в системе")
    public void testRegisteredUserAuth() {
        Response response = given(loginApi.build())
                .body(TestData.VALID_USER)
                .post();

        BasicAssert.assertThat(response)
                .hasStatusCode(200)
                .containsField("access_token")
                .fieldIsNotEmpty("access_token");
    }

    @Test
    @DisplayName("Проверка авторизации зарегистрированного пользователя с неверным паролем")
    public void testRegisteredUserWrongPassAuth() {
        Response response = given(loginApi.build())
                .body(TestData.USER_INVALID_PASS)
                .post();
        BasicAssert.assertThat(response)
                .hasStatusCode(401)
                .containsField("message")
                .fieldIsEqual("message","Invalid credentials");
    }
}
