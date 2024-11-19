package ru.t1academy.autotests.uitests.task7.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class InputsPage extends BasePage {

    public InputsPage(String path) {
        super(path);
    }
    static final Logger log = getLogger(lookup().lookupClass());
    SelenideElement number = $(By.cssSelector("input[type='number']"));

    @Step("Ввод значения '{input}'")
    public InputsPage sendKeys(String input) {
        number.click();
        number.sendKeys(input);
        log.info("Input value : {}", number.getAttribute("value"));
        return this;
    }
}
