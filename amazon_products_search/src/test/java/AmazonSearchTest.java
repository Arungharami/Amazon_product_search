import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;

public class AmazonSearchTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set the path to your WebDriver executable
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");

        // Initialize the Chrome driver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        // Navigate to Amazon
        driver.get("https://www.amazon.com");
    }

    @Test
    public void searchIphone13() {
        // Locate the search box and enter "iPhone 13"
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("iPhone 13");
        searchBox.submit();

        // Wait for results to load
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Retrieve product details
        List<WebElement> products = driver.findElements(By.cssSelector("div.s-main-slot div.s-result-item"));

        // Print details of the first 5 products
        for (int i = 0; i < Math.min(5, products.size()); i++) {
            WebElement product = products.get(i);

            String title = product.findElement(By.cssSelector("h2 a span")).getText();
            String price = "Price not available";
            try {
                price = product.findElement(By.cssSelector("span.a-price span.a-offscreen")).getAttribute("innerHTML");
            } catch (Exception e) {
                // Handle case where price is not available
            }

            System.out.println("Product " + (i + 1) + ":");
            System.out.println("Title: " + title);
            System.out.println("Price: " + price);
            System.out.println("-----------------------------");
        }

        // Assert that at least one product is displayed
        Assert.assertTrue(products.size() > 0, "No products found for the search query.");
    }

    @AfterMethod
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}