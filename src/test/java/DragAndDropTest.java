// src/test/java/org/example/DragAndDropTest.java
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;

import static org.testng.Assert.*;

public class DragAndDropTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait   = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().setSize(new Dimension(1280, 900));
    }

    @AfterClass
    public void teardown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void dragAndDrop_works() {
        driver.get("https://testautomationcentral.com/demo/drag_and_drop.html");

        // Robust locators (don’t depend on ids)
        WebElement source = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[normalize-space()='Drag me']")));
        WebElement target = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[normalize-space()='Drop here']")));

        // 1) Try native Actions API
        boolean dropped = tryActionsDnD(source, target);

        // 2) If not changed, use HTML5 JS fallback
        if (!dropped) {
            html5DragAndDrop(source, target);
            dropped = true;
        }

        // Assert: target text or class/state should change after drop
        wait.until(d -> !target.getText().equalsIgnoreCase("Drop here") || target.getAttribute("class").contains("dropped"));
        String targetText = target.getText().trim();
        assertTrue(dropped && (targetText.equalsIgnoreCase("Dropped!") || targetText.contains("Drag") || target.getAttribute("class").contains("dropped")),
                "Expected drop target to reflect a successful drop. Target text: '" + targetText + "'");
    }

    private boolean tryActionsDnD(WebElement source, WebElement target) {
        String before = target.getText();
        try {
            new Actions(driver)
                    .clickAndHold(source)
                    .moveToElement(target)
                    .pause(Duration.ofMillis(200))
                    .release()
                    .perform();
            // small wait to see if UI updated
            Thread.sleep(300);
        } catch (Exception ignored) {}
        String after = target.getText();
        return !before.equals(after);
    }

    /** HTML5 DataTransfer-based drag & drop (works when Actions() doesn’t). */
    private void html5DragAndDrop(WebElement source, WebElement target) {
        String script =
                "const src=arguments[0], tgt=arguments[1];" +
                        "const dataTransfer=new DataTransfer();" +
                        "function fire(el, type){const e=new DragEvent(type,{bubbles:true,cancelable:true,dataTransfer});el.dispatchEvent(e);} " +
                        "fire(src,'dragstart'); fire(tgt,'dragenter'); fire(tgt,'dragover'); fire(tgt,'drop'); fire(src,'dragend');";
        ((JavascriptExecutor) driver).executeScript(script, source, target);
    }
}