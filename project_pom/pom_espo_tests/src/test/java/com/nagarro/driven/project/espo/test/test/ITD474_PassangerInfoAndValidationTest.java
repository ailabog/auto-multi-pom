package com.nagarro.driven.project.espo.test.test;


import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.Calendar;


public class ITD474_PassangerInfoAndValidationTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    HomePage homePage;
    PickDatesPage pickDatesPage;
    WhoIsFlyingModal whoIsFlyingModal;
    WhoIsFlyingPage whoIsFlyingPage;
    ContactInformationPage contactInformationPage;
    PickSeatPage pickSeatPage;
    LoginPage loginPage;

    private final int maxChildAge = 15;

    @BeforeMethod
    public void setup() {
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
        homePage = new HomePage(seleniumAbstractDriverProvider.get());
        pickDatesPage = new PickDatesPage(seleniumAbstractDriverProvider.get());
        whoIsFlyingModal = new WhoIsFlyingModal(seleniumAbstractDriverProvider.get());
        whoIsFlyingPage = new WhoIsFlyingPage(seleniumAbstractDriverProvider.get());
        contactInformationPage = new ContactInformationPage(seleniumAbstractDriverProvider.get());
        pickSeatPage = new PickSeatPage(seleniumAbstractDriverProvider.get());
     }

    @Test
    public void testFillInPassengerInformationAndValidations() throws InterruptedException {
        /**
         * TestSet ITD-474 testing task ITD-56 "Add name, gender for each traveller to booking"
         * Check correct selection of Adult, Children and Infants (add name, sex and date of birth for Children and Infants)
         * Check validations: missing information, Child age validation, Infant age validation
         */
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;

        cookieSection.acceptAllCookies();

        homePage.clickWhoIsFlyingButton();
        whoIsFlyingModal.setNumberOfAdults(2);
        whoIsFlyingModal.setNumberOfChildren(2);
        whoIsFlyingModal.setNumberOfInfants(1);
        whoIsFlyingModal.clickConfirm();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();
        Assert.assertEquals(pickDatesPage.getNumberOfTravelersLabel(), (5));
        pickDatesPage.setOneWay();

        pickDatesPage.selectRandomAvailableDepartureDay();
        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectSmartFlightFromModal();
        pickDatesPage.clickContinueButton();

        loginPage.continueWithoutProfile();

        whoIsFlyingPage.setAdultFirstNameWithIndex("FirstNameAdultOne", 1);
        whoIsFlyingPage.setAdultLastNameWithIndex("LastNameAdultOne", 1);
        whoIsFlyingPage.setAdultSexMaleWithIndex(1);
        whoIsFlyingPage.setAdultFirstNameWithIndex("FirstNameAdultTwo", 2);
        whoIsFlyingPage.setAdultLastNameWithIndex("LastNameAdultTwo", 2);
        whoIsFlyingPage.setAdultSexMaleWithIndex(2);

        whoIsFlyingPage.setChildFirstNameWithIndex("FirstNameChildOne", 1);
        whoIsFlyingPage.setChildLastNameWithIndex("LastNameChildOne", 1);
        whoIsFlyingPage.setChildSexMaleWithIndex(1);
        whoIsFlyingPage.setChildFirstNameWithIndex("FirstNameChildTwo", 2);
        whoIsFlyingPage.setChildLastNameWithIndex("LastNameChildTwo", 2);
        whoIsFlyingPage.setChildSexMaleWithIndex(2);

        whoIsFlyingPage.setInfantFirstNameWithIndex("FirstNameInfantOne", 1);
        whoIsFlyingPage.setInfantLastNameWithIndex("LastNameInfantOne", 1);
        whoIsFlyingPage.setInfantSexMaleWithIndex(1);

        //Check error messages displayed for child not between 2 and 15 years of age

        whoIsFlyingPage.setChildBirthdayWithIndex("0101" + (currentYear - maxChildAge - 1), 1); //Child too old (adult)
        whoIsFlyingPage.setChildBirthdayWithIndex("0101" + (currentYear - 3), 2);
        whoIsFlyingPage.setInfantBirthdayWithIndex("0101" + (currentYear - 2), 1);              //Infant is actually a Child
        whoIsFlyingPage.clickContinueButton();

        Assert.assertTrue(whoIsFlyingPage.isErrorMessageChildTooOldDisplayedWithIndex(1));
        Assert.assertTrue(whoIsFlyingPage.isErrorMessageInfantTooOldChildDisplayedWithIndex(1));

        whoIsFlyingPage.setChildBirthdayWithIndex("1231" + (currentYear - maxChildAge - 2), 1);     //Child too old (adult)
        whoIsFlyingPage.setChildBirthdayWithIndex("0101" + (currentYear - 1), 2);                   //OK - infant booked as child, for seat.
        whoIsFlyingPage.setInfantBirthdayWithIndex("0101" + (currentYear - 16), 1);                 //Infant is actually an Adult
        whoIsFlyingPage.clickContinueButton();

        Assert.assertTrue(whoIsFlyingPage.isErrorMessageChildTooOldDisplayedWithIndex(1));
        Assert.assertTrue(whoIsFlyingPage.isErrorMessageInfantTooOldAdultDisplayedWithIndex(1));

        //Check age related error messages clear + check error messages for missing fields

        whoIsFlyingPage.setChildBirthdayWithIndex("0101" + (currentYear - maxChildAge + 1), 1);
        whoIsFlyingPage.setChildBirthdayWithIndex("0101" + (currentYear - 2), 2);
        whoIsFlyingPage.setInfantBirthdayWithIndex("0101" + (currentYear - 1), 1);

        whoIsFlyingPage.setChildFirstNameWithIndex("", 2);
        whoIsFlyingPage.clickContinueButton();

        Assert.assertTrue(whoIsFlyingPage.isErrorMessageChildTooOldNotDisplayedWithIndex(1));
        Assert.assertTrue(whoIsFlyingPage.isErrorMessageChildNameRequiredDisplayedWithIndex(2));
        Assert.assertTrue(whoIsFlyingPage.isErrorMessageInfantTooOldAdultNotDisplayedWithIndex(1));
        Assert.assertTrue(whoIsFlyingPage.isErrorMessageInfantTooOldAdultNotDisplayedWithIndex(1));

        whoIsFlyingPage.setChildFirstNameWithIndex("FirstNameChildTwo", 2);
        whoIsFlyingPage.clickContinueButton();

        pickSeatPage.selectFirstAvailableSeat();
    }
}
