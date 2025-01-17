package testng1;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;



public class testLogoSkillo {

    private WebDriver driver;


    @BeforeMethod
    public void setUpTest() {
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
        this.driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
        this.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    public void testLogo() {

        this.driver.get("http://training.skillo-bg.com:4300/posts/all");

        WebElement logoHomePage = driver.findElement(By.id("homeIcon"));
        logoHomePage.isDisplayed();

        WebElement loginLink = driver.findElement(By.id("nav-link-login"));
        loginLink.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4300/users/login"));

        WebElement logoLoginPage = driver.findElement(By.id("homeIcon"));
        logoHomePage.isDisplayed();

        logoLoginPage.click();

        wait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4300/users/login"));
    }

    @DataProvider(name = "userNames")
    public Object[][] userName(){
        return new Object[][] {
                {"ivelinQA", "Ivelin123", "ivelinQA"},
                {"testAdmin@gmail.com", "Admin1.User1", "AdminUser"},
                {"manager@gmail.com", "Manager1.Use1", "ManagerUser"}};
    }

    @Test(dataProvider = "userNames")
    public void testLogoLogin(String userName, String password, String name) {
        this.driver.get("http://training.skillo-bg.com:4300/posts/all");

        WebElement loginLink = driver.findElement(By.id("nav-link-login"));
        loginLink.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4300/users/login"));

        WebElement signInElement = driver.findElement(By.xpath("//p[text()='Sign in']"));
        wait.until(ExpectedConditions.visibilityOf(signInElement));

        WebElement userNameField = driver.findElement(By.id("defaultLoginFormUsername"));
        userNameField.sendKeys(userName);

        WebElement passwordField = driver.findElement(By.id("defaultLoginFormPassword"));
        passwordField.sendKeys(password);

        WebElement signInButton = driver.findElement(By.id("sign-in-button"));
        wait.until(ExpectedConditions.elementToBeClickable(signInElement));
        signInButton.click();

        WebElement profileLink = driver.findElement(By.id("nav-link-profile"));
        wait.until(ExpectedConditions.elementToBeClickable(signInElement));

        profileLink.click();

        Boolean isTextDisplayed = wait.until(ExpectedConditions.textToBe(By.tagName("h2"), name));
        Assert.assertTrue(isTextDisplayed);

        WebElement logoHomePage = driver.findElement(By.id("homeIcon"));
        logoHomePage.isDisplayed();

        WebElement newPostLink = driver.findElement(By.id("nav-link-new-post"));
        newPostLink.click();

        WebElement logoNewPost = driver.findElement(By.id("homeIcon"));
        logoNewPost.isDisplayed();

        logoNewPost.click();

        wait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4300/posts/all"));

        WebElement logoHomePageRedirect = driver.findElement(By.id("homeIcon"));
        logoHomePageRedirect.isDisplayed();
    }
    @AfterTest
    public void browserClosing() {
        this.driver.close();
    }
}
