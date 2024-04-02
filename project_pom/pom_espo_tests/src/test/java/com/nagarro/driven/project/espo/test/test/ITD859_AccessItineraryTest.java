package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ITD859_AccessItineraryTest extends EspoPOMTestBase {

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
    public void testAccessItinerary() throws InterruptedException {
        /**
         * TestSet ITD-859 testing task ITD-83 Access and print itinerary
         * Book a two way flight, one adult, pay.
         * After successful payment, go to journey page.
         * Check correct elements in the journey page:
         *  booking reference
         *  "from" and "to" header fields
         *  number of passengers
         *  flight type (round trip)
         *  flight dates (departure and return)
         *  flight airports for both outbound and inbound
         *  passengers initials
         *  links:  ask for assistance
         *          change date and time
         *          cancel journey
         *          download receipt
         *          send booking confirmation
         */
        String bookingReference;
        String firstNameAdult = "Grøn-bæk";
        String lastNameAdult = "Lastname";
//        String lastNameAdult = "Last C'name";
//        https://flyr.atlassian.net/browse/ITD-1708

        cookieSection.acceptAllCookies();

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
        pickDatesPage.selectFleksFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex(firstNameAdult, 1);
        whoIsFlyingPage.setAdultLastNameWithIndex(lastNameAdult, 1);
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

        bookingReference = confirmationPage.getBookingReference();
        confirmationPage.clickGoToJourneyButton();
        Assert.assertEquals(bookingReference, myJourneyConfirmationPage.getBookingReference());
        Assert.assertTrue(myJourneyConfirmationPage.checkCorrectHeaderFrom(defaultDepartureAirport));
        Assert.assertTrue(myJourneyConfirmationPage.checkCorrectHeaderTo(defaultDestinationAirport));
        Assert.assertTrue(myJourneyConfirmationPage.checkCorrectNumberOfPassengers(1));
        Assert.assertTrue(myJourneyConfirmationPage.checkCorrectFlightType("Round trip"));
        Assert.assertTrue(myJourneyConfirmationPage.checkCorrectFlightDates(
                Integer.toString(pickDatesPage.departureSelectionDay), pickDatesPage.departureSelectionMonth,
                Integer.toString(pickDatesPage.returnSelectionDay), pickDatesPage.returnSelectionMonth));
        Assert.assertTrue(myJourneyConfirmationPage.checkCorrectOutboundAirports(defaultDepartureAirport, defaultDestinationAirport));
        Assert.assertTrue(myJourneyConfirmationPage.checkCorrectReturnAirports(defaultDestinationAirport, defaultDepartureAirport));
        Assert.assertTrue(myJourneyConfirmationPage.checkCorrectPassengerInitials("GL"));
        Assert.assertTrue(myJourneyConfirmationPage.checkAskForAssistanceButtonIsDisplayed());
        Assert.assertTrue(myJourneyConfirmationPage.checkChangeDateAndTimeButtonIsDisplayed());
        Assert.assertTrue(myJourneyConfirmationPage.checkCancelJourneyButtonIsDisplayed());
        Assert.assertTrue(myJourneyConfirmationPage.checkDownloadReceiptButtonIsDisplayed());
        Assert.assertTrue(myJourneyConfirmationPage.checkSendBookingConfirmationButtonIsDisplayed());

        myJourneyConfirmationPage.clickGoToPassengersLink();
    }
}
