package ru.t1academy.autotests.uitests.task7.pages;

import com.codeborne.selenide.ElementsCollection;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$$;

public class DisappearingElementsPage extends BasePage {

    public DisappearingElementsPage(String path) {
        super(path);
    }
    ElementsCollection elements = $$(By.cssSelector("ul li a"));
}
