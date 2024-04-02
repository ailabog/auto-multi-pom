package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Sleeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoginPage {

    private final Logger log = LoggerFactory.getLogger(LoginPage.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "LoginPage";

    /**
     * The home page constructor
     */
    public LoginPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public void continueWithoutProfile() {
        seleniumClient.element(PAGE_NAME, "ContinueWithoutProfileButton").click();
        Sleeper.silentSleep(500);
    }
}

