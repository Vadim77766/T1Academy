package ru.t1academy.autotests.uitests.task4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import java.time.Duration;
import java.util.List;
import static java.lang.invoke.MethodHandles.lookup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

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
        driver.get("https://the-internet.herokuapp.com/checkboxes");
        WebElement checkBox1 =
                driver.findElement(By.cssSelector("form#checkboxes input[type='checkbox']:first-child"));
        WebElement checkBox2 =
                driver.findElement(By.cssSelector("form#checkboxes input[type='checkbox']:last-child"));
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
        driver.get("https://the-internet.herokuapp.com/dropdown");
        Select dropdown = new Select(driver.findElement(By.id("dropdown")));
        String option = "Option 1";
        dropdown.selectByVisibleText(option);
        log.info("Dropdown text : {}", dropdown.getFirstSelectedOption().getText());
        assertThat(dropdown.getFirstSelectedOption().getText()).isEqualTo(option);
        option = "Option 2";
        dropdown.selectByVisibleText(option);
        log.info("Dropdown text : {}", dropdown.getFirstSelectedOption().getText());
        assertThat(dropdown.getFirstSelectedOption().getText()).isEqualTo(option);
        waits();
    }

    @Test
    @DisplayName("Tests on page Disappearing Elements")
    public void testDisappearingElements() {
        driver.get("https://the-internet.herokuapp.com/disappearing_elements");
        int countDown = 10;
        while (countDown-- > 0) {
            List<WebElement> elements = driver.findElements(By.cssSelector("ul li a"));
            if(elements.size() == 5) {
                assertThat(elements.size()).isEqualTo(5);
                return;
            }
            driver.navigate().refresh();
            waits();
        }
        log.info("Number of elements not equal to 5");
        Assertions.fail();
    }

    @Test
    @DisplayName("Tests on page Inputs")
    public void testInputs() {
        driver.get("https://the-internet.herokuapp.com/inputs");
        WebElement number =
                driver.findElement(By.cssSelector("input[type='number']"));
        String numberText = "1234";
        number.sendKeys(numberText);
        log.info("Input value : {}", number.getAttribute("value"));
        assertThat(number.getAttribute("value")).isEqualTo(numberText);
        waits();
    }

    @Test
    @DisplayName("Tests on page Hovers")
    public void testHovers() {
        driver.get("https://the-internet.herokuapp.com/hovers");
        Actions actions = new Actions(driver);
        List<WebElement> images =
                driver.findElements(By.cssSelector(".figure img"));
        for(WebElement image : images) {
            actions.moveToElement(image).build().perform();

            // искомый текст располагается ниже(near) относительно(relative) картинки
            WebElement text = driver.findElement(
                    RelativeLocator.with(By.cssSelector("div.figcaption")).near(image));
            log.info("Text : {}", text.getText());
            assertThat(text.getText()).isNotEmpty();
        }
        waits();
    }

    @Test
    @DisplayName("Tests on page Notification Messages")
    public void testNotificationMessages() {
        driver.get("https://the-internet.herokuapp.com/notification_message_rendered");
        while (true) {
            List<WebElement> flash = driver.findElements(By.cssSelector("div#flash"));
            if(flash.size() > 0) {
                if(flash.get(0).getText().contains("unsuccesful")) {
                    // на элемент нельзя кликнуть если он перекрывается другим
                    // чтобы элементы не перекрывались максимизируем окно
                    driver.manage().window().maximize();
                    driver.findElement(By.cssSelector("a.close")).click();

                    // альтернативный вариант кликнуть джаваскриптом
//                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();",
//                            driver.findElement(By.cssSelector("a.close")));
                } else {
                    break;
                }
            }
            driver.findElement(By.cssSelector("a[href='/notification_message")).click();
        }
        assertThat(driver.findElement(By.cssSelector("div#flash")).getText()).contains("Action successful");
        waits();
    }

    @Test
    @DisplayName("Tests on page Add/Remove Elements")
    public void testAddRemoveElements() {
        driver.get("https://the-internet.herokuapp.com/add_remove_elements/");
        WebElement addElement =
                driver.findElement(By.cssSelector("button[onclick='addElement()']"));
        for (int i = 0; i < 5; i++) {
            addElement.click();
            WebElement delete = driver.findElement(By.cssSelector("#elements button:last-child"));
            log.info("Last added element text : {}", delete.getText());
        }
        By deletes = By.cssSelector("button[onclick='deleteElement()']");
        for (int i = 0; i < 3; i++) {
            List<WebElement> delete = driver.findElements(deletes);
            int r = (int) (Math.random() * delete.size());
            log.info("Click by index of element : {}", r);
            delete.get(r).click();
            List<WebElement> delete_ = driver.findElements(deletes);
            log.info("Number of Deletes elements : {}", delete_.size());
            for (int j = 0; j < delete_.size(); j++) {
                log.info("Text of Delete #{} : {}", j, delete_.get(j).getText());
            }
        }
        waits();
    }

    @Test
    @DisplayName("Tests on page Status Codes")
    public void testStatusCodes() {
        driver.get("https://the-internet.herokuapp.com/status_codes");

    }

    @Test
    @DisplayName("Tests on page Status Codes 200")
    public void testStatusCodes200() {
        driver.get("https://the-internet.herokuapp.com/status_codes");
        driver.findElement(By.cssSelector("a[href*='200']")).click();
        String text = driver.findElement(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        driver.navigate().back();
    }

    @Test
    @DisplayName("Tests on page Status Codes 301")
    public void testStatusCodes301() {
        driver.get("https://the-internet.herokuapp.com/status_codes");
        driver.findElement(By.cssSelector("a[href*='301']")).click();
        String text = driver.findElement(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        driver.navigate().back();
    }

    @Test
    @DisplayName("Tests on page Status Codes 404")
    public void testStatusCodes404() {
        driver.get("https://the-internet.herokuapp.com/status_codes");
        driver.findElement(By.cssSelector("a[href*='404']")).click();
        String text = driver.findElement(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        driver.navigate().back();
    }

    @Test
    @DisplayName("Tests on page Status Codes 500")
    public void testStatusCodes500() {
        driver.get("https://the-internet.herokuapp.com/status_codes");
        driver.findElement(By.cssSelector("a[href*='500']")).click();
        String text = driver.findElement(By.xpath("//p[1]")).getText();
        log.info("Status text : {}", text);
        driver.navigate().back();
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }
}
