package ru.t1academy.autotests.uitests.task6;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.remote.SessionId;
import org.slf4j.Logger;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class SelenideTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true));
    }

    @BeforeEach
    void setup() {

    }

    @Tag("smoke")
    @Test
    public void smoke() {
        String url = "https://the-internet.herokuapp.com/";
        open(url);
        String title = title();
        log.info("The title of {} is {}", url, title);
        assertThat(title).isEqualTo("The Internet");
        SessionId sessionId = Selenide.sessionId();
        assertThat(sessionId).isNotNull();
        log.info("The sessionId is {}", sessionId.toString());
    }

    @Description("Перейти на страницу Drag and Drop. " +
            "Перетащить элемент A на элемент B. " +
            "Задача на 10 баллов – сделать это, не прибегая к методу DragAndDrop(); " +
            "Проверить, что элементы поменялись местами")
    @Owner("Vadim")
    @ParameterizedTest
    @DisplayName("Test on page Drag and Drop")
    @ValueSource(booleans = {true, false})
    public void testDradAndDrop(boolean withoutDragAndDropMethod) {
        open("https://the-internet.herokuapp.com/drag_and_drop");
        SelenideElement draggableA = $(By.xpath("//header[text()='A']"));
        SelenideElement draggableB = $(By.xpath("//header[text()='B']"));
        Point locationA = draggableA.getLocation();
        Point locationB = draggableB.getLocation();
        log.info("Position of element A : {}", locationA);
        log.info("Position of element B : {}", locationB);

        if(withoutDragAndDropMethod) {
            actions()
                    .clickAndHold(draggableA)
                    .pause(50)
                    .moveToElement(draggableB)
                    .pause(50)
                    .release().build().perform();
        } else {
            actions().dragAndDrop(draggableA, draggableB).build().perform();
        }

        Point locationAAfter = $(By.xpath("//header[text()='A']")).getLocation();
        Point locationBAfter = $(By.xpath("//header[text()='B']")).getLocation();
        log.info("New Position of element A : {}", locationAAfter);
        log.info("New Position of element B : {}", locationBAfter);

        assertThat(locationA).isEqualTo(locationBAfter);
        assertThat(locationAAfter).isEqualTo(locationB);
    }

    @Description("Перейти на страницу Context menu. " +
            "Нажать правой кнопкой мыши на отмеченной области и проверить, " +
            "что JS Alert имеет ожидаемый текст.")
    @Owner("Vadim")
    @Test
    @DisplayName("Test on page Context menu")
    public void testContextMenu() {
        open("https://the-internet.herokuapp.com/context_menu");
        SelenideElement hotSpot = $(By.id("hot-spot"));
        actions().contextClick(hotSpot).build().perform();
        Alert alert = switchTo().alert();
        String text = alert.getText();
        alert.accept();
        log.info("Alert Text : {}", text);
        assertThat(text).isEqualTo("You selected a context menu");
    }

    @Description("Перейти на страницу Infinite Scroll. " +
            "Проскролить страницу до текста «Eius», проверить, что текст в поле зрения.")
    @Owner("Vadim")
    @Test
    @DisplayName("Test on page Infinite Scroll")
    public void testInfiniteScroll() {
        open("https://the-internet.herokuapp.com/infinite_scroll");
        final String SEARCH_TEXT = "Eius";
        Action action = actions().scrollByAmount(0, 300).build();
        while (true) {
            action.perform();
            SelenideElement scroll = $(By.cssSelector(".jscroll-inner .jscroll-added:last-child"));
            String text = scroll.getText();
            if (text.contains(SEARCH_TEXT)) {
                log.info("Text found in paragraph : {}", text);
                scroll.shouldBe(visible);
                break;
            }
        }
    }

    @Description("Перейти на страницу Key Presses. " +
            "Нажать по 10 латинских символов, клавиши Enter, Ctrl, Alt, Tab. " +
            "Проверить, что после нажатия отображается всплывающий текст снизу, " +
            "соответствующий конкретной клавише.")
    @Owner("Vadim")
    @Test
    @DisplayName("Test on page Key Presses")
    public void testKeyPresses() {
        open("https://the-internet.herokuapp.com/key_presses");
        CharSequence[] keys = new CharSequence[] { "A", "H", "G", "R", "U", "K", "N", "C", "M", "Q",
                Keys.ENTER, Keys.CONTROL, Keys.ALT, Keys.TAB };
        for (CharSequence key : keys) {
            actions().pause(50).sendKeys(key).build().perform();
            SelenideElement result = $(By.id("result"));
            String keyText = String.valueOf(key); // название кнопки
            if (key instanceof Keys) {
                keyText = ((Keys) key).name();
            }
            assertThat(result.getText()).isEqualTo("You entered: " + keyText);
            log.info("Key sent : {}", keyText);
        }
    }

    @AfterEach
    void teardown() {
        closeWebDriver();
    }
}
