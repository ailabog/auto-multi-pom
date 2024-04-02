package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;


public class MyJourneyConfirmationPage {

    private final Logger log = LoggerFactory.getLogger(MyJourneyConfirmationPage.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "MyJourneyConfirmationPage";

    /**
     * The home page constructor
     */
    public MyJourneyConfirmationPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public boolean checkCorrectCheckinNotOpenMessageDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CheckInMessage").isDisplayed());
        return (seleniumClient.element(PAGE_NAME, "CheckInMessage").getText().contains("Check in opens ") ||
                seleniumClient.element(PAGE_NAME, "CheckInMessage").getText().contains("Check in is not open yet"));
    }

    public boolean checkCorrectCheckinOpenMessageDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CheckInMessage").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "CheckInMessage").getText().equals("Check in is open");
    }

    public boolean checkCorrectHeaderFrom(String airportName) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "HeaderFrom").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "HeaderFrom").getText().replace("\n", "").equals("From " + airportName);
    }

    public boolean checkCorrectHeaderTo(String airportName) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "HeaderTo").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "HeaderTo").getText().replace("\n", "").equals("to " + airportName);
    }

    public boolean checkCorrectNumberOfPassengers(int numberOfPassengers) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "NumberOfPassengers").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "NumberOfPassengers").getText().equals(Integer.toString(numberOfPassengers));
    }

    public boolean checkCorrectFlightType(String flightType) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FlightType").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "FlightType").getText().equals(flightType);
    }

    public boolean checkCorrectFlightDates(String departureDay, String departureMonth,
                                           String returnDay, String returnMonth) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FlightDates").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "FlightDates").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "FlightDates").getText().equals(departureDay + " " + departureMonth.substring(0,3) + " - " + returnDay + " " + returnMonth.substring(0,3));
    }

    public boolean checkCorrectOutboundAirports(String departureAirport, String returnAirport) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "OutboundAirports").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "OutboundAirports").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "OutboundAirports").getText().equals(departureAirport + "-" + returnAirport);
    }

    public boolean checkCorrectReturnAirports(String departureAirport, String returnAirport) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ReturnAirports").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "ReturnAirports").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "ReturnAirports").getText().equals(departureAirport + "-" + returnAirport);
    }

    public boolean checkCorrectPassengerInitials(String passengerInitials) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "PassengersInitials").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "PassengersInitials").getText().equals(passengerInitials);
    }

    public boolean checkAskForAssistanceButtonIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "AskForAssistanceButton").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "AskForAssistanceButton").size() == 1;
    }

    public boolean checkChangeDateAndTimeButtonIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ChangeDateAndTimeButton").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "ChangeDateAndTimeButton").size() == 1;
    }

    public boolean checkCancelJourneyButtonIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CancelJourneyButton").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "CancelJourneyButton").size() == 1;
    }

    public void clickCancelJourneyButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CancelJourneyButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "CancelJourneyButton").click();
    }

//    These below are external pages, for cancel journey
    public void clickCancelJourneyModalContinueButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CancelJourneyModalContinueButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "CancelJourneyModalContinueButton").click();
    }

    public void clickSignInWithVippsButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "SignInWithVippsButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "SignInWithVippsButton").click();
    }

    public boolean checkLoginFormIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "LogInTitle").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "LogInTitle").size() == 1;
    }

//    These above are external pages/modals, for cancel journey

    public boolean checkDownloadReceiptButtonIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DownloadReceiptButton").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "DownloadReceiptButton").size() == 1;
    }

    public boolean checkSendBookingConfirmationButtonIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "SendBookingConfirmationButton").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "SendBookingConfirmationButton").size() == 1;
    }

    public void clickGoToPassengersLink() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "PassengersLink").isDisplayed());
        seleniumClient.element(PAGE_NAME, "PassengersLink").click();
    }

    public String getBookingReference() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "HeaderTo").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "BookingReference").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "BookingReference").getText();
    }

    public boolean checkCorrectCheckinLink(String reference, String lastname, int departureSelectionDay, String departureSelectionMonth) {
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        String currentMonth= currentDate.getMonth().name();

        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CheckInLink").isDisplayed());
        if ((departureSelectionDay == currentDay) && (departureSelectionMonth.toLowerCase().equals(currentMonth.toLowerCase()))) {
            log.info("Flight is in the current day, check-in should be open.");
            return seleniumClient.element(PAGE_NAME, "CheckInLink").getAttribute("href").equals(
                    "https://st-flyr.inkcloud.io/web_checkin.php?booking_reference=" + reference + "&surname=" + lastname
            );
        }
        return true;
    }
}
