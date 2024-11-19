package ru.t1academy.autotests.uitests.task7.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.$;
import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class DropdownPage extends BasePage{

    public DropdownPage(String path) {
        super(path);
    }
    static final Logger log = getLogger(lookup().lookupClass());
    SelenideElement dropdown = $(By.id("dropdown"));

    @Step("Выбрать опцию '{option}'")
    public DropdownPage selectOption(String option) {
        dropdown.selectOption(option);
        log.info("Dropdown text : {}", dropdown.getSelectedOptionText());
        return this;
    }
}
