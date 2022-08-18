package ru.rgs.tests;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.rgs.base.BaseTests;
import ru.rgs.framework.pages.DMSPage;
import ru.rgs.framework.pages.ForCompaniesPage;
import ru.rgs.framework.pages.StartPage;
import ru.rgs.myannotatin.allTests;

import java.util.concurrent.TimeUnit;

public class RgsStrahovanie extends BaseTests {
    StartPage startPage = new StartPage(driver);
    ForCompaniesPage forCompaniesPage = new ForCompaniesPage(driver);
    DMSPage dmsPage = new DMSPage(driver);

    @allTests
    public void test() {
        waitForPageStability(10000, 1000);

        startPage.closeCookies();
        startPage.selectBaseMenuByText("Компаниям");

        forCompaniesPage.checkOpenPage();
        forCompaniesPage.selectSubMenuByText("Здоровье");
        forCompaniesPage.selectMenuInSecondMenu("Добровольное медицинское страхование");

        dmsPage.checkOpenPage();
        dmsPage.scrollToButtonConnectWithMe();
        dmsPage.fillInTheApplicationsField("userName", "Борисенков Дмитрий Владимирович");
        dmsPage.fillInTheApplicationsField("userTel", "7776665555");
        dmsPage.fillInTheApplicationsField("userEmail", "qwertyqwerty");
        dmsPage.fillInTheApplicationsField("userAddress", "г Москва, Перовское шоссе, д 2");
        dmsPage.clickOnBtnContactWithMe();
        dmsPage.checkErrorMassagesUnderFillApplicationsFields();

    }
}
