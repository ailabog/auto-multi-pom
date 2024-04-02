package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import com.nagarro.driven.project.espo.test.pageobjects.utils.GenerateRandomDataUtils;
import com.nagarro.driven.project.espo.test.pageobjects.utils.Phones;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ITD470_ContactDetailsTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    HomePage homePage;
    PickDatesPage pickDatesPage;
    WhoIsFlyingModal whoIsFlyingModal;
    WhoIsFlyingPage whoIsFlyingPage;
    ContactInformationPage contactInformationPage;
    PickSeatPage pickSeatPage;
    MyJourneyPage myJourneyPage;
    LoginPage loginPage;
    public static final String FIRST_NAME = GenerateRandomDataUtils.generateRandomString(5);
    public static final String LAST_NAME = GenerateRandomDataUtils.generateRandomString(7);
    Phones phoneNo;
    private static final String EMAIL_ADDRESS = GenerateRandomDataUtils.generateEmail(12);
    private static final String INVALID_EMAIL_ADDRESS = GenerateRandomDataUtils.generateRandomAlphaNumeric(7);
    private static final String INVALID_EMAIL_ADDRESS_STRING = GenerateRandomDataUtils.generateRandomString(1);

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
        loginPage = new LoginPage(seleniumAbstractDriverProvider.get());
    }

    @Test
    public void testContactDetails() throws InterruptedException {
        /**
         * TestSet ITD-470 testing task ITD-65 "Add contact details"
         * Check invalid and missing email and phone error messages in various combinations.
         */

        cookieSection.acceptAllCookies();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();
        pickDatesPage.selectNextMonthWeek();
        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.setOneWay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectFleksFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex(FIRST_NAME, 1);
        whoIsFlyingPage.setAdultLastNameWithIndex(LAST_NAME, 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.selectFirstAvailableSeat();
        pickSeatPage.clickContinueButton();

        contactInformationPage.setEmail(EMAIL_ADDRESS);
        contactInformationPage.setPhoneNumber(phoneNo.PHONE_NO_INVALID.getNumber());
        contactInformationPage.clickContinueButton();
        Assert.assertTrue(contactInformationPage.isErrorMessageInvalidPhoneNumberDisplayed());

        contactInformationPage.setEmail(INVALID_EMAIL_ADDRESS);
        contactInformationPage.setPhoneNumber(phoneNo.PHONE_NO_INVALID.getNumber());
        contactInformationPage.clickContinueButton();
        Assert.assertTrue(contactInformationPage.isErrorMessageInvalidPhoneNumberDisplayed());
        Assert.assertTrue(contactInformationPage.isErrorMessageInvalidEmailAddressDisplayed());

        contactInformationPage.setEmail(INVALID_EMAIL_ADDRESS);
        contactInformationPage.setPhoneNumber(phoneNo.PHONE_RO.getNumber());
        contactInformationPage.clickContinueButton();
        Assert.assertTrue(contactInformationPage.isErrorMessageInvalidEmailAddressDisplayed());

        contactInformationPage.setEmail(INVALID_EMAIL_ADDRESS_STRING);
        contactInformationPage.setPhoneNumber(phoneNo.PHONE_INVALID_RO.getNumber());
        contactInformationPage.clickContinueButton();
        Assert.assertTrue(contactInformationPage.isErrorMessageEmailAddressMissingDisplayed());
        Assert.assertTrue(contactInformationPage.isErrorMessageInvalidPhoneNumberDisplayed());

        contactInformationPage.setEmail(EMAIL_ADDRESS);
        contactInformationPage.setPhoneNumber(phoneNo.PHONE_RO.getNumber());
        contactInformationPage.clickContinueButton();

        myJourneyPage.clickProceedToPaymentButton();
    }
}
