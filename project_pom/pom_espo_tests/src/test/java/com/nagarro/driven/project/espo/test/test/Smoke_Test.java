package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Calendar;


/**
 * All attributes are labeled data-qa  and then with a value as an identificator
 * Loading: loading
 * Segment time info: flight-time-info
 * Segment airport info: flight-airport-info
 * Seat: seat-unavailable or seat-available  depending on status
 *
 * Example:
 * Loading <div data-qa="loading">
 * Seat: <div data-qa="seat-unavailable" />
 */


public class Smoke_Test extends EspoPOMTestBase {

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
    public void testSmoke1() throws InterruptedException {
        /**
         * End to end test, shortest path, one way, Mini flight plan.
         */

        cookieSection.acceptAllCookies();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.setOneWay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectMiniFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Silviu", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Stanciu", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
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

    @Test
    public void testSmoke2() throws InterruptedException {
        /**
         * End to end test, shortest path, one way, Fleks flight plan, pay with Visa NOK
         */

        cookieSection.acceptAllCookies();

        homePage.clickWhoIsFlyingButton();
        whoIsFlyingModal.setNumberOfAdults(1);
        whoIsFlyingModal.setNumberOfChildren(1);
        whoIsFlyingModal.clickConfirm();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.setOneWay();
        pickDatesPage.selectFirstDepartureFlight();
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

        pickSeatPage.selectFirstDepartureAvailableSeatForTraveler("Adult Lastname");
        pickSeatPage.selectFirstDepartureAvailableSeatForTraveler("Child Lastname");

        pickSeatPage.clickContinueButton();

        contactInformationPage.setEmail("emailaddress@example.com");
        contactInformationPage.setPhoneNumber("+40734111222");
        contactInformationPage.clickContinueButton();

        Assert.assertTrue(myJourneyPage.departureSelectedSeatsAreCorrectlyDisplayed(
                pickSeatPage.getTravelersSelectedSeatsDeparture()));
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
    public void testSmoke3() throws InterruptedException {
        /**
         * End to end test, shortest path, one way, Smart flight plan, pay with Mastercard EUR
         */

        cookieSection.acceptAllCookies();

        homePage.clickConfigMenuButton();
        configMenuPage.selectCurrencyEUR();
        Assert.assertTrue(configMenuPage.checkIsSelectedCurrencyEUR());
        Assert.assertTrue(configMenuPage.checkIsNotSelectedCurrencyNOK());
        configMenuPage.exitConfigMenuPage();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.setOneWay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectSmartFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Adult", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("Lastname", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.selectFirstDepartureAvailableSeatForTraveler("Adult Lastname");
        pickSeatPage.clickContinueButton();

        contactInformationPage.setEmail("emailaddress@example.com");
        contactInformationPage.setPhoneNumber("+40734111222");
        contactInformationPage.clickContinueButton();

        Assert.assertTrue(myJourneyPage.departureSelectedSeatsAreCorrectlyDisplayed(
                pickSeatPage.getTravelersSelectedSeatsDeparture()));
        myJourneyPage.clickProceedToPaymentButton();

        paymentPage.setCreditCardNumber("5577 0000 5577 0004");
        paymentPage.setExpiryDateMMYY("0330");
        paymentPage.setSecurityCode("737");
        paymentPage.setNameOnCard("John Doe");
        paymentPage.clickPurchaseConditionsRadioButton();
        paymentPage.clickPayButton();

        confirmationPage.clickGoToJourneyButton();
        myJourneyConfirmationPage.clickGoToPassengersLink();
    }
}
