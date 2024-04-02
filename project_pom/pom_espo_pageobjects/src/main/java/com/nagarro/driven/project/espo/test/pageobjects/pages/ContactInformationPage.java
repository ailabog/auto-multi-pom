package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Waiter;
import org.openqa.selenium.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ContactInformationPage {

    private final Logger log = LoggerFactory.getLogger(ContactInformationPage.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "ContactInformationPage";

    /**
     * The home page constructor
     */
    public ContactInformationPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public void setEmail(String email) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "Email").isDisplayed());
        seleniumClient.element(PAGE_NAME, "Email").click();
        seleniumClient.element(PAGE_NAME, "Email").clear();
        seleniumClient.element(PAGE_NAME, "Email").sendKeys(email);
    }

    public void setPhoneNumber(String phoneNumber) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "PhoneNumber").isDisplayed());
        seleniumClient.element(PAGE_NAME, "PhoneNumber").click();
        seleniumClient.element(PAGE_NAME, "PhoneNumber").clear();
        seleniumClient.element(PAGE_NAME, "PhoneNumber").sendKeys(Keys.chord(Keys.CONTROL, "a"));
        seleniumClient.element(PAGE_NAME, "PhoneNumber").sendKeys(phoneNumber);
    }

    public boolean isErrorMessageInvalidEmailAddressDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "InvalidEmailAddressErrorMessage").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "InvalidEmailAddressErrorMessage").size() == 1;
    }

    public boolean isErrorMessageInvalidPhoneNumberDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "InvalidPhoneNumberErrorMessage").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "InvalidPhoneNumberErrorMessage").size() == 1;
    }

    public boolean isErrorMessageEmailAddressMissingDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "EmailAddressMissingErrorMessage").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "EmailAddressMissingErrorMessage").size() == 1;
    }

    public boolean isErrorMessagePhoneNumberMissingDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "PhoneNumberMissingErrorMessage").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "PhoneNumberMissingErrorMessage").size() == 1;
    }

    public void clickContinueButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ContinueButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ContinueButton").click();
    }

    public void navigateBack() {
        seleniumClient.getWebDriver().navigate().back();
    }
}
