package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ReturnPage {

    private final Logger log = LoggerFactory.getLogger(ReturnPage.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "ReturnPage";

    /**
     * The home page constructor
     */
    public ReturnPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public void smallChecks() {
        seleniumClient.element(PAGE_NAME, "OutboundDayMonth").isDisplayed();
        //seleniumClient.element(PAGE_NAME, "AddToCalendar").isDisplayed();
        seleniumClient.element(PAGE_NAME, "HandLugageSeat").isDisplayed();
    }
}
