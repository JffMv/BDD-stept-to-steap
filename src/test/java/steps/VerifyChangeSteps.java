package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.Assert.assertNotEquals;
import java.util.concurrent.TimeUnit;

public class VerifyChangeSteps{
    private WebDriver driver;
    private String initialContent;

    @Before
    public void setUp() {
        try {
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--remote-allow-origins=*");
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize ChromeDriver", e);
        }
    }

    @Given("I am on the dynamic_content")
    public void i_am_on_the_google_search_page() {
        driver.get("https://the-internet.herokuapp.com/dynamic_content");
        initialContent = driver.findElement(By.id("content")).getText();
    }

    @When("I click on click here")
    public void i_click_on_click_here() {
        WebElement link = driver.findElement(By.xpath("//a[@href='/dynamic_content?with_content=static']"));
        link.click();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS); // Espera por carga de la página
    }

    @Then("I should see change content of the threeth agent")
    public void i_should_see_that_the_content_has_changed() {
        String newContent = driver.findElement(By.id("content")).getText();
        assertNotEquals("The content should have changed", initialContent, newContent);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
