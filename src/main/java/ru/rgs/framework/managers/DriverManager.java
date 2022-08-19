package ru.rgs.framework.managers;

import org.apache.commons.exec.OS;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.rgs.framework.utils.PropsConst;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

// Синглтон
public class DriverManager {
    private static DriverManager INSTANCE;
    private final TestPropManager propManager = TestPropManager.getInstance();
    private WebDriver driver;

    private DriverManager() {

    }

    public static DriverManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DriverManager();
        return INSTANCE;
    }

    /**
     * Метод ленивой инициализации веб драйвера
     *
     * @return WebDriver - возвращает веб драйвер
     */
    public WebDriver getDriver() {
        if (driver == null) {
            initDriver();
        }
        return driver;
    }


    /**
     * Метод для закрытия сессии драйвера и браузера
     *
     * @see WebDriver#quit()
     */
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }


    /**
     * Метод инициализирующий веб драйвер
     */
    private void initDriver() {
        System.setProperty("webdriver.chrome.driver",propManager.getProperty(PropsConst.PATH_CHROME_DRIVER_WINDOWS));
        driver = new ChromeDriver();
    }
}
