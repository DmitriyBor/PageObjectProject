package ru.rgs.base;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.rgs.framework.managers.DriverManager;
import ru.rgs.framework.managers.InitManager;
import ru.rgs.framework.managers.PageManager;
import ru.rgs.framework.managers.TestPropManager;
import ru.rgs.framework.utils.PropsConst;

import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTests {
    private final DriverManager driverManager = DriverManager.getInstance();
    protected PageManager pageManager = PageManager.getInstance();
    private final TestPropManager propManager = TestPropManager.getInstance();
    protected WebDriverWait wait ;
    protected Actions actions;
    protected JavascriptExecutor jsExecutor;


    @BeforeEach
    public void beforeEach() {
        driverManager.getDriver().get(propManager.getProperty(PropsConst.BASE_URL));
    }

    @BeforeAll
    public void before() {
        InitManager.initFramework();
    }

    @AfterAll
    public void after() {
        InitManager.quitFramework();
    }
}
