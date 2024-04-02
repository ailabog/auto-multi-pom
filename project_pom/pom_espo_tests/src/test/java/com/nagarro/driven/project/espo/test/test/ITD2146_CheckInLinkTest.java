package com.nagarro.driven.project.espo.test.test;


import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.LocalDate;


public class ITD2146_CheckInLinkTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    ConfigMenuPage configMenuPage;
    HomePage homePage;
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
    PassengersConfirmationPage passengersConfirmationPage;
    FindMyBookingPage findMyBookingPage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(seleniumAbstractDriverProvider.get());
        configMenuPage = new ConfigMenuPage(seleniumAbstractDriverProvider.get());
        pickDatesPage = new PickDatesPage(seleniumAbstractDriverProvider.get());
        whoIsFlyingModal = new WhoIsFlyingModal(seleniumAbstractDriverProvider.get());
        whoIsFlyingPage = new WhoIsFlyingPage(seleniumAbstractDriverProvider.get());
        contactInformationPage = new ContactInformationPage(seleniumAbstractDriverProvider.get());
        paymentPage = new PaymentPage(seleniumAbstractDriverProvider.get());
        confirmationPage = new ConfirmationPage(seleniumAbstractDriverProvider.get());
        myJourneyConfirmationPage = new MyJourneyConfirmationPage(seleniumAbstractDriverProvider.get());
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
    }

    @Test
    public void testCheckinLink() throws InterruptedException {
        /**
         * https://jira.flyr.com/browse/ITD-2146
         * Automated test to check correct Checkin link in the My Journey confirmation page
         */

        String bookingReference;
        String lastNameAdult = "Grøn-bæk";
        String lastNameAdultEncoded = "Gr%C3%B8n-b%C3%A6k";

        LocalDate currentDate = LocalDate.now();

        cookieSection.acceptAllCookies();

        homePage.clickWhoIsFlyingButton();
        whoIsFlyingModal.clickConfirm();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.selectFirstAvailableDepartureDay();

        pickDatesPage.setOneWay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectFleksFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("Adult", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex(lastNameAdult, 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.selectFirstDepartureAvailableSeatForTraveler("Adult " + lastNameAdult);

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

        bookingReference = confirmationPage.getBookingReference();

        confirmationPage.clickGoToJourneyButton();
        Assert.assertEquals(bookingReference, myJourneyConfirmationPage.getBookingReference());
//        https://flyr.atlassian.net/browse/ITD-2279 - bug
        Assert.assertTrue(myJourneyConfirmationPage.checkCorrectCheckinLink(bookingReference, lastNameAdultEncoded, pickDatesPage.departureSelectionDay, pickDatesPage.departureSelectionMonth));
    }
}
