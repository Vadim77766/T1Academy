package ru.t1academy.autotests.uitests.task5.asserts;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.text.NumberFormat;
import java.text.ParseException;

public class NumberAssert extends AbstractAssert<NumberAssert, WebElement> {
    protected NumberAssert(WebElement element) {
        super(element, NumberAssert.class);
    }

    public static NumberAssert assertThat(WebElement element) {
        return new NumberAssert(element);
    }

    public NumberAssert isNumberOrEmpty(String expected) {
        try {
            Integer.parseInt(expected);
            Assertions.assertThat(actual.getAttribute("value")).isEqualTo(expected);
        } catch (NumberFormatException e) {
            Assertions.assertThat(actual.getAttribute("value")).isEmpty();
        }
        return this;
    }
}
