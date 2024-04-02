package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WhoIsFlyingModal {

    private final Logger log = LoggerFactory.getLogger(WhoIsFlyingModal.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "WhoIsFlyingModal";
    private final int MAX_TRAVELLERS_ALLOWED = 20;

    /**
     * The home page constructor
     */
    public WhoIsFlyingModal(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public void setNumberOfAdults(int desiredNumberOfAdults) {
        int iterations = 0;
        while (desiredNumberOfAdults > getNumberOfAdults()) {
            seleniumClient.element(PAGE_NAME, "AddAdultButton").click();
            iterations++;
            if (iterations > MAX_TRAVELLERS_ALLOWED) {return;}
        }
        while (desiredNumberOfAdults < getNumberOfAdults()) {
            seleniumClient.element(PAGE_NAME, "RemoveAdultButton").click();
            iterations++;
            if (iterations > MAX_TRAVELLERS_ALLOWED) {return;}
        }
    }

    public void setNumberOfChildren(int desiredNumberOfChildren) {
        int iterations = 0;
        while (desiredNumberOfChildren > getNumberOfChildren()) {
            seleniumClient.element(PAGE_NAME, "AddChildButton").click();
            iterations++;
            if (iterations > MAX_TRAVELLERS_ALLOWED) {return;}
        }
        while (desiredNumberOfChildren < getNumberOfChildren()) {
            seleniumClient.element(PAGE_NAME, "RemoveChildButton").click();
            iterations++;
            if (iterations > MAX_TRAVELLERS_ALLOWED) {return;}
        }
    }

    public void setNumberOfInfants(int desiredNumberOfInfants) {
        int iterations = 0;
        while (desiredNumberOfInfants > getNumberOfInfants()) {
            seleniumClient.element(PAGE_NAME, "AddInfantButton").click();
            iterations++;
            if (iterations > MAX_TRAVELLERS_ALLOWED) {return;}
        }
        while (desiredNumberOfInfants < getNumberOfInfants()) {
            seleniumClient.element(PAGE_NAME, "RemoveInfantButton").click();
            iterations++;
            if (iterations > MAX_TRAVELLERS_ALLOWED) {return;}
        }
    }

    public void clickConfirm() {
        seleniumClient.element(PAGE_NAME, "ConfirmButton").click();
    }

    public void clickCancel() {
        seleniumClient.element(PAGE_NAME, "CancelButton").click();
    }

    public int getNumberOfAdults() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "NumberOfAdults").isDisplayed());
        return Integer.parseInt(seleniumClient.element(PAGE_NAME, "NumberOfAdults").getText());
    }

    public int getNumberOfChildren() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "NumberOfChildren").isDisplayed());
        return Integer.parseInt(seleniumClient.element(PAGE_NAME, "NumberOfChildren").getText());
    }

    public int getNumberOfInfants() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "NumberOfInfants").isDisplayed());
        return Integer.parseInt(seleniumClient.element(PAGE_NAME, "NumberOfInfants").getText());
    }

    public boolean isRemoveAdultButtonDisabled() {
        return seleniumClient.element(PAGE_NAME, "RemoveAdultButton").getAttribute("aria-disabled").equals("true");
    }

    public boolean isRemoveAdultButtonEnabled() {
        return seleniumClient.element(PAGE_NAME, "RemoveAdultButton").getAttribute("aria-disabled") == null;
    }

    public boolean isRemoveChildButtonDisabled() {
        return seleniumClient.element(PAGE_NAME, "RemoveChildButton").getAttribute("aria-disabled").equals("true");
    }

    public boolean isRemoveChildButtonEnabled() {
        return seleniumClient.element(PAGE_NAME, "RemoveChildButton").getAttribute("aria-disabled") == null;
    }

    public boolean isRemoveInfantButtonDisabled() {
        return seleniumClient.element(PAGE_NAME, "RemoveInfantButton").getAttribute("aria-disabled").equals("true");
    }

    public boolean isRemoveInfantButtonEnabled() {
        return seleniumClient.element(PAGE_NAME, "RemoveInfantButton").getAttribute("aria-disabled") == null;
    }

    public boolean isAddAdultButtonDisabled() {
        return seleniumClient.element(PAGE_NAME, "AddAdultButton").getAttribute("aria-disabled").equals("true");
    }

    public boolean isAddAdultButtonEnabled() {
        return seleniumClient.element(PAGE_NAME, "AddAdultButton").getAttribute("aria-disabled") == null;
    }

    public boolean isAddChildButtonDisabled() {
        return seleniumClient.element(PAGE_NAME, "AddChildButton").getAttribute("aria-disabled").equals("true");
    }

    public boolean isAddChildButtonEnabled() {
        return seleniumClient.element(PAGE_NAME, "AddChildButton").getAttribute("aria-disabled") == null;
    }

    public boolean isAddInfantButtonDisabled() {
        return seleniumClient.element(PAGE_NAME, "AddInfantButton").getAttribute("aria-disabled").equals("true");
    }

    public boolean isAddInfantButtonEnabled() {
        return seleniumClient.element(PAGE_NAME, "AddInfantButton").getAttribute("aria-disabled") == null;
    }

    public boolean isConfirmButtonDisabled() {
        return seleniumClient.element(PAGE_NAME, "ConfirmButton").getAttribute("aria-disabled").equals("true");
    }

    public boolean isConfirmButtonEnabled() {
        return seleniumClient.element(PAGE_NAME, "ConfirmButton").getAttribute("aria-disabled") == null;
    }

    public boolean isErrorMessageDisplayed() {
        return seleniumClient.elements(PAGE_NAME, "ErrorMessage").size() == 1;
    }

    public boolean isErrorMessageAddMorePassengersDisplayed() {
        seleniumClient.element(PAGE_NAME, "ErrorMessageAddMorePassengers").click();
        return seleniumClient.elements(PAGE_NAME, "ErrorMessageAddMorePassengers").size() == 1;
    }

    public boolean isErrorMessageAtLeastOneAdultPerInfantDisplayed() {
        return seleniumClient.elements(PAGE_NAME, "ErrorMessageAtLeastOneAdultPerInfant").size() == 1;
    }

    public boolean isErrorMessageAtLeastOneAdultPerInfantNotDisplayed() {
        return seleniumClient.elements(PAGE_NAME, "ErrorMessageAtLeastOneAdultPerInfant").size() == 0;
    }

    public boolean isErrorMessageMax10AdultsPlusChildrenDisplayed() {
        return seleniumClient.elements(PAGE_NAME, "ErrorMessageMax10AdultsPlusChildren").size() == 1;
    }

    public boolean isErrorMessageMax10AdultsPlusChildrenNotDisplayed() {
        return seleniumClient.elements(PAGE_NAME, "ErrorMessageMax10AdultsPlusChildren").size() == 0;
    }

    public boolean isWarningMessageChildTravelingAloneDisplayed() {
        return (seleniumClient.elements(PAGE_NAME, "WarningMessageChildTravelingAloneLabel").size() == 1) &&
                (seleniumClient.elements(PAGE_NAME, "WarningMessageChildTravelingAloneContactLink").size() == 1);
    }

    public boolean isWarningMessageChildTravelingAloneNotDisplayed() {
        return (seleniumClient.elements(PAGE_NAME, "WarningMessageChildTravelingAloneLabel").size() == 0) &&
                (seleniumClient.elements(PAGE_NAME, "WarningMessageChildTravelingAloneContactLink").size() == 0);
    }
}
