package ru.t1academy.autotests.uitests.task5;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import ru.t1academy.autotests.uitests.task5.asserts.NumberAssert;
import ru.t1academy.autotests.uitests.task5.asserts.UrlAssert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.slf4j.LoggerFactory.getLogger;
import static ru.t1academy.autotests.uitests.task5.asserts.SelectAssert.assertThat;

public class SeleniumTest {

    static final Logger log = getLogger(lookup().lookupClass());

    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Tag("smoke")
    @Test
    public void test1() {
        String url = "https://the-internet.herokuapp.com/";
        driver.get(url);
        String title = driver.getTitle();
        log.info("The title of {} is {}", url, title);
        assertThat(title).isEqualTo("The Internet");

        SessionId sessionId = ((RemoteWebDriver) driver).getSessionId();
        assertThat(sessionId).isNotNull();
        log.info("The sessionId is {}", sessionId.toString());
    }

    @Owner("Vadim")
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Tests on page Checkboxes")
    public void testCheckBoxes(boolean isForward) {
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        WebElement checkBox1 =
                driver.findElement(By.cssSelector("form#checkboxes input[type='checkbox']:first-child"));
        WebElement checkBox2 =
                driver.findElement(By.cssSelector("form#checkboxes input[type='checkbox']:last-child"));
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
        assertThat(checkBox1.isSelected()).isTrue();
        assertThat(checkBox2.isSelected()).isFalse();
    }

    @Owner("Vadim")
    @Description("Добавить проверки в задание Dropdown из предыдущей лекции.")
    @Test
    @DisplayName("Tests on page Dropdown")
    public void testDropdown() {
        driver.get("https://the-internet.herokuapp.com/dropdown");
        Select dropdown = new Select(driver.findElement(By.id("dropdown")));
        String option = "Option 1";
        dropdown.selectByVisibleText(option);
        log.info("Dropdown text : {}", dropdown.getFirstSelectedOption().getText());
        assertThat(dropdown).hasSelectedOption(option);
        option = "Option 2";
        dropdown.selectByVisibleText(option);
        log.info("Dropdown text : {}", dropdown.getFirstSelectedOption().getText());
        assertThat(dropdown).hasSelectedOption(option);
    }

    @Owner("Vadim")
    @RepeatedTest(value = 10, failureThreshold = 9)
    @DisplayName("Tests on page Disappearing Elements")
    public void testDisappearingElements() {
        driver.get("https://the-internet.herokuapp.com/disappearing_elements");
        List<WebElement> elements = driver.findElements(By.cssSelector("ul li a"));
        assertThat(elements.size()).isEqualTo(5);
    }

    @Owner("Vadim")
    @TestFactory
    @DisplayName("Tests on page Inputs")
    List<DynamicTest> testInputs() {
        String[] data = new String[] {"123", "321", " 123", "123 ", "-123", "123foo", "f123oo", "&*^", "1-1", "-1-1", "  "};
        List<DynamicTest> list = new ArrayList<>();
        for(String input : data) {
            list.add(dynamicTest("Input \"" + input + "\"",
                    () -> {
                        driver.get("https://the-internet.herokuapp.com/inputs");
                        WebElement number =
                                driver.findElement(By.cssSelector("input[type='number']"));
                        number.click();
                        number.sendKeys(input);
                        log.info("Input value : {}", number.getAttribute("value"));
                        NumberAssert.assertThat(number).isNumberOrEmpty(input.replaceAll("[^0-9\\-]", ""));
                    }));
        }
        return list;
    }

    @Owner("Vadim")
    @ParameterizedTest
    @ValueSource(ints = {3, 2, 1})
    @DisplayName("Tests on page Hovers")
    public void testHovers(int index) {
        driver.get("https://the-internet.herokuapp.com/hovers");
        Actions actions = new Actions(driver);
        List<WebElement> images =
                driver.findElements(By.cssSelector(".figure img"));
        WebElement image = images.get(index - 1);
        actions.moveToElement(image).build().perform();

        // искомый текст располагается ниже(near) относительно картинки
        // можно воспользоваться относительным локатором (Relative locator)
        String text = driver.findElement(RelativeLocator.with(By.cssSelector("div.figcaption"))
                        .near(image))
                        .getText();
        log.info("Text : {}", text);
        assertThat(text).contains("user" + index);
    }

