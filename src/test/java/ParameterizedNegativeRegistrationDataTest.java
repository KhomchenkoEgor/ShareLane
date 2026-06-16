import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class ParameterizedNegativeRegistrationDataTest {

    WebDriver browser;

    @BeforeMethod
    public void setUp() {
        browser = new ChromeDriver();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @DataProvider(name = "registrationData")
    public Object[][] registrationData() {
        return new Object[][]{
                {"елена", "Secure_Qwerty7", "Secure_Qwerty7"},
                {"#@!% ", "2399", "2399"},
                {"ИГОРЬ", "Root Password_1", "Root Password_1"},
                {"kate!", "12@#", "12@#"},
                {"Ivan", " ", "1234"},
                {"Alla", "1234", " "},
                {"", "1234", "1234"}
        };
    }

    @Test(dataProvider = "registrationData") // Указываем имя источника данных
    public void checkUserRegistration(String name, String password, String confirmPassword) {
        browser.get("https://www.sharelane.com/cgi-bin/register.py");

        browser.findElement(By.name("zip_code")).sendKeys("12345");
        browser.findElement(By.cssSelector("[value=Continue]")).click();

        browser.findElement(By.name("first_name")).sendKeys(name);
        browser.findElement(By.name("last_name")).sendKeys("Petrov");
        browser.findElement(By.name("email")).sendKeys(System.currentTimeMillis() + "@mail.com");
        browser.findElement(By.name("password1")).sendKeys(password);
        browser.findElement(By.name("password2")).sendKeys(confirmPassword);
        browser.findElement(By.cssSelector("[value=Register]")).click();

        boolean isDisplayed = browser.findElement(By.cssSelector("[class=error_message]")).isDisplayed();

        Assert.assertTrue(isDisplayed);
    }

    @AfterMethod
    public void finish() {
        if (browser != null) {
            browser.quit();
        }
    }
}
