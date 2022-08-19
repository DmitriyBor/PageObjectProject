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

public class StartPage extends BasePage {

    @FindBy(xpath = "//div[@class='cookie block--cookie']//button")
    private WebElement closeCookie;

    @FindBy(xpath = "//a[@data-v-2642864c]")
    private List<WebElement> baseMenu;

    public StartPage pageStability(){
        waitForPageStability(10000, 2000);
        return pageManager.getStartPage();
    }

    /**
     * Закрывает окно Cookie
     */
    public StartPage closeCookies() {
        // Проверка на наличие куки
        waitAndCloseWidget();
        waitUtilElementToBeClickable(closeCookie).click();
        return pageManager.getStartPage();
    }

    /**
     * Клик по базовому меню - меню выбирается по тексту переданному на вход функции
     *
     * @param nameMenu - Текст, который будет передан пользователем (наименования базового меню)
     */
    public ForCompaniesPage selectBaseMenuByText(String nameMenu) {
        waitAndCloseWidget();
        for (WebElement itemMenu : baseMenu) {
            if (itemMenu.getText().contains(nameMenu)) {
                itemMenu.click();
                return pageManager.getForCompaniesPage();
            }
        }
        return pageManager.getForCompaniesPage();
//        Assertion.fail("Меню с текстом " + nameMenu + " не найдено на стартовой страничке");
//        wait.until(ExpectedConditions.titleIs("Страхование компаний и юридических лиц | Росгосстрах"));
    }
}
