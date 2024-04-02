package com.nagarro.driven.project.espo.test.test;

import com.nagarro.driven.project.espo.data.TestDataProvider;
import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class IDT471_FlightSelection_AdultsChildrenInfantsSelectionTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    HomePage homePage;
    PickDatesPage pickDatesPage;
    WhoIsFlyingModal whoIsFlyingModal;
    ConfigMenuPage configMenuPage;
    LoginPage loginPage;

    @BeforeMethod
    public void setup() {
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
        homePage = new HomePage(seleniumAbstractDriverProvider.get());
        pickDatesPage = new PickDatesPage(seleniumAbstractDriverProvider.get());
        whoIsFlyingModal = new WhoIsFlyingModal(seleniumAbstractDriverProvider.get());
        configMenuPage = new ConfigMenuPage(seleniumAbstractDriverProvider.get());
        loginPage = new LoginPage(seleniumAbstractDriverProvider.get());
    }

    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void testSelectNumberOfAdultsChildrenInfantsTraveling(String airportName, String numberOfAdultsS,
                                                                 String numberOfChildrenS, String numberOfInfantsS) throws InterruptedException {
        /**
         * TestSet ITD--471 testing task ITD-20 Select number of adults, children and infants travelling.
         * Verify default values in traveller(s) selection.
         * Verify correct adding and removing of traveller(s).
         * Verify that cancelling the operation does not save the selected values.
         */

        int numberOfAdults = Integer.parseInt(numberOfAdultsS);
        int numberOfChildren = Integer.parseInt(numberOfChildrenS);
        int numberOfInfants = Integer.parseInt(numberOfInfantsS);
        int totalNumberOfTravelers = numberOfAdults + numberOfChildren + numberOfInfants;

        cookieSection.acceptAllCookies();
        homePage.setToField(airportName);
        homePage.clickDestinationFirstElementSuggestion();

        Assert.assertEquals(pickDatesPage.getNumberOfTravelersLabel(), (1));

        /* Check defaults */
        pickDatesPage.openWhoIsFlyingModalSelection();
        Assert.assertEquals(whoIsFlyingModal.getNumberOfAdults(), 1);
        Assert.assertEquals(whoIsFlyingModal.getNumberOfChildren(), 0);
        Assert.assertEquals(whoIsFlyingModal.getNumberOfInfants(), 0);
        Assert.assertTrue(whoIsFlyingModal.isRemoveAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveChildButtonDisabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveInfantButtonDisabled());
        Assert.assertTrue(whoIsFlyingModal.isConfirmButtonEnabled());

        /* Set some number of adults, children and infants */
        whoIsFlyingModal.setNumberOfAdults(numberOfAdults);
        whoIsFlyingModal.setNumberOfChildren(numberOfChildren + 2);
        whoIsFlyingModal.setNumberOfChildren(numberOfChildren);
        whoIsFlyingModal.setNumberOfInfants(numberOfInfants);
        whoIsFlyingModal.clickConfirm();
        Assert.assertEquals(totalNumberOfTravelers, pickDatesPage.getNumberOfTravelersLabel());
        pickDatesPage.openWhoIsFlyingModalSelection();
        Assert.assertEquals(numberOfAdults, whoIsFlyingModal.getNumberOfAdults());
        Assert.assertEquals(numberOfChildren, whoIsFlyingModal.getNumberOfChildren());
        Assert.assertEquals(numberOfInfants, whoIsFlyingModal.getNumberOfInfants());
        whoIsFlyingModal.clickCancel();

        /* Check that cancel operation does not save the modifications */
        pickDatesPage.openWhoIsFlyingModalSelection();
        whoIsFlyingModal.setNumberOfAdults(numberOfAdults + 1);
        whoIsFlyingModal.setNumberOfChildren(numberOfChildren + 3);
        whoIsFlyingModal.clickCancel();
        Assert.assertEquals(totalNumberOfTravelers, pickDatesPage.getNumberOfTravelersLabel());
        pickDatesPage.openWhoIsFlyingModalSelection();
        Assert.assertEquals(numberOfAdults, whoIsFlyingModal.getNumberOfAdults());
        Assert.assertEquals(numberOfChildren, whoIsFlyingModal.getNumberOfChildren());
        Assert.assertEquals(numberOfInfants, whoIsFlyingModal.getNumberOfInfants());
        whoIsFlyingModal.clickCancel();
    }

    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void testChooseBetweenThreeDifferentProducts(String airportName, String numberOfAdultsS,
                                                        String numberOfChildrenS, String numberOfInfantsS) throws InterruptedException {
        /**
         * TestSet ITD-471 testing task ITD-29 Choose between three different products
         * Switch between Mini, Smart and Fleks and check that they are correctly applied.
         */

        int numberOfAdults = Integer.parseInt(numberOfAdultsS);
        int numberOfChildren = Integer.parseInt(numberOfChildrenS);
        int numberOfInfants = Integer.parseInt(numberOfInfantsS);

        cookieSection.acceptAllCookies();
        homePage.setToField(airportName);
        homePage.clickDestinationFirstElementSuggestion();

        Assert.assertEquals(pickDatesPage.getNumberOfTravelersLabel(), (1));
        pickDatesPage.openWhoIsFlyingModalSelection();

        whoIsFlyingModal.setNumberOfAdults(numberOfAdults);
        whoIsFlyingModal.setNumberOfChildren(numberOfChildren);
        whoIsFlyingModal.setNumberOfInfants(numberOfInfants);
        whoIsFlyingModal.clickConfirm();

        Assert.assertEquals((numberOfAdults + numberOfChildren + numberOfInfants), pickDatesPage.getNumberOfTravelersLabel());
        pickDatesPage.setOneWay();

        pickDatesPage.selectRandomAvailableDepartureDay();

        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectMiniFlightFromModal();
        Assert.assertEquals(pickDatesPage.getFirstDepartureFlightType(), "Mini");

        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectSmartFlightFromModal();
        Assert.assertEquals(pickDatesPage.getFirstDepartureFlightType(), "Smart");

        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectFleksFlightFromModal();
        Assert.assertEquals(pickDatesPage.getFirstDepartureFlightType(), "Fleks");

        pickDatesPage.selectFirstDepartureFlight();
        pickDatesPage.selectFleksFlightFromModal();
        Assert.assertEquals(pickDatesPage.getFirstDepartureFlightType(), "Fleks");

        pickDatesPage.clickContinueButton();
        loginPage.continueWithoutProfile();
    }

    @Test
    public void testSelectNumberOfAdultsChildrenInfantsTravelingValidations() throws InterruptedException {
        /**
         * TestSet ITD--471 testing task ITD-20 Select number of adults, children and infants travelling.
         * Verify validations:
         * *** Should not allow confirmation with 0 adults
         * *** Should not allow more infants than adults: ITD-54
         * *** Should not allow more than 20 (adults + children): ITD-54
         * *** Should display a warning "Contact support" message if adults = 0 and children > 0
         */

        cookieSection.acceptAllCookies();
        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();

        Assert.assertEquals(pickDatesPage.getNumberOfTravelersLabel(), (1));
        pickDatesPage.openWhoIsFlyingModalSelection();

        Assert.assertEquals(whoIsFlyingModal.getNumberOfAdults(), 1);
        Assert.assertTrue(whoIsFlyingModal.isRemoveAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveChildButtonDisabled());
        Assert.assertTrue(whoIsFlyingModal.isAddChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveInfantButtonDisabled());
        Assert.assertTrue(whoIsFlyingModal.isAddInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isConfirmButtonEnabled());

        whoIsFlyingModal.setNumberOfAdults(0);
        whoIsFlyingModal.setNumberOfChildren(0);
        whoIsFlyingModal.setNumberOfInfants(0);
        Assert.assertTrue(whoIsFlyingModal.isRemoveAdultButtonDisabled());
        Assert.assertTrue(whoIsFlyingModal.isAddAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveChildButtonDisabled());
        Assert.assertTrue(whoIsFlyingModal.isAddChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveInfantButtonDisabled());
        Assert.assertTrue(whoIsFlyingModal.isAddInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isErrorMessageAddMorePassengersDisplayed());
        Assert.assertTrue(whoIsFlyingModal.isConfirmButtonDisabled());

        whoIsFlyingModal.setNumberOfAdults(1);
        whoIsFlyingModal.setNumberOfChildren(0);
        whoIsFlyingModal.setNumberOfInfants(2);
        Assert.assertTrue(whoIsFlyingModal.isRemoveAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveChildButtonDisabled());
        Assert.assertTrue(whoIsFlyingModal.isAddChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isErrorMessageAtLeastOneAdultPerInfantDisplayed());
        Assert.assertTrue(whoIsFlyingModal.isConfirmButtonDisabled());

        whoIsFlyingModal.setNumberOfAdults(1);
        whoIsFlyingModal.setNumberOfChildren(1);
        whoIsFlyingModal.setNumberOfInfants(1);
        Assert.assertTrue(whoIsFlyingModal.isRemoveAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isErrorMessageAtLeastOneAdultPerInfantNotDisplayed());
        Assert.assertTrue(whoIsFlyingModal.isConfirmButtonEnabled());

        whoIsFlyingModal.setNumberOfAdults(0);
        whoIsFlyingModal.setNumberOfChildren(1);
        whoIsFlyingModal.setNumberOfInfants(1);
        Assert.assertTrue(whoIsFlyingModal.isRemoveAdultButtonDisabled());
        Assert.assertTrue(whoIsFlyingModal.isAddAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isErrorMessageAtLeastOneAdultPerInfantDisplayed());
        Assert.assertTrue(whoIsFlyingModal.isConfirmButtonDisabled());

        whoIsFlyingModal.setNumberOfAdults(0);
        whoIsFlyingModal.setNumberOfChildren(1);
        whoIsFlyingModal.setNumberOfInfants(0);
        Assert.assertTrue(whoIsFlyingModal.isRemoveAdultButtonDisabled());
        Assert.assertTrue(whoIsFlyingModal.isAddAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveInfantButtonDisabled());
        Assert.assertTrue(whoIsFlyingModal.isAddInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isWarningMessageChildTravelingAloneDisplayed());
        Assert.assertTrue(whoIsFlyingModal.isConfirmButtonDisabled());

        whoIsFlyingModal.setNumberOfAdults(3);
        whoIsFlyingModal.setNumberOfChildren(1);
        whoIsFlyingModal.setNumberOfInfants(2);
        Assert.assertTrue(whoIsFlyingModal.isRemoveAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isWarningMessageChildTravelingAloneNotDisplayed());
        Assert.assertTrue(whoIsFlyingModal.isConfirmButtonEnabled());

        whoIsFlyingModal.setNumberOfAdults(5);
        whoIsFlyingModal.setNumberOfChildren(6);
        whoIsFlyingModal.setNumberOfInfants(3);
        Assert.assertTrue(whoIsFlyingModal.isRemoveAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddAdultButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddChildButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isRemoveInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isAddInfantButtonEnabled());
        Assert.assertTrue(whoIsFlyingModal.isErrorMessageMax10AdultsPlusChildrenDisplayed());
        Assert.assertTrue(whoIsFlyingModal.isConfirmButtonDisabled());

        whoIsFlyingModal.setNumberOfAdults(5);
        whoIsFlyingModal.setNumberOfChildren(5);
        whoIsFlyingModal.setNumberOfInfants(6);
        Assert.assertTrue(whoIsFlyingModal.isErrorMessageMax10AdultsPlusChildrenNotDisplayed());
        Assert.assertTrue(whoIsFlyingModal.isErrorMessageAtLeastOneAdultPerInfantDisplayed());
        Assert.assertTrue(whoIsFlyingModal.isConfirmButtonDisabled());

        whoIsFlyingModal.setNumberOfAdults(5);
        whoIsFlyingModal.setNumberOfChildren(5);
        whoIsFlyingModal.setNumberOfInfants(4);
        Assert.assertTrue(whoIsFlyingModal.isErrorMessageAtLeastOneAdultPerInfantNotDisplayed());
        Assert.assertTrue(whoIsFlyingModal.isConfirmButtonEnabled());
    }
}
