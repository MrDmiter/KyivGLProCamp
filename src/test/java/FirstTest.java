import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class FirstTest {

    //Webdriver instance
    WebDriver driver;

    /**
     * Actions before test
     */
    @Before
    public void setUp() {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-fullscreen");
        driver = new ChromeDriver(options);
    }

    /**
     * Empty test
     */
    @Test
    public void firstTest() {

    }

    /**
     * Actions after test
     */
    @After
    public void tearDown() {
        driver.quit();
    }
}
