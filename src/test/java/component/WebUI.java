package component;

import base.DriverManager;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WebUI {

    private static WebElement findElement(By locator) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not found: " + locator.toString());
        }
    }

    public static void clickButtonByText(String text) {
        try {
            By locator = By.xpath("//button[normalize-space()='" + text + "']");
            WebElement button = findElement(locator);
            button.click();
            System.out.println("SUCCESS: Press button with text: '" + text + "'");
        } catch (TimeoutException e) {
            System.out
                    .println("ERROR: Field with button with text: '" + text + "' not found after waiting 10 seconds.");
            throw new RuntimeException("Element not found: button with text = " + text);
        }
    }

    public static void setTextByPlaceholder(String placeholder, String value) {
        try {
            By locator = By.xpath("//input[@placeholder='" + placeholder + "']");
            WebElement field = findElement(locator);
            field.clear();
            field.sendKeys(value);
            System.out.println("SUCCESS: Input '" + value + "' into field with placeholder '" + placeholder + "'");
        } catch (TimeoutException e) {
            System.out
                    .println("ERROR: Field with placeholder '" + placeholder + "' not found after waiting 10 seconds.");
            throw new RuntimeException("Element not found: placeholder = " + placeholder);
        }
    }

    public static void setTextByName(String name, String value) {
        try {
            By locator = By.xpath("//input[@name='" + name + "']");
            WebElement field = findElement(locator);
            field.clear();
            field.sendKeys(value);
            System.out.println("SUCCESS: Input '" + value + "' into field with name '" + name + "'");
        } catch (TimeoutException e) {
            System.out.println("ERROR: Field with name '" + name + "' not found after waiting 10 seconds.");
            throw new RuntimeException("Element not found: name = " + name);
        }
    }

    public static void clickNavbar(String menuName) {
        try {
            By locator = By.xpath("//span[normalize-space()='" + menuName + "']");
            WebElement menu = findElement(locator);
            menu.click();
            System.out.println("SUCCESS: Clicked navbar -> " + menuName);
        } catch (Exception e) {
            throw new RuntimeException("Navbar not found: " + menuName);
        }
    }

    public static boolean verifyElementPresent(String xpath) {
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
            System.out.println("Element FOUND: " + xpath);
            return true;
        } catch (TimeoutException e) {
            System.out.println("Element NOT found: " + xpath);
            return false;
        }
    }

    public static void stopIfFalse(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("TEST STOPPED: " + message);
        }
    }

    public static void clickButtonByLabel(String text) {
        try {
            By locator = By.xpath("//a[normalize-space()='" + text + "']");
            WebElement button = findElement(locator);
            button.click();
            System.out.println("SUCCESS: Press button with label: '" + text + "'");
        } catch (TimeoutException e) {
            System.out
                    .println("ERROR: Field with button with label: '" + text + "' not found after waiting 10 seconds.");
            throw new RuntimeException("Element not found: button with label = " + text);
        }
    }

    // Login for custom xpath
    public static void clickLoginButton() {
        try {
            By locator = By.xpath("//*[@id='login-button']");
            WebElement button = findElement(locator);
            button.click();
            System.out.println("SUCCESS: Press button with label: '");
        } catch (TimeoutException e) {
            System.out.println("ERROR: Field with button with label: ' not found after waiting 10 seconds.");
            throw new RuntimeException("Element not found");
        }
    }

    public static void verifyTextPresent(String text) {
        try {
            By locator = By.xpath("//a[normalize-space()='" + text + "']");
            findElement(locator);
            System.out.println("SUCCESS: Text is present: '" + text + "'");
        } catch (TimeoutException e) {
            System.out.println("ERROR: Text: '" + text + "' not found after waiting 10 seconds.");
            throw new RuntimeException("Element not found");
        }
    }

    public static void click(By locator) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    public static void takeScreenshot(String fileName) {
        try {
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
            File source = ts.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fullPath = "screenshots/" + fileName + "_" + timestamp + ".png";
            File destination = new File(fullPath);
            FileUtils.copyFile(source, destination);
            System.out.println("Screenshot saved at: " + destination.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to take screenshot", e);
        }
    }

    public static void delay(int seconds) {
        try {
            System.out.println("DELAY: Waiting for " + seconds + " seconds...");
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Delay interrupted", e);
        }
    }

    public static String getText(By locator) {
        WebDriver driver = DriverManager.getDriver();
        return driver.findElement(locator).getText().trim();
    }

    public static void closeAdIfPresent() {

    WebDriver driver = DriverManager.getDriver();

    try {
        List<WebElement> iframes = driver.findElements(By.tagName("iframe"));

        for (WebElement frame : iframes) {
            driver.switchTo().frame(frame);

            System.out.println("iframe");
            List<WebElement> closeBtn = driver.findElements(
                By.xpath("//div[contains(@style,'cursor') and .//svg]")
            );

            if (!closeBtn.isEmpty()) {
                closeBtn.get(0).click();
                driver.switchTo().defaultContent();
                return;
            }

            driver.switchTo().defaultContent();
        }

    } catch (Exception e) {
        driver.switchTo().defaultContent();
    }
}

}
