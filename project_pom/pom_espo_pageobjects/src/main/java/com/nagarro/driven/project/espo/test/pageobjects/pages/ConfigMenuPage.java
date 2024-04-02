package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Sleeper;
import com.nagarro.driven.core.util.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfigMenuPage {

    private final Logger log = LoggerFactory.getLogger(ConfigMenuPage.class);
    private String selectedButtonBackgroundColor = "rgba(198, 238, 211, 1)";
    private String unselectedButtonBackgroundColor = "rgba(0, 0, 0, 0)";

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "ConfigMenuPage";

    /**
     * The home page constructor
     */
    public ConfigMenuPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public void exitConfigMenuPage() {
        seleniumClient.element(PAGE_NAME, "ExitConfigMenuButton").click();
        Sleeper.silentSleep(500);
    }

    public void selectCurrencyEUR() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CurrencyButtonEUR").isDisplayed());
        seleniumClient.element(PAGE_NAME, "CurrencyButtonEUR").click();
    }

    public void selectCurrencyNOK() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CurrencyButtonNOK").isDisplayed());
        seleniumClient.element(PAGE_NAME, "CurrencyButtonNOK").click();
    }

    public void selectLanguageEnglish() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "LanguageButtonEnglish").isDisplayed());
        seleniumClient.element(PAGE_NAME, "LanguageButtonEnglish").click();
    }

    public void selectLanguageNorwegian() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "LanguageButtonNorwegian").isDisplayed());
        seleniumClient.element(PAGE_NAME, "LanguageButtonNorwegian").click();
    }

    public boolean checkIsSelectedCurrencyEUR() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CurrencyButtonEUR").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "CurrencyButtonEUR").getCss("background-color").equals(selectedButtonBackgroundColor);
    }

    public boolean checkIsNotSelectedCurrencyEUR() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CurrencyButtonEUR").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "CurrencyButtonEUR").getCss("background-color").equals(unselectedButtonBackgroundColor);
    }

    public boolean checkIsSelectedCurrencyNOK() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CurrencyButtonNOK").getCss("background-color").equals(selectedButtonBackgroundColor));
        return seleniumClient.element(PAGE_NAME, "CurrencyButtonNOK").getCss("background-color").equals(selectedButtonBackgroundColor);
    }

    public boolean checkIsNotSelectedCurrencyNOK() {
        return seleniumClient.element(PAGE_NAME, "CurrencyButtonNOK").getCss("background-color").equals(unselectedButtonBackgroundColor);
    }

    public boolean checkIsSelectedLanguageEnglish() {
        return seleniumClient.element(PAGE_NAME, "LanguageButtonEnglish").getCss("background-color").equals(selectedButtonBackgroundColor);
    }

    public boolean checkIsNotSelectedLanguageEnglish() {
        return seleniumClient.element(PAGE_NAME, "LanguageButtonEnglish").getCss("background-color").equals(unselectedButtonBackgroundColor);
    }

    public boolean checkIsSelectedLanguageNorwegian() {
        return seleniumClient.element(PAGE_NAME, "LanguageButtonNorwegian").getCss("background-color").equals(selectedButtonBackgroundColor);
    }

    public boolean checkIsNotSelectedLanguageNorwegian() {
        return seleniumClient.element(PAGE_NAME, "LanguageButtonNorwegian").getCss("background-color").equals(unselectedButtonBackgroundColor);
    }
}

