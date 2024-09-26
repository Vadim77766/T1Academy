package ru.t1academy.apitests.services;

import io.restassured.http.ContentType;

public class CartService extends BaseService {
    public CartService() {
        super("cart");
        requestSpecification.contentType(ContentType.JSON);
    }
}
