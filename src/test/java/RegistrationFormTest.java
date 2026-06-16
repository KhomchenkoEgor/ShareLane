import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class RegistrationFormTest {

    /*
    Прекондишен: открыть браузер !!!
    1. Открыть страницу https://www.sharelane.com/cgi-bin/register.py !!!
    2. Ввести в поле Zip Code значение 12345 !!!
    3. Нажать кнопку Continue
    4. Ввести в поле First Name значение ivan
    5. Ввести в поле Email значение ivan@mail.com
    6. Ввести в поле Password 1234
    7. Ввести в поле Confirm Password 1234
    Ожидаемый результат: Мы оказались на странице с успешным подтверждением о регистрации.
    Посткондишен: закрыть браузер !!!
    */

    @Test
    public void varifyFillingPersonalDataInTheRegistrationForm() {
        //инициализируем браузер как Chrome
        WebDriver browser = new ChromeDriver();
        //определяем время ожидания элемента
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //переходим на страницу https://www.sharelane.com/cgi-bin/register.py
        browser.get("https://www.sharelane.com/cgi-bin/register.py");
        //вводим значение 12345 в поле зип код
        browser.findElement(By.name("zip_code")).sendKeys("12345");
        //нажимаем кнопку Continue
        browser.findElement(By.cssSelector("[value=Continue]")).click();

        //вводим значение ivan в поле first name
        browser.findElement(By.name("first_name")).sendKeys("ivan"); //имя только на английском (можно написать одну букву)
        //вводим значение ivan@mail.com в поле email
        browser.findElement(By.name("email")).sendKeys("ivan@mail.com");//имя цифрами, буквами английскими и русскими
        //вводим значение 1234 в поле password
        browser.findElement(By.name("password1")).sendKeys("1234");//любой символ, но от 4 и более
        //вводим значение 1234 в поле confirm password
        browser.findElement(By.name("password2")).sendKeys("1234");//любой символ, но от 4 и более
        //нажимаем кнопку register
        browser.findElement(By.cssSelector("[value=Register]")).click();

        //создаем boolean переменную, которая определяет наличие надписи Account is created! на странице
        boolean isDisplayed = browser.findElement(By.cssSelector("[class=confirmation_message]")).isDisplayed();
        //Проверяем, что надпись действительно есть
        Assert.assertTrue(isDisplayed);

        browser.close();
    }

    /*
    Прекондишен: открыть браузер !!!
    1. Открыть страницу https://www.sharelane.com/cgi-bin/register.py !!!
    2. Ввести в поле Zip Code значение 12345 !!!
    3. Нажать кнопку Continue
    4. Ввести в поле First Name значение ivan
    5. Ввести в поле Email значение ivan@gmail.com
    6. Ввести в поле Password 123
    7. Ввести в поле Confirm Password 1234
    Ожидаемый результат: Мы оказались на странице с уведомлением об ошибке при регистрации.
    Посткондишен: закрыть браузер !!!
    */

    @Test
    public void checkFillingPersonalDataInRegistrationForm() {
        //инициализируем браузер как Chrome
        WebDriver browser = new ChromeDriver();
        //определяем время ожидания элемента
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //переходим на страницу https://www.sharelane.com/cgi-bin/register.py
        browser.get("https://www.sharelane.com/cgi-bin/register.py");
        //вводим значение 12345 в поле зип код
        browser.findElement(By.name("zip_code")).sendKeys("12345");
        //нажимаем кнопку Continue
        browser.findElement(By.cssSelector("[value=Continue]")).click();


        browser.findElement(By.name("first_name")).sendKeys("ivan");

        browser.findElement(By.name("email")).sendKeys("ivan@mail.com");

        browser.findElement(By.name("password1")).sendKeys("123");

        browser.findElement(By.name("password2")).sendKeys("1234");

        browser.findElement(By.cssSelector("[value=Register]")).click();

        //создаем boolean переменную, которая определяет наличие надписи Oops, error on page. Some of your fields have invalid data or email was previously used на странице
        boolean isDisplayed = browser.findElement(By.cssSelector("[class=error_message]")).isDisplayed();
        //Проверяем, что надпись действительно есть
        Assert.assertTrue(isDisplayed);

        browser.close();
    }

    @Test
    public void checkMultipleNegativeFirstNamesTest() {
        String[] names = {"иван", "12345 ", "&#@!"};
        WebDriver browser = new ChromeDriver();
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        try {
            for (String firstName : names) {
                browser.get("https://www.sharelane.com/cgi-bin/register.py");
                browser.findElement(By.name("zip_code")).sendKeys("12345");
                browser.findElement(By.cssSelector("[value=Continue]")).click();

                browser.findElement(By.name("first_name")).sendKeys(firstName);
                browser.findElement(By.name("email")).sendKeys("ivan@mail.com");
                browser.findElement(By.name("password1")).sendKeys("1234");
                browser.findElement(By.name("password2")).sendKeys("1234");
                browser.findElement(By.cssSelector("[value=Register]")).click();

                boolean isDisplayed = browser.findElement(By.cssSelector("[class=error_message]")).isDisplayed();
                Assert.assertTrue(isDisplayed);
            }
        } finally {
            browser.quit();
        }
    }
}
