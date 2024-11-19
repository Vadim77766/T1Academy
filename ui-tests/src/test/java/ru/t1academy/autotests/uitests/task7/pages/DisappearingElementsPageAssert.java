package ru.t1academy.autotests.uitests.task7.pages;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.CollectionCondition.size;

public class DisappearingElementsPageAssert extends AbstractAssert<DisappearingElementsPageAssert, DisappearingElementsPage> {
    protected DisappearingElementsPageAssert(DisappearingElementsPage actual) {
        super(actual, DisappearingElementsPageAssert.class);
    }

    public static DisappearingElementsPageAssert assertThat(DisappearingElementsPage actual) {
        return new DisappearingElementsPageAssert(actual);
    }

    @Step("Проверяем, что количество элементов равно {expectedValue}")
    public DisappearingElementsPageAssert numberOfElementsIsEqual(int expectedValue) {
        actual.elements.shouldHave(size(expectedValue));
        return this;
    }
}
