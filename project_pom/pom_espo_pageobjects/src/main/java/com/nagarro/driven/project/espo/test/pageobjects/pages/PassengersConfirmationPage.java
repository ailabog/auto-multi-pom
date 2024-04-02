package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PassengersConfirmationPage {

    private final Logger log = LoggerFactory.getLogger(PassengersConfirmationPage.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "PassengersConfirmationPage";

    /**
     * The home page constructor
     */
    public PassengersConfirmationPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public void clickFlyrLogoLink() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FlyrLogoLink").isDisplayed());
        seleniumClient.elements(PAGE_NAME, "FlyrLogoLink").click();
    }

    public void navigateBack() {
        seleniumClient.getWebDriver().navigate().back();
    }
}
