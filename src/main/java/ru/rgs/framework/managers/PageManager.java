package ru.rgs.framework.managers;

import ru.rgs.framework.pages.DMSPage;
import ru.rgs.framework.pages.ForCompaniesPage;
import ru.rgs.framework.pages.StartPage;

// Синглтон
public class PageManager {

    private static PageManager INSTANCE;

    private DMSPage dmsPage;
    private ForCompaniesPage forCompaniesPage;
    private StartPage startPage;

    private PageManager() {

    }

    public static PageManager getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PageManager();
        return INSTANCE;
    }

    public DMSPage getDMSPage() {
        if (dmsPage == null)
            dmsPage = new DMSPage();
        return dmsPage;
    }

    public ForCompaniesPage getForCompaniesPage() {
        if (forCompaniesPage == null)
            forCompaniesPage = new ForCompaniesPage();
        return forCompaniesPage;
    }

    public StartPage getStartPage() {
        if (startPage == null)
            startPage = new StartPage();
        return startPage;
    }
}
