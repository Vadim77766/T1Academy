package ru.t1academy.apitests.assertions;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;

public class DTOObjectAssert extends AbstractAssert<DTOObjectAssert, Response> {
    protected DTOObjectAssert(Response actual) {
        super(actual, DTOObjectAssert.class);
    }

    public static DTOObjectAssert assertThat(Response actual) {
        return new DTOObjectAssert(actual);
    }

    @Step("Проверка что респонс содежит поле {path} с объектами типа {clazz} размером не менее {boundary}")
    public DTOObjectAssert hasSizeGreaterThan(String path, Class<?> clazz, int boundary) {
        Assertions.assertThat(actual.jsonPath().getList(path, clazz))
                .as("Размер списка объектов '%s' в поле '%s' не больше '%d'"
                        .formatted(clazz.getName(), path, boundary))
                .hasSizeGreaterThan(boundary);
        return this;
    }
    @Step("Проверка что респонс содежит поле {path} с объектами типа {clazz} размером {expectedSize}")
    public DTOObjectAssert hasSize(String path, Class<?> clazz, int expectedSize) {
        Assertions.assertThat(actual.jsonPath().getList(path, clazz))
                .as("Размер списка объектов '%s' в поле '%s' не равен '%d'"
                        .formatted(clazz.getName(), path, expectedSize))
                .hasSize(expectedSize);
        return this;
    }
}