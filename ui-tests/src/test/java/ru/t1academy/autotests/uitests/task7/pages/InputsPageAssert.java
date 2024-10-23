package ru.t1academy.autotests.uitests.task7.pages;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.value;

public class InputsPageAssert extends AbstractAssert<InputsPageAssert, InputsPage> {
    protected InputsPageAssert(InputsPage actual) {
        super(actual, InputsPageAssert.class);
    }

    public static InputsPageAssert assertThat(InputsPage actual) {
        return new InputsPageAssert(actual);
    }

    @Step("Проверяем, что значение аттрибута value равно '{expectedValue}'")
    public InputsPageAssert inputHasValue(String expectedValue) {
        actual.number.shouldHave(value(expectedValue));
        return this;
    }

}
