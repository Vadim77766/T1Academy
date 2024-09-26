package ru.t1academy.apitests.tests;

import org.junit.jupiter.api.*;
import ru.t1academy.apitests.data.TestData;
import ru.t1academy.apitests.model.*;
import ru.t1academy.apitests.services.CartService;
import ru.t1academy.apitests.services.LoginService;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartTests {
    private static String token;

    @BeforeAll
    static public void installSpecs() {
        token = new LoginService().getToken();
        new CartService().installSpecs();
    }

    @Test
    @DisplayName("Проверка получения карты клиента")
    @Order(1)
    public void testGettingShopingCart() {
        ShoppingCartRes scres = given()
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract().as(ShoppingCartRes.class);
        assertTrue(scres.getCart().size() > 0);
    }
    @Test
    @DisplayName("Проверка добавления существующего продукта в карточку клиента")
    @Order(2)
    public void testAddingProduct() {
        Message m = given()
                        .header("Authorization", "Bearer " + token)
                        .body(TestData.EXISTING_PRODUCT)
                        .when()
                        .post()
                        .then()
                        .statusCode(201)
                        .extract().as(Message.class);
        assertNotNull(m);
        assertEquals(m.getMessage(), "Product added to cart successfully");
        ShoppingCartRes scres = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().as(ShoppingCartRes.class);
        assertTrue(scres.getCart().size() > 0);
        assertTrue(scres.getCart().stream().anyMatch(p -> p.getId() == TestData.EXISTING_PRODUCT.getProductId()));
    }
    @Test
    @DisplayName("Проверка добавления в карточку клиента несуществующего продукта")
    @Order(3)
    public void testAdditionNotExistingProduct() {
        Message m = given()
                .header("Authorization", "Bearer " + token)
                .body(TestData.NOT_EXISTING_PRODUCT)
                .post()
                .then()
                .statusCode(404)
                .extract().as(Message.class);
        assertNotNull(m);
        assertEquals(m.getMessage(), "Product not found");
    }
    @Test
    @DisplayName("Проверка добавления продукта в карточку клиента, если клиент не авторизован")
    @Order(4)
    public void testAdditionProductWithoutAuthorization() {
        Msg msg = given()
                .header("Authorization", "")
                .body(TestData.EXISTING_PRODUCT)
                .post()
                .then()
                .statusCode(401)
                .extract().as(Msg.class);
        assertNotNull(msg);
        assertEquals(msg.getMsg(), "Missing Authorization Header");
    }
    @Test
    @DisplayName("удаление продукта из карточки клиента")
    @Order(5)
    public void testDeletingProductFromCart() {
        Message m = given()
                .header("Authorization", "Bearer " + token)
                .delete(Integer.toString(TestData.EXISTING_PRODUCT_ID))
                .then()
                .statusCode(200)
                .extract().as(Message.class);
        assertNotNull(m);
        assertEquals(m.getMessage(), "Product removed from cart");
        ShoppingCartRes scres = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().as(ShoppingCartRes.class);
        assertTrue(scres.getCart().size() > 0);
        assertTrue(scres.getCart().stream().noneMatch(p -> p.getId() == TestData.EXISTING_PRODUCT_ID),
                "Product was not deleted !!!");
    }
    @Test
    @DisplayName("Проверка удаления продукта, который не был добавлен в карточку клиента")
    @Order(6)
    public void testDeletingProductNotAddedInCart() {
        Message m = given()
                .header("Authorization", "Bearer " + token)
                .delete(Integer.toString(TestData.NOT_EXISTING_PRODUCT_ID))
                .then()
                .statusCode(404)
                .extract().as(Message.class);
        assertNotNull(m);
        assertEquals(m.getMessage(), "Product not found in cart");
    }
}
