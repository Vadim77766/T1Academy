package ru.t1academy.apitests.endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.t1academy.apitests.model.request.AddProduct;
import ru.t1academy.apitests.model.response.Product;
import ru.t1academy.apitests.model.response.ShoppingCart;

import static io.restassured.RestAssured.given;

public class CartApi extends BaseApi {
    public CartApi() {
        super(Urls.CART);
        requestSpecification.contentType(ContentType.JSON);
    }

    public Response getShopingCartResponse(String token) {
        return given(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .get();
    }

    public ShoppingCart getShopingCart(String token) {
        return getShopingCartResponse(token)
                .as(ShoppingCart.class);
    }

    public Response addProductToCart(AddProduct product, String token) {
        return given(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .body(product)
                .post();
    }

    public Response deleteProductFromCart(int id, String token) {
        return given(requestSpecification)
                .header("Authorization", "Bearer " + token)
                .delete(Integer.toString(id));
    }
}
