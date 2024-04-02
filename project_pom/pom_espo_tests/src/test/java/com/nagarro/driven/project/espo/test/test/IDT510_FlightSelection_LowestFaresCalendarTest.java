package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.data.TestDataProvider;
import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.CookieSection;
import com.nagarro.driven.project.espo.test.pageobjects.pages.HomePage;
import com.nagarro.driven.project.espo.test.pageobjects.pages.PickDatesPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class IDT510_FlightSelection_LowestFaresCalendarTest extends EspoPOMTestBase {

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
    public void testCalendarDisplaysLowestFares(String airportName) throws InterruptedException {
        /**
         * TestSet ITD--510 testing task ITD-22 View calendar displaying lowest fares per month
         * Verify that calendar price is the lowest flight price.
         * Verify that displayed price is flight selection is the price corresponding to the Basic plan.
         * Check for both departure and return.
         */
        cookieSection.acceptAllCookies();
        homePage.setToField(airportName);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.clickChooseOutbound();
        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();
        Assert.assertTrue(pickDatesPage.checkDepartureFlightSelectionPricesAreNotLowerThanDepartureCalendarPrice());
        Assert.assertTrue(pickDatesPage.checkDepartureDisplayedPriceIsBasicPlanPrice());

        pickDatesPage.clickChooseReturn();
        pickDatesPage.selectRandomAvailableReturnDay();
        Assert.assertTrue(pickDatesPage.checkReturnFlightSelectionPricesAreNotLowerThanReturnCalendarPrice());
        Assert.assertTrue(pickDatesPage.checkReturnDisplayedPriceIsBasicPlanPrice());
    }
}
