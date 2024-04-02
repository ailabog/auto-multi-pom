package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Waiter;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PickSeatPage {

    private int travelerIndex = 1;
    private String priceFreeMiddleSeatUpgrade;
    private String priceFrontOfCabinSeatUpgrade;
    private String priceExtraLegRoomSeatUpgrade;
    private String priceAftOfCabinSeatUpgrade;
    private Map<String, String> travelersSelectedSeatsDeparture = new HashMap<>();
    private Map<String, String> travelersSelectedSeatsReturn = new HashMap<>();

    private final Logger log = LoggerFactory.getLogger(PickSeatPage.class);
    private By allSelectableSeats = By.xpath("(//div[contains(@data-qa,'seat-available')])[position()>5]");
    private By firstSelectableSeat = By.xpath("(//div[contains(@data-qa,'seat-available')])[position()>5][1]");

    private By allSelectableFreeMiddleSeats = By.xpath("(//div[contains(@data-qa,'seat-available') and contains(@data-qa,'middle_seat_blocked')])[position()>5]");
    private By firstSelectableFreeMiddleSeat = By.xpath("(//div[contains(@data-qa,'seat-available') and contains(@data-qa,'middle_seat_blocked')])[position()>5][1]");
    private By priceFreeMiddleSeat = By.xpath("//div[contains(text(),'Free middle') and contains(text(),'seat')]/following-sibling::div");

    private By allSelectableFrontOfCabinSeats = By.xpath("(//div[contains(@data-qa,'seat-available') and contains(@data-qa,'cabin_front')])");
    private By firstSelectableFrontOfCabinSeat = By.xpath("(//div[contains(@data-qa,'seat-available') and contains(@data-qa,'cabin_front')])[1]");
    private By priceFrontOfCabinSeat = By.xpath("//div[contains(text(),'Front of') and contains(text(),'cabin')]/following-sibling::div");

    private By allSelectableExtraLegRoomSeats = By.xpath("(//div[contains(@data-qa,'seat-available') and contains(@data-qa,'extra_legroom')])");
    private By firstSelectableExtraLegRoomSeat = By.xpath("(//div[contains(@data-qa,'seat-available') and contains(@data-qa,'extra_legroom')])[1]");
    private By priceExtraLegRoomSeat = By.xpath("//div[contains(text(),'Extra leg') and contains(text(),'room')]/following-sibling::div");

    private By allSelectableAftOfCabinSeats = By.xpath("(//div[contains(@data-qa,'seat-available') and contains(@data-qa,'cabin_back')])");
    private By firstSelectableAftOfCabinSeat = By.xpath("(//div[contains(@data-qa,'seat-available') and contains(@data-qa,'cabin_back')])[1]");
    private By priceAftOfCabinSeat = By.xpath("//div[contains(text(),'Aft of') and contains(text(),'cabin')]/following-sibling::div");

    private By firstSelectableSeatWithIndex(int travelerIndex) {
        return By.xpath("(//div[contains(@data-qa,'seat-available')])[position()>5][" + travelerIndex + "]");
    }

    private By firstSelectableFreeMiddleSeatWithIndex(int travelerIndex) {
        return By.xpath("(//div[contains(@data-qa,'seat-available') and contains(@data-qa,'middle_seat_blocked')])[position()>5][" + travelerIndex + "]");
    }

    private By firstSelectableFrontOfCabinSeatWithIndex(int travelerIndex) {
        return By.xpath("(//div[contains(@data-qa,'seat-available') and contains(@data-qa,'cabin_front')])[" + travelerIndex + "]");
    }

    private By firstSelectableExtraLegRoomSeatWithIndex(int travelerIndex) {
        return By.xpath("(//div[contains(@data-qa,'seat-available') and contains(@data-qa,'extra_legroom')])[" + travelerIndex + "]");
    }

    private By seat(int row, Character column) {
        int columnIndex = 0;
        switch(Character.toUpperCase(column)) {
            case 'A':
                columnIndex = 1;
                break;
            case 'B':
                columnIndex = 2;
                break;
            case 'C':
                columnIndex = 3;
                break;
            case 'D':
                columnIndex = 4;
                break;
            case 'E':
                columnIndex = 5;
                break;
            case 'F':
                columnIndex = 6;
                break;
        }
        return By.xpath("(//div[contains(@data-qa, 'seat-available')])[position()>5][" + (6 * (row-2) + columnIndex) + "]");
    }

    private By travelerButton (String travelerName) {
        return By.xpath("//div[@title='" + travelerName + "']/parent::button");
    }

    private By travelerSelectedSeat (String travelerName) {
        return By.xpath("//div[@title='" + travelerName + "']/parent::button/parent::div/following-sibling::div//div[last()][text()]");
    }

    private By upgradeInfoMessage = By.xpath("//span[contains(text(), 'Upgrade')]/parent::div");

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    private static final String PAGE_NAME = "PickSeatPage";

    /**
     * The home page constructor
     */
    public PickSeatPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
        travelerIndex = 1;
        travelersSelectedSeatsDeparture = new HashMap<>();
        travelersSelectedSeatsReturn = new HashMap<>();
    }

    public void selectSeat(int row, char column) {
        seleniumClient.getWebDriver().findElement(seat(row, column)).click();
    }

    public void selectFirstAvailableSeat() {
        Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(firstSelectableSeatWithIndex(travelerIndex)).isDisplayed());
        seleniumClient.getWebDriver().findElement(firstSelectableSeatWithIndex(travelerIndex)).click();
        travelerIndex++;
    }

    public void selectFirstAvailableFreeMiddleSeat() {
        Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(firstSelectableFreeMiddleSeatWithIndex(travelerIndex)).isDisplayed());
        seleniumClient.getWebDriver().findElement(firstSelectableFreeMiddleSeatWithIndex(travelerIndex)).click();
        travelerIndex++;
    }

    public void selectFirstAvailableFrontOfCabinSeat() {
        Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(firstSelectableFrontOfCabinSeatWithIndex(travelerIndex)).isDisplayed());
        seleniumClient.getWebDriver().findElement(firstSelectableFrontOfCabinSeatWithIndex(travelerIndex)).click();
        travelerIndex++;
    }

    public void selectFirstAvailableExtraLegRoomSeat() {
        Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(firstSelectableExtraLegRoomSeatWithIndex(travelerIndex)).isDisplayed());
        seleniumClient.getWebDriver().findElement(firstSelectableExtraLegRoomSeatWithIndex(travelerIndex)).click();
        travelerIndex++;
    }

    public void selectFirstWithOffsetAvailableSeat(int offset) {
        int indexWithOffset = travelerIndex + offset;
        Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(firstSelectableSeatWithIndex(indexWithOffset)).isDisplayed());
        seleniumClient.getWebDriver().findElement(firstSelectableSeatWithIndex(indexWithOffset)).click();
        travelerIndex++;
    }

    private void selectFirstAvailableSeatForTraveler(String travelerName) {
        waitForSeatLoaderToFinish();
        seleniumClient.getWebDriver().findElement(travelerButton(travelerName)).click();
        selectFirstAvailableSeat();
        seleniumClient.getWebDriver().findElement(travelerButton(travelerName)).click();
    }

    private void selectFirstAvailableFreeMiddleSeatForTraveler(String travelerName) {
        waitForSeatLoaderToFinish();
        seleniumClient.getWebDriver().findElement(travelerButton(travelerName)).click();
        selectFirstAvailableFreeMiddleSeat();
        seleniumClient.getWebDriver().findElement(travelerButton(travelerName)).click();
    }

    private void selectFirstAvailableFrontOfCabinSeatForTraveler(String travelerName) {
        waitForSeatLoaderToFinish();
        seleniumClient.getWebDriver().findElement(travelerButton(travelerName)).click();
        selectFirstAvailableFrontOfCabinSeat();
        seleniumClient.getWebDriver().findElement(travelerButton(travelerName)).click();
    }

    private void selectFirstAvailableExtraLegRoomSeatForTraveler(String travelerName) {
        waitForSeatLoaderToFinish();
        seleniumClient.getWebDriver().findElement(travelerButton(travelerName)).click();
        selectFirstAvailableExtraLegRoomSeat();
        seleniumClient.getWebDriver().findElement(travelerButton(travelerName)).click();
    }

    private void selectFirstWithOffsetAvailableSeatForTraveler(String travelerName, int offset) {
        waitForSeatLoaderToFinish();
        seleniumClient.getWebDriver().findElement(travelerButton(travelerName)).click();
        selectFirstWithOffsetAvailableSeat(offset);
        seleniumClient.getWebDriver().findElement(travelerButton(travelerName)).click();
    }

    private void waitForSeatLoaderToFinish() {
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "SeatLoader").isDisplayed());
    }

    public void readUpgradePrices() {
        waitForSeatLoaderToFinish();
        this.priceFreeMiddleSeatUpgrade = extractPrice(seleniumClient.getWebDriver().findElement(priceFreeMiddleSeat).getText());
        this.priceFrontOfCabinSeatUpgrade = extractPrice(seleniumClient.getWebDriver().findElement(priceFrontOfCabinSeat).getText());
        this.priceExtraLegRoomSeatUpgrade = extractPrice(seleniumClient.getWebDriver().findElement(priceExtraLegRoomSeat).getText());
        this.priceAftOfCabinSeatUpgrade = extractPrice(seleniumClient.getWebDriver().findElement(priceAftOfCabinSeat).getText());
        log.info("Upgrade prices found:");
        log.info("FreeMiddleSeatUpgrade: " + priceFreeMiddleSeatUpgrade);
        log.info("FrontOfCabinSeatUpgrade: " + priceFrontOfCabinSeatUpgrade);
        log.info("ExtraLegRoomSeatUpgrade: " + priceExtraLegRoomSeatUpgrade);
        log.info("AftOfCabinSeatUpgrade: " + priceAftOfCabinSeatUpgrade);
    }

    public boolean checkCorrectUpgradePricesFreeFrontExtraAft(String priceFreeMiddleSeatUpgrade, String priceFrontOfCabinSeatUpgrade,
                                          String priceExtraLegRoomSeatUpgrade, String priceAftOfCabinSeatUpgrade) {
        boolean eval = true;

        if (priceFreeMiddleSeatUpgrade.equalsIgnoreCase("Included")) {
            eval = (eval && this.priceFreeMiddleSeatUpgrade.equalsIgnoreCase("Included"));
        } else {
            eval = (eval && (Integer.parseInt(this.priceFreeMiddleSeatUpgrade) > 0));
        }

        if (priceFrontOfCabinSeatUpgrade.equalsIgnoreCase("Included")) {
            eval = (eval && this.priceFrontOfCabinSeatUpgrade.equalsIgnoreCase("Included"));
        } else {
            eval = (eval && (Integer.parseInt(this.priceFrontOfCabinSeatUpgrade) > 0));
        }

        if (priceExtraLegRoomSeatUpgrade.equalsIgnoreCase("Included")) {
            eval = (eval && this.priceExtraLegRoomSeatUpgrade.equalsIgnoreCase("Included"));
        } else {
            eval = (eval && (Integer.parseInt(this.priceExtraLegRoomSeatUpgrade) > 0));
        }

        if (priceAftOfCabinSeatUpgrade.equalsIgnoreCase("Included")) {
            eval = (eval && this.priceAftOfCabinSeatUpgrade.equalsIgnoreCase("Included"));
        } else {
            eval = (eval && (Integer.parseInt(this.priceAftOfCabinSeatUpgrade) > 0));
        }
        return eval;
    }

    public boolean checkCorrectUpgradeMessageDisplayedMiniFree(PickDatesPage pickDatesPage) {
        if (pickDatesPage.modalSelectionMiniPriceDeparture + Integer.parseInt(this.priceFreeMiddleSeatUpgrade) < pickDatesPage.modalSelectionSmartPriceDeparture) {
            log.info("Should upgrade to Flex notification expected, because Smart does not have Free Middle Seats.");
            Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(upgradeInfoMessage).isDisplayed());
            return seleniumClient.getWebDriver().findElement(upgradeInfoMessage).getText().equals
                    ("Upgrade to Fleks! Get extra luggage, fast track, and a refundable ticket for " +
                            (pickDatesPage.modalSelectionFleksPriceDeparture - pickDatesPage.modalSelectionMiniPriceDeparture - Integer.parseInt(this.priceFreeMiddleSeatUpgrade)) +
                            " kr per adult.");
        }

        if ((pickDatesPage.modalSelectionMiniPriceDeparture + Integer.parseInt(this.priceFreeMiddleSeatUpgrade) >= pickDatesPage.modalSelectionSmartPriceDeparture) &&
                (pickDatesPage.modalSelectionMiniPriceDeparture + Integer.parseInt(this.priceFreeMiddleSeatUpgrade) < pickDatesPage.modalSelectionFleksPriceDeparture)) {
            log.info("Should upgrade to Fleks notification expected");
            Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(upgradeInfoMessage).isDisplayed());
            return seleniumClient.getWebDriver().findElement(upgradeInfoMessage).getText().equals
                    ("Upgrade to Fleks! Get extra luggage, fast track, and a refundable ticket for " +
                            (pickDatesPage.modalSelectionFleksPriceDeparture - pickDatesPage.modalSelectionMiniPriceDeparture - Integer.parseInt(this.priceFreeMiddleSeatUpgrade)) +
                            " kr per adult.");
        }

        if (pickDatesPage.modalSelectionMiniPriceDeparture + Integer.parseInt(this.priceFreeMiddleSeatUpgrade) >= pickDatesPage.modalSelectionFleksPriceDeparture) {
            log.info("Service upgrade notification not expected.");
            Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(upgradeInfoMessage).isDisplayed());
            return (seleniumClient.getWebDriver().findElements(upgradeInfoMessage).size() == 0);
        }
        return false;
    }

    public boolean checkCorrectUpgradeMessageDisplayedMiniFront(PickDatesPage pickDatesPage) {
        if (pickDatesPage.modalSelectionMiniPriceDeparture + Integer.parseInt(this.priceFrontOfCabinSeatUpgrade) < pickDatesPage.modalSelectionSmartPriceDeparture) {
            log.info("Should upgrade to Smart notification expected.");
            Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(upgradeInfoMessage).isDisplayed());
            return seleniumClient.getWebDriver().findElement(upgradeInfoMessage).getText().equals
                    ("Upgrade to Smart! Bring luggage and change up to three days before departure for " +
                            (pickDatesPage.modalSelectionSmartPriceDeparture - pickDatesPage.modalSelectionMiniPriceDeparture - Integer.parseInt(this.priceFrontOfCabinSeatUpgrade)) +
                            " kr per adult.");
        }

        if ((pickDatesPage.modalSelectionMiniPriceDeparture + Integer.parseInt(this.priceFrontOfCabinSeatUpgrade) >= pickDatesPage.modalSelectionSmartPriceDeparture) &&
                (pickDatesPage.modalSelectionMiniPriceDeparture + Integer.parseInt(this.priceFrontOfCabinSeatUpgrade) < pickDatesPage.modalSelectionFleksPriceDeparture)) {
            log.info("Should upgrade to Fleks notification expected");
            Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(upgradeInfoMessage).isDisplayed());
            return seleniumClient.getWebDriver().findElement(upgradeInfoMessage).getText().equals
                    ("Upgrade to Fleks! Get extra luggage, fast track, and a refundable ticket for " +
                            (pickDatesPage.modalSelectionFleksPriceDeparture - pickDatesPage.modalSelectionMiniPriceDeparture - Integer.parseInt(this.priceFreeMiddleSeatUpgrade)) +
                            " kr per adult.");
        }

        if (pickDatesPage.modalSelectionMiniPriceDeparture + Integer.parseInt(this.priceFrontOfCabinSeatUpgrade) >= pickDatesPage.modalSelectionFleksPriceDeparture) {
            log.info("Service upgrade notification not expected.");
            Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(upgradeInfoMessage).isDisplayed());
            return (seleniumClient.getWebDriver().findElements(upgradeInfoMessage).size() == 0);
        }
        return false;
    }

    public boolean checkCorrectUpgradeMessageDisplayedSmartFree(PickDatesPage pickDatesPage) {
        if (pickDatesPage.modalSelectionSmartPriceDeparture + Integer.parseInt(this.priceFreeMiddleSeatUpgrade) < pickDatesPage.modalSelectionFleksPriceDeparture) {
            log.info("Should upgrade to Fleks notification expected");
            Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(upgradeInfoMessage).isDisplayed());
            return seleniumClient.getWebDriver().findElement(upgradeInfoMessage).getText().equals
                    ("Upgrade to Fleks! Get extra luggage, fast track, and a refundable ticket for " +
                            (pickDatesPage.modalSelectionFleksPriceDeparture - pickDatesPage.modalSelectionSmartPriceDeparture - Integer.parseInt(this.priceFreeMiddleSeatUpgrade)) +
                            " kr per adult.");
        }

        if (pickDatesPage.modalSelectionSmartPriceDeparture + Integer.parseInt(this.priceFreeMiddleSeatUpgrade) >= pickDatesPage.modalSelectionFleksPriceDeparture) {
            log.info("Service upgrade notification not expected.");
            Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(upgradeInfoMessage).isDisplayed());
            return (seleniumClient.getWebDriver().findElements(upgradeInfoMessage).size() == 0);
        }
        return false;
    }

    public boolean checkUpgradeMessageIsNotDisplayed() {
        Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(upgradeInfoMessage).isDisplayed());
        return (seleniumClient.getWebDriver().findElements(upgradeInfoMessage).size() == 0);
    }

    public boolean checkUpgradeMessageIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(upgradeInfoMessage).isDisplayed());
        return (seleniumClient.getWebDriver().findElements(upgradeInfoMessage).size() == 1);
    }

    public boolean checkUpgradeToFleksMessageIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.getWebDriver().findElement(upgradeInfoMessage).isDisplayed());
        return (seleniumClient.getWebDriver().findElement(upgradeInfoMessage).getText().contains("Upgrade to Fleks!"));
    }

    public void selectFirstDepartureAvailableSeatForTraveler(String travelerName) {
        selectFirstAvailableSeatForTraveler(travelerName);
        travelersSelectedSeatsDeparture.put(travelerName,
                seleniumClient.getWebDriver().findElement(travelerSelectedSeat(travelerName)).getText());
        log.info("Seats departure:");
        travelersSelectedSeatsDeparture.forEach((key, value) -> log.info(key + ": " + value));
    }

    public void selectFirstDepartureAvailableFreeMiddleSeatForTraveler(String travelerName) {
        selectFirstAvailableFreeMiddleSeatForTraveler(travelerName);
        travelersSelectedSeatsDeparture.put(travelerName,
                seleniumClient.getWebDriver().findElement(travelerSelectedSeat(travelerName)).getText());
        log.info("Seats departure:");
        travelersSelectedSeatsDeparture.forEach((key, value) -> log.info(key + ": " + value));
    }

    public void selectFirstDepartureAvailableFrontOfCabinForTraveler(String travelerName) {
        selectFirstAvailableFrontOfCabinSeatForTraveler(travelerName);
        travelersSelectedSeatsDeparture.put(travelerName,
                seleniumClient.getWebDriver().findElement(travelerSelectedSeat(travelerName)).getText());
        log.info("Seats departure:");
        travelersSelectedSeatsDeparture.forEach((key, value) -> log.info(key + ": " + value));
    }

    public void selectFirstDepartureAvailableExtraLegRoomForTraveler(String travelerName) {
        selectFirstAvailableExtraLegRoomSeatForTraveler(travelerName);
        travelersSelectedSeatsDeparture.put(travelerName,
                seleniumClient.getWebDriver().findElement(travelerSelectedSeat(travelerName)).getText());
        log.info("Seats departure:");
        travelersSelectedSeatsDeparture.forEach((key, value) -> log.info(key + ": " + value));
    }

    public void selectFirstDepartureAvailableSeatForTravelerWithLeg(String travelerName, int leg) {
        selectFirstAvailableSeatForTraveler(travelerName);
        travelersSelectedSeatsDeparture.put(travelerName + " " + leg,
                seleniumClient.getWebDriver().findElement(travelerSelectedSeat(travelerName)).getText());
        log.info("Seats departure:");
        travelersSelectedSeatsDeparture.forEach((key, value) -> log.info(key + ": " + value));
    }

    public void selectFirstDepartureAvailableFreeMiddleSeatForTravelerWithLeg(String travelerName, int leg) {
        selectFirstAvailableFreeMiddleSeatForTraveler(travelerName);
        travelersSelectedSeatsDeparture.put(travelerName + " " + leg,
                seleniumClient.getWebDriver().findElement(travelerSelectedSeat(travelerName)).getText());
        log.info("Seats departure:");
        travelersSelectedSeatsDeparture.forEach((key, value) -> log.info(key + ": " + value));
    }

    public void selectFirstDepartureAvailableFrontOfCabinSeatForTravelerWithLeg(String travelerName, int leg) {
        selectFirstAvailableFrontOfCabinSeatForTraveler(travelerName);
        travelersSelectedSeatsDeparture.put(travelerName + " " + leg,
                seleniumClient.getWebDriver().findElement(travelerSelectedSeat(travelerName)).getText());
        log.info("Seats departure:");
        travelersSelectedSeatsDeparture.forEach((key, value) -> log.info(key + ": " + value));
    }

    public void selectFirstReturnAvailableSeatForTraveler(String travelerName) {
        selectFirstAvailableSeatForTraveler(travelerName);
        travelersSelectedSeatsReturn.put(travelerName,
                seleniumClient.getWebDriver().findElement(travelerSelectedSeat(travelerName)).getText());
        log.info("Seats return:");
        travelersSelectedSeatsReturn.forEach((key, value) -> log.info(key + ": " + value));
    }

    public void selectFirstWithOffsetDepartureAvailableSeatForTraveler(String travelerName, int offset) {
        selectFirstWithOffsetAvailableSeatForTraveler(travelerName, offset);
        travelersSelectedSeatsDeparture.put(travelerName,
                seleniumClient.getWebDriver().findElement(travelerSelectedSeat(travelerName)).getText());
        log.info("Seats departure:");
        travelersSelectedSeatsDeparture.forEach((key, value) -> log.info(key + ": " + value));
    }

    public void selectFirstWithOffsetReturnAvailableSeatForTraveler(String travelerName, int offset) {
        selectFirstWithOffsetAvailableSeatForTraveler(travelerName, offset);
        travelersSelectedSeatsReturn.put(travelerName,
                seleniumClient.getWebDriver().findElement(travelerSelectedSeat(travelerName)).getText());
        log.info("Seats return:");
        travelersSelectedSeatsReturn.forEach((key, value) -> log.info(key + ": " + value));
    }

    public void clickContinueButton() {
        waitForSeatLoaderToFinish();
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ContinueButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ContinueButton").click();
        travelerIndex = 1;
    }

    public void clickSkipButton() {
        waitForSeatLoaderToFinish();
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "SkipButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "SkipButton").click();
        travelerIndex = 1;
    }

    public void clickGoBackButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "GoBackButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "GoBackButton").click();
        travelerIndex = 1;
    }

    public Map<String, String> getTravelersSelectedSeatsDeparture() {
        return travelersSelectedSeatsDeparture;
    }

    public Map<String, String> getTravelersSelectedSeatsReturn() {
        return travelersSelectedSeatsReturn;
    }

    private String extractPrice(String priceLabel) {
        Pattern patternNumber = Pattern.compile("\\d+");
        Matcher matcherNumber = patternNumber.matcher(priceLabel.replace("\n", "").replace(",", ""));

        Pattern patternIncluded = Pattern.compile("Included");
        Matcher matcherIncluded = patternIncluded.matcher(priceLabel.replace("\n", "").replace(",", ""));

        if (matcherNumber.find()) {
            return matcherNumber.group();
        }
        if (matcherIncluded.find()) {
            return matcherIncluded.group();
        }
        return "-1";
    }
}
