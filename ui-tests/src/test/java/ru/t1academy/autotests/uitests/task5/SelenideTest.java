package ru.t1academy.autotests.uitests.task5;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverConditions.urlContaining;
import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
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

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Tests on page Checkboxes")
    public void testCheckBoxes(boolean isForward) {
        open("https://the-internet.herokuapp.com/checkboxes");
        SelenideElement checkBox1 =
                $(By.cssSelector("form#checkboxes input[type='checkbox']:first-child"));
        SelenideElement checkBox2 =
                $(By.cssSelector("form#checkboxes input[type='checkbox']:last-child"));
        log.info("Checkboxes attribute checked state before clicks");
        log.info("Checkbox 1 : attribute checked : {}", checkBox1.getAttribute("checked"));
        log.info("Checkbox 2 : attribute checked : {}", checkBox2.getAttribute("checked"));
        if(isForward) {
            checkBox1.click();
            checkBox2.click();
        } else {
            checkBox2.click();
            checkBox1.click();
        }
        log.info("Checkboxes attribute checked state after clicks");
        log.info("Checkbox 1 : attribute checked : {}", checkBox1.getAttribute("checked"));
        log.info("Checkbox 2 : attribute checked : {}", checkBox2.getAttribute("checked"));
        checkBox1.shouldBe(checked);
        checkBox2.shouldNotBe((checked));
    }

    @Test
    @DisplayName("Tests on page Dropdown")
    public void testDropdown() {
        open("https://the-internet.herokuapp.com/dropdown");
        SelenideElement dropdown = $(By.id("dropdown"));
        String option = "Option 1";
        dropdown.selectOption(option);
        log.info("Dropdown text : {}", dropdown.getSelectedOptionText());
        dropdown.shouldHave(text(option));
        option = "Option 2";
        dropdown.selectOption(option);
        log.info("Dropdown text : {}", dropdown.getSelectedOptionText());
        dropdown.shouldHave(text(option));
    }

    @RepeatedTest(value = 10, failureThreshold = 9)
    @DisplayName("Tests on page Disappearing Elements")
    public void testDisappearingElements() {
        open("https://the-internet.herokuapp.com/disappearing_elements");
        ElementsCollection elements = $$(By.cssSelector("ul li a"));
        elements.shouldHave(size(5));
    }

    @TestFactory
    @DisplayName("Tests on page Inputs")
    List<DynamicTest> testInputs() {
        String[] data = new String[] {"123", "321", " 123", "123 ", "-123", "123foo", "f123oo", "&*^", "1-1", "-1-1", "  "};
        List<DynamicTest> list = new ArrayList<>();
        for(String input : data) {
            list.add(dynamicTest(String.format("Input \"%s\"", input),
                    () -> {
                        open("https://the-internet.herokuapp.com/inputs");
                        SelenideElement number =
                                $(By.cssSelector("input[type='number']"));
                        number.click();
                        number.sendKeys(input);
                        log.info("Input value : {}", number.getAttribute("value"));
                        String expectedValue = input;
                        if(input.indexOf("-",1) >= 1)
                            expectedValue = "";
                        number.shouldHave(value(expectedValue.replaceAll("[^0-9\\-]", "")));
                    }));
        }
        return list;
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 2, 1})
    @DisplayName("Tests on page Hovers")
    public void testHovers(int index) {
        open("https://the-internet.herokuapp.com/hovers");
        ElementsCollection images = $$(By.cssSelector(".figure img"));
        SelenideElement image = images.get(index - 1);
        image.hover();

        // искомый текст располагается ниже(near) относительно картинки
        // можно воспользоваться относительным локатором (Relative locator)
        SelenideElement text = $(RelativeLocator.with(By.cssSelector("div.figcaption"))
                                .near(image.getWrappedElement()));
        log.info("Text : {}", text.getText());
        text.shouldBe(text("user" + index));
    }

    @RepeatedTest(value = 10, failureThreshold = 6)
    @DisplayName("Tests on page Notification Messages")
    public void testNotificationMessages(RepetitionInfo info) {
        open("https://the-internet.herokuapp.com/");
        $(By.cssSelector("a[href='/notification_message']")).click();
        SelenideElement flash = $(By.cssSelector("div#flash"));
        flash.shouldHave(text("Action successful"));
    }

    @TestFactory
    @DisplayName("Tests on page Add/Remove Elements")
    List<DynamicTest> addRemoveElements() {
        int[][] params = new int[][] {{2, 1}, {5, 2}, {1, 3}};
        List<DynamicTest> list = new ArrayList<>();
        for(int[] param : params) {
            int additionCount = param[0];
            int deletionCount = param[1];
            list.add(dynamicTest("Add/Remove " + Arrays.toString(param),
                    () -> {
                        open("https://the-internet.herokuapp.com/add_remove_elements/");
                        By deletesLocator = By.cssSelector("button[onclick='deleteElement()']");
                        SelenideElement addElement =
                                $(By.cssSelector("button[onclick='addElement()']"));
                        for (int i = 1; i <= additionCount; i++) {
                            addElement.click();
                            SelenideElement lastDeleteElement = $(By.cssSelector("#elements button:last-child"));
                            log.info("Last added element text : {}", lastDeleteElement.getText());
                            ElementsCollection deleteElements = $$(deletesLocator);
                            log.info("Number of Deletes elements : {}", deleteElements.size());
                            deleteElements.shouldHave(size(i));
                        }
                        for (int i = 1; i <= deletionCount; i++) {
                            ElementsCollection deleteElements = $$(deletesLocator);
                            deleteElements.shouldHave(sizeGreaterThan(0));

                            int randomIndex = (int) (Math.random() * deleteElements.size());
                            log.info("Click by index of element : {}", randomIndex);
                            deleteElements.get(randomIndex).click();

                            deleteElements = $$(deletesLocator);
                            log.info("Number of Deletes elements : {}", deleteElements.size());
                            for (int deleteIndex = 0; deleteIndex < deleteElements.size(); deleteIndex++) {
                                log.info("Text of Delete element #{} : {}", deleteIndex, deleteElements.get(deleteIndex).getText());
                            }
                        }
                    }));
        }
        return list;
    }

    @Test
    @DisplayName("Tests on page Status Codes 200")
    public void testStatusCodes200() {
        open("https://the-internet.herokuapp.com/status_codes");
        String code = "200";
        $(By.cssSelector(String.format("a[href*='%s']", code))).click();
        String text = $(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        webdriver().shouldHave(urlContaining(code));
    }

    @Test
    @DisplayName("Tests on page Status Codes 301")
    public void testStatusCodes301() {
        open("https://the-internet.herokuapp.com/status_codes");
        String code = "301";
        $(By.cssSelector(String.format("a[href*='%s']", code))).click();
        String text = $(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        webdriver().shouldHave(urlContaining(code));
    }

    @Test
    @DisplayName("Tests on page Status Codes 404")
    public void testStatusCodes404() {
        open("https://the-internet.herokuapp.com/status_codes");
        String code = "404";
        $(By.cssSelector(String.format("a[href*='%s']", code))).click();
        String text = $(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        webdriver().shouldHave(urlContaining(code));
    }

    @Test
    @DisplayName("Tests on page Status Codes 500")
    public void testStatusCodes500() {
        open("https://the-internet.herokuapp.com/status_codes");
        String code = "500";
        $(By.cssSelector(String.format("a[href*='%s']", code))).click();
        String text = $(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        webdriver().shouldHave(urlContaining(code));
    }

    @AfterEach
    void teardown() {
        closeWebDriver();
    }
}
