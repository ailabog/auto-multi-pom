package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Sleeper;
import com.nagarro.driven.core.util.Waiter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


public class MyJourneyPage {

    private final Logger log = LoggerFactory.getLogger(MyJourneyPage.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "MyJourneyPage";

    /**
     * The home page constructor
     */
    public MyJourneyPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    private By travellersInitials() {
        return By.xpath("//div[text()='Travellers']/following-sibling::div/div[1]");
    }

    private By travellersNames() {
        return By.xpath("//div[text()='Travellers']/following-sibling::div/div[2]");
    }

    private By dayOfOutboundFlight() {
        return By.xpath("//div[text()='Outbound']/parent::div/following-sibling::div/div[1]");
    }

    private By dayOfReturnFlight() {
        return By.xpath("//div[text()='Return']/parent::div/following-sibling::div/div[1]");
    }

    public void clickProceedToPaymentButton() {
        Sleeper.silentSleep(1000);
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ProceedToPaymentButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ProceedToPaymentButton").click();
        Sleeper.silentSleep(1000);
    }

    private String getSelectedSeatsDeparture() {
        return seleniumClient.element(PAGE_NAME, "SelectedSeatsLabelDeparture").getText();
    }

    private String getSelectedSeatsReturn() {
        return seleniumClient.element(PAGE_NAME, "SelectedSeatsLabelReturn").getText();
    }

    public boolean departureSelectedSeatsAreCorrectlyDisplayed(Map<String, String> travelersSelectedSeatsDeparture) {
        String displayedSeats = getSelectedSeatsDeparture();
        log.info("Displayed departure seats: " + displayedSeats);
        for(String expectedSeat : travelersSelectedSeatsDeparture.values()) {
            log.info("Checking if " + displayedSeats + " contains " + expectedSeat);
            if (!displayedSeats.contains(expectedSeat)) {
                return false;
            }
        }
        return true;
    }

    public boolean returnSelectedSeatsAreCorrectlyDisplayed(Map<String, String> travelersSelectedSeatsReturn) {
        String displayedSeats = getSelectedSeatsReturn();
        log.info("Displayed return seats: " + displayedSeats);
        for(String expectedSeat : travelersSelectedSeatsReturn.values()) {
            log.info("Checking if " + displayedSeats + " contains " + expectedSeat);
            if (!displayedSeats.contains(expectedSeat)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkTravellerNameDisplayed(String travellerExpectedName) {
        for (WebElement travellerDisplayedName : seleniumClient.getWebDriver().findElements(travellersNames())) {
            if (travellerDisplayedName.getText().contains(travellerExpectedName)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTravellerInitialsDisplayed(String travellerExpectedInitials) {
        for (WebElement travellerDisplayedInitials : seleniumClient.getWebDriver().findElements(travellersInitials())) {
            if (travellerDisplayedInitials.getText().contains(travellerExpectedInitials)) {
                return true;
            }
        }
        return false;
    }

    public String getDepartureAirport() {
        return seleniumClient.element(PAGE_NAME, "DepartureAirport").getText();
    }

    public String getDestinationAirport() {
        return seleniumClient.element(PAGE_NAME, "DestinationAirport").getText();
    }

    public String getDatesDepartureReturn() {
        return seleniumClient.element(PAGE_NAME, "DatesDepartureReturn").getText();
    }

    public boolean checkCorrectDatesDepartureReturn(int departureDay, String departureMonth, int returnDay, String returnMonth) {
        boolean match = false;

        if ((departureDay != returnDay) || (!departureMonth.equalsIgnoreCase(returnMonth))) {
            match = getDatesDepartureReturn().equals(departureDay + " " + departureMonth.substring(0,3) + " - " + returnDay + " " + returnMonth.substring(0,3));
        }
        if ((departureDay == returnDay) && (departureMonth.equalsIgnoreCase(returnMonth))) {
            match = getDatesDepartureReturn().equals(departureDay + " " + departureMonth.substring(0,3));
        }
        return match;
    }

    public void navigateBack() {
        seleniumClient.getWebDriver().navigate().back();
    }
}
