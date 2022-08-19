package ru.rgs.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.rgs.framework.managers.PageManager;

import java.util.List;

public class ForCompaniesPage extends BasePage {

    @FindBy(xpath = "//span[@data-v-376b4ab5]")
    private List<WebElement> secondMenu;

    @FindBy(xpath = "//a[@data-v-60d3f7d6]")
    private List<WebElement> menuInSecondMenu;



    /**
     * Проверяем открытие страницы
     */
    public ForCompaniesPage checkOpenPage() {
        wait.until(ExpectedConditions.titleIs("Страхование компаний и юридических лиц | Росгосстрах"));
        return pageManager.getForCompaniesPage();
    }

    /**
     * Клик по элементу из второго меню - меня выбирается по тексту переданному на вход от пользователя
     *
     * @param nameMenu -Текст, который будет передан пользователем
     */
    public ForCompaniesPage selectSubMenuByText(String nameMenu) {
        waitAndCloseWidget();
        for (WebElement itemMenu : secondMenu) {
            if (itemMenu.getText().contains(nameMenu)) {
                itemMenu.click();
                // проверка
//                WebElement parentButtonHealth = driver.findElement(By.xpath(itemMenu. + "/ancestor::li[contains(@class, active)]"));
//                wait.until(ExpectedConditions.visibilityOf(parentButtonHealth));
                return pageManager.getForCompaniesPage();
            }
        }
        return pageManager.getForCompaniesPage();
//        Assertions.fail("Меню с текстом " + nameMenu + " не найдено во второстепенном меню");
    }

    /**
     * Клик по элементу из выпадающего меню дополнительного меню на странице
     *
     * @param nameMenu - Текст меню для поиска по нему
     */
    public DMSPage selectMenuInSecondMenu(String nameMenu) {
        waitAndCloseWidget();
        for (WebElement itemMenu : menuInSecondMenu) {
            if (itemMenu.getText().contains(nameMenu)) {
                itemMenu.click();
                return pageManager.getDMSPage();
            }
        }
        return pageManager.getDMSPage();
//        Assertions.fail("Подменю с текстом " + nameMenu + " не найдено во второстепенном меню");
//        wait.until(ExpectedConditions.titleIs("Добровольное медицинское страхование для компаний и юридических лиц в Росгосстрахе"));
    }
}
