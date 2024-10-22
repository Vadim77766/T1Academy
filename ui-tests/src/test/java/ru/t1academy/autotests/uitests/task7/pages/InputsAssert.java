package ru.t1academy.autotests.uitests.task7.pages;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;

public class InputsAssert extends AbstractAssert<InputsAssert, InputsPage> {
    protected InputsAssert(InputsPage actual) {
        super(actual, InputsAssert.class);
    }

    public static InputsAssert assertThat(InputsPage actual) {
        return new InputsAssert(actual);
    }

    @Step("Проверяем, что значение аттрибута value равно '{expectedValue}'")
    public InputsAssert inputHasValue(String expectedValue) {
        actual.number.shouldHave(value(expectedValue));
        return this;
    }

}
