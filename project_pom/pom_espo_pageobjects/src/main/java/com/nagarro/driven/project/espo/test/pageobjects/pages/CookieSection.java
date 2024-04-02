package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Waiter;
import java.util.concurrent.TimeUnit;


public class CookieSection {

    SeleniumAbstractDriver seleniumClient;

    private static final String PAGE_NAME = "CookieSection";

    public CookieSection(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public void acceptAllCookies() throws InterruptedException {
        TimeUnit.SECONDS.sleep(2);
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "AcceptAllCookiesButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "AcceptAllCookiesButton").click();
    }
}
