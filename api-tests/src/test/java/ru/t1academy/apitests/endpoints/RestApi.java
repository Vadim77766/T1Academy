package ru.t1academy.apitests.endpoints;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;

import ru.t1academy.apitests.utils.ApiProps;

public abstract class RestApi {

    static ApiProps props = ConfigFactory.create(ApiProps.class);

    static {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        if(props.enableLogs()) {
            requestSpecBuilder.log(LogDetail.ALL);
        }
        RestAssured.requestSpecification = requestSpecBuilder
                .addFilter(new AllureRestAssured())
                .build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        if(props.enableLogs()) {
            responseSpecBuilder.log(LogDetail.ALL);
        }
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    protected final String basePath;
    protected RequestSpecification requestSpecification;

    protected RestApi(String basePath) {
        this.basePath = basePath;
        this.requestSpecification = new RequestSpecBuilder().build()
                .config(RestAssuredConfig.config()
                        .httpClient(HttpClientConfig.httpClientConfig()
                                .setParam("http.connection.timeout", 5000)))
                .baseUri(props.baseUrl())
                .basePath(basePath)
                .accept(ContentType.JSON)
                .relaxedHTTPSValidation();
        RestAssured.requestSpecification = requestSpecification;
    }

    public RequestSpecification build() {
        return requestSpecification;
    }

    public void addAuth(String token) {
        requestSpecification.header("Authorization", "Bearer " + token);

    }
}
