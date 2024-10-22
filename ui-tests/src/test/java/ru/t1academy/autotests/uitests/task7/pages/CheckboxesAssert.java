package ru.t1academy.autotests.uitests.task7.pages;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.selected;
import static com.codeborne.selenide.Condition.visible;

public class CheckboxesAssert extends AbstractAssert<CheckboxesAssert, CheckboxesPage> {
    protected CheckboxesAssert(CheckboxesPage actual) {
        super(actual, CheckboxesAssert.class);
    }

    public static CheckboxesAssert assertThat(CheckboxesPage actual) {
        return new CheckboxesAssert(actual);
    }

    @Step("Пользователь видит Checkbox1")
    public CheckboxesAssert firstCheckboxIsVisible() {
        actual.checkBox1.shouldBe(visible);
        return this;
    }

    @Step("Пользователь видит Checkbox2")
    public CheckboxesAssert secondCheckboxIsVisible() {
        actual.checkBox2.shouldBe(visible);
        return this;
    }

    @Step("Первый checkbox выбран")
    public CheckboxesAssert firstCheckboxIsSelected() {
        actual.checkBox1.shouldBe(selected);
        return this;
    }

    @Step("Второй checkbox не выбран")
    public CheckboxesAssert secondCheckboxIsNotSelected() {
        actual.checkBox2.shouldNotBe(selected);
        return this;
    }
}
