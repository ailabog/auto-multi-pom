package com.nagarro.driven.project.espo.test.test;


import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ITD2163_SeatReservation_MiniSmartFleksTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    HomePage homePage;
    PickDatesPage pickDatesPage;
    WhoIsFlyingModal whoIsFlyingModal;
    WhoIsFlyingPage whoIsFlyingPage;
    ContactInformationPage contactInformationPage;
    PickSeatPage pickSeatPage;
    MyJourneyPage myJourneyPage;
    PriceDetailsModal priceDetailsModal;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
        homePage = new HomePage(seleniumAbstractDriverProvider.get());
        pickDatesPage = new PickDatesPage(seleniumAbstractDriverProvider.get());
        whoIsFlyingModal = new WhoIsFlyingModal(seleniumAbstractDriverProvider.get());
        whoIsFlyingPage = new WhoIsFlyingPage(seleniumAbstractDriverProvider.get());
        contactInformationPage = new ContactInformationPage(seleniumAbstractDriverProvider.get());
        pickSeatPage = new PickSeatPage(seleniumAbstractDriverProvider.get());
        myJourneyPage = new MyJourneyPage(seleniumAbstractDriverProvider.get());
        priceDetailsModal = new PriceDetailsModal(seleniumAbstractDriverProvider.get());
        loginPage = new LoginPage(seleniumAbstractDriverProvider.get());

    }

    @Test
    public void testMiniFree() throws InterruptedException {
        /**
         * TestSet ITD-2163 testing task ITD-1952 "Convert to higher fare brands"
         * Check correct notification message in case of Mini service and selection of a "Free middle seat" seat
         *
         */

        cookieSection.acceptAllCookies();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.setOneWay();
        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectMiniFlightFromModal();

        pickDatesPage.clickContinueButton();
        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Adult", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.readUpgradePrices();
        Assert.assertTrue(pickSeatPage.checkCorrectUpgradePricesFreeFrontExtraAft("Not included", "Not included", "Not included", "Not included"));
        pickSeatPage.selectFirstDepartureAvailableFreeMiddleSeatForTraveler("Adult Lastname");

        Assert.assertTrue(pickSeatPage.checkCorrectUpgradeMessageDisplayedMiniFree(pickDatesPage));
    }

    @Test
    public void testMiniFront() throws InterruptedException {
        /**
         * TestSet ITD-2163 testing task ITD-1952 "Convert to higher fare brands"
         * Check correct notification message in case of Mini service and selection of a "Front of cabin" seat
         *
         */

        cookieSection.acceptAllCookies();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.setOneWay();
        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectMiniFlightFromModal();

        pickDatesPage.clickContinueButton();
        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Adult", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.readUpgradePrices();
        Assert.assertTrue(pickSeatPage.checkCorrectUpgradePricesFreeFrontExtraAft("Not included", "Not included", "Not included", "Not included"));
        pickSeatPage.selectFirstDepartureAvailableFrontOfCabinForTraveler("Adult Lastname");

        Assert.assertTrue(pickSeatPage.checkCorrectUpgradeMessageDisplayedMiniFront(pickDatesPage));
    }

    @Test
    public void testSmartFree() throws InterruptedException {
        /**
         * TestSet ITD-2163 testing task ITD-1952 "Convert to higher fare brands"
         * Check correct notification message in case of Smart service and selection of a "Free middle seat" seat
         *
         */

        cookieSection.acceptAllCookies();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.setOneWay();
        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectSmartFlightFromModal();

        pickDatesPage.clickContinueButton();
        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Adult", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.readUpgradePrices();
        Assert.assertTrue(pickSeatPage.checkCorrectUpgradePricesFreeFrontExtraAft("Not included", "Included", "Included", "Included"));
        pickSeatPage.selectFirstDepartureAvailableFreeMiddleSeatForTraveler("Adult Lastname");

        Assert.assertTrue(pickSeatPage.checkCorrectUpgradeMessageDisplayedSmartFree(pickDatesPage));
    }

    @Test
    public void testSmartExtra() throws InterruptedException {
        /**
         * TestSet ITD-2163 testing task ITD-1952 "Convert to higher fare brands"
         * Check no notification message in case of Smart service and selection of a "Extra leg room" seat
         *
         */

        cookieSection.acceptAllCookies();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.setOneWay();
        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectSmartFlightFromModal();

        pickDatesPage.clickContinueButton();
        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Adult", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.readUpgradePrices();
        Assert.assertTrue(pickSeatPage.checkCorrectUpgradePricesFreeFrontExtraAft("Not included", "Included", "Included", "Included"));
        pickSeatPage.selectFirstDepartureAvailableExtraLegRoomForTraveler("Adult Lastname");

        Assert.assertTrue(pickSeatPage.checkUpgradeMessageIsNotDisplayed());
    }

    @Test
    public void testFleks() throws InterruptedException {
        /**
         * TestSet ITD-2163 testing task ITD-1952 "Convert to higher fare brands"
         * Check no notification message in case of Fleks service and selection of a "Free middle seat" seat
         *
         */

        cookieSection.acceptAllCookies();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.setOneWay();
        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectFleksFlightFromModal();

        pickDatesPage.clickContinueButton();
        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Adult", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.readUpgradePrices();
        Assert.assertTrue(pickSeatPage.checkCorrectUpgradePricesFreeFrontExtraAft("Included", "Included", "Included", "Included"));
        pickSeatPage.selectFirstDepartureAvailableFreeMiddleSeatForTraveler("Adult Lastname");

        Assert.assertTrue(pickSeatPage.checkUpgradeMessageIsNotDisplayed());
    }

    @Test
    public void testConnectingSuggestionDisplayed() throws InterruptedException {
        /**
         * TestSet ITD-2163 testing task ITD-1952 "Convert to higher fare brands"
         * Indirect flight, 1 connection
         * Check notification message is present in case of Mini service
         * and selection of a "Free middle seat" seat for leg 1 and "Free middle seat" seat for leg 2
         *
         */
        String destinationAirport = "Bergen";
        cookieSection.acceptAllCookies();

        homePage.setToField(destinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.setOneWay();
        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectFirstDepartureIndirectFlight();
        pickDatesPage.selectMiniFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Adult", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.readUpgradePrices();
        Assert.assertTrue(pickSeatPage.checkCorrectUpgradePricesFreeFrontExtraAft("Not included", "Not included", "Not included", "Not included"));
        pickSeatPage.selectFirstDepartureAvailableFreeMiddleSeatForTravelerWithLeg("Adult Lastname", 1);
        Assert.assertTrue(pickSeatPage.checkUpgradeMessageIsNotDisplayed());
        pickSeatPage.clickContinueButton();
        Assert.assertTrue(pickSeatPage.checkCorrectUpgradePricesFreeFrontExtraAft("Not included", "Not included", "Not included", "Not included"));
        pickSeatPage.selectFirstDepartureAvailableFreeMiddleSeatForTravelerWithLeg("Adult Lastname", 2);
        Assert.assertTrue(pickSeatPage.checkUpgradeToFleksMessageIsDisplayed());
    }

    @Test
    public void testConnectingSuggestionNotDisplayed() throws InterruptedException {
        /**
         * TestSet ITD-2163 testing task ITD-1952 "Convert to higher fare brands"
         * Indirect flight, 1 connection
         * Check no notification message is present in case of Mini service
         * and selection of a "Free middle seat" seat for leg 1 and "Front of cabin" seat for leg 2
         *
         */
        String destinationAirport = "Bergen";
        cookieSection.acceptAllCookies();

        homePage.setToField(destinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.setOneWay();
        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectFirstDepartureIndirectFlight();
        pickDatesPage.selectMiniFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Adult", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.readUpgradePrices();
        Assert.assertTrue(pickSeatPage.checkCorrectUpgradePricesFreeFrontExtraAft("Not included", "Not included", "Not included", "Not included"));
        pickSeatPage.selectFirstDepartureAvailableFreeMiddleSeatForTravelerWithLeg("Adult Lastname", 1);
        Assert.assertTrue(pickSeatPage.checkUpgradeMessageIsNotDisplayed());
        pickSeatPage.clickContinueButton();
        Assert.assertTrue(pickSeatPage.checkCorrectUpgradePricesFreeFrontExtraAft("Not included", "Not included", "Not included", "Not included"));
        pickSeatPage.selectFirstDepartureAvailableFrontOfCabinSeatForTravelerWithLeg("Adult Lastname", 2);
        Assert.assertTrue(pickSeatPage.checkUpgradeMessageIsNotDisplayed());
    }
}
