package ru.rgs.base;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTests {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor jsExecutor;

    @BeforeEach
    public void beforeEach() {
        driver.get("https://rgs.ru/");
    }

    @BeforeAll
    public void before() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, 10, 2000);

        actions = new Actions(driver);

        jsExecutor = (JavascriptExecutor) driver;
    }

    @AfterAll
    public void after() {
        driver.quit();
    }

    protected boolean elementIsExist(By by, int time) {
        try {
            driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
            driver.findElement(by);
            return true;
            // Перехватываю ошибку от Selenium, не от java.util
        } catch (NoSuchElementException ignore) {
        } finally {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return false;   // запись вне кетча дает вернуть фолс в любом случа, а ошибку игнорим
    }

    protected void fillInputField(WebElement element, String value) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
//        element.click();
        actions.moveToElement(element).pause(200).click(element).build().perform(); // наводим мышку на элемент, кликаем, билдим и перформим
        // так работает экшон, собирает события и потом надо вызвать build и perform
        // это один из способов клика по веб элементу
        element.clear();
        element.sendKeys(value);

        boolean telNumber = element.getAttribute("name").equals("userTel");

        // проверка на телефон
        value = telNumber ? getForMaskValue(value) : value;

        boolean elemFilled = wait.until(ExpectedConditions.attributeContains(element, "value", value));

        Assertions.assertTrue(elemFilled, "Поле не заполнено");
    }

    protected String getForMaskValue(String value) {
        return "+7 (" + value.substring(0, 3) + ") " + value.substring(3, 6) + "-" + value.substring(6, value.length() - 1);
    }

    protected ExpectedCondition<Boolean> completedJSPage() {
        return new ExpectedCondition<Boolean>() {
            @NullableDecl
            @Override
            public Boolean apply(@NullableDecl WebDriver webDriver) {
                return jsExecutor.executeScript("return document.readyState").equals("complete");
                // документ это html станица, readystate свойство этой странички, которая говорит о том что данная страничка в статусе каком то
            }
        };
    }

    protected void waitForPageStability(int maxWaitMillis, int pollDelimiter) {        // максимальное время ожидание и интервал
        double startTime = System.currentTimeMillis();     // Получаем наше время
        while (System.currentTimeMillis() < startTime + maxWaitMillis) {    // цикл ограничения по времени
            String prevState = driver.getPageSource();      // getPageSource вернуть все что видно на html страничке, весь html код
            try {
                Thread.sleep(pollDelimiter); // <-- would need to wrap in a try catch
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (prevState.equals(driver.getPageSource())) {     // если предыдущее состояние не изменилось спустя какой то
                // промежуток времени, то значит страничка статичная и проверка останавливается
                return;
            }
        }
        throw new RuntimeException("Станица не стабилизировалась за указанное время " + maxWaitMillis + "ms");
    }

    // работа с фреймом рекламы
    protected void waitAndCloseWidget() {
        try {
            String xpathWidget = "//div[@data-showed-up='true' and @class='flocktory-widget-overlay']/iframe[@class='flocktory-widget']";
            String xpathClose = "//div[contains(@class, 'close js')]";
            if (elementIsExist(By.xpath(xpathWidget), 1)) {
                driver.switchTo().frame(driver.findElement(By.xpath(xpathWidget)));
                if (elementIsExist(By.xpath(xpathClose), 1)) {
                    waitForPageStability(5000, 500);
                    WebElement closeButton = driver.findElement(By.xpath(xpathClose));
                    closeButton.click();
                }
            }
        } finally {
            driver.switchTo().defaultContent();     // переключаемся на начальный драйвер, то есть к базовой странице
        }
    }
}
