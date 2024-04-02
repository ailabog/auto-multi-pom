package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;

public class ITD475_PriceDetailsModalTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    HomePage homePage;
    PickDatesPage pickDatesPage;
    WhoIsFlyingModal whoIsFlyingModal;
    WhoIsFlyingPage whoIsFlyingPage;
    PriceDetailsModal priceDetailsModal;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
        homePage = new HomePage(seleniumAbstractDriverProvider.get());
        pickDatesPage = new PickDatesPage(seleniumAbstractDriverProvider.get());
        whoIsFlyingModal = new WhoIsFlyingModal(seleniumAbstractDriverProvider.get());
        whoIsFlyingPage = new WhoIsFlyingPage(seleniumAbstractDriverProvider.get());
        priceDetailsModal = new PriceDetailsModal(seleniumAbstractDriverProvider.get());
        loginPage = new LoginPage(seleniumAbstractDriverProvider.get());
    }

    @Test
    public void testPriceDetailsModal() throws InterruptedException {
        /**
         * TestSet ITD-475 testing task ITD-57 "Show total price for selected flights and travellers"
         * Select 2 Adults, 3 Children, 1 Infant, both way flight
         * Open the price details modal:
         *  - Check for correctly displayed airports for both departure and return
         *  - Check for correctly displayed departure and arrival dates and times
         *  - Check for correctly displayed travellers with prices
         *  - Check for correctly displayed total
         */

        cookieSection.acceptAllCookies();

        homePage.clickWhoIsFlyingButton();
        whoIsFlyingModal.setNumberOfAdults(2);
        whoIsFlyingModal.setNumberOfChildren(3);
        whoIsFlyingModal.setNumberOfInfants(1);
        whoIsFlyingModal.clickConfirm();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.selectNextMonthWeek();

        pickDatesPage.clickChooseOutbound();
        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectFleksFlightFromModal();

        pickDatesPage.clickChooseReturn();
        pickDatesPage.selectRandomAvailableReturnDay();
        pickDatesPage.selectFirstReturnFlight();
        pickDatesPage.selectSmartFlightFromModal();
        pickDatesPage.openPriceDetails();

        Assert.assertEquals(priceDetailsModal.getDepartureAirports(),
                defaultDepartureAirport + " - " + defaultDestinationAirport);
        Assert.assertEquals(priceDetailsModal.getReturnAirports(),
                defaultDestinationAirport + " - " + defaultDepartureAirport);

        Assert.assertTrue(priceDetailsModal.checkDepartureDateTimeDisplayedCorrectly(
                pickDatesPage.departureSelectionDay, pickDatesPage.departureSelectionMonth
        ));
        Assert.assertTrue(priceDetailsModal.checkReturnDateTimeDisplayedCorrectly(
                pickDatesPage.returnSelectionDay, pickDatesPage.returnSelectionMonth
        ));

        Assert.assertTrue(priceDetailsModal.checkDeparturePricesDisplayedCorrectly(new ArrayList<>() {{
            add("2 x Adult (Fleks)");
            add("3 x Child (Fleks)");
            add("1 x Infant (Fleks)");
        }}));

        Assert.assertTrue(priceDetailsModal.checkReturnPricesDisplayedCorrectly(new ArrayList<>() {{
            add("2 x Adult (Smart)");
            add("3 x Child (Smart)");
            add("1 x Infant (Smart)");
        }}));

        Assert.assertTrue(priceDetailsModal.checkTotalDisplayedCorrectly());

        priceDetailsModal.closePriceDetailsModal();
        pickDatesPage.clickContinueButton();
    }
}
