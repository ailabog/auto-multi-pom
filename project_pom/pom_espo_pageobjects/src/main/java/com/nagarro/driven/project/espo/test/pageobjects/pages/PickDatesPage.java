package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Sleeper;
import com.nagarro.driven.core.util.Waiter;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PickDatesPage {

    private final Logger log = LoggerFactory.getLogger(PickDatesPage.class);
    public int modalSelectionMiniPriceDeparture;
    public int modalSelectionSmartPriceDeparture;
    public int modalSelectionFleksPriceDeparture;

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "PickDatesPage";
    public int departureSelectionDay;
    public String departureSelectionMonth;
    public int departureSelectionYear;
    public int departureSelectionPrice;
    public int returnSelectionDay;
    public String returnSelectionMonth;
    public int returnSelectionYear;
    public int returnSelectionPrice;

    private By allAvailableDays = By.xpath("//div[@aria-label='Low Fare Calendar']//button[not(@disabled)]");

    private By nthAvailableDay(int dayIndex) {
        return By.xpath("(//div[@aria-label='Low Fare Calendar']//button[not(@disabled)])[" + dayIndex + "]");
    }

    private By nthAvailableDayNumber(int dayIndex) {
        return By.xpath("(//div[@aria-label='Low Fare Calendar']//button[not(@disabled)])[" + dayIndex + "]/div[1]");
    }

    private By nthAvailableDayPrice(int dayIndex) {
        return By.xpath("(//div[@aria-label='Low Fare Calendar']//button[not(@disabled)])[" + dayIndex + "]/div[2]");
    }

    /**
     * The home page constructor
     */
    public PickDatesPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public void clickConfigMenuButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ConfigMenuButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ConfigMenuButton").click();
    }

    public void setOneWay() {
        seleniumClient.element(PAGE_NAME, "RoundTripButton").click();
    }

    public void setRoundTrip() {
        seleniumClient.element(PAGE_NAME, "OneWayButton").click();
    }

    public void openWhoIsFlyingModalSelection() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "WhoIsFlyingButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "WhoIsFlyingButton").click();
    }

    public int getNumberOfTravelersLabel() {
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "WhoIsFlyingButtonNumberOfTravelers").getText().contains("traveller"));
        return Integer.parseInt(seleniumClient.element(PAGE_NAME, "WhoIsFlyingButtonNumberOfTravelers").getText());
    }

    public String getDepartureFieldAirport() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DepartureField").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "DepartureField").getAttribute("value").equals(""));
        return seleniumClient.element(PAGE_NAME, "DepartureField").getAttribute("value");
    }

    public String getDestinationFieldAirport() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DestinationField").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "DestinationField").getAttribute("value").equals(""));
        return seleniumClient.element(PAGE_NAME, "DestinationField").getAttribute("value");
    }

    public void clickSwitchDepDestAirportButton() {
        seleniumClient.element(PAGE_NAME, "SwitchDepDestButton").click();
    }

    public boolean checkRoundTripButtonIsPresent() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "RoundTripButton").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "RoundTripButton").size() == 1;
    }

    public boolean checkOneWayButtonIsPresent() {
        return seleniumClient.elements(PAGE_NAME, "OneWayButton").size() == 1;
    }

    public boolean checkChooseOutboundIsPresent() {
        return seleniumClient.elements(PAGE_NAME, "ChooseOutbound").size() == 1;
    }

    public boolean checkChooseReturnIsPresent() {
        return seleniumClient.elements(PAGE_NAME, "ChooseReturn").size() == 1;
    }

    public void clickChooseOutbound() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ChooseOutbound").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ChooseOutbound").click();
    }

    public void clickChooseReturn() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ChooseReturn").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ChooseReturn").click();
    }

    public void selectFirstDepartureFlight() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelection").isDisplayed());
        while(seleniumClient.elements(PAGE_NAME, "FirstDepartureFlightSelection").size() == 0) {
            selectRandomAvailableDepartureDay();
        }
        seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelection").click();
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "SelectYourFlightModalHeading").isDisplayed());
        waitForFlightModalSelectionPricesToBeDisplayed();
        readFarePricesDeparture();
    }

    public String getFirstDepartureFlightType() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelectionType").isDisplayed());
        return seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelectionType").getText();
    }

    public void selectFirstReturnFlight() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FirstReturnFlightSelection").isDisplayed());
        while(seleniumClient.elements(PAGE_NAME, "FirstReturnFlightSelection").size() == 0) {
            selectRandomAvailableReturnDay();
        }
        seleniumClient.element(PAGE_NAME, "FirstReturnFlightSelection").click();
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "SelectYourFlightModalHeading").isDisplayed());
        waitForFlightModalSelectionPricesToBeDisplayed();
    }

    public void selectFirstDepartureIndirectFlight() {
        int monthIndex = 0;
        int maxNumberOfMonths = 3;

        waitForDepartureDestinationElementsToLoad();
        while(monthIndex < maxNumberOfMonths) {
            for (int i = 2; i<= seleniumClient.getWebDriver().findElements(allAvailableDays).size(); i++){
                seleniumClient.getWebDriver().findElement(nthAvailableDay(i)).click();
                Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelection").isDisplayed());
                clickMonthViewButton();

                List<String> flightTypeLabels =
                        seleniumClient.elements(PAGE_NAME, "AllDepartureFlightsTypesLabels").getText();

                if (flightTypeLabels.contains("1 connection")) {
                    readAvailableDepartureDayData(i);
                    seleniumClient.element(PAGE_NAME, "FirstIndirectFlightSelection").click();
                    Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "SelectYourFlightModalHeading").isDisplayed());
                    waitForFlightModalSelectionPricesToBeDisplayed();
                    return;
                }
            }
            selectNextMonthWeek();
            monthIndex++;
        }
    }

    public void selectRandomAvailableDepartureDay() {
        browseToNextMonthOrWeekWithAvailableFlights();
        int dayIndex = generateRandomAvailableDayIndex();
        readAvailableDepartureDayData(dayIndex);
        seleniumClient.getWebDriver().findElement(nthAvailableDay(dayIndex)).click();
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelection").isDisplayed());
    }

    public void selectFirstAvailableDepartureDay() {
        browseToNextMonthOrWeekWithAvailableFlights();
        readAvailableDepartureDayData(1);
        seleniumClient.element(PAGE_NAME, "FirstAvailableDay").click();
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelection").isDisplayed());
    }

    public void selectRandomAvailableReturnDay() {
        browseToNextMonthOrWeekWithAvailableFlights();
        int dayIndex = generateRandomAvailableDayIndex();
        readAvailableReturnDayData(dayIndex);
        seleniumClient.getWebDriver().findElement(nthAvailableDay(dayIndex)).click();
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FirstReturnFlightSelection").isDisplayed());
    }

    private void readAvailableDepartureDayData(int dayIndex) {
        clickMonthViewButton();
        departureSelectionDay = Integer.parseInt(seleniumClient.getWebDriver().findElement(nthAvailableDayNumber(dayIndex)).getText());
        departureSelectionPrice = Integer.parseInt(seleniumClient.getWebDriver().findElement(nthAvailableDayPrice(dayIndex)).getText().replace(",", ""));

        Pattern pattern = Pattern.compile("(\\w+)\\s(\\d+)");
        Matcher matcher = pattern.matcher(seleniumClient.element(PAGE_NAME, "MonthYearLabel").getText());
        if (matcher.find()) {
            this.departureSelectionMonth = matcher.group(1);
            this.departureSelectionYear = Integer.parseInt(matcher.group(2));
        }
    }

    private void readAvailableReturnDayData(int dayIndex) {
        clickMonthViewButton();
        returnSelectionDay = Integer.parseInt(seleniumClient.getWebDriver().findElement(nthAvailableDayNumber(dayIndex)).getText());
        returnSelectionPrice = Integer.parseInt(seleniumClient.getWebDriver().findElement(nthAvailableDayPrice(dayIndex)).getText().replace(",", ""));

        Pattern pattern = Pattern.compile("(\\w+)\\s(\\d+)");
        Matcher matcher = pattern.matcher(seleniumClient.element(PAGE_NAME, "MonthYearLabel").getText());
        if (matcher.find()) {
            this.returnSelectionMonth = matcher.group(1);
            this.returnSelectionYear = Integer.parseInt(matcher.group(2));
        }
    }

    private void browseToNextMonthOrWeekWithAvailableFlights() {
        // Browse to a month/week with at least 1 available day
        waitForDepartureDestinationElementsToLoad();
        int safeCount = 0;
        while ((seleniumClient.getWebDriver().findElements(allAvailableDays).size() < 1) && (safeCount < 3)) {
            selectNextMonthWeek();
            safeCount++;
        }
    }

    public void selectNextMonthWeek() {
        seleniumClient.element(PAGE_NAME, "NextMonthWeekButton").click();
    }

    public String getFirstDepartureFlightSelectionDeparture() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelectionDeparture").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelectionDeparture").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelectionDeparture").getText().replace("\n", "");
    }

    public String getFirstDepartureFlightSelectionDestination() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelectionDestination").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelectionDestination").getText().equals(""));
        return seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelectionDestination").getText().replace("\n", "");
    }

    public boolean checkDepartureFlightSelectionPricesAreNotLowerThanDepartureCalendarPrice() {
        List<String> priceLabels = seleniumClient.elements(PAGE_NAME, "AllDepartureFlightsPriceButtons").getText();
        for (int price : extractPrices(priceLabels)) {
            if (price < departureSelectionPrice) {
                return false;
            }
        }
        return true;
    }

    public boolean checkReturnFlightSelectionPricesAreNotLowerThanReturnCalendarPrice() {
        List<String> priceLabels = seleniumClient.elements(PAGE_NAME, "AllReturnFlightsPriceButtons").getText();
        for (int price : extractPrices(priceLabels)) {
            if (price < returnSelectionPrice) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDepartureFlightSelectionPricesAreDisplayedInEUR() {
        List<String> priceLabels = seleniumClient.elements(PAGE_NAME, "AllDepartureFlightsPriceButtons").getText();

        for (String priceLabel : priceLabels) {
            if (!checkFlightSelectionPriceIsDisplayedInEUR(priceLabel.replace("\n", " "))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkReturnFlightSelectionPricesAreDisplayedInEUR() {
        List<String> priceLabels = seleniumClient.elements(PAGE_NAME, "AllReturnFlightsPriceButtons").getText();

        for (String priceLabel : priceLabels) {
            if (!checkFlightSelectionPriceIsDisplayedInEUR(priceLabel.replace("\n", " "))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkDepartureFlightSelectionPricesAreDisplayedInKR() {
        List<String> priceLabels = seleniumClient.elements(PAGE_NAME, "AllDepartureFlightsPriceButtons").getText();
        for (String priceLabel : priceLabels) {
            if (!checkFlightSelectionPriceIsDisplayedInKR(priceLabel.replace("\n", " "))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkReturnFlightSelectionPricesAreDisplayedInKR() {
        List<String> priceLabels = seleniumClient.elements(PAGE_NAME, "AllReturnFlightsPriceButtons").getText();
        for (String priceLabel : priceLabels) {
            if (!checkFlightSelectionPriceIsDisplayedInKR(priceLabel.replace("\n", " "))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkOnlyDirectCheckboxIsPresent() {
        return seleniumClient.elements(PAGE_NAME, "OnlyDirectCheckbox").size() == 1;
    }

    public boolean checkOnlyDirectCheckboxIsSelected() {
        return seleniumClient.element(PAGE_NAME, "OnlyDirectCheckbox").isSelected();
    }

    public boolean checkOnlyDirectCheckboxIsNotSelected() {
        return !checkOnlyDirectCheckboxIsSelected();
    }

    public void selectOnlyDirectFlights() {
        if (!seleniumClient.element(PAGE_NAME, "OnlyDirectCheckbox").isSelected()) {
            seleniumClient.element(PAGE_NAME, "OnlyDirectFlights").click();
        }
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "OnlyDirectCheckbox").isSelected());
        waitForFilterToApply();
    }

    public void unselectOnlyDirectFlights() {
        if (seleniumClient.element(PAGE_NAME, "OnlyDirectCheckbox").isSelected()) {
            seleniumClient.element(PAGE_NAME, "OnlyDirectFlights").click();
        }
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "OnlyDirectCheckbox").isSelected());
        waitForFilterToApply();
    }

    public boolean checkAtLeastOneDepartureFlightIsNotDirect() {
        List<String> intermediateAirports =
                seleniumClient.elements(PAGE_NAME, "AllDepartureFlightsSelectionIntermediateAirports").getText();
        List<String> flightTypeLabels =
                seleniumClient.elements(PAGE_NAME, "AllDepartureFlightsTypesLabels").getText();
        for (String intermediateAirport : intermediateAirports) {
            if (!intermediateAirport.contains("Flight")) {
                return true;
            }
        }
        for (String flightTypeLabel : flightTypeLabels) {
            if (!flightTypeLabel.equals("Direct")) {
                return true;
            }
        }
        return false;
    }

    public boolean checkAllDepartureFlightsAreDirect() {
        return !checkAtLeastOneDepartureFlightIsNotDirect();
    }

    public boolean checkAllDepartureFlightsTypeLabelsAreCorrectlyDisplayed() {
        List<String> flightTypeLabelsTexts =
                seleniumClient.elements(PAGE_NAME, "AllDepartureFlightsTypesLabels").getText();
        for (String flightTypeLabelText : flightTypeLabelsTexts) {
            if (!(flightTypeLabelText.equals("Direct") || (flightTypeLabelText.matches("^\\d+\\sconnection(s)?$")))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAllDepartureIntermediateAirportsAreCorrectlyDisplayed() {
        List<String> intermediateAirportsTexts =
                seleniumClient.elements(PAGE_NAME, "AllDepartureFlightsSelectionIntermediateAirports").getText();

        for (String intermediateAirportText : intermediateAirportsTexts) {
            if (!(intermediateAirportText.matches("^Flight\\s\\w+\\d+$") || intermediateAirportText.matches("^.*\\s\\(\\d+h\\s\\d+m\\)$"))) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAllDepartureFlightsPriceButtonsAreCorrectlyDisplayed() {
        List<String> flightPriceButtonsTexts =
                seleniumClient.elements(PAGE_NAME, "AllDepartureFlightsPriceButtons").getText();
        for (String flightPriceButtonText : flightPriceButtonsTexts) {
            String normalizedFlightPriceButtonText = flightPriceButtonText.replace("\n", " ").replace(",", "");
            if (!normalizedFlightPriceButtonText.matches("^Select from\\s(\\d+,)?\\d+\\skr$")) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAllDepartureFlightsDurationsAreCorrectlyDisplayed() {
        List<String> flightDurationsTexts =
                seleniumClient.elements(PAGE_NAME, "AllDepartureFlightsDurations").getText();
        for (String flightDurationText : flightDurationsTexts) {
            if (!flightDurationText.matches("^\\d+h\\s\\d+m$")) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAllDepartureFlightsDepDestTimesAreCorrectlyDisplayed() {
        List<String> flightDepDestTimesTexts =
                seleniumClient.elements(PAGE_NAME, "AllDepartureFlightsDepDestTimes").getText();
        for (String flightDepDestTimeText : flightDepDestTimesTexts) {
            if (!flightDepDestTimeText.matches("^\\d\\d:\\d\\d$")) {
                return false;
            }
        }
        return true;
    }

    public void closeModalSelection(){
        seleniumClient.elements(PAGE_NAME, "ModalSelectionCloseButton").click();
    }

    public boolean checkSelectYourFlightPopupIsOpen() {
        return seleniumClient.elements(PAGE_NAME, "SelectYourFlightModalHeading").size() == 1;
    }

    public boolean checkDepartureDisplayedPriceIsBasicPlanPrice(){
        int priceFirstFlightSelection =
                extractPrice(seleniumClient.element(PAGE_NAME, "FirstDepartureFlightSelectionPrice").getText());
        selectFirstDepartureFlight();
        int priceModalSelectionBasic =
                extractPrice(seleniumClient.element(PAGE_NAME, "ModalSelectionMiniPrice").getText());
        closeModalSelection();
        return (priceFirstFlightSelection == priceModalSelectionBasic);
    }

    public boolean checkReturnDisplayedPriceIsBasicPlanPrice(){
        int priceFirstFlightSelection =
                extractPrice(seleniumClient.element(PAGE_NAME, "FirstReturnFlightSelectionPrice").getText());
        selectFirstReturnFlight();
        int priceModalSelectionBasic =
                extractPrice(seleniumClient.element(PAGE_NAME, "ModalSelectionMiniPrice").getText());
        closeModalSelection();
        return (priceFirstFlightSelection == priceModalSelectionBasic);
    }

    public boolean checkFlightModalSelectionPricesAreDisplayedInEUR() {
        waitForFlightModalSelectionPricesToBeDisplayed();
        List<String> pricesModalSelection =
                seleniumClient.elements(PAGE_NAME, "ModalSelectionPrices").getText();
        for (String priceModalSelection : pricesModalSelection) {
            if (!checkFlightModalSelectionPriceIsDisplayedInEUR(priceModalSelection)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkFlightModalSelectionPricesAreDisplayedInKR() {
        waitForFlightModalSelectionPricesToBeDisplayed();
        List<String> pricesModalSelection =
                seleniumClient.elements(PAGE_NAME, "ModalSelectionPrices").getText();
        for (String priceModalSelection : pricesModalSelection) {
            if (!checkFlightModalSelectionPriceIsDisplayedInKR(priceModalSelection)) {
                return false;
            }
        }
        return true;
    }

    public void selectMiniFlightFromModal() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ModalSelectionMini").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ModalSelectionMini").click();
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "SelectYourFlightModalHeading").isDisplayed());
    }

    public void selectFleksFlightFromModal() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ModalSelectionFleks").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ModalSelectionFleks").click();
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "SelectYourFlightModalHeading").isDisplayed());
    }

    public void selectSmartFlightFromModal() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ModalSelectionSmart").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ModalSelectionSmart").click();
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "SelectYourFlightModalHeading").isDisplayed());
    }

    private void readFarePricesDeparture() {
        this.modalSelectionMiniPriceDeparture = extractPrice(seleniumClient.element(PAGE_NAME, "ModalSelectionMiniPrice").getText());
        this.modalSelectionSmartPriceDeparture = extractPrice(seleniumClient.element(PAGE_NAME, "ModalSelectionSmartPrice").getText());
        this.modalSelectionFleksPriceDeparture = extractPrice(seleniumClient.element(PAGE_NAME, "ModalSelectionFleksPrice").getText());
    }

    public void openPriceDetails() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "PriceDetails").isDisplayed());
        seleniumClient.element(PAGE_NAME, "PriceDetails").click();
        //Necessary hardcoded wait: modal to load, prices to be displayed, total to be calculated.
        Sleeper.silentSleep(2000);
    }

    private List<Integer> extractPrices(List<String> priceLabels) {
        List<Integer> extractedPrices = new ArrayList<>();
        for (String priceLabel : priceLabels) {
            extractedPrices.add(extractPrice(priceLabel));
        }
        return extractedPrices;
    }

    private Integer extractPrice(String priceLabel) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(priceLabel.replace("\n", "").replace(",", ""));
        if (matcher.find()) {
            return Integer.valueOf(matcher.group());
        }
        return -1;
    }

    private boolean checkFlightSelectionPriceIsDisplayedInKR(String priceLabel) {
        return priceLabel.matches("^Select from\\s(\\d+,)?\\d+\\skr$");
    }

    private boolean checkFlightSelectionPriceIsDisplayedInEUR(String priceLabel) {
        return priceLabel.matches("^Select from\\s€\\s(\\d+,)?\\d+$");
    }

    private boolean checkFlightModalSelectionPriceIsDisplayedInKR(String priceLabel) {
        return priceLabel.matches("^(\\d+,)?\\d+\\skr$");
    }

    private boolean checkFlightModalSelectionPriceIsDisplayedInEUR(String priceLabel) {
        return priceLabel.matches("^€\\s(\\d+,)?\\d+$");
    }

    private void waitForDepartureDestinationElementsToLoad() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DepartureField").isDisplayed());
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "DestinationField").isDisplayed());
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "DepartureField").getAttribute("value").equals(""));
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "DestinationField").getAttribute("value").equals(""));
        Waiter.waitFor(() -> (seleniumClient.getWebDriver().findElements(allAvailableDays).size() > 0));
    }

    private void waitForFlightModalSelectionPricesToBeDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ModalSelectionMiniPrice").isDisplayed());
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ModalSelectionSmartPrice").isDisplayed());
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ModalSelectionFleksPrice").isDisplayed());
    }

    public void clickContinueButton() {
        seleniumClient.element(PAGE_NAME, "ContinueButton").click();
    }

    private void clickMonthViewButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "MonthViewButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "MonthViewButton").click();
    }

    private int generateRandomAvailableDayIndex() {
        int numberOfAvailableDays = seleniumClient.getWebDriver().findElements(allAvailableDays).size();
        return new Random().nextInt(numberOfAvailableDays) + 1;
    }

    private void waitForFilterToApply(){
        Sleeper.silentSleep(1000);
    }
}
