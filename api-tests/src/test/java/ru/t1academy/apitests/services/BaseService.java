package ru.t1academy.apitests.services;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public abstract class BaseService {

    private static final String BASE_URL = "http://9b142cdd34e.vps.myjino.ru:49268/";

    static {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .log(LogDetail.ALL)
                .addFilter(new AllureRestAssured())
                .build();

        RestAssured.responseSpecification = new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }

    protected final String basePath;
    protected RequestSpecification requestSpecification;
    private ResponseSpecification responseSpecification;

    protected BaseService(String basePath) {
        this.basePath = basePath;
        this.requestSpecification = new RequestSpecBuilder().build()
                .baseUri(BASE_URL)
                .basePath(basePath)
                .accept(ContentType.JSON);
    }
    public void installSpecs() {
        RestAssured.requestSpecification = requestSpecification;
//        RestAssured.responseSpecification = responseSpecification;
    }
}
