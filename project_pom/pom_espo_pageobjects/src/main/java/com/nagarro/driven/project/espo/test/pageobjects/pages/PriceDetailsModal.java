package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Waiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PriceDetailsModal {

    private final Logger log = LoggerFactory.getLogger(PriceDetailsModal.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "PriceDetailsModal";

    /**
     * The home page constructor
     */
    public PriceDetailsModal(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public String getDepartureAirports() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DepartureAirports").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "DepartureAirports").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "DepartureAirports").getText();
    }

    public String getReturnAirports() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ReturnAirports").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "ReturnAirports").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "ReturnAirports").getText();
    }

    public String getDepartureDateTime() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DepartureDateTime").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "DepartureDateTime").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "DepartureDateTime").getText();
    }

    public String getReturnDateTime() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ReturnDateTime").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "ReturnDateTime").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "ReturnDateTime").getText();
    }

    public String getDeparturePrices() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DeparturePrices").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "DeparturePrices").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "DeparturePrices").getText().replace(",", "");
    }

    public String getReturnPrices() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ReturnPrices").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "ReturnPrices").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "ReturnPrices").getText().replace(",", "");
    }

    public String getTotal() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "TotalSection").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "TotalSection").getText().equals("0"));
        return seleniumClient.element(PAGE_NAME, "TotalSection").getText().replace(",", "");
    }

    public boolean checkDepartureDateTimeDisplayedCorrectly(int departureSelectionDay, String departureSelectionMonth) {
        Pattern pattern = Pattern.compile("^\\D{1}\\w{2}\\s" +
                departureSelectionDay + "\\s" +
                departureSelectionMonth.substring(0, 3) + "\\s\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(getDepartureDateTime());
        return matcher.find();
    }

    public boolean checkReturnDateTimeDisplayedCorrectly(int returnSelectionDay, String returnSelectionMonth) {
        Pattern pattern = Pattern.compile("^\\D{1}\\w{2}\\s" +
                returnSelectionDay + "\\s" +
                returnSelectionMonth.substring(0, 3) + "\\s\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(getReturnDateTime());
        return matcher.find();
    }

    public boolean checkDeparturePricesDisplayedCorrectly(List<String> expectedTravelerAndServices) {
        String departurePrices = getDeparturePrices();
        for (String expectedTravelerAndService : expectedTravelerAndServices) {
            if (!departurePrices.contains(expectedTravelerAndService)) {
                return false;
            }
        }
        return getNumberOfDisplayedPrices(departurePrices) == expectedTravelerAndServices.size();
    }

    public boolean checkReturnPricesDisplayedCorrectly(List<String> expectedTravelerAndServices) {
        String returnPrices = getReturnPrices();
        for (String expectedTravelerAndService : expectedTravelerAndServices) {
            if (!returnPrices.contains(expectedTravelerAndService)) {
                return false;
            }
        }
        return getNumberOfDisplayedPrices(returnPrices) == expectedTravelerAndServices.size();
    }

    private long getNumberOfDisplayedPrices(String displayedPrices) {
        Pattern pattern = Pattern.compile("\\d+\\skr");
        Matcher matcher = pattern.matcher(displayedPrices);
        return matcher.results().count();
    }

    public boolean checkTotalDisplayedCorrectly() {
        String total = getTotal()
                .replace(",", "")
                .replace("\n"," ");
        Pattern pattern = Pattern.compile("^Total \\(Incl. taxes and fees\\) \\d+ kr$");
        Matcher matcher = pattern.matcher(total);
        return matcher.find();
    }

    public void closePriceDetailsModal() {
        seleniumClient.element(PAGE_NAME, "CloseButton").click();
    }
}
