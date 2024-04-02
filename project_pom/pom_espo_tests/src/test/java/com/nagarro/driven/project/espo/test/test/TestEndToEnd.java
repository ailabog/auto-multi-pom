package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import com.nagarro.driven.project.espo.test.pageobjects.utils.CreditCards;
import com.nagarro.driven.project.espo.test.pageobjects.utils.GenerateRandomDataUtils;
import com.nagarro.driven.project.espo.test.pageobjects.utils.Phones;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


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


public class TestEndToEnd extends EspoPOMTestBase {

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
    ReturnPage returnPage;
    public static final String FIRST_NAME = GenerateRandomDataUtils.generateRandomString(5);
    public static final String LAST_NAME = GenerateRandomDataUtils.generateRandomString(7);
    CreditCards creditCard;
    Phones phoneNo;
    private static final String EMAIL_ADDRESS = GenerateRandomDataUtils.generateEmail(12);

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
        returnPage = new ReturnPage(seleniumAbstractDriverProvider.get());
    }

    @Test
    public void oneTest() throws InterruptedException {
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

        whoIsFlyingPage.setAdultFirstNameWithIndex(FIRST_NAME, 1);
        whoIsFlyingPage.setAdultLastNameWithIndex(LAST_NAME, 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.clickSkipButton();

        contactInformationPage.setEmail(EMAIL_ADDRESS);
        contactInformationPage.setPhoneNumber(phoneNo.PHONE_RO.getNumber());
        contactInformationPage.clickContinueButton();

        myJourneyPage.clickProceedToPaymentButton();

        paymentPage.setCreditCardNumber(creditCard.MASTER_CARD_CONSUMER_GB.getNumber());
        paymentPage.setExpiryDateMMYY(creditCard.MASTER_CARD_CONSUMER_GB.getExpirationDate());
        paymentPage.setSecurityCode(creditCard.MASTER_CARD_CONSUMER_GB.getCvs());
        paymentPage.setNameOnCard(FIRST_NAME + "" + LAST_NAME);
        paymentPage.clickPurchaseConditionsRadioButton();
        paymentPage.clickPayButton();

        confirmationPage.clickGoToJourneyButton();
        myJourneyConfirmationPage.getBookingReference();
        myJourneyConfirmationPage.getBookingReference();
        myJourneyConfirmationPage.checkCancelJourneyButtonIsDisplayed();
        myJourneyConfirmationPage.checkChangeDateAndTimeButtonIsDisplayed();
        myJourneyConfirmationPage.checkSendBookingConfirmationButtonIsDisplayed();
        returnPage.smallChecks();
    }
}
