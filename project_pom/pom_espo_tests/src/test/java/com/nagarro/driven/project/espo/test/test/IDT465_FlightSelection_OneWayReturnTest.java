package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.data.TestDataProvider;
import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.CookieSection;
import com.nagarro.driven.project.espo.test.pageobjects.pages.HomePage;
import com.nagarro.driven.project.espo.test.pageobjects.pages.PickDatesPage;
import com.nagarro.driven.project.espo.test.pageobjects.pages.WhoIsFlyingModal;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class IDT465_FlightSelection_OneWayReturnTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    HomePage homePage;
    PickDatesPage pickDatesPage;
    WhoIsFlyingModal whoIsFlyingModal;

    @BeforeMethod
    public void setup() {
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
        homePage = new HomePage(seleniumAbstractDriverProvider.get());
        pickDatesPage = new PickDatesPage(seleniumAbstractDriverProvider.get());
        whoIsFlyingModal = new WhoIsFlyingModal(seleniumAbstractDriverProvider.get());
    }

    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void testChooseOneWayOrReturn(String departureAirportNameAndIATA, String destinationAirportNameAndIATA) throws InterruptedException {
        /**
         * TestSet ITD--465 testing task ITD-24 Web: Choose one-way or return flights
         * Verify correct behaviour of "Round trip"/"One way" button: "Pick departure" and "Pick return"
         *  tabs are both available in "Round trip"; only "Pick departure" is available in "One way".
         * Verify also basic behaviour of "Switch" between airports button.
         */

        cookieSection.acceptAllCookies();
        homePage.updateFromField(departureAirportNameAndIATA);
        homePage.clickDepartureFirstElementSuggestion();
        homePage.setToField(destinationAirportNameAndIATA);
        homePage.clickDestinationFirstElementSuggestion();

        Assert.assertTrue(pickDatesPage.checkRoundTripButtonIsPresent());
        Assert.assertTrue(pickDatesPage.getDepartureFieldAirport().contains(departureAirportNameAndIATA));
        Assert.assertTrue(pickDatesPage.getDestinationFieldAirport().contains(destinationAirportNameAndIATA));

        /* Perform a switch and a switch-back between airports. */
        pickDatesPage.clickSwitchDepDestAirportButton();
        Assert.assertTrue(pickDatesPage.getDepartureFieldAirport().contains(destinationAirportNameAndIATA));
        Assert.assertTrue(pickDatesPage.getDestinationFieldAirport().contains(departureAirportNameAndIATA));
        Assert.assertTrue(pickDatesPage.checkRoundTripButtonIsPresent());
        pickDatesPage.clickSwitchDepDestAirportButton();
        Assert.assertTrue(pickDatesPage.getDepartureFieldAirport().contains(departureAirportNameAndIATA));
        Assert.assertTrue(pickDatesPage.getDestinationFieldAirport().contains(destinationAirportNameAndIATA));
        Assert.assertTrue(pickDatesPage.checkRoundTripButtonIsPresent());

        /* Perform a switch and a switch-back between "Round trip" and "One way". */
        Assert.assertTrue(pickDatesPage.checkChooseOutboundIsPresent());
        Assert.assertTrue(pickDatesPage.checkChooseReturnIsPresent());
        pickDatesPage.setOneWay();
        Assert.assertTrue(pickDatesPage.checkOneWayButtonIsPresent());
        Assert.assertTrue(pickDatesPage.checkChooseOutboundIsPresent());
        Assert.assertFalse(pickDatesPage.checkChooseReturnIsPresent());
        pickDatesPage.setRoundTrip();
        Assert.assertTrue(pickDatesPage.checkRoundTripButtonIsPresent());
        Assert.assertTrue(pickDatesPage.checkChooseOutboundIsPresent());
        Assert.assertTrue(pickDatesPage.checkChooseReturnIsPresent());

        /* Perform a switch and a switch-back between airports. */
        pickDatesPage.clickSwitchDepDestAirportButton();
        Assert.assertTrue(pickDatesPage.getDepartureFieldAirport().contains(destinationAirportNameAndIATA));
        Assert.assertTrue(pickDatesPage.getDestinationFieldAirport().contains(departureAirportNameAndIATA));
        Assert.assertTrue(pickDatesPage.checkRoundTripButtonIsPresent());
        pickDatesPage.clickSwitchDepDestAirportButton();
        Assert.assertTrue(pickDatesPage.getDepartureFieldAirport().contains(departureAirportNameAndIATA));
        Assert.assertTrue(pickDatesPage.getDestinationFieldAirport().contains(destinationAirportNameAndIATA));
        Assert.assertTrue(pickDatesPage.checkRoundTripButtonIsPresent());

        /* Successfully select a day and a flight. */
        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.selectFirstDepartureFlight();
        Assert.assertTrue(pickDatesPage.checkSelectYourFlightPopupIsOpen());
    }
}
