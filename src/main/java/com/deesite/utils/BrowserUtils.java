package com.deesite.utils;

import com.deesite.managers.CreateDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BrowserUtils {

    /**
     * waitFor method to wait up to a designated period before throwing exception (static locator)
     *
     * @param element
     * @param timer
     * @throws Exception
     */
    public static void waitFor(WebElement element, long timer) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        // wait for the static element to appear
        //WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(timer));
        WebDriverWait exists = new WebDriverWait(driver, timer);
        exists.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
    }

    /**
     * overloaded waitFor method to wait up to a designated period before * throwing exception (dynamic locator)
     *
     * @param by
     * @param timer
     * @throws Exception
     */
    public static void waitFor(By by, long timer) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        // wait for the dynamic element to appear
        //WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(timer));
        WebDriverWait exists = new WebDriverWait(driver, timer);
        // examples: By.id(id),By.name(name),By.xpath(locator), // By.cssSelector(css)
        exists.until(ExpectedConditions.refreshed(
                ExpectedConditions.visibilityOfElementLocated(by)));
    }

    /**
     * waitForGone method to wait up to a designated period before throwing exception if element still exists
     *
     * @param by
     * @param timer
     * @throws Exception
     */
    public static void waitForGone(By by, int timer) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        // wait for the dynamic element to disappear
        //WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(timer));
        WebDriverWait exists = new WebDriverWait(driver, timer);
        // examples: By.id(id),By.name(name),By.xpath(locator), // By.cssSelector(css)
        exists.until(ExpectedConditions.refreshed(
                ExpectedConditions.invisibilityOfElementLocated(by)));
    }

    /**
     * waitForURL method to wait up to a designated period before throwing exception if URL is not found
     *
     * @param url
     * @param seconds
     * @throws Exception
     */
    public static void waitForURL(String url, int seconds) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        //WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(seconds));
        WebDriverWait exists = new WebDriverWait(driver, seconds);
        exists.until(ExpectedConditions.refreshed(
                ExpectedConditions.urlContains(url)));
    }

    /**
     * waitFor method to wait up to a designated period before throwing exception if Title is not found
     *
     * @param title
     * @param timer
     * @throws Exception
     */
    public static void waitFor(String title, long timer) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getCurrentDriver();
        //WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(timer));
        WebDriverWait exists = new WebDriverWait(driver, timer);
    }

    /**
     * waitForClickable method to poll for clickable
     *
     * @param by
     * @param timer
     * @throws Exception
     */
    public static void waitForClickable(By by, long timer) throws Exception {
        WebDriver driver = CreateDriver.getInstance().getDriver();
        //WebDriverWait exists = new WebDriverWait(driver, Duration.ofSeconds(timer));
        WebDriverWait exists = new WebDriverWait(driver, timer);
        exists.until(ExpectedConditions.refreshed(
                ExpectedConditions.elementToBeClickable(by)));
    }

}
