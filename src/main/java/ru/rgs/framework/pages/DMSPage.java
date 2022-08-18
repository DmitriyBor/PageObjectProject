package ru.rgs.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class DMSPage extends BasePage{
    @FindBy(xpath = "//button[text()='Свяжитесь со мной' and @type='submit']")
    private WebElement btnContactWithMe;

    @FindBy(xpath = "//input[contains(@class,'input')]")
    private List<WebElement> listOfFillableFields;

    @FindBy(xpath = "//span[@class='input__error text--small']")
    private List<WebElement> listErrorMassages;

    public DMSPage(WebElement driver){
        PageFactory.initElements(driver, this);
    }

    /**
     * Проверяем открытие страницы
     */
    public void checkOpenPage(){
        wait.until(ExpectedConditions.titleIs("Добровольное медицинское страхование для компаний и юридических лиц в Росгосстрахе"));
    }

    /**
     * Скролить до кнопки "Свяжитесь со мной"
     */
    public void scrollToButtonConnectWithMe(){
        waitAndCloseWidget();
        jsExecutor.executeScript("arguments[0].scrollIntoView(false)", driver.findElement(By.xpath("//button[text()='Свяжитесь со мной']")));
        waitForPageStability(10000, 1000);
    }

    /**
     * Заполняет выбранное поле введенным текстом и проверяет то, что оно заполнилось
     * @param fieldName - Ввести название поля для ввода, на данный момент есть 4: userName, userTel, userEmail, userAddress(его нет, но просто проверка на него может быть)
     * @param text - Текст, который будет введён в заполняемое поле
     */
    public void fillInTheApplicationsField(String fieldName, String text){
        waitAndCloseWidget();
        for (WebElement itemMenu: listOfFillableFields) {
            if (itemMenu.getText().contains(fieldName) || fieldName.equals("userAddress")){
                wait.until(ExpectedConditions.elementToBeClickable(itemMenu));
                actions.moveToElement(itemMenu).pause(200).click(itemMenu).build().perform();
                itemMenu.clear();
                itemMenu.sendKeys(text);
                boolean telNumber = itemMenu.getAttribute("name").equals("userTel");
                text = telNumber ? getForMaskValue(text) : text;
                boolean elemFilled = wait.until(ExpectedConditions.attributeContains(itemMenu, "value", text));
                Assertions.assertTrue(elemFilled, "Поле не заполнено");
            }
        }
        Assertions.fail("Названия поля, которое вы ввели не существует или не найдено");
    }


    /**
     * Нажать на кнопку "Свяжитесь со мной"
     */
    public void clickOnBtnContactWithMe(){
        btnContactWithMe.click();
    }

    /**
     * Находит ошибку заполнения и выводит в консоль название этой ошибки
     */
    public void checkErrorMassagesUnderFillApplicationsFields(){
        for (WebElement errorMassage : listErrorMassages) {
            System.out.println(errorMassage.getText());
        }
        Assertions.fail("Ошибки были не найдены");
    }
}
