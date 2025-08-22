// src/test/java/org/example/RadioButtonsTest.java
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import static org.testng.Assert.*;

import java.time.Duration;

public class RadioButtonsTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().setSize(new Dimension(1280, 900));
    }

    @AfterClass
    public void teardown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void radioButtons_flow() {
        driver.get("https://testautomationcentral.com/demo/radiobuttons.html");
        var page = new RadioButtonsPage(driver, wait);

        // Simple radios: select "Option B"
        page.selectSimple("Option B");
        assertTrue(page.isSimpleSelected("Option B"));
        assertFalse(page.isSimpleSelected("Option A"));
        assertFalse(page.isSimpleSelected("Option C"));

        // Clicking label should toggle selection
        page.clickSimpleLabel("Option C");
        assertTrue(page.isSimpleSelected("Option C"));
        assertFalse(page.isSimpleSelected("Option B"));

        // Styled radios: select A then C
        page.selectStyled("Option A");
        assertTrue(page.isStyledSelected("Option A"));
        page.selectStyled("Option C");
        assertTrue(page.isStyledSelected("Option C"));
        assertFalse(page.isStyledSelected("Option A"));

        // Mutual exclusivity check (same name group)
        int checkedCount = page.countStyledChecked();
        assertEquals(checkedCount, 1, "Only one styled radio should be selected");
    }

    // ---------- Page Object ----------
    static class RadioButtonsPage {
        private final WebDriver driver;
        private final WebDriverWait wait;
        RadioButtonsPage(WebDriver d, WebDriverWait w) { this.driver = d; this.wait = w; }

        void selectSimple(String optionText) { selectInSection("Simple Radio Buttons", optionText); }
        boolean isSimpleSelected(String optionText) { return findRadio("Simple Radio Buttons", optionText).isSelected(); }
        void clickSimpleLabel(String optionText) { findLabel("Simple Radio Buttons", optionText).click(); }

        void selectStyled(String optionText) { selectInSection("Styled Radio Buttons", optionText); }
        boolean isStyledSelected(String optionText) { return findRadio("Styled Radio Buttons", optionText).isSelected(); }

        int countStyledChecked() {
            String x = sectionBase("Styled Radio Buttons") + "//input[@type='radio' and @checked or @aria-checked='true' or self::input[@type='radio' and @value][@checked]]";
            return driver.findElements(By.xpath(x)).size();
        }

        // --- helpers ---
        private void selectInSection(String sectionTitle, String optionText) {
            WebElement rb = findRadio(sectionTitle, optionText);
            wait.until(ExpectedConditions.elementToBeClickable(rb));
            if (!rb.isSelected()) {
                // some UIs only toggle via label; click input then label if needed
                try { rb.click(); } catch (ElementClickInterceptedException ignored) {}
                if (!rb.isSelected()) associatedLabel(rb).click();
            }
            if (!rb.isSelected()) throw new AssertionError("Radio '" + optionText + "' not selected");
        }

        private WebElement findRadio(String sectionTitle, String optionText) {
            // case 1: label wraps input
            String x1 = sectionBase(sectionTitle) +
                    "//label[normalize-space()='" + optionText + "']//*[self::input and @type='radio']";
            var list = driver.findElements(By.xpath(x1));
            if (!list.isEmpty()) return list.get(0);

            // case 2: label with @for points to input
            String x2 = sectionBase(sectionTitle) +
                    "//*[@type='radio'][@id=//label[normalize-space()='" + optionText + "']/@for]";
            list = driver.findElements(By.xpath(x2));
            if (!list.isEmpty()) return list.get(0);

            // fallback: any element containing text -> descendant radio
            String x3 = sectionBase(sectionTitle) +
                    "//*[contains(normalize-space(.),'" + optionText + "')]/descendant-or-self::*/input[@type='radio']";
            return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(x3)));
        }

        private WebElement findLabel(String sectionTitle, String optionText) {
            String x = sectionBase(sectionTitle) + "//label[normalize-space()='" + optionText + "']";
            return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(x)));
        }

        private String sectionBase(String sectionTitle) {
            return "//*[self::h2 or self::h3 or self::h4][normalize-space()='" + sectionTitle + "']" +
                    "/following::*[self::section or self::div][1]";
        }

        private WebElement associatedLabel(WebElement input) {
            String id = input.getAttribute("id");
            if (id != null && !id.isEmpty()) {
                var labels = driver.findElements(By.cssSelector("label[for='" + id + "']"));
                if (!labels.isEmpty()) return labels.get(0);
            }
            return input.findElement(By.xpath("ancestor::label[1]"));
        }
    }
}