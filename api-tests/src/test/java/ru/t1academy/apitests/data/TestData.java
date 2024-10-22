package ru.t1academy.apitests.data;

import ru.t1academy.apitests.model.request.AddProduct;
import ru.t1academy.apitests.model.request.UserLogin;
import ru.t1academy.apitests.model.request.UserRegistration;

public class TestData {

    public static UserRegistration USER_REGISTRATION = new UserRegistration("Joe", "1234");
    public static UserLogin USER_NOT_EXISTING = new UserLogin("JoeUnknown", "1234");
    public static UserLogin USER_INVALID_PASS = new UserLogin("Joe", "12345");
    public static UserLogin VALID_USER = new UserLogin("Joe", "1234");
    public static int EXISTING_PRODUCT_ID = 3;
    public static int NOT_EXISTING_PRODUCT_ID = 3333;
    public static AddProduct EXISTING_PRODUCT = new AddProduct(EXISTING_PRODUCT_ID, 4);

    public static AddProduct NOT_EXISTING_PRODUCT = new AddProduct(NOT_EXISTING_PRODUCT_ID, 4);
}
