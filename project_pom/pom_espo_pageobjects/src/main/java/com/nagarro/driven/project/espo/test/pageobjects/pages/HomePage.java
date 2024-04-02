package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Sleeper;
import com.nagarro.driven.core.util.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HomePage {

    private final Logger log = LoggerFactory.getLogger(HomePage.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "HomePage";

    /**
     * The home page constructor
     */
    public HomePage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public boolean checkGreetingHeadingIsDisplayedInEnglish() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "GreetingHeadingEnglish").isDisplayed());
        return (seleniumClient.elements(PAGE_NAME, "GreetingHeadingEnglish").size() == 1);
    }

    public boolean checkGreetingHeadingIsDisplayedInNorwegian() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "GreetingHeadingNorwegian").isDisplayed());
        return (seleniumClient.elements(PAGE_NAME, "GreetingHeadingNorwegian").size() == 1);
    }

    public void clickConfigMenuButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ConfigMenuButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ConfigMenuButton").click();
    }

    public void clickWhoIsFlyingButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "WhoIsFlyingButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "WhoIsFlyingButton").click();
    }

    public void updateFromField(String text){
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FromField").isDisplayed());
        seleniumClient.element(PAGE_NAME, "FromField").click();
        seleniumClient.element(PAGE_NAME, "FromFieldClearButton").click();
        seleniumClient.element(PAGE_NAME, "FromField").setText(text);
    }

    public void clickToField(){
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ToField").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ToField").click();
    }

    public void setToField(String text){
        clickToField();
        seleniumClient.element(PAGE_NAME, "ToField").setText(text);
    }

    public void updateToField(String text){
        clickToField();
        seleniumClient.element(PAGE_NAME, "ToFieldClearButton").click();
        seleniumClient.element(PAGE_NAME, "ToField").setText(text);
    }

    public void clickDestinationButton(String destinationName){
        seleniumClient.element(PAGE_NAME, "DestButton" + destinationName).click();
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "FromField").getAttribute("value").equals(""));
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "ToField").getAttribute("value").equals(""));
    }

    public String getDepartureFirstElementSuggestionName(){
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DepartureSuggestionsDisplayed").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "DepartureFirstElementSuggestionName").getText();
    }

    public String getDestinationFirstElementSuggestionName(){
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DestinationSuggestionsDisplayed").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "DestinationFirstElementSuggestionName").getText();
    }

    public String getDepartureFirstElementSuggestionIATA(){
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DepartureSuggestionsDisplayed").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "DepartureFirstElementSuggestionIATA").getText();
    }

    public String getDestinationFirstElementSuggestionIATA(){
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DestinationSuggestionsDisplayed").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "DestinationFirstElementSuggestionIATA").getText();
    }

    public void clickDepartureFirstElementSuggestion(){
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DepartureSuggestionsDisplayed").isDisplayed());
        seleniumClient.element(PAGE_NAME, "DepartureFirstElementSuggestionName").click();
    }

    public void clickDestinationFirstElementSuggestion(){
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DestinationSuggestionsDisplayed").isDisplayed());
        seleniumClient.element(PAGE_NAME, "DestinationFirstElementSuggestionName").click();
        Sleeper.silentSleep(1000);
    }

    public void clickFindMyBooking(){
        seleniumClient.element(PAGE_NAME, "FindMyBookingLink").scrollToElement();
        seleniumClient.element(PAGE_NAME, "FindMyBookingLink").click();
    }

    public List getDestinationElementSuggestionNames() {
        return seleniumClient.elements(PAGE_NAME, "DestinationElementSuggestionNames").getText();
    }
}


