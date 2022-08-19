package ru.rgs.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.rgs.framework.data.FieldFormReg;

import java.util.List;

public class DMSPage extends BasePage {
    @FindBy(xpath = "//button[text()='Свяжитесь со мной' and @type='submit']")
    private WebElement btnContactWithMe;

    @FindBy(xpath = "//input[contains(@class,'input')]")
    private List<WebElement> listOfFillableFields;

    @FindBy(xpath = "//span[@class='input__error text--small']")
    private List<WebElement> listErrorMassages;

    /**
     * Проверяем открытие страницы
     */
    public DMSPage checkOpenPage() {
        wait.until(ExpectedConditions.titleIs("Добровольное медицинское страхование для компаний и юридических лиц в Росгосстрахе"));
        return pageManager.getDMSPage();
    }

    /**
     * Скролить до кнопки "Свяжитесь со мной"
     */
    public DMSPage scrollToButtonConnectWithMe() {
        waitAndCloseWidget();
        jsExecutor.executeScript("arguments[0].scrollIntoView(false)", driver.findElement(By.xpath("//button[text()='Свяжитесь со мной']")));
        waitForPageStability(10000, 1000);
        return pageManager.getDMSPage();
    }

    /**
     * Заполняет выбранное поле введенным текстом и проверяет то, что оно заполнилось
     *
     * @param fieldName - Ввести название поля для ввода, на данный момент есть 4: userName, userTel, userEmail, userAddress(его нет, но просто проверка на него может быть)
     * @param text      - Текст, который будет введён в заполняемое поле
     */
    public DMSPage fillInTheApplicationsField(FieldFormReg fieldName, String text) {
        waitAndCloseWidget();
        for (WebElement itemMenu : listOfFillableFields) {
            if (fieldName.equals(FieldFormReg.userAddress)
                    || fieldName.equals(FieldFormReg.userEmail)
                    || fieldName.equals(FieldFormReg.userTel)
                    || fieldName.equals(FieldFormReg.userName)) {
                wait.until(ExpectedConditions.elementToBeClickable(itemMenu));
                actions.moveToElement(itemMenu).pause(200).click(itemMenu).build().perform();
                itemMenu.clear();
                itemMenu.sendKeys(text);
                boolean telNumber = itemMenu.getAttribute("name").equals("userTel");
                text = telNumber ? getForMaskValue(text) : text;
                boolean elemFilled = wait.until(ExpectedConditions.attributeContains(itemMenu, "value", text));
//                Assertions.assertTrue(elemFilled, "Поле не заполнено");
                return pageManager.getDMSPage();
            }
        }
        return pageManager.getDMSPage();
//        Assertions.fail("Названия поля, которое вы ввели не существует или не найдено");
    }


    /**
     * Нажать на кнопку "Свяжитесь со мной"
     */
    public DMSPage clickOnBtnContactWithMe() {
        waitUtilElementToBeClickable(btnContactWithMe).click();
        return pageManager.getDMSPage();
    }

    /**
     * Находит ошибку заполнения и выводит в консоль название этой ошибки
     */
    public DMSPage checkErrorMassagesUnderFillApplicationsFields() {
        for (WebElement errorMassage : listErrorMassages) {
            System.out.println(errorMassage.getText());
        }
//        Assertions.fail("Ошибки были не найдены");
        return pageManager.getDMSPage();
    }
}
