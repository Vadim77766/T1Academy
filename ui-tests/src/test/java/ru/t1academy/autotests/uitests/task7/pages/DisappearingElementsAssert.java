package ru.t1academy.autotests.uitests.task7.pages;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.value;

public class DisappearingElementsAssert extends AbstractAssert<DisappearingElementsAssert, DisappearingElementsPage> {
    protected DisappearingElementsAssert(DisappearingElementsPage actual) {
        super(actual, DisappearingElementsAssert.class);
    }

    public static DisappearingElementsAssert assertThat(DisappearingElementsPage actual) {
        return new DisappearingElementsAssert(actual);
    }

    @Step("Проверяем, что количество элементов равно {expectedValue}")
    public DisappearingElementsAssert numberOfElementsIsEqual(int expectedValue) {
        actual.elements.shouldHave(size(expectedValue));
        return this;
    }
}
