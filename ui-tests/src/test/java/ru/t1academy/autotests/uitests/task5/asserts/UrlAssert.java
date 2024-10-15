package ru.t1academy.autotests.uitests.task5.asserts;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebDriver;
public class UrlAssert extends AbstractAssert<UrlAssert, WebDriver> {
    protected UrlAssert(WebDriver driver) {
        super(driver, UrlAssert.class);
    }

    public static UrlAssert assertThat(WebDriver driver) {
        return new UrlAssert(driver);
    }

    public UrlAssert containsPath(String path) {
        Assertions.assertThat(actual.getCurrentUrl()).contains(path);
        return this;
    }
}
