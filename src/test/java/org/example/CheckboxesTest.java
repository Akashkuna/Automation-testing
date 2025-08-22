package org.example;// pom.xml deps: selenium-java, testng, webdrivermanager

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.*;
import static org.testng.Assert.*;

import java.time.Duration;

public class CheckboxesTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().setSize(new Dimension(1280, 900));
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) driver.quit();
    }

    @Test
    public void checkboxes_flow() {
        driver.get("https://testautomationcentral.com/demo/checkboxes.html");
        var page = new CheckboxesPage(driver, wait);

        // Simple Checkboxes: select 1 & 3 only
        page.setSimple("Option 1", true);
        page.setSimple("Option 2", false);
        page.setSimple("Option 3", true);

        assertTrue(page.isSimpleChecked("Option 1"));
        assertFalse(page.isSimpleChecked("Option 2"));
        assertTrue(page.isSimpleChecked("Option 3"));

        // Styled Checkboxes: select all, then unselect Option 2
        page.setStyled("Option 1", true);
        page.setStyled("Option 2", true);
        page.setStyled("Option 3", true);
        page.setStyled("Option 2", false);

        assertTrue(page.isStyledChecked("Option 1"));
        assertFalse(page.isStyledChecked("Option 2"));
        assertTrue(page.isStyledChecked("Option 3"));

        // Idempotence: clicking an already-checked box keeps it checked
        page.setSimple("Option 1", true);
        assertTrue(page.isSimpleChecked("Option 1"));

        // Accessibility: label is clickable and linked
        page.clickSimpleLabel("Option 3"); // toggles
        assertFalse(page.isSimpleChecked("Option 3"));
        page.clickSimpleLabel("Option 3"); // toggles back
        assertTrue(page.isSimpleChecked("Option 3"));
    }

    // ------- Page Object -------
    static class CheckboxesPage {
        private final WebDriver driver;
        private final WebDriverWait wait;

        CheckboxesPage(WebDriver d, WebDriverWait w) { this.driver = d; this.wait = w; }

        // --- Public API ---
        void setSimple(String optionText, boolean shouldBeChecked) {
            WebElement cb = findCheckboxInSection("Simple Checkboxes", optionText);
            setState(cb, shouldBeChecked);
        }
        boolean isSimpleChecked(String optionText) {
            return findCheckboxInSection("Simple Checkboxes", optionText).isSelected();
        }
        void clickSimpleLabel(String optionText) {
            findLabelInSection("Simple Checkboxes", optionText).click();
        }

        void setStyled(String optionText, boolean shouldBeChecked) {
            WebElement cb = findCheckboxInSection("Styled Checkboxes", optionText);
            setState(cb, shouldBeChecked);
        }
        boolean isStyledChecked(String optionText) {
            return findCheckboxInSection("Styled Checkboxes", optionText).isSelected();
        }

        // --- Helpers ---
        private void setState(WebElement cb, boolean shouldBeChecked) {
            wait.until(ExpectedConditions.elementToBeClickable(cb));
            if (cb.isSelected() != shouldBeChecked) cb.click();
            // guard: some styled UIs toggle via label only
            if (cb.isSelected() != shouldBeChecked) {
                associatedLabel(cb).click();
            }
            if (cb.isSelected() != shouldBeChecked) {
                throw new AssertionError("Checkbox state did not change as expected");
            }
        }

        private WebElement findCheckboxInSection(String sectionTitle, String optionText) {
            // Strategy 1: input linked to a label with text
            String xpath =
                    "//*[self::h2 or self::h3 or self::h4][normalize-space()='" + sectionTitle + "']" +
                            "/following::*[self::section or self::div][1]" +
                            "//*[self::label][normalize-space()='" + optionText + "']" +
                            "//*[self::input and @type='checkbox']";
            var elems = driver.findElements(By.xpath(xpath));
            if (!elems.isEmpty()) return elems.get(0);

            // Strategy 2: label next to input (label has @for)
            String xpath2 =
                    "//*[self::h2 or self::h3 or self::h4][normalize-space()='" + sectionTitle + "']" +
                            "/following::*[self::section or self::div][1]" +
                            "//*[@type='checkbox'][@id=//label[normalize-space()='" + optionText + "']/@for]";
            elems = driver.findElements(By.xpath(xpath2));
            if (!elems.isEmpty()) return elems.get(0);

            // Fallback: search by visible text anywhere within the section container
            String xpath3 =
                    "//*[self::h2 or self::h3 or self::h4][normalize-space()='" + sectionTitle + "']" +
                            "/following::*[self::section or self::div][1]" +
                            "//*[contains(normalize-space(.),'" + optionText + "')]" +
                            "/descendant-or-self::*/input[@type='checkbox']";
            return wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath3)));
        }

        private WebElement findLabelInSection(String sectionTitle, String optionText) {
            String xpath =
                    "//*[self::h2 or self::h3 or self::h4][normalize-space()='" + sectionTitle + "']" +
                            "/following::*[self::section or self::div][1]" +
                            "//label[normalize-space()='" + optionText + "']";
            return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        }

        private WebElement associatedLabel(WebElement checkbox) {
            String id = checkbox.getAttribute("id");
            if (id != null && !id.isEmpty()) {
                var byFor = By.cssSelector("label[for='" + id + "']");
                var labels = driver.findElements(byFor);
                if (!labels.isEmpty()) return labels.get(0);
            }
            // fallback: nearest label ancestor
            return checkbox.findElement(By.xpath("ancestor::label[1]"));
        }
    }
}