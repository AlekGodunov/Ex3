import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;


public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilites = new DesiredCapabilities();

        capabilites.setCapability("platformName","Android");
        capabilites.setCapability("deviceName","AndroidTestDevice");
        capabilites.setCapability("platformVersion","8.0");
        capabilites.setCapability("automationName","Appium");
        capabilites.setCapability("appPackage","org.wikipedia");
        capabilites.setCapability("appActivity",".main.MainActivity");
        capabilites.setCapability("app","/Users/Godunov/IdeaProjects/Ex1.1/apks/org.wikipedia.apk");


        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilites);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void test() {

        String searchString = "Godunov";

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'search Wikipedia' input",
                5);

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                searchString,
                "Cannot find search input",
                5);

        waitForElementPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Cannot find search results",
                5);


        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel Search",
                10);


        waitForElementNotPresent(
                By.id("org.wikipedia:id/page_list_item_title"),
                "Search is not cleared yet",
                15);
    }


    private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(by));

    }

    private WebElement waitForElementPresent(By by, String errorMessage) {

        return waitForElementPresent(by, errorMessage, 5);

    }

    private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.click();
        return element;

    }

    private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.sendKeys(value);
        return element;

    }

    private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {

        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(errorMessage + "\n");
        return wait.until(
                ExpectedConditions.invisibilityOfElementLocated(by)
        );

    }

    private WebElement waitForElementAndClear(By by, String errorMessage, long timeoutInSeconds) {

        WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
        element.clear();
        return element;

    }
}