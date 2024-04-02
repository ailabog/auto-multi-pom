package com.nagarro.driven.project.espo.test.test;


import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;


public class ITD992_FindFlightsBookingTest extends EspoPOMTestBase {

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
    private static final String INVALID_REFFERENCE = "WRONG";
    private static final String INVALID_NAME = "WrongLastName";
    private static final String EMAIL_ADDRESS = "emailaddress@example.com";
    private static final String PHONE_NUMBER = "+40734111222";

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
        passengersConfirmationPage = new PassengersConfirmationPage(seleniumAbstractDriverProvider.get());
        findMyBookingPage = new FindMyBookingPage(seleniumAbstractDriverProvider.get());
    }


    @Test
    public void testFindFlightBooking() throws InterruptedException {
        /**
         * TestSet ITD-992 testing task ITD-309 Access bookings on web as unregistered user.
         * Select one way flight, one adult, one child, one way, Fleks, select seats, pay.
         * Check correct booking reference in my journey confirmation page.
         * Navigate to home page, search for booking with:
         *  Wrong booking reference and correct name - NOK
         *  Wrong name and correct booking reference - NOK
         *  Correct last name of adult and correct booking reference - OK
         *  Correct last name of child and correct booking reference - OK
         *
         */
        String bookingReference;
        String lastNameAdult = "Grøn-bæk";
        String lastNameChild = "Last C.name";

        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        String currentMonth= currentDate.getMonth().name();

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
        whoIsFlyingPage.setAdultLastNameWithIndex(lastNameAdult, 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.setChildFirstNameWithIndex("Child", 1);
        whoIsFlyingPage.setChildLastNameWithIndex(lastNameChild, 1);
        whoIsFlyingPage.setChildSexFemaleWithIndex(1);
        whoIsFlyingPage.setChildBirthdayWithIndex("0101" + (Calendar.getInstance().get(Calendar.YEAR) - 8), 1);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.selectFirstDepartureAvailableSeatForTraveler("Adult " + lastNameAdult);
        pickSeatPage.selectFirstDepartureAvailableSeatForTraveler("Child " + lastNameChild);

        pickSeatPage.clickContinueButton();

        contactInformationPage.setEmail(EMAIL_ADDRESS);
        contactInformationPage.setPhoneNumber(PHONE_NUMBER);
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
        myJourneyConfirmationPage.clickGoToPassengersLink();

        passengersConfirmationPage.clickFlyrLogoLink();

        homePage.clickFindMyBooking();

        findMyBookingPage.enterReference(bookingReference);
        findMyBookingPage.enterLastName(INVALID_NAME);
        findMyBookingPage.clickFindBookingButton();
        Assert.assertTrue(findMyBookingPage.checkBookingNotFoundErrorMessageIsDisplayed());

        findMyBookingPage.enterReference(INVALID_REFFERENCE);
        findMyBookingPage.enterLastName(lastNameAdult);
        findMyBookingPage.clickFindBookingButton();
        Assert.assertTrue(findMyBookingPage.checkBookingNotFoundErrorMessageIsDisplayed());

        findMyBookingPage.enterReference(INVALID_REFFERENCE);
        findMyBookingPage.enterLastName(lastNameChild);
        findMyBookingPage.clickFindBookingButton();
        Assert.assertTrue(findMyBookingPage.checkBookingNotFoundErrorMessageIsDisplayed());

        findMyBookingPage.enterReference(bookingReference);
        findMyBookingPage.enterLastName(lastNameAdult);
        findMyBookingPage.clickFindBookingButton();

        Assert.assertEquals(myJourneyConfirmationPage.getBookingReference(), bookingReference);
        myJourneyConfirmationPage.clickGoToPassengersLink();
        passengersConfirmationPage.clickFlyrLogoLink();

        homePage.clickFindMyBooking();

        findMyBookingPage.enterReference(bookingReference);
        findMyBookingPage.enterLastName(lastNameChild);
        findMyBookingPage.clickFindBookingButton();

        Assert.assertEquals(myJourneyConfirmationPage.getBookingReference(), bookingReference);

        // If departure day is current day, verify that Check in is open
        if ((currentDay == pickDatesPage.departureSelectionDay) && (currentMonth.toLowerCase().equals(pickDatesPage.departureSelectionMonth.toLowerCase(Locale.ROOT)))) {
            Assert.assertTrue(myJourneyConfirmationPage.checkCorrectCheckinOpenMessageDisplayed());
        }

        // If departure day is two days or more in future, verify that Check in is not open
        if ((currentDay < pickDatesPage.departureSelectionDay - 1) && (currentMonth.toLowerCase().equals(pickDatesPage.departureSelectionMonth.toLowerCase(Locale.ROOT)))) {
            Assert.assertTrue(myJourneyConfirmationPage.checkCorrectCheckinNotOpenMessageDisplayed());
        }

        myJourneyConfirmationPage.clickGoToPassengersLink();
        passengersConfirmationPage.navigateBack();

        myJourneyConfirmationPage.clickCancelJourneyButton();
        myJourneyConfirmationPage.clickCancelJourneyModalContinueButton();
        myJourneyConfirmationPage.clickSignInWithVippsButton();
        Assert.assertTrue(myJourneyConfirmationPage.checkLoginFormIsDisplayed());
    }
}
