package com.nagarro.driven.project.espo.test.test;


import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;


public class ITD2325_IndirectFlightsTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    HomePage homePage;
    ConfigMenuPage configMenuPage;
    PickDatesPage pickDatesPage;
    WhoIsFlyingModal whoIsFlyingModal;
    WhoIsFlyingPage whoIsFlyingPage;
    ContactInformationPage contactInformationPage;
    PickSeatPage pickSeatPage;
    MyJourneyPage myJourneyPage;
    PaymentPage paymentPage;
    ConfirmationPage confirmationPage;
    MyJourneyConfirmationPage myJourneyConfirmationPage;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(seleniumAbstractDriverProvider.get());
        configMenuPage = new ConfigMenuPage(seleniumAbstractDriverProvider.get());
        pickDatesPage = new PickDatesPage(seleniumAbstractDriverProvider.get());
        whoIsFlyingModal = new WhoIsFlyingModal(seleniumAbstractDriverProvider.get());
        whoIsFlyingPage = new WhoIsFlyingPage(seleniumAbstractDriverProvider.get());
        contactInformationPage = new ContactInformationPage(seleniumAbstractDriverProvider.get());
        pickSeatPage = new PickSeatPage(seleniumAbstractDriverProvider.get());
        myJourneyPage = new MyJourneyPage(seleniumAbstractDriverProvider.get());
        paymentPage = new PaymentPage(seleniumAbstractDriverProvider.get());
        confirmationPage = new ConfirmationPage(seleniumAbstractDriverProvider.get());
        myJourneyConfirmationPage = new MyJourneyConfirmationPage(seleniumAbstractDriverProvider.get());
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
        loginPage = new LoginPage(seleniumAbstractDriverProvider.get());
    }

    @Test
    public void testAdtIndirectFlights() throws InterruptedException {
        /**
         * Select one adult, one way indirect flight.
         * https://jira.flyr.com/browse/ITD-2325
         */

        String destinationAirport = "Trondheim";
        cookieSection.acceptAllCookies();

        homePage.setToField(destinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.setOneWay();
        pickDatesPage.selectFirstDepartureIndirectFlight();
        pickDatesPage.selectFleksFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("AdultOne", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.clickSkipButton();
        pickSeatPage.clickSkipButton();

        contactInformationPage.setEmail("emailaddress@example.com");
        contactInformationPage.setPhoneNumber("+40734111222");
        contactInformationPage.clickContinueButton();

        myJourneyPage.clickProceedToPaymentButton();

        paymentPage.setCreditCardNumber("4111 1111 4555 1142");
        paymentPage.setExpiryDateMMYY("0330");
        paymentPage.setSecurityCode("737");
        paymentPage.setNameOnCard("John Doe");
        paymentPage.clickPurchaseConditionsRadioButton();
        paymentPage.clickPayButton();

        confirmationPage.clickGoToJourneyButton();
        myJourneyConfirmationPage.clickGoToPassengersLink();
    }

    @Test
    public void testAddIndirectFlights() throws InterruptedException {
        /**
         * Select one adult, one way indirect flight.
         * https://jira.flyr.com/browse/ITD-2325
         */

        String destinationAirport = "Trondheim";
        cookieSection.acceptAllCookies();

        homePage.setToField(destinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.setOneWay();
        pickDatesPage.selectFirstDepartureIndirectFlight();
        pickDatesPage.selectFleksFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("AdultOne", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.clickSkipButton();
        pickSeatPage.clickSkipButton();

        contactInformationPage.setEmail("emailaddress@example.com");
        contactInformationPage.setPhoneNumber("+40734111222");
        contactInformationPage.clickContinueButton();

        myJourneyPage.clickProceedToPaymentButton();

        paymentPage.setCreditCardNumber("4111 1111 4555 1142");
        paymentPage.setExpiryDateMMYY("0330");
        paymentPage.setSecurityCode("737");
        paymentPage.setNameOnCard("John Doe");
        paymentPage.clickPurchaseConditionsRadioButton();
        paymentPage.clickPayButton();

        confirmationPage.clickGoToJourneyButton();
        myJourneyConfirmationPage.clickGoToPassengersLink();
    }

    @Test
    public void testAdInfant() throws InterruptedException {
        /**
         * End to end test, shortest path, one way, Mini flight plan.
         * Adult + infant
         * https://jira.flyr.com/browse/ITD-2325
         */

        cookieSection.acceptAllCookies();

        homePage.clickWhoIsFlyingButton();
        whoIsFlyingModal.setNumberOfAdults(1);
        whoIsFlyingModal.setNumberOfInfants(1);
        whoIsFlyingModal.clickConfirm();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.setOneWay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectMiniFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Adult", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.setInfantFirstNameWithIndex("Infant", 1);
        whoIsFlyingPage.setInfantLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setInfantSexFemaleWithIndex(1);
        whoIsFlyingPage.setInfantBirthdayWithIndex("0101" + (Calendar.getInstance().get(Calendar.YEAR) - 1), 1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.clickSkipButton();

        contactInformationPage.setEmail("emailaddress@example.com");
        contactInformationPage.setPhoneNumber("+40734111222");
        contactInformationPage.clickContinueButton();

        myJourneyPage.clickProceedToPaymentButton();

        paymentPage.setCreditCardNumber("4111 1111 4555 1142");
        paymentPage.setExpiryDateMMYY("0330");
        paymentPage.setSecurityCode("737");
        paymentPage.setNameOnCard("John Doe");
        paymentPage.clickPurchaseConditionsRadioButton();
        paymentPage.clickPayButton();

        confirmationPage.clickGoToJourneyButton();
        myJourneyConfirmationPage.clickGoToPassengersLink();
    }
}
