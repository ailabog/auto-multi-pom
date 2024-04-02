package com.nagarro.driven.project.espo.test.test;


import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;


public class ITD1349_SeatReservationTest extends EspoPOMTestBase {

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
    PaymentPage paymentPage;
    ConfirmationPage confirmationPage;

    private final int minChildAge = 2;

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
        paymentPage = new PaymentPage(seleniumAbstractDriverProvider.get());
        confirmationPage = new ConfirmationPage(seleniumAbstractDriverProvider.get());
    }

    @Test
    public void testSeatReservation() throws InterruptedException {
        /**
         * TestSet ITD-1349 testing task ITD-30 "Add seat reservation"
         * Select one adult, one child, both ways flights.
         * Select seats for both the adult and the child, check they are correctly displayed in the summary section.
         * Go back, change departure seat for adult, change return seat for child, check they are correctly displayed in the summary section.
         * Go back, change departure seat for adult, change return seat for child, change again departure seat for adult, check they are correctly displayed in the summary section.
         */

        cookieSection.acceptAllCookies();

        homePage.clickWhoIsFlyingButton();
        whoIsFlyingModal.setNumberOfAdults(1);
        whoIsFlyingModal.setNumberOfChildren(1);
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
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Adult", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.setChildFirstNameWithIndex("Child", 1);
        whoIsFlyingPage.setChildLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setChildSexFemaleWithIndex(1);
        whoIsFlyingPage.setChildBirthdayWithIndex("0101" + (Calendar.getInstance().get(Calendar.YEAR) - 8), 1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.selectFirstDepartureAvailableSeatForTraveler("Adult Lastname");
        pickSeatPage.selectFirstDepartureAvailableSeatForTraveler("Child Lastname");
        pickSeatPage.clickContinueButton();
        pickSeatPage.selectFirstReturnAvailableSeatForTraveler("Adult Lastname");
        pickSeatPage.selectFirstReturnAvailableSeatForTraveler("Child Lastname");
        pickSeatPage.clickContinueButton();

        contactInformationPage.setEmail("emailaddress@example.com");
        contactInformationPage.setPhoneNumber("+40734111222");
        contactInformationPage.clickContinueButton();

        Assert.assertTrue(myJourneyPage.departureSelectedSeatsAreCorrectlyDisplayed(
                pickSeatPage.getTravelersSelectedSeatsDeparture()
        ));
        Assert.assertTrue(myJourneyPage.returnSelectedSeatsAreCorrectlyDisplayed(
                pickSeatPage.getTravelersSelectedSeatsReturn()
        ));
        myJourneyPage.navigateBack();
        contactInformationPage.navigateBack();

        pickSeatPage.selectFirstWithOffsetDepartureAvailableSeatForTraveler("Adult Lastname", 2);
        pickSeatPage.clickContinueButton();
        pickSeatPage.selectFirstWithOffsetReturnAvailableSeatForTraveler("Child Lastname", 2);
        pickSeatPage.clickContinueButton();

        contactInformationPage.clickContinueButton();

        Assert.assertTrue(myJourneyPage.departureSelectedSeatsAreCorrectlyDisplayed(
                pickSeatPage.getTravelersSelectedSeatsDeparture()
        ));
        Assert.assertTrue(myJourneyPage.returnSelectedSeatsAreCorrectlyDisplayed(
                pickSeatPage.getTravelersSelectedSeatsReturn()
        ));
        myJourneyPage.navigateBack();
        contactInformationPage.navigateBack();

        pickSeatPage.selectFirstWithOffsetDepartureAvailableSeatForTraveler("Adult Lastname", 3);
        pickSeatPage.clickContinueButton();
        pickSeatPage.selectFirstWithOffsetReturnAvailableSeatForTraveler("Child Lastname", 3);
        pickSeatPage.clickGoBackButton();
        pickSeatPage.selectFirstWithOffsetDepartureAvailableSeatForTraveler("Adult Lastname", 4);
        pickSeatPage.clickContinueButton();
        pickSeatPage.clickContinueButton();

        contactInformationPage.clickContinueButton();

        Assert.assertTrue(myJourneyPage.departureSelectedSeatsAreCorrectlyDisplayed(
                pickSeatPage.getTravelersSelectedSeatsDeparture()
        ));
        Assert.assertTrue(myJourneyPage.returnSelectedSeatsAreCorrectlyDisplayed(
                pickSeatPage.getTravelersSelectedSeatsReturn()
        ));
    }

    @Test
    public void testSeatReservationIndirectFlights() throws InterruptedException {
        /**
         * Select one adult, one child, one way flight.
         * Select seats for both the adult and the child, check they are correctly displayed in the summary section.
         */

        String destinationAirport = "Trondheim";
        cookieSection.acceptAllCookies();

        homePage.clickWhoIsFlyingButton();
        whoIsFlyingModal.setNumberOfAdults(1);
        whoIsFlyingModal.setNumberOfChildren(1);
        whoIsFlyingModal.clickConfirm();

        homePage.setToField(destinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.setOneWay();
        pickDatesPage.selectFirstDepartureIndirectFlight();
        pickDatesPage.selectFleksFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Adult", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.setChildFirstNameWithIndex("Child", 1);
        whoIsFlyingPage.setChildLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setChildSexFemaleWithIndex(1);
        whoIsFlyingPage.setChildBirthdayWithIndex("0101" + (Calendar.getInstance().get(Calendar.YEAR) - 8), 1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.selectFirstDepartureAvailableSeatForTravelerWithLeg("Adult Lastname", 1);
        pickSeatPage.selectFirstDepartureAvailableSeatForTravelerWithLeg("Child Lastname", 1);
        pickSeatPage.clickContinueButton();
        pickSeatPage.selectFirstDepartureAvailableSeatForTravelerWithLeg("Adult Lastname", 2);
        pickSeatPage.selectFirstDepartureAvailableSeatForTravelerWithLeg("Child Lastname", 2);
        pickSeatPage.clickContinueButton();

        contactInformationPage.setEmail("emailaddress@example.com");
        contactInformationPage.setPhoneNumber("+40734111222");
        contactInformationPage.clickContinueButton();

        Assert.assertTrue(myJourneyPage.departureSelectedSeatsAreCorrectlyDisplayed(
                pickSeatPage.getTravelersSelectedSeatsDeparture()
        ));
    }
}
