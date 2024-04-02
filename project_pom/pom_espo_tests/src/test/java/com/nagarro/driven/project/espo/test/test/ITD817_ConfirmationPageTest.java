package com.nagarro.driven.project.espo.test.test;


import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ITD817_ConfirmationPageTest extends EspoPOMTestBase {

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
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
    }

    @Test
    public void testPageConfirmation() throws InterruptedException {
        /**
         * TestSet ITD-817 testing task ITD-82 Confirmation page after payment
         * Book two adults, two way flight, select return flights seats, pay.
         * In confirmation page after payment, check:
         *  Correct heading title.
         *  Correct dates for departure and return flights.
         *  Correct airports for both outbound and return flights.
         *  Correct traveller names.
         *  Correct booking reference format.
         *  Correct email address.
         *  Go to journey button.
         */

        String firstNameAdultOne = "AdultOne";
        String lastNameAdultOne = "Lastname";
        String firstNameAdultTwo = "AdultTwo";
        String lastNameAdultTwo = "Lastname";

        cookieSection.acceptAllCookies();

        homePage.clickWhoIsFlyingButton();
        whoIsFlyingModal.setNumberOfAdults(2);
        whoIsFlyingModal.clickConfirm();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        pickDatesPage.clickChooseOutbound();
        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectFleksFlightFromModal();
        pickDatesPage.clickChooseReturn();

        pickDatesPage.selectRandomAvailableReturnDay();
//        https://jira.flyr.com/browse/ITD-2144
        pickDatesPage.selectFirstReturnFlight();
        pickDatesPage.selectFleksFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex(firstNameAdultOne, 1);
        whoIsFlyingPage.setAdultLastNameWithIndex(lastNameAdultOne, 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.setAdultFirstNameWithIndex(firstNameAdultTwo, 2);
        whoIsFlyingPage.setAdultLastNameWithIndex(lastNameAdultTwo, 2);
        whoIsFlyingPage.setAdultSexFemaleWithIndex(2);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.clickSkipButton();
        pickSeatPage.selectFirstReturnAvailableSeatForTraveler(firstNameAdultOne + " " + lastNameAdultOne);
        pickSeatPage.selectFirstReturnAvailableSeatForTraveler(firstNameAdultTwo + " " + lastNameAdultTwo);
        pickSeatPage.clickContinueButton();

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

        Assert.assertTrue(confirmationPage.checkHeadingTitleIsDisplayedCorrectly());

        Assert.assertTrue(confirmationPage.checkCorrectOutboundDepartureAirport(defaultDepartureAirport));
        Assert.assertTrue(confirmationPage.checkCorrectOutboundReturnAirport(defaultDestinationAirport));
        Assert.assertTrue(confirmationPage.checkCorrectReturnDepartureAirport(defaultDestinationAirport));
        Assert.assertTrue(confirmationPage.checkCorrectReturnReturnAirport(defaultDepartureAirport));

        Assert.assertTrue(confirmationPage.checkCorrectFirstTravellerName(firstNameAdultOne + " " + lastNameAdultOne));
        Assert.assertTrue(confirmationPage.checkCorrectSecondTravellerName(firstNameAdultTwo + " " + lastNameAdultTwo));

        Assert.assertTrue(confirmationPage.checkBookingReferenceHasCorrectFormat());
        Assert.assertEquals(confirmationPage.getEmailAddress(), "emailaddress@example.com");

        Assert.assertTrue(confirmationPage.checkCorrectDepartureDayMonth(
                pickDatesPage.departureSelectionDay,
                pickDatesPage.departureSelectionMonth));
        Assert.assertTrue(confirmationPage.checkCorrectReturnDayMonth(
                pickDatesPage.returnSelectionDay,
                pickDatesPage.returnSelectionMonth));
    }
}
