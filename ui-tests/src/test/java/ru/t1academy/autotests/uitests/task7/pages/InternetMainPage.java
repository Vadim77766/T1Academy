package ru.t1academy.autotests.uitests.task7.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class InternetMainPage {

    SelenideElement checkboxesLink = $("a[href='/checkboxes']");
    SelenideElement dropdownLink = $("a[href='/dropdown']");
    SelenideElement inputLink = $("a[href='/input']");

    @Step("Перейти на страницу 'Checkboxes'")
    void clickCheckBoxesLink() {
        checkboxesLink.shouldBe(visible).click();
    }

    @Step("Перейти на страницу 'Dropdown'")
    void clickDropdownLink() {
        dropdownLink.shouldBe(visible).click();
    }

    @Step("Перейти на страницу 'Input'")
    void clickInputLink() {
        inputLink.shouldBe(visible).click();
    }
}
