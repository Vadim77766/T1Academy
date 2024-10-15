package ru.t1academy.autotests.uitests.task5.asserts;

import io.qameta.allure.Step;
import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.support.ui.Select;

public class SelectAssert extends AbstractAssert<SelectAssert, Select> {

    protected SelectAssert(Select select) {
        super(select, SelectAssert.class);
    }

    @Step("Select assert")
    public static SelectAssert assertThat(Select select) {
        return new SelectAssert(select);
    }

    public SelectAssert hasSelectedOption(String optionExpected) {
        Assertions.assertThat(actual.getFirstSelectedOption().getText()).isEqualTo(optionExpected);
        return this;
    }
}
