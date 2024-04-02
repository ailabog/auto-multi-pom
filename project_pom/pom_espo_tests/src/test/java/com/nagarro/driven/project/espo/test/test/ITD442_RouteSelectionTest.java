package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ITD442_RouteSelectionTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    HomePage homePage;
    PickDatesPage pickDatesPage;
    WhoIsFlyingModal whoIsFlyingModal;
    ConfigMenuPage configMenuPage;

    @BeforeMethod
    public void setup() {
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
        homePage = new HomePage(seleniumAbstractDriverProvider.get());
        pickDatesPage = new PickDatesPage(seleniumAbstractDriverProvider.get());
        whoIsFlyingModal = new WhoIsFlyingModal(seleniumAbstractDriverProvider.get());
        configMenuPage = new ConfigMenuPage(seleniumAbstractDriverProvider.get());
    }

    @Test
    public void testAirportSuggestions() throws InterruptedException {
        /**
         * TestSet ITD-442 testing task ITD-19 Input origin/destination and start flight search
         * Check alternatively that IATA code, first digits and full airport name trigger the correct airport suggestion.
         * Repeat for both Departure and Destination fields.
         * Check switch airports functionality in the PickDates page.
         */

        cookieSection.acceptAllCookies();

        homePage.updateFromField("TRD");
        Assert.assertEquals(homePage.getDepartureFirstElementSuggestionName(), "Trondheim - Trondheim Airport Værnes");
        Assert.assertEquals(homePage.getDepartureFirstElementSuggestionIATA(), "TRD");
        homePage.updateFromField("Trondheim");
        Assert.assertEquals(homePage.getDepartureFirstElementSuggestionName(), "Trondheim - Trondheim Airport Værnes");
        Assert.assertEquals(homePage.getDepartureFirstElementSuggestionIATA(), "TRD");
        homePage.updateFromField("Tron");
        Assert.assertEquals(homePage.getDepartureFirstElementSuggestionName(), "Trondheim - Trondheim Airport Værnes");
        Assert.assertEquals(homePage.getDepartureFirstElementSuggestionIATA(), "TRD");

        homePage.clickDepartureFirstElementSuggestion();

        homePage.setToField("BGO");
        Assert.assertEquals(homePage.getDestinationFirstElementSuggestionName(), "Bergen - Bergen Airport Flesland");
        Assert.assertEquals(homePage.getDestinationFirstElementSuggestionIATA(), "BGO");
        homePage.updateToField("Berg");
        Assert.assertEquals(homePage.getDestinationFirstElementSuggestionName(), "Bergen - Bergen Airport Flesland");
        Assert.assertEquals(homePage.getDestinationFirstElementSuggestionIATA(), "BGO");
        homePage.updateToField("Bergen");
        Assert.assertEquals(homePage.getDestinationFirstElementSuggestionName(), "Bergen - Bergen Airport Flesland");
        Assert.assertEquals(homePage.getDestinationFirstElementSuggestionIATA(), "BGO");

        homePage.clickDestinationFirstElementSuggestion();

        Assert.assertEquals(pickDatesPage.getDepartureFieldAirport(), "Trondheim (TRD)");
        Assert.assertEquals(pickDatesPage.getDestinationFieldAirport(), "Bergen (BGO)");

        pickDatesPage.clickSwitchDepDestAirportButton();

        Assert.assertEquals(pickDatesPage.getDepartureFieldAirport(), "Bergen (BGO)");
        Assert.assertEquals(pickDatesPage.getDestinationFieldAirport(), "Trondheim (TRD)");
    }

    @Test
    public void testAirportSuggestionsValidateConnections() throws InterruptedException {
        /**
         * TestSet ITD-442 testing task ITD-19 Input origin/destination and start flight search
         * Check presence of destination airports for a specific originating airport.
         * Because it is a matter of configuration, test is not checking suggestion list itself,
         * just that it is displayed and there are more than one destination suggestions displayed.
         */

        cookieSection.acceptAllCookies();

        homePage.updateFromField("TRD");
        homePage.clickDepartureFirstElementSuggestion();

        homePage.clickToField();
        Assert.assertTrue(homePage.getDestinationElementSuggestionNames().size() > 2);
    }
}
