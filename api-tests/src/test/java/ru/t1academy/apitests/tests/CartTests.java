package ru.t1academy.apitests.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import ru.t1academy.apitests.assertions.BasicAssert;
import ru.t1academy.apitests.assertions.DTOObjectAssert;
import ru.t1academy.apitests.data.TestData;
import ru.t1academy.apitests.endpoints.CartApi;
import ru.t1academy.apitests.endpoints.LoginApi;
import ru.t1academy.apitests.model.ProductQuantity;
import ru.t1academy.apitests.model.response.ShoppingCart;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CartTests {
    private static LoginApi loginApi;
    private static CartApi cartApi;
    private static String token;

    @BeforeAll
    static public void installSpecs() {
        loginApi = new LoginApi();
        token = loginApi.getDefaultToken();
        cartApi = new CartApi();
    }

    @Test
    @DisplayName("Проверка получения карты клиента")
    @Order(1)
    public void testGettingShopingCart() {
        Response response = cartApi.getShopingCartResponse(token);

        BasicAssert.assertThat(response)
                .hasStatusCode(200);

        DTOObjectAssert.assertThat(response)
                .hasSizeGreaterThan("cart", ProductQuantity.class, 0);
    }
    @Test
    @DisplayName("Проверка добавления существующего продукта в карточку клиента")
    @Order(2)
    public void testAddingProduct() {
        Response response = cartApi.addProductToCart(TestData.EXISTING_PRODUCT, token);

        BasicAssert.assertThat(response)
                .hasStatusCode(201)
                .containsField("message")
                .fieldIsEqual("message", "Product added to cart successfully");

        response = cartApi.getShopingCartResponse(token);

        DTOObjectAssert.assertThat(response)
                .hasSizeGreaterThan("cart", ProductQuantity.class, 0);

        ShoppingCart scres = response.as(ShoppingCart.class);
        Assertions.assertTrue(scres.getCart().stream()
                .anyMatch(p -> p.getId() == TestData.EXISTING_PRODUCT.getProductId()));
    }
    @Test
    @DisplayName("Проверка добавления в карточку клиента несуществующего продукта")
    @Order(3)
    public void testAdditionNotExistingProduct() {
        Response response = cartApi.addProductToCart(TestData.NOT_EXISTING_PRODUCT, token);

        BasicAssert.assertThat(response)
                .hasStatusCode(404)
                .containsField("message")
                .fieldIsEqual("message", "Product not found");
    }
    @Test
    @DisplayName("Проверка добавления продукта в карточку клиента, если клиент не авторизован")
    @Order(4)
    public void testAdditionProductWithoutAuthorization() {
        Response response = cartApi.addProductToCart(TestData.EXISTING_PRODUCT, "");

        BasicAssert.assertThat(response)
                .hasStatusCode(422)
                .containsField("msg")
                .fieldIsEqual("msg", "Bad Authorization header. Expected 'Authorization: Bearer <JWT>'");
    }
    @Test
    @DisplayName("удаление продукта из карточки клиента")
    @Order(5)
    public void testDeletingProductFromCart() {
        Response response = cartApi.deleteProductFromCart(TestData.EXISTING_PRODUCT_ID, token);

        BasicAssert.assertThat(response)
                .hasStatusCode(200)
                .containsField("message")
                .fieldIsEqual("message", "Product removed from cart");

        response = cartApi.getShopingCartResponse(token);

        DTOObjectAssert.assertThat(response)
                .hasSizeGreaterThan("cart", ProductQuantity.class, 0);

        ShoppingCart scres = response.as(ShoppingCart.class);
        Assertions.assertTrue(scres.getCart().stream()
                .noneMatch(p -> p.getId() == TestData.EXISTING_PRODUCT_ID),
                "Product was not deleted !!!");
    }
    @Test
    @DisplayName("Проверка удаления продукта, который не был добавлен в карточку клиента")
    @Order(6)
    public void testDeletingProductNotAddedInCart() {
        Response response = cartApi.deleteProductFromCart(TestData.NOT_EXISTING_PRODUCT_ID, token);

        BasicAssert.assertThat(response)
                .hasStatusCode(404)
                .containsField("message")
                .fieldIsEqual("message", "Product not found in cart");
    }
}
