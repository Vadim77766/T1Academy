package ru.t1academy.autotests.uitests.task6;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;

import java.time.Duration;

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

    @Description("Перейти на страницу Drag and Drop. " +
            "Перетащить элемент A на элемент B. " +
            "Задача на 10 баллов – сделать это, не прибегая к методу DragAndDrop(); " +
            "Проверить, что элементы поменялись местами")
    @Owner("Vadim")
    @ParameterizedTest
    @DisplayName("Test on page Drag and Drop")
    @ValueSource(booleans = {true, false})
    public void testDradAndDrop(boolean withoutDragAndDropMethod) {
        driver.get("https://the-internet.herokuapp.com/drag_and_drop");
        Actions actions = new Actions(driver);
        WebElement draggableA = driver.findElement(By.xpath("//header[text()='A']"));
        WebElement draggableB = driver.findElement(By.xpath("//header[text()='B']"));
        Point locationA = draggableA.getLocation();
        Point locationB = draggableB.getLocation();
        log.info("Position of element A : {}", locationA);
        log.info("Position of element B : {}", locationB);

        if(withoutDragAndDropMethod) {
            actions
                    .clickAndHold(draggableA)
                    .pause(50)
                    .moveToElement(draggableB)
                    .pause(50)
                    .release().build().perform();
        } else {
            actions.dragAndDrop(draggableA, draggableB).build().perform();
        }

        Point locationAAfter = driver.findElement(By.xpath("//header[text()='A']")).getLocation();
        Point locationBAfter = driver.findElement(By.xpath("//header[text()='B']")).getLocation();
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
        driver.get("https://the-internet.herokuapp.com/context_menu");
        WebElement hotSpot = driver.findElement(By.id("hot-spot"));
        new Actions(driver).contextClick(hotSpot).build().perform();
        Alert alert = driver.switchTo().alert();
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
        driver.get("https://the-internet.herokuapp.com/infinite_scroll");
        final String SEARCH_TEXT = "Eius";
        Action action = new Actions(driver).scrollByAmount(0, 300).build();
        Wait<WebDriver> wait = new FluentWait<>(driver)
                .pollingEvery(Duration.ofMillis(500))
                .withTimeout(Duration.ofSeconds(10));
        while (true) {
            action.perform();
            WebElement scroll = wait.until(ExpectedConditions.presenceOfElementLocated((
                            By.cssSelector(".jscroll-inner .jscroll-added:last-child"))));
            String text = scroll.getText();
            if (text.contains(SEARCH_TEXT)) {
                log.info("Text found in paragraph : {}", text);
                assertThat(scroll.isDisplayed()).isTrue();
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
        driver.get("https://the-internet.herokuapp.com/key_presses");
        CharSequence[] keys = new CharSequence[] { "A", "H", "G", "R", "U", "K", "N", "C", "M", "Q",
                Keys.ENTER, Keys.CONTROL, Keys.ALT, Keys.TAB };
        Actions actions = new Actions(driver);
        for (CharSequence key : keys) {
            actions.pause(50).sendKeys(key).build().perform();
            WebElement result = driver.findElement(By.id("result"));
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
        driver.quit();
    }
}
