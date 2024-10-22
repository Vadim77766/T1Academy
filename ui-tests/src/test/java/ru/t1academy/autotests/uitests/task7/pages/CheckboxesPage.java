package ru.t1academy.autotests.uitests.task7.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class CheckboxesPage extends BasePage {

    public CheckboxesPage(String path) {
        super(path);
    }
    static final Logger log = getLogger(lookup().lookupClass());
    SelenideElement checkBox1 = $(By.cssSelector("form#checkboxes input[type='checkbox']:first-child"));
    SelenideElement checkBox2 = $(By.cssSelector("form#checkboxes input[type='checkbox']:last-child"));

    public CheckboxesAssert check() {
        return CheckboxesAssert.assertThat(this);
    }

    @Step("Кликнуть на первый чекбокс")
    public CheckboxesPage clickFirstCheckbox() {
        checkBox1.shouldBe(visible).click();
        return this;
    }
    @Step("Кликнуть на второй чекбокс")
    public CheckboxesPage clickSecondCheckbox() {
        checkBox2.shouldBe(visible).click();
        return this;
    }
    @Step("Кликнуть на чекбоксы в прямом порядке")
    public CheckboxesPage forwardCheckboxesClick() {
        log.info("Checkboxes attribute checked state before clicks");
        log.info("Checkbox 1 : attribute checked : {}", checkBox1.getAttribute("checked"));
        log.info("Checkbox 2 : attribute checked : {}", checkBox2.getAttribute("checked"));
        clickFirstCheckbox();
        clickSecondCheckbox();
        log.info("Checkboxes attribute checked state after clicks");
        log.info("Checkbox 1 : attribute checked : {}", checkBox1.getAttribute("checked"));
        log.info("Checkbox 2 : attribute checked : {}", checkBox2.getAttribute("checked"));
        return this;
    }
    @Step("Кликнуть на чекбоксы в обратном порядке")
    public CheckboxesPage reverseCheckboxesClick() {
        log.info("Checkboxes attribute checked state before clicks");
        log.info("Checkbox 1 : attribute checked : {}", checkBox1.getAttribute("checked"));
        log.info("Checkbox 2 : attribute checked : {}", checkBox2.getAttribute("checked"));
        clickSecondCheckbox();
        clickFirstCheckbox();
        log.info("Checkboxes attribute checked state after clicks");
        log.info("Checkbox 1 : attribute checked : {}", checkBox1.getAttribute("checked"));
        log.info("Checkbox 2 : attribute checked : {}", checkBox2.getAttribute("checked"));
        return this;
    }
}
