package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.ConfigMenuPage;
import com.nagarro.driven.project.espo.test.pageobjects.pages.CookieSection;
import com.nagarro.driven.project.espo.test.pageobjects.pages.HomePage;
import com.nagarro.driven.project.espo.test.pageobjects.pages.PickDatesPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class IDT618_FlightSelection_CurrenciesTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    HomePage homePage;
    PickDatesPage pickDatesPage;
    ConfigMenuPage configMenuPage;

    @BeforeMethod
    public void setup() {
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
        homePage = new HomePage(seleniumAbstractDriverProvider.get());
        pickDatesPage = new PickDatesPage(seleniumAbstractDriverProvider.get());
        configMenuPage = new ConfigMenuPage(seleniumAbstractDriverProvider.get());
    }

    @Test()
    public void testCurrencyNOK() throws InterruptedException {
        /**
         * TestSet ITD-618 testing task ITD-75 Support NOK and EUR currencies
         * Verify correct default currency (NOK)
         * Verify that if currency is NOK, prices in Flight Selection and Flight Selection Modal are correctly displayed in NOK.
         */

        cookieSection.acceptAllCookies();
        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();

        Assert.assertTrue(pickDatesPage.checkDepartureFlightSelectionPricesAreDisplayedInKR());
        pickDatesPage.selectFirstDepartureFlight();
        Assert.assertTrue(pickDatesPage.checkFlightModalSelectionPricesAreDisplayedInKR());
        pickDatesPage.closeModalSelection();

        pickDatesPage.clickChooseReturn();
        pickDatesPage.selectRandomAvailableDepartureDay();

        Assert.assertTrue(pickDatesPage.checkReturnFlightSelectionPricesAreDisplayedInKR());
        pickDatesPage.selectFirstReturnFlight();
        Assert.assertTrue(pickDatesPage.checkFlightModalSelectionPricesAreDisplayedInKR());
    }

    @Test()
    public void testCurrencyEUR() throws InterruptedException {
        /**
         * TestSet ITD-618 testing task ITD-75 Support NOK and EUR currencies
         * Verify correct default currency (NOK)
         * Verify correct currency update currency button elements.
         * Verify that if currency is EUR, prices in Flight Selection and Flight Selection Modal are correctly displayed in EUR.
         */

        cookieSection.acceptAllCookies();

        homePage.clickConfigMenuButton();
        Assert.assertTrue(configMenuPage.checkIsNotSelectedCurrencyEUR());
        Assert.assertTrue(configMenuPage.checkIsSelectedCurrencyNOK());
        configMenuPage.selectCurrencyEUR();
        Assert.assertTrue(configMenuPage.checkIsSelectedCurrencyEUR());
        Assert.assertTrue(configMenuPage.checkIsNotSelectedCurrencyNOK());
        configMenuPage.exitConfigMenuPage();

        homePage.clickConfigMenuButton();
        Assert.assertTrue(configMenuPage.checkIsSelectedCurrencyEUR());
        Assert.assertTrue(configMenuPage.checkIsNotSelectedCurrencyNOK());
        configMenuPage.selectCurrencyNOK();
        Assert.assertTrue(configMenuPage.checkIsNotSelectedCurrencyEUR());
        Assert.assertTrue(configMenuPage.checkIsSelectedCurrencyNOK());
        configMenuPage.exitConfigMenuPage();

        homePage.clickConfigMenuButton();
        Assert.assertTrue(configMenuPage.checkIsNotSelectedCurrencyEUR());
        Assert.assertTrue(configMenuPage.checkIsSelectedCurrencyNOK());
        configMenuPage.selectCurrencyEUR();
        Assert.assertTrue(configMenuPage.checkIsSelectedCurrencyEUR());
        Assert.assertTrue(configMenuPage.checkIsNotSelectedCurrencyNOK());
        configMenuPage.exitConfigMenuPage();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();

        Assert.assertTrue(pickDatesPage.checkDepartureFlightSelectionPricesAreDisplayedInEUR());
        pickDatesPage.selectFirstDepartureFlight();
        Assert.assertTrue(pickDatesPage.checkFlightModalSelectionPricesAreDisplayedInEUR());
        pickDatesPage.closeModalSelection();

        pickDatesPage.clickChooseReturn();
        pickDatesPage.selectRandomAvailableReturnDay();

        Assert.assertTrue(pickDatesPage.checkDepartureFlightSelectionPricesAreDisplayedInEUR());
        pickDatesPage.selectFirstReturnFlight();
        Assert.assertTrue(pickDatesPage.checkFlightModalSelectionPricesAreDisplayedInEUR());
    }
}
