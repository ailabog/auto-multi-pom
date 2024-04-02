package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.data.TestDataProvider;
import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.CookieSection;
import com.nagarro.driven.project.espo.test.pageobjects.pages.HomePage;
import com.nagarro.driven.project.espo.test.pageobjects.pages.PickDatesPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ITD461_FlightSelection_SearchResultsTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    HomePage homePage;
    PickDatesPage pickDatesPage;
    private static final String FLIGHT_DEPARTURE = "OsloOSL";

    @BeforeMethod
    public void setup() {
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
        homePage = new HomePage(seleniumAbstractDriverProvider.get());
        pickDatesPage = new PickDatesPage(seleniumAbstractDriverProvider.get());
    }

    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void testFlightResultsElements(String airportName, String expectedAirportNameAndIATA) throws InterruptedException {
        /**
         * TestSet ITD-461 testing task ITD-26 "See flight search results and select based on available flights"
         * User selects a destination, and picks departure date.
         * Check that following elements are displayed correctly in the list of results: departure and destination airport,
         *  flight types, intermediate airports, price buttons, flights durations, departure times, destination times.
         * Check that is possible to correctly access the first element from the result list.
         */

        cookieSection.acceptAllCookies();
        homePage.setToField(airportName);
        homePage.clickDestinationFirstElementSuggestion();
        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();
        Assert.assertEquals(pickDatesPage.getFirstDepartureFlightSelectionDeparture(), FLIGHT_DEPARTURE);
        Assert.assertEquals(expectedAirportNameAndIATA, pickDatesPage.getFirstDepartureFlightSelectionDestination());
        Assert.assertTrue(pickDatesPage.checkAllDepartureFlightsTypeLabelsAreCorrectlyDisplayed());
        Assert.assertTrue(pickDatesPage.checkAllDepartureIntermediateAirportsAreCorrectlyDisplayed());
        Assert.assertTrue(pickDatesPage.checkAllDepartureFlightsPriceButtonsAreCorrectlyDisplayed());
        Assert.assertTrue(pickDatesPage.checkAllDepartureFlightsDurationsAreCorrectlyDisplayed());
        Assert.assertTrue(pickDatesPage.checkAllDepartureFlightsDepDestTimesAreCorrectlyDisplayed());
        pickDatesPage.selectFirstDepartureFlight();
        Assert.assertTrue(pickDatesPage.checkSelectYourFlightPopupIsOpen());
    }
}
