package ru.t1academy.autotests.uitests.task7.pages;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;

import static com.codeborne.selenide.Condition.*;

public class DropdownAssert extends AbstractAssert<DropdownAssert, DropdownPage> {
    protected DropdownAssert(DropdownPage actual) {
        super(actual, DropdownAssert.class);
    }

    public static DropdownAssert assertThat(DropdownPage actual) {
        return new DropdownAssert(actual);
    }

    @Step("Пользователь видит, что выбрана опция '{option}'")
    public DropdownAssert hasSelectedOption(String option) {
        actual.dropdown.shouldHave(text(option));
        return this;
    }

}
