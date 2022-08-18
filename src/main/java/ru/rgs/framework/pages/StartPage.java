package ru.rgs.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class StartPage extends BasePage {

    @FindBy(xpath = "//div[@class='cookie block--cookie']")
    private WebElement closeCookie;

    @FindBy(xpath = "//a[@data-v-2642864c]")
    private List<WebElement> baseMenu;

    public StartPage(WebElement driver){
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Если есть окно Cookie, то проверяет его наличие и закрывает его
     */
    public void closeCookies() {
        // Проверка на наличие куки
        waitAndCloseWidget();
        if (elementIsExist(closeCookie, 2)) {
            WebElement closeCookieBtn = driver.findElement(By.xpath("//div[@class='cookie block--cookie']//button"));
            closeCookieBtn.click();
        }
    }

    /**
     * Клик по базовому меню - меню выбирается по тексту переданному на вход функции
     * @param nameMenu - Текст, который будет передан пользователем (наименования базового меню)
     */
    public void selectBaseMenuByText(String nameMenu){
        waitAndCloseWidget();
        for (WebElement itemMenu: baseMenu) {
            if (itemMenu.getText().contains(nameMenu)){
                itemMenu.click();
            }
        }
        Assertion.fail("Меню с текстом " + nameMenu + " не найдено на стартовой страничке");
//        wait.until(ExpectedConditions.titleIs("Страхование компаний и юридических лиц | Росгосстрах"));
    }
}
