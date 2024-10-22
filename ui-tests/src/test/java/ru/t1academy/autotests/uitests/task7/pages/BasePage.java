package ru.t1academy.autotests.uitests.task7.pages;

import org.aeonbits.owner.ConfigFactory;
import ru.t1academy.autotests.uitests.task7.UIProps;

import static com.codeborne.selenide.Selenide.open;

public class BasePage {

    UIProps props = ConfigFactory.create(UIProps.class);

    BasePage(String path) {
        open(props.baseUrl() + path);
    }
}
