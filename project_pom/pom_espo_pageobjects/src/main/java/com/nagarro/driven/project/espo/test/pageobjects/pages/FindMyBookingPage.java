package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Sleeper;
import com.nagarro.driven.core.util.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FindMyBookingPage {

    private final Logger log = LoggerFactory.getLogger(FindMyBookingPage.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "FindMyBookingPage";

    /**
     * The home page constructor
     */
    public FindMyBookingPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public void enterReference(String reference) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ReferenceInput").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ReferenceInput").setText(reference);
    }

    public void enterLastName(String lastName) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "LastNameInput").isDisplayed());
        seleniumClient.element(PAGE_NAME, "LastNameInput").setText(lastName);
    }

    public void clickFindBookingButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FindBookingButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "FindBookingButton").click();
    }

    public boolean checkBookingNotFoundErrorMessageIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "NoBookingFoundErrorMessage").isDisplayed());
        boolean displayed = seleniumClient.elements(PAGE_NAME, "NoBookingFoundErrorMessage").size() == 1;
        waitForErrorMessageToClear();
        return displayed;
    }

    private void waitForErrorMessageToClear() {
        Sleeper.silentSleep(5000);
    }
}
