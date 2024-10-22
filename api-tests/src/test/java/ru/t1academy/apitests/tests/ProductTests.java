package ru.t1academy.apitests.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.t1academy.apitests.assertions.BasicAssert;
import ru.t1academy.apitests.assertions.DTOObjectAssert;
import ru.t1academy.apitests.endpoints.ProductApi;
import ru.t1academy.apitests.model.response.Product;

public class ProductTests {

    private static ProductApi productApi;
    @BeforeAll
    static public void installSpecs() {
        productApi = new ProductApi();
    }

    @Test
    @DisplayName("Проверка получения списка продуктов")
    public void testGettingProductList() {
        Response response = productApi.getProductsResponse();

        BasicAssert.assertThat(response)
                .hasStatusCode(200);
        DTOObjectAssert.assertThat(response)
                .hasSizeGreaterThan("", Product.class, 1);
    }

    @Test
    @DisplayName("Проверка поиска продукта по id")
    public void testGettingProductById() {
        int id = 3;
        Response response = productApi.getProductByIdResponse(id);

        BasicAssert.assertThat(response)
                .hasStatusCode(200);
        DTOObjectAssert.assertThat(response)
                .hasSize("", Product.class, 1);
    }

    @Test
    @DisplayName("Проверка поиска несуществующего продукта по id")
    public void testGettingProductByUnknownId() {
        int productId = 333;
        Response response = productApi.getProductByIdResponse(productId);

        BasicAssert.assertThat(response)
                .hasStatusCode(404)
                .containsField("message")
                .fieldIsEqual("message", "Product not found");
    }
}
