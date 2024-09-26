package ru.t1academy.apitests.data;

import ru.t1academy.apitests.model.AddProductReq;
import ru.t1academy.apitests.model.UserLoginReq;
import ru.t1academy.apitests.model.UserRegistrationReq;

public class TestData {

    public static UserRegistrationReq USER_REGISTRATION = new UserRegistrationReq("Joe", "1234");
    public static UserLoginReq USER_NOT_EXISTING = new UserLoginReq("JoeUnknown", "1234");
    public static UserLoginReq USER_INVALID_PASS = new UserLoginReq("Joe", "12345");
    public static UserLoginReq VALID_USER = new UserLoginReq("Joe", "1234");
    public static int EXISTING_PRODUCT_ID = 3;
    public static int NOT_EXISTING_PRODUCT_ID = 3333;
    public static AddProductReq EXISTING_PRODUCT = new AddProductReq(EXISTING_PRODUCT_ID, 4);

    public static AddProductReq NOT_EXISTING_PRODUCT = new AddProductReq(NOT_EXISTING_PRODUCT_ID, 4);
}
