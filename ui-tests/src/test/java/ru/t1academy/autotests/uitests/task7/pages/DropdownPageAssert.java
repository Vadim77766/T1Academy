package ru.t1academy.autotests.uitests.task7.pages;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.*;

public class DropdownPageAssert extends AbstractAssert<DropdownPageAssert, DropdownPage> {
    protected DropdownPageAssert(DropdownPage actual) {
        super(actual, DropdownPageAssert.class);
    }

    public static DropdownPageAssert assertThat(DropdownPage actual) {
        return new DropdownPageAssert(actual);
    }

    @Step("Пользователь видит, что выбрана опция '{option}'")
    public DropdownPageAssert hasSelectedOption(String option) {
        actual.dropdown.shouldHave(text(option));
        return this;
    }

}
