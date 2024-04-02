package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WhoIsFlyingPage {

    private final Logger log = LoggerFactory.getLogger(WhoIsFlyingPage.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "WhoIsFlyingPage";

    /**
     * The home page constructor
     */
    public WhoIsFlyingPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    //Locators for Adult travellers elements

    public void setAdultFirstNameWithIndex(String firstName, int adultIndex) {
        seleniumClient.element(PAGE_NAME, "Adult" + adultIndex + "FirstName").clear();
        seleniumClient.element(PAGE_NAME, "Adult" + adultIndex + "FirstName").sendKeys(firstName);
    }

    public void setAdultLastNameWithIndex(String lastName, int adultIndex) {
        seleniumClient.element(PAGE_NAME, "Adult" + adultIndex + "LastName").clear();
        seleniumClient.element(PAGE_NAME, "Adult" + adultIndex + "LastName").sendKeys(lastName);
    }

    public void setAdultSexMaleWithIndex(int adultIndex) {
        seleniumClient.element(PAGE_NAME, "Adult" + adultIndex + "SexRadioButtonMale").click();
    }

    public void setAdultSexFemaleWithIndex(int adultIndex) {
        seleniumClient.element(PAGE_NAME, "Adult" + adultIndex + "SexRadioButtonMale").click();
    }

    //Locators for Child travellers elements

    public void setChildFirstNameWithIndex(String firstName, int childIndex) {
        seleniumClient.element(PAGE_NAME, "Child" + childIndex + "FirstName").clear();
        seleniumClient.element(PAGE_NAME, "Child" + childIndex + "FirstName").sendKeys(firstName);
    }

    public void setChildLastNameWithIndex(String lastName, int childIndex) {
        seleniumClient.element(PAGE_NAME, "Child" + childIndex + "LastName").clear();
        seleniumClient.element(PAGE_NAME, "Child" + childIndex + "LastName").sendKeys(lastName);
    }

    public void setChildSexMaleWithIndex(int childIndex) {
        seleniumClient.element(PAGE_NAME, "Child" + childIndex + "SexRadioButtonMale").scrollToElement();
        seleniumClient.element(PAGE_NAME, "Child" + childIndex + "SexRadioButtonMale").click();
    }

    public void setChildSexFemaleWithIndex(int childIndex) {
        seleniumClient.element(PAGE_NAME, "Child" + childIndex + "SexRadioButtonFemale").scrollToElement();
        seleniumClient.element(PAGE_NAME, "Child" + childIndex + "SexRadioButtonFemale").click();
    }

    public void setChildBirthdayWithIndex(String birthday, int childIndex) {
        seleniumClient.element(PAGE_NAME, "Child" + childIndex + "Birthday").scrollToElement();
        seleniumClient.element(PAGE_NAME, "Child" + childIndex + "Birthday").sendKeys(birthday);
    }

    //Locators for Infant travellers elements

    public void setInfantFirstNameWithIndex(String firstName, int infantIndex) {
        seleniumClient.element(PAGE_NAME, "Infant" + infantIndex + "FirstName").clear();
        seleniumClient.element(PAGE_NAME, "Infant" + infantIndex + "FirstName").sendKeys(firstName);
    }

    public void setInfantLastNameWithIndex(String lastName, int infantIndex) {
        seleniumClient.element(PAGE_NAME, "Infant" + infantIndex + "LastName").clear();
        seleniumClient.element(PAGE_NAME, "Infant" + infantIndex + "LastName").sendKeys(lastName);
    }

    public void setInfantSexMaleWithIndex(int infantIndex) {
        seleniumClient.element(PAGE_NAME, "Infant" + infantIndex + "SexRadioButtonMale").scrollToElement();
        seleniumClient.element(PAGE_NAME, "Infant" + infantIndex + "SexRadioButtonMale").click();
    }

    public void setInfantSexFemaleWithIndex(int infantIndex) {
        seleniumClient.element(PAGE_NAME, "Infant" + infantIndex + "SexRadioButtonFemale").scrollToElement();
        seleniumClient.element(PAGE_NAME, "Infant" + infantIndex + "SexRadioButtonFemale").click();
    }

    public void setInfantBirthdayWithIndex(String birthday, int infantIndex) {
        seleniumClient.element(PAGE_NAME, "Infant" + infantIndex + "Birthday").scrollToElement();
        seleniumClient.element(PAGE_NAME, "Infant" + infantIndex + "Birthday").sendKeys(birthday);
    }



    public void clickContinueButton() {
        seleniumClient.element(PAGE_NAME, "ContinueButton").click();
    }

    //Error messages children

    public boolean isErrorMessageChildTooOldDisplayedWithIndex(int childIndex) {
        return seleniumClient.elements(PAGE_NAME, "Child" + childIndex + "ErrorMessageTooOld").size() == 1;
    }

    public boolean isErrorMessageChildTooOldNotDisplayedWithIndex(int childIndex) {
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "Child" + childIndex + "ErrorMessageTooOld").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "Child" + childIndex + "ErrorMessageTooOld").size() == 0;
    }

    public boolean isErrorMessageChildNameRequiredDisplayedWithIndex(int childIndex) {
        return seleniumClient.elements(PAGE_NAME, "Child" + childIndex + "ErrorMessageNameRequired").size() == 1;
    }

    //Error messages infants

    public boolean isErrorMessageInfantTooOldAdultDisplayedWithIndex(int infantIndex) {
        return seleniumClient.elements(PAGE_NAME, "Infant" + infantIndex + "ErrorMessageTooOldAdult").size() == 1;
    }

    public boolean isErrorMessageInfantTooOldChildDisplayedWithIndex(int infantIndex) {
        return seleniumClient.elements(PAGE_NAME, "Infant" + infantIndex + "ErrorMessageTooOldChild").size() == 1;
    }

    public boolean isErrorMessageInfantTooOldAdultNotDisplayedWithIndex(int infantIndex) {
        return seleniumClient.elements(PAGE_NAME, "Infant" + infantIndex + "ErrorMessageTooOldAdult").size() == 0;
    }

    public boolean isErrorMessageInfantTooOldChildNotDisplayedWithIndex(int infantIndex) {
        return seleniumClient.elements(PAGE_NAME, "Infant" + infantIndex + "ErrorMessageTooOldChild").size() == 0;
    }
}
