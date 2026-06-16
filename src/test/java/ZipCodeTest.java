import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class ZipCodeTest {

    /*
    Прекондишен: открыть браузер !!!
    1. Открыть страницу https://www.sharelane.com/cgi-bin/register.py !!!
    2. Ввести в поле Zip Code значение 12345 !!!
    3. Нажать кнопку Continue
    Ожидаемый результат: Мы оказались на странице с формой регистрации
    Посткондишен: закрыть браузер !!!
    By.cssSelector("[class=error_message]");
     */

    @Test
    public void checkZipCodeFieldWithFiveDigits() {
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

        //создаем boolean переменную, которая определяет наличие кнопки Register на странице
        boolean isDisplayed = browser.findElement(By.cssSelector("[value=Register]")).isDisplayed();
        //Проверяем, что кнопка действительно есть
        Assert.assertTrue(isDisplayed);

        browser.close();
    }

    /*
    Прекондишен: открыть браузер !!!
    1. Открыть страницу https://www.sharelane.com/cgi-bin/register.py !!!
    2. Ввести в поле Zip Code значение 1234 !!!
    3. Нажать кнопку Continue
    Ожидаемый результат: Мы оказались на странице с сообщением об ошибке количества вводимых символов
    Посткондишен: закрыть браузер !!!
    By.cssSelector("[class=error_message]");
     */

    @Test
    public void checkZipCodeFieldWithFourDigits(){
        WebDriver browser = new ChromeDriver();
        //определяем время ожидания элемента
        browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        //переходим на страницу https://www.sharelane.com/cgi-bin/register.py
        browser.get("https://www.sharelane.com/cgi-bin/register.py");
        //вводим значение 1234 в поле зип код
        browser.findElement(By.name("zip_code")).sendKeys("1234");//использовать только цифры
        //нажимаем кнопку Continue
        browser.findElement(By.cssSelector("[value=Continue]")).click();

        //создаем boolean переменную, которая определяет наличие сообщения Oops, error on page. ZIP code should have 5 digits на странице
        boolean isDisplayed = browser.findElement(By.cssSelector("[class=error_message]")).isDisplayed();
        //Проверяем, что сообщение действительно есть
        Assert.assertTrue(isDisplayed);

        browser.close();
    }
}