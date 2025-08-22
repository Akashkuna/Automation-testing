package amazon.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LaunchBrowser {
    public static void main(String[] args) {
        FirefoxDriver driver = new FirefoxDriver();
        driver.get("https://the-internet.herokuapp.com/login");
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.className("radius")).click();
    }
}