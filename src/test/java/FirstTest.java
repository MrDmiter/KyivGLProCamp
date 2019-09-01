import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class FirstTest {

    //Webdriver instance
    WebDriver driver;
    WebDriverWait wait;

    /**
     * Actions before test
     */
    @Before
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);

    }

    /**
     * Empty test
     */
    @Test
    public void firstTest() {
        //Enter the site
        driver.get("http://demo.litecart.net/admin/");
        //Wait until login button is clickable
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button"))).click();
        //Wait until the slowest element on th page is uploaded
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@id='copyright']"), "Copyright © 2012-2019"));
        //Find all the visible categories from left bar
        List<WebElement> categories = driver.findElements(By.xpath("//ul[@id='box-apps-menu']/li"));
        //Take each element and click on it
        for (int j = 0; j < categories.size(); j++) {
            categories.get(j).click();
            //Find h1 element on the page
            WebElement h1 = driver.findElement(By.xpath("//div[@class='panel-heading']"));
            //Ensure that title is present and displayed on the page
            Assert.assertTrue(h1.isDisplayed());
            //Find all visible subcategories
            List<WebElement> subCategories = driver.findElements(By.xpath("//ul[@class='docs']/li"));
            //If there are present subelements click on each of them and verify that title is present on the page
            if (subCategories.size() != 0) {
                for (int i = 0; i < subCategories.size(); i++) {
                    subCategories.get(i).click();
                    h1 = driver.findElement(By.xpath("//div[@class='panel-heading']"));
                    Assert.assertTrue(h1.isDisplayed());
                    subCategories = driver.findElements(By.xpath("//ul[@class='docs']/li"));
                }
            }
            categories = driver.findElements(By.xpath("//ul[@id='box-apps-menu']/li"));
        }
    }


    /**
     * Actions after test
     */
    @After
    public void tearDown() {
        driver.quit();
    }
}
