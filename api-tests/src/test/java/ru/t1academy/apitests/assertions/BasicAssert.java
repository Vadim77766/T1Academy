package ru.t1academy.apitests.assertions;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class BasicAssert extends AbstractAssert<BasicAssert, Response> {
    protected BasicAssert(Response actual) {
        super(actual, BasicAssert.class);
    }

    public static BasicAssert assertThat(Response actual) {
        return new BasicAssert(actual);
    }

    @Step("Проверка что статус код равен '{expectedCode}'")
    public BasicAssert hasStatusCode(int expectedCode) {
        Assertions.assertThat(actual.getStatusCode())
                .as("Код ответа не равен " + expectedCode)
                .isEqualTo(expectedCode);
        return this;
    }
    @Step("Проверка что ответ содержит поле '{path}'")
    public BasicAssert containsField(String path) {
        Assertions.assertThat(actual.jsonPath().getString(path))
                .as("Поле '" + path + "' не найдено в теле ответа")
                .isNotNull();
        return this;
    }
    @Step("Проверка что ответ поле '{path}' равно '{value}'")
    public BasicAssert fieldIsEqual(String path, String value) {
        Assertions.assertThat(actual.jsonPath().getString(path))
                .as("Поле '%s' не равно '%s'".formatted(path, value))
                .isEqualTo(value);
        return this;
    }
    @Step("Проверка что поле '{path}' не пустое")
    public BasicAssert fieldIsNotEmpty(String path) {
        Assertions.assertThat(actual.jsonPath().getString(path))
                .as("Поле '" + path + "' пустое")
                .isNotEmpty();
        return this;
    }
}