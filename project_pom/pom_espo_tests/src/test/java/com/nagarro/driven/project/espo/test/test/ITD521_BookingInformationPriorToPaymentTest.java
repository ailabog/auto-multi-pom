package com.nagarro.driven.project.espo.test.test;


import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import com.nagarro.driven.project.espo.test.pageobjects.utils.CreditCards;
import com.nagarro.driven.project.espo.test.pageobjects.utils.GenerateRandomDataUtils;
import com.nagarro.driven.project.espo.test.pageobjects.utils.Phones;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Calendar;


public class ITD521_BookingInformationPriorToPaymentTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    HomePage homePage;
    PickDatesPage pickDatesPage;
    WhoIsFlyingModal whoIsFlyingModal;
    WhoIsFlyingPage whoIsFlyingPage;
    ContactInformationPage contactInformationPage;
    PickSeatPage pickSeatPage;
    MyJourneyPage myJourneyPage;
    LoginPage loginPage;
    PaymentPage paymentPage;
    CreditCards creditCard;
    private static final String EMAIL_ADDRESS = GenerateRandomDataUtils.generateEmail(12);
    Phones phoneNo;

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
        paymentPage = new PaymentPage(seleniumAbstractDriverProvider.get());
    }

    @Test
    public void bookingInformationPriorToPayment() throws InterruptedException {
        /**
         * TestSet ITD-521 testing task ITD-66 "See booking summary prior to payment"
         * Select 1 Adults and 1 Child, 2 way flight, Fleks, select seats for both travellers
         * Check correct data in My journey page prior to payment:
         *  Traveller names and initials
         *  Departure and Destination Airports
         *  Departure and Return Dates
         */
        cookieSection.acceptAllCookies();

        homePage.clickWhoIsFlyingButton();
        whoIsFlyingModal.setNumberOfAdults(1);
        whoIsFlyingModal.setNumberOfChildren(1);
        whoIsFlyingModal.clickConfirm();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

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

        contactInformationPage.setEmail(EMAIL_ADDRESS);
        contactInformationPage.setPhoneNumber("+40734111222");
        contactInformationPage.clickContinueButton();

        Assert.assertTrue(myJourneyPage.departureSelectedSeatsAreCorrectlyDisplayed(
                pickSeatPage.getTravelersSelectedSeatsDeparture()
        ));
        Assert.assertTrue(myJourneyPage.returnSelectedSeatsAreCorrectlyDisplayed(
                pickSeatPage.getTravelersSelectedSeatsReturn()
        ));

        Assert.assertTrue(myJourneyPage.checkTravellerNameDisplayed("Adult Lastname"));
        Assert.assertTrue(myJourneyPage.checkTravellerNameDisplayed("Child Lastname"));
        Assert.assertTrue(myJourneyPage.checkTravellerInitialsDisplayed("AL"));
        Assert.assertTrue(myJourneyPage.checkTravellerInitialsDisplayed("CL"));

        Assert.assertEquals(myJourneyPage.getDepartureAirport(), defaultDepartureAirport);
        Assert.assertEquals(myJourneyPage.getDestinationAirport(), defaultDestinationAirport);

        Assert.assertTrue(myJourneyPage.checkCorrectDatesDepartureReturn(
                pickDatesPage.departureSelectionDay,
                pickDatesPage.departureSelectionMonth,
                pickDatesPage.returnSelectionDay,
                pickDatesPage.returnSelectionMonth
        ));

        myJourneyPage.clickProceedToPaymentButton();
        paymentPage.setCreditCardNumber(creditCard.VISA_CLASSIC_NL.getNumber());
    }
}
