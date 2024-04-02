package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.data.TestDataProvider;
import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.ConfigMenuPage;
import com.nagarro.driven.project.espo.test.pageobjects.pages.CookieSection;
import com.nagarro.driven.project.espo.test.pageobjects.pages.HomePage;
import com.nagarro.driven.project.espo.test.pageobjects.pages.PickDatesPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ITD459_FlightSelection_DirectFlightsSelectionTest extends EspoPOMTestBase {

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

    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void testDirectFlightsSelection(String departureAirportName, String destinationAirportName) throws InterruptedException {
        /**
         * TestSet ITD-459 testing task ITD-25 "View and filter direct and/or connecting flights"
         * User selects a destination, and picks departure date.
         * "Only direct" flight filter checkbox should be visible if at least one flight is not direct, and vice-versa.
         * "Only direct" flight filter should filter out the indirect flights when checked, and back in when unchecked.
         */

        cookieSection.acceptAllCookies();
        homePage.updateFromField(departureAirportName);
        homePage.clickDepartureFirstElementSuggestion();
        homePage.setToField(destinationAirportName);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();
        if (pickDatesPage.checkOnlyDirectCheckboxIsPresent()){
            Assert.assertTrue(pickDatesPage.checkOnlyDirectCheckboxIsNotSelected());
            Assert.assertTrue(pickDatesPage.checkAtLeastOneDepartureFlightIsNotDirect());
            pickDatesPage.selectOnlyDirectFlights();
            Assert.assertTrue(pickDatesPage.checkAllDepartureFlightsAreDirect());
            pickDatesPage.unselectOnlyDirectFlights();
            Assert.assertTrue(pickDatesPage.checkAtLeastOneDepartureFlightIsNotDirect());
        }
        if (pickDatesPage.checkAtLeastOneDepartureFlightIsNotDirect()){
            Assert.assertTrue(pickDatesPage.checkOnlyDirectCheckboxIsPresent());
        }
    }
 }
