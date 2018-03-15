package com.datascope;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import static junit.framework.TestCase.assertTrue;

public class TestSelenium {

    @Test
    public void gg () throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");


        WebDriver browser = new ChromeDriver(ChromeDriverService.createDefaultService());
        browser.get("http://saucelabs.com");
       // Thread.sleep(5000);
        WebElement header = browser.findElement(By.id("page-header"));
        assertTrue((header.isDisplayed()));

        browser.close();
    }
}
