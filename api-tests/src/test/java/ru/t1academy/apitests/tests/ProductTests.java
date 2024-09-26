package ru.t1academy.apitests.tests;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.t1academy.apitests.model.Message;
import ru.t1academy.apitests.model.ProductRes;
import ru.t1academy.apitests.services.ProductService;
import java.util.List;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductTests {

    @BeforeAll
    static public void installSpecs() {
        new ProductService().installSpecs();
    }

    @Test
    @DisplayName("Проверка получения списка продуктов")
    public void testGettingProductList() {
        List<ProductRes> prod =
                given()
                        .when()
                        .get()
                        .then()
                        .statusCode(200)
                        .extract().body().jsonPath().getList("", ProductRes.class);
        assertTrue(prod.size() > 1);
    }

    @Test
    @DisplayName("Проверка поиска продукта по id")
    public void testGettingProductById() {
        int id = 3;
        List<ProductRes> prod =
                given()
                        .when()
                        .get(Integer.toString(id))
                        .then()
                        .statusCode(200)
                        .extract().body().jsonPath().getList("", ProductRes.class);
        assertEquals(1, prod.size());
        assertTrue(prod.stream().anyMatch(p -> p.getId() == id));
    }

    @Test
    @DisplayName("Проверка поиска несуществующего продукта по id")
    public void testGettingProductByUnknownId() {
        int productId = 333;
        Message message = given()
                .when()
                .get(Integer.toString(productId))
                .then()
                .statusCode(404)
                .extract().body().jsonPath().getObject("", Message.class);
        assertEquals(message.getMessage(), "Product not found");
    }
}
