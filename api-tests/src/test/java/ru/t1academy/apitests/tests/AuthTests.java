package ru.t1academy.apitests.tests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.t1academy.apitests.data.TestData;
import ru.t1academy.apitests.services.LoginService;
import ru.t1academy.apitests.services.RegisterService;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class AuthTests {
    @BeforeAll
    static public void installSpecs() {
        RegisterService rs = new RegisterService();
        rs.installSpecs();
        rs.registerUser();
        new LoginService().installSpecs();
    }

    @Tag("smoke")
    @Test
    @DisplayName("Проверка авторизации незарегистрированного пользователя")
    public void testUnregisteredUserAuth() {
        String message = given()
                .body(TestData.USER_NOT_EXISTING)
                .post()
                .then()
                .statusCode(401)
                .extract().response().jsonPath().getString("message");
        assertNotNull(message);
        assertEquals("Invalid credentials", message);
    }

    @Test
    @DisplayName("Проверка авторизации пользователя, зарегистрированного в системе")
    public void testRegisteredUserAuth() {
        String token = given()
                .body(TestData.VALID_USER)
                .post()
                .then()
                .statusCode(200)
                .extract().response().jsonPath().getString("access_token");
        assertNotNull(token);
        assertFalse(token.isEmpty(), "error : empty token");
    }

    @Test
    @DisplayName("Проверка авторизации зарегистрированного пользователя с неверным паролем")
    public void testRegisteredUserWrongPassAuth() {
        String message = given()
                .body(TestData.USER_INVALID_PASS)
                .post()
                .then()
                .statusCode(401)
                .body("message", Matchers.equalTo("Invalid credentials"))
                .extract().response().jsonPath().getString("message");
        assertNotNull(message);
        assertEquals("Invalid credentials", message);
    }
}
