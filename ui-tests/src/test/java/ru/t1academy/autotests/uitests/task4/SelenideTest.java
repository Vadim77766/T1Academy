package ru.t1academy.autotests.uitests.task4;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.slf4j.Logger;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
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
    public void test1() {
        String url = "https://the-internet.herokuapp.com/";
        open(url);

        String title = title();
        log.info("The title of {} is {}", url, title);
        assertThat(title).isEqualTo("The Internet");

        SessionId sessionId = Selenide.sessionId();
        assertThat(sessionId).isNotNull();
        log.info("The sessionId is {}", sessionId.toString());
    }

    void waits() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    @DisplayName("Tests on page Checkboxes")
    public void testCheckBoxes() {
        open("https://the-internet.herokuapp.com/checkboxes");
        SelenideElement checkBox1 =
                $(By.cssSelector("form#checkboxes input[type='checkbox']:first-child"));
        SelenideElement checkBox2 =
                $(By.cssSelector("form#checkboxes input[type='checkbox']:last-child"));
        log.info("Checkboxes attribute checked state before clicks");
        log.info("Checkbox 1 : attribute checked : {}", checkBox1.getAttribute("checked"));
        log.info("Checkbox 2 : attribute checked : {}", checkBox2.getAttribute("checked"));
        checkBox1.click();
        checkBox2.click();
        log.info("Checkboxes attribute checked state after clicks");
        log.info("Checkbox 1 : attribute checked : {}", checkBox1.getAttribute("checked"));
        log.info("Checkbox 2 : attribute checked : {}", checkBox2.getAttribute("checked"));
        assertThat(checkBox1.isSelected()).isTrue();
        assertThat(checkBox2.isSelected()).isFalse();
        waits();
    }

    @Test
    @DisplayName("Tests on page Dropdown")
    public void testDropdown() {
        open("https://the-internet.herokuapp.com/dropdown");
        SelenideElement dropdown = $(By.id("dropdown"));
        String option = "Option 1";
        dropdown.selectOption(option);
        log.info("Dropdown text : {}", dropdown.getSelectedOptionText());
        option = "Option 2";
        dropdown.selectOption(option);
        log.info("Dropdown text : {}", dropdown.getSelectedOptionText());
        assertThat(dropdown.getSelectedOptionText()).isEqualTo(option);
        waits();
    }

    @Test
    @DisplayName("Tests on page Disappearing Elements")
    public void testDisappearingElements() {
        open("https://the-internet.herokuapp.com/disappearing_elements");
        int countDown = 10;
        while (countDown-- > 0) {
            ElementsCollection elements = $$(By.cssSelector("ul li a"));
            if(elements.size() == 5) {
                assertThat(elements.size()).isEqualTo(5);
                return;
            }
            refresh();
            waits();
        }
        log.info("Number of elements not equal to 5");
        Assertions.fail();
    }

    @Test
    @DisplayName("Tests on page Inputs")
    public void testInputs() {
        open("https://the-internet.herokuapp.com/inputs");
        SelenideElement number =
                $(By.cssSelector("input[type='number']"));
        String numberText = "1234";
        number.sendKeys(numberText);
        log.info("Input value : {}", number.getAttribute("value"));
        assertThat(number.getAttribute("value")).isEqualTo(numberText);
        waits();
    }

    @Test
    @DisplayName("Tests on page Hovers")
    public void testHovers() {
        open("https://the-internet.herokuapp.com/hovers");
        Actions actions = actions();
        ElementsCollection images =
                $$(By.cssSelector(".figure img"));
        for(SelenideElement image : images) {
            image.hover();
            // искомый текст располагается ниже(near) относительно(relative) картинки
            SelenideElement text = $(RelativeLocator.
                    with(By.cssSelector("div.figcaption")).near(image.getWrappedElement()));
            log.info("Text : {}", text.getText());
            assertThat(text.getText()).isNotEmpty();
        }
        waits();
    }

    @Test
    @DisplayName("Tests on page Notification Messages")
    public void testNotificationMessages() {
        open("https://the-internet.herokuapp.com/notification_message_rendered");
        // на элемент нельзя кликнуть если он перекрывается другим
        // чтобы элементы не перекрывались максимизируем окно
        getWebDriver().manage().window().maximize();
        while (true) {
            ElementsCollection flash = $$(By.cssSelector("div#flash"));
            if(flash.size() > 0) {
                if(flash.get(0).getText().contains("unsuccesful")) {
                    $(By.cssSelector("a.close")).click();
                    // альтернативный вариант кликнуть джаваскпиптом
                    // Selenide.executeJavaScript("arguments[0].click();", $(By.cssSelector("a.close")));
                } else {
                    break;
                }
            }
            $(By.cssSelector("a[href='/notification_message")).click();
        }
        assertThat($(By.cssSelector("div#flash")).getText()).contains("Action successful");
        waits();
    }

    @Test
    @DisplayName("Tests on page Add/Remove Elements")
    public void testAddRemoveElements() {
        open("https://the-internet.herokuapp.com/add_remove_elements/");
        SelenideElement addElement =
                $(By.cssSelector("button[onclick='addElement()']"));
        for (int i = 0; i < 5; i++) {
            addElement.click();
            SelenideElement deleteElement = $(By.cssSelector("#elements button:last-child"));
            log.info("Element text : {}", deleteElement.getText());
        }
        By deletesLocator = By.cssSelector("button[onclick='deleteElement()']");
        for (int i = 0; i < 3; i++) {
            ElementsCollection deleteElements = $$(deletesLocator);
            int r = (int) (Math.random() * deleteElements.size());
            log.info("Click by index of element : {}", r);
            deleteElements.get(r).click();
            deleteElements = $$(deletesLocator);
            log.info("Number of Delete Elements : {}", deleteElements.size());
            for (int deleteIndex = 0; deleteIndex < deleteElements.size(); deleteIndex++) {
                log.info("Text of Delete element #{} : {}", deleteIndex, deleteElements.get(deleteIndex).getText());
            }
        }
        waits();
    }

    @Test
    @DisplayName("Tests on page Status Codes")
    public void testStatusCodes() {
        open("https://the-internet.herokuapp.com/status_codes");

    }

    @Test
    @DisplayName("Tests on page Status Codes 200")
    public void testStatusCodes200() {
        open("https://the-internet.herokuapp.com/status_codes");
        $(By.cssSelector("a[href*='200']")).click();
        String text = $(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        back();
    }

    @Test
    @DisplayName("Tests on page Status Codes 301")
    public void testStatusCodes301() {
        open("https://the-internet.herokuapp.com/status_codes");
        $(By.cssSelector("a[href*='301']")).click();
        String text = $(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        back();
    }

    @Test
    @DisplayName("Tests on page Status Codes 404")
    public void testStatusCodes404() {
        open("https://the-internet.herokuapp.com/status_codes");
        $(By.cssSelector("a[href*='404']")).click();
        String text = $(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        back();
    }

    @Test
    @DisplayName("Tests on page Status Codes 500")
    public void testStatusCodes500() {
        open("https://the-internet.herokuapp.com/status_codes");
        $(By.cssSelector("a[href*='500']")).click();
        String text = $(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        back();
    }

    @AfterEach
    void teardown() {
        closeWebDriver();
    }
}
