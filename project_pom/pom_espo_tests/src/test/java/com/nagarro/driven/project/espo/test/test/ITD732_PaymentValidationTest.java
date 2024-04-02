package com.nagarro.driven.project.espo.test.test;


import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;


public class ITD732_PaymentValidationTest extends EspoPOMTestBase {

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
    public void testPaymentValidations() throws InterruptedException {
        /**
         * TestSet ITD-732 testing task ITD-81 "Pay for booking with VISA and Mastercard"
         * Book one adult, one infant, one way, no seat selection, reach payment page.
         * Fill in only expiry date and name on card, click Pay  => check error messages: missing card number and missing security code
         * Fill in correct security code and invalid card number, click Pay => check error message: invalid card number
         * Fill in correct card number, click Pay => check error message that terms and conditions must be accepted
         * Check terms and conditions, but fill in wrong security code, click Pay => check that transaction is rejected
         * Click Retry
         * Fill in correct data, but leave security code empty, click Pay => check error message: missing security code
         * Fill in correct data, click Pay => successful transaction
         */

        String correctSecurityCode = "737";
        String incorrectSecurityCode = "777";
        String validCardNumber = "4988 4388 4388 4305";
        String invalidCardNumber = "4988 4388 4388 4306";

        String insufficientFundsCardNumber = "4242420100058064";
        String insufficientFundsSecurityCode = "123";
        String insufficientFundsExpiryDate = "1229";

        cookieSection.acceptAllCookies();

        homePage.clickWhoIsFlyingButton();
        whoIsFlyingModal.setNumberOfAdults(1);
        whoIsFlyingModal.setNumberOfInfants(1);
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

        Assert.assertTrue(paymentPage.checkPurchaseConditionsIsCorrectlyDisplayed());

        paymentPage.setExpiryDateMMYY("0330");
        paymentPage.setNameOnCard("John Doe");
        paymentPage.clickPayButton();

        Assert.assertTrue(paymentPage.checkCardNumberIncompleteErrorMessageIsDisplayed());
        Assert.assertTrue(paymentPage.checkSecurityCodeIncompleteErrorMessageIsDisplayed());

        paymentPage.setCreditCardNumber(invalidCardNumber);
        paymentPage.setSecurityCode(correctSecurityCode);
        paymentPage.clickPayButton();

        Assert.assertTrue(paymentPage.checkCardNumberInvalidErrorMessageIsDisplayed());

        paymentPage.setCreditCardNumber(validCardNumber);
        paymentPage.setSecurityCode(correctSecurityCode);
        paymentPage.clickPayButton();

        Assert.assertTrue(paymentPage.checkPaymentConditionsErrorMessageIsDisplayed());

        paymentPage.clickPurchaseConditionsRadioButton();
        Assert.assertTrue(paymentPage.checkPurchaseConditionsIsCorrectlyDisplayed());

        paymentPage.setCreditCardNumber(validCardNumber);
        paymentPage.setSecurityCode(incorrectSecurityCode);
        paymentPage.clickPayButton();

        Assert.assertTrue(paymentPage.checkPaymentRefusedErrorMessageIsDisplayed());

        paymentPage.clickRetryButton();

        paymentPage.setCreditCardNumber(insufficientFundsCardNumber);
        paymentPage.setSecurityCode(insufficientFundsSecurityCode);
        paymentPage.setExpiryDateMMYY(insufficientFundsExpiryDate);
        paymentPage.setNameOnCard("John Doe");
        paymentPage.clickPayButton();

        Assert.assertTrue(paymentPage.checkPaymentRefusedErrorMessageIsDisplayed());

        paymentPage.clickRetryButton();

        paymentPage.setCreditCardNumber(validCardNumber);
        paymentPage.setSecurityCode("");
        paymentPage.setExpiryDateMMYY("0330");
        paymentPage.setNameOnCard("John Doe");
        paymentPage.clickPayButton();

        Assert.assertTrue(paymentPage.checkSecurityCodeIncompleteErrorMessageIsDisplayed());

        paymentPage.setSecurityCode(correctSecurityCode);
        paymentPage.clickPayButton();

        confirmationPage.clickGoToJourneyButton();
    }
}
