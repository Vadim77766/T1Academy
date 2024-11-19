package ru.t1academy.autotests.uitests.task7.pages;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.selected;
import static com.codeborne.selenide.Condition.visible;

public class CheckboxesPageAssert extends AbstractAssert<CheckboxesPageAssert, CheckboxesPage> {
    protected CheckboxesPageAssert(CheckboxesPage actual) {
        super(actual, CheckboxesPageAssert.class);
    }

    public static CheckboxesPageAssert assertThat(CheckboxesPage actual) {
        return new CheckboxesPageAssert(actual);
    }

    @Step("Пользователь видит Checkbox1")
    public CheckboxesPageAssert firstCheckboxIsVisible() {
        actual.checkBox1.shouldBe(visible);
        return this;
    }

    @Step("Пользователь видит Checkbox2")
    public CheckboxesPageAssert secondCheckboxIsVisible() {
        actual.checkBox2.shouldBe(visible);
        return this;
    }

    @Step("Первый checkbox выбран")
    public CheckboxesPageAssert firstCheckboxIsSelected() {
        actual.checkBox1.shouldBe(selected);
        return this;
    }

    @Step("Второй checkbox не выбран")
    public CheckboxesPageAssert secondCheckboxIsNotSelected() {
        actual.checkBox2.shouldNotBe(selected);
        return this;
    }
}
