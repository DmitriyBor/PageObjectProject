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
import ru.rgs.framework.data.FieldFormReg;
import ru.rgs.framework.pages.DMSPage;
import ru.rgs.framework.pages.ForCompaniesPage;
import ru.rgs.framework.pages.StartPage;
import ru.rgs.myannotatin.allTests;

import java.util.concurrent.TimeUnit;

public class RgsStrahovanie extends BaseTests {

    @allTests
    public void test() {
        pageManager.getStartPage()
                .pageStability()
                .closeCookies()
                .selectBaseMenuByText("Компаниям")
                .checkOpenPage()
                .selectSubMenuByText("Здоровье")
                .selectMenuInSecondMenu("Добровольное медицинское страхование")
                .checkOpenPage()
                .scrollToButtonConnectWithMe()
                .fillInTheApplicationsField(FieldFormReg.userName, "Борисенков Дмитрий Владимирович")
                .fillInTheApplicationsField(FieldFormReg.userTel, "7776665555")
                .fillInTheApplicationsField(FieldFormReg.userEmail, "qwertyqwerty")
                .fillInTheApplicationsField(FieldFormReg.userAddress, "г Москва, Перовское шоссе, д 2")
                .clickOnBtnContactWithMe()
                .checkErrorMassagesUnderFillApplicationsFields();
    }
}
