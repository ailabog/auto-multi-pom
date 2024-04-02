package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ConfirmationPage {

    private final Logger log = LoggerFactory.getLogger(ConfirmationPage.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "ConfirmationPage";

    /**
     * The home page constructor
     */
    public ConfirmationPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public boolean checkHeadingTitleIsDisplayedCorrectly() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ThankYouForBookingHeading").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "ThankYouForBookingHeading").size() == 1;
    }

    public boolean checkCorrectDepartureDayMonth(int day, String month) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "OutboundDayMonth").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "OutboundDayMonth").getText().equals("Outbound " + day + " " + month);
    }

    public boolean checkCorrectReturnDayMonth(int day, String month) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ReturnDayMonth").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "ReturnDayMonth").getText().equals("Return " + day + " " + month);
    }

    public boolean checkCorrectOutboundDepartureAirport(String airportName) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "OutboundDepartureAirport").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "OutboundDepartureAirport").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "OutboundDepartureAirport").getText().contains(airportName);
    }

    public boolean checkCorrectOutboundReturnAirport(String airportName) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "OutboundReturnAirport").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "OutboundReturnAirport").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "OutboundReturnAirport").getText().contains(airportName);
    }

    public boolean checkCorrectReturnDepartureAirport(String airportName) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ReturnDepartureAirport").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "ReturnDepartureAirport").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "ReturnDepartureAirport").getText().contains(airportName);
    }

    public boolean checkCorrectReturnReturnAirport(String airportName) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ReturnReturnAirport").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "ReturnReturnAirport").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "ReturnReturnAirport").getText().contains(airportName);
    }

    public boolean checkCorrectFirstTravellerName(String travellerName) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FirstTravellerName").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "FirstTravellerName").getText().equals(travellerName);
    }

    public boolean checkCorrectSecondTravellerName(String travellerName) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "SecondTravellerName").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "SecondTravellerName").getText().equals(travellerName);
    }

    public void clickGoToJourneyButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "GoToJourneyButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "GoToJourneyButton").click();
    }

    public String getBookingReference() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "BookingReference").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "BookingReference").getText();
    }

    public String getEmailAddress() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "EmailAddress").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "EmailAddress").getText();
    }

    public boolean checkBookingReferenceHasCorrectFormat() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "BookingReference").isDisplayed());
        if (seleniumClient.element(PAGE_NAME, "BookingReference").getText().matches("^[A-Z0-9]{7}$")) {
            return true;
        }
        return false;
    }
}
