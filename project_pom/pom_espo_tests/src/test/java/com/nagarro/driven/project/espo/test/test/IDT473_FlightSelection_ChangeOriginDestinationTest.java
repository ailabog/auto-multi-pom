package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.data.TestDataProvider;
import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.CookieSection;
import com.nagarro.driven.project.espo.test.pageobjects.pages.HomePage;
import com.nagarro.driven.project.espo.test.pageobjects.pages.PickDatesPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class IDT473_FlightSelection_ChangeOriginDestinationTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    HomePage homePage;
    PickDatesPage pickDatesPage;

    @BeforeMethod
    public void setup() {
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
        homePage = new HomePage(seleniumAbstractDriverProvider.get());
        pickDatesPage = new PickDatesPage(seleniumAbstractDriverProvider.get());
    }

    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void testChangeOriginDestination(String airportDestination, String airportNewDestination,
                                            String airportNewDeparture) throws InterruptedException {
        /**
         * TestSet ITD--473 testing task ITD-55 Support booking adjustments while booking.
         * Verify that is possible to change departure and destination flights in the flight searching process.
         */

        cookieSection.acceptAllCookies();
        homePage.setToField(airportDestination);
        homePage.clickDestinationFirstElementSuggestion();
        Assert.assertEquals(pickDatesPage.getNumberOfTravelersLabel(), (1));

        homePage.updateFromField(airportNewDeparture);
        Assert.assertTrue(homePage.getDepartureFirstElementSuggestionName().contains(airportNewDeparture));
        homePage.clickDepartureFirstElementSuggestion();

        homePage.updateToField(airportNewDestination);
        Assert.assertTrue(homePage.getDestinationFirstElementSuggestionName().contains(airportNewDestination));
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.selectRandomAvailableDepartureDay();
        Assert.assertTrue(pickDatesPage.getFirstDepartureFlightSelectionDeparture().contains(airportNewDeparture));
        Assert.assertTrue(pickDatesPage.getFirstDepartureFlightSelectionDestination().contains(airportNewDestination));

        pickDatesPage.clickSwitchDepDestAirportButton();
        pickDatesPage.selectRandomAvailableDepartureDay();
        Assert.assertTrue(pickDatesPage.getFirstDepartureFlightSelectionDeparture().contains(airportNewDestination));
        Assert.assertTrue(pickDatesPage.getFirstDepartureFlightSelectionDestination().contains(airportNewDeparture));
    }
}