    @Owner("Vadim")
    @Description("Добавить проверки в задание Notification Message из предыдущей лекции.")
    @RepeatedTest(value = 10, failureThreshold = 6)
    @DisplayName("Tests on page Notification Messages")
    public void testNotificationMessages(RepetitionInfo info) {
        driver.get("https://the-internet.herokuapp.com/");
        driver.findElement(By.cssSelector("a[href='/notification_message']")).click();
        WebElement flash = driver.findElement(By.cssSelector("div#flash"));
        assertThat(flash.getText()).contains("Action successful");
    }

    @Owner("Vadim")
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
                        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");
                        By deletesLocator = By.cssSelector("button[onclick='deleteElement()']");
                        WebElement addElement =
                                driver.findElement(By.cssSelector("button[onclick='addElement()']"));
                        for (int i = 1; i <= additionCount; i++) {
                            addElement.click();
                            WebElement lastDeleteElement = driver.findElement(By.cssSelector("#elements button:last-child"));
                            log.info("Last added element text : {}", lastDeleteElement.getText());
                            List<WebElement> deleteElements = driver.findElements(deletesLocator);
                            log.info("Number of Deletes elements : {}", deleteElements.size());
                            assertThat(deleteElements).hasSize(i);
                        }
                        for (int i = 1; i <= deletionCount; i++) {
                            List<WebElement> deleteElements = driver.findElements(deletesLocator);
                            assertThat(deleteElements).hasSizeGreaterThan(0);

                            int randomIndex = (int) (Math.random() * deleteElements.size());
                            log.info("Click by index of element : {}", randomIndex);
                            deleteElements.get(randomIndex).click();

                            deleteElements = driver.findElements(deletesLocator);
                            log.info("Number of Deletes elements : {}", deleteElements.size());
                            for (int deleteIndex = 0; deleteIndex < deleteElements.size(); deleteIndex++) {
                                log.info("Text of Delete element #{} : {}", deleteIndex, deleteElements.get(deleteIndex).getText());
                            }
                        }
                    }));

        }
        return list;
    }

    @Owner("Vadim")
    @Test
    @DisplayName("Tests on page Status Codes 200")
    public void testStatusCodes200() {
        driver.get("https://the-internet.herokuapp.com/status_codes");
        String code = "200";
        driver.findElement(By.cssSelector(String.format("a[href*='%s']", code))).click();
        String text = driver.findElement(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        UrlAssert.assertThat(driver).containsPath(code);
    }

    @Owner("Vadim")
    @Test
    @DisplayName("Tests on page Status Codes 301")
    public void testStatusCodes301() {
        driver.get("https://the-internet.herokuapp.com/status_codes");
        String code = "301";
        driver.findElement(By.cssSelector(String.format("a[href*='%s']", code))).click();
        String text = driver.findElement(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        UrlAssert.assertThat(driver).containsPath(code);
    }

    @Owner("Vadim")
    @Test
    @DisplayName("Tests on page Status Codes 404")
    public void testStatusCodes404() {
        driver.get("https://the-internet.herokuapp.com/status_codes");
        String code = "404";
        driver.findElement(By.cssSelector(String.format("a[href*='%s']", code))).click();
        String text = driver.findElement(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        UrlAssert.assertThat(driver).containsPath(code);
    }

    @Owner("Vadim")
    @Test
    @DisplayName("Tests on page Status Codes 500")
    public void testStatusCodes500() {
        driver.get("https://the-internet.herokuapp.com/status_codes");
        String code = "500";
        driver.findElement(By.cssSelector(String.format("a[href*='%s']", code))).click();
        String text = driver.findElement(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        UrlAssert.assertThat(driver).containsPath(code);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }
}
