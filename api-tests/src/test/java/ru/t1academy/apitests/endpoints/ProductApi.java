package ru.t1academy.apitests.endpoints;

import io.restassured.response.Response;
import ru.t1academy.apitests.model.response.Product;

import java.util.List;

import static io.restassured.RestAssured.given;

public class ProductApi extends BaseApi {
    public ProductApi() {
        super(Urls.PRODUCTS);
    }

    public Response getProductsResponse() {
        return given(requestSpecification)
                .get();
    }

    public Response getProductByIdResponse(int id) {
        return given(requestSpecification)
                .get(Integer.toString(id));
    }

    public List<Product> getProductList() {
        return getProductsResponse()
                .jsonPath().getList("", Product.class);
    }

    public List<Product> getProductList(int id) {
        return getProductByIdResponse(id)
                .jsonPath().getList("", Product.class);
    }

    public Response deleteProductByIdResponse(int id) {
        return given(requestSpecification)
                .delete(Integer.toString(id));
    }

    public Response putProductByIdResponse(int id) {
        return given(requestSpecification)
                .put(Integer.toString(id));
    }

    public Response addProduct(int id, String name, String category, double price, double discount) {
        return given(requestSpecification)
                .body(new Product(id, name, category, price, discount))
                .post();
    }
}
