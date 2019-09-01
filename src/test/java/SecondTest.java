import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class SecondTest {

    WebDriverWait wait;
    EventFiringWebDriver edr;
    //Browser stack credentials
    public static final String USERNAME = "bsuser52865";
    public static final String AUTOMATE_KEY = "Tv7mwrTQBFxSzzzp5qBn";
    public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

    /**
     * Actions before test
     */
    @Before
    public void setUp() throws MalformedURLException {

        WebDriverManager.chromedriver().setup();
        //Browser stack settings
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "62.0");
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "10");
        caps.setCapability("resolution", "1024x768");
        caps.setCapability("name", "Bstack-[Java] Sample Test");
        //Event Firing webdriver instance
        edr = new EventFiringWebDriver(new RemoteWebDriver(new URL(URL), caps));
        edr.register(new Listener());
        //Wait init
        wait = new WebDriverWait(edr, 10);

    }


    @Test
    public void workWithCartTest() {
        //Enter the site
        edr.get("http://demo.litecart.net/");
        //Take all products on the page
        List<WebElement> products = edr.findElements(By.cssSelector(".product-column"));
        //Click on the second product in collection
        products.get(1).click();
        //Choose size if there is "Size" dropdown
        if (edr.findElements(By.xpath("//select")).size() > 0) {
            Select select = new Select(edr.findElement(By.xpath("//select")));
            select.selectByIndex(1);
        }
        //Add product to cart
        edr.findElement(By.xpath("//button[@name='add_cart_product']")).click();
        //Wait until product in cart
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath("//div[@class='badge quantity']"), "1"));
        //Enter cart
        edr.findElement(By.xpath("//div[@id='cart']")).click();
        //Wait until cart is opened
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));
        //Remove item from cart
        edr.findElement(By.xpath("//button[@name='remove_cart_item']")).click();
        //Wait until cart is empty
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//em")));
        //Verify presence of the cart is empty notification
        Assert.assertEquals(edr.findElement(By.xpath("//em")).getText(), "There are no items in your cart.");
    }

    /**
     * Actions after test
     */
    @After
    public void tearDown() {
        edr.quit();
    }
}
