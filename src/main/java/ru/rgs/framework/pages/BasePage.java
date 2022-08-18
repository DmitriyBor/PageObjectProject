package ru.rgs.framework.pages;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor jsExecutor;



    public BasePage(WebElement driver){
        PageFactory.initElements(driver, this);
    }

    /**
     * Проверка наличия элемента на странице
     * @param by - передается xpath проверяемого объекта на странице
     * @param time - периодичность, с которой будет проверяться появился ли элемент
     * @return - возвращает, либо true, либо false, в зависимости от того найден элемент в итоге или нет
     */
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

    /**
     * Преобразовывает переданную строку номера телефона и преобразовывает её для проверки
     * @param value - строка из чисел, которую надо преобразовать
     * @return - возвращает телефон в нужном формате для дальнейшей проверки
     */
    protected String getForMaskValue(String value) {
        return "+7 (" + value.substring(0, 3) + ") " + value.substring(3, 6) + "-" + value.substring(6, value.length() - 1);
    }

    /**
     * Функция, которая ожидает стабильности страницы на сайте, как правило, используется для проверки можно ли со страницей работать дальше.
     * Проверяется путём сравнения html страницы с её предыдущими состояниями за каждый переданный промежуток времени
     * Как только они состояния совпадут, будет выход из функции
     * @param maxWaitMillis - Максимальное время ожидания стабилизации
     * @param pollDelimiter - Интервал времени, с которым будет проверяться стабилизировалась страница или нет
     */
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

    /**
     * Функция для закрытия фрейма рекламы, который всплывает в рандомный момент
     * Т.к. реклама находится на странице в iframe, что является страницей в станице, то нужно было делать переключение на работу в этой странице
     * и после уже закрытие этой страницы и переключение на работу с основной html
     */
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
