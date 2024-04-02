package com.nagarro.driven.project.espo.test.test;


import com.nagarro.driven.project.espo.test.base.EspoPOMTestBase;
import com.nagarro.driven.project.espo.test.pageobjects.pages.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


public class ITD653_CheckLanguagesEN_NOTest extends EspoPOMTestBase {

    CookieSection cookieSection;
    ConfigMenuPage configMenuPage;
    HomePage homePage;
    PickDatesPage pickDatesPage;
    PassengersConfirmationPage passengersConfirmationPage;

    @BeforeMethod
    public void setup() {
        homePage = new HomePage(seleniumAbstractDriverProvider.get());
        configMenuPage = new ConfigMenuPage(seleniumAbstractDriverProvider.get());
        pickDatesPage = new PickDatesPage(seleniumAbstractDriverProvider.get());
        cookieSection = new CookieSection(seleniumAbstractDriverProvider.get());
        passengersConfirmationPage = new PassengersConfirmationPage(seleniumAbstractDriverProvider.get());
    }

    @Test()
    public void testSupportEnglishAndNorwegian() throws InterruptedException {
        /**
         * TestSet ITD-653 testing task ITD-526 Support Norwegian and English languages on web.
         * Access English version of page. Check that language button English is selected.
         * Toggle between English and Norwegian and check that greeting element is correctly updated.
         * Navigate to the next page, check that English language was preserved.
         */
        cookieSection.acceptAllCookies();
        homePage.checkGreetingHeadingIsDisplayedInEnglish();

        homePage.clickConfigMenuButton();
        Assert.assertTrue(configMenuPage.checkIsNotSelectedLanguageNorwegian());
        Assert.assertTrue(configMenuPage.checkIsSelectedLanguageEnglish());
        configMenuPage.selectLanguageNorwegian();
        homePage.checkGreetingHeadingIsDisplayedInNorwegian();

        homePage.clickConfigMenuButton();
        Assert.assertTrue(configMenuPage.checkIsSelectedLanguageNorwegian());
        Assert.assertTrue(configMenuPage.checkIsNotSelectedLanguageEnglish());
        configMenuPage.selectLanguageEnglish();
        homePage.checkGreetingHeadingIsDisplayedInEnglish();

        homePage.clickConfigMenuButton();
        Assert.assertTrue(configMenuPage.checkIsNotSelectedLanguageNorwegian());
        Assert.assertTrue(configMenuPage.checkIsSelectedLanguageEnglish());
        configMenuPage.exitConfigMenuPage();
        homePage.checkGreetingHeadingIsDisplayedInEnglish();

        homePage.setToField(defaultDestinationAirport);
        homePage.clickDestinationFirstElementSuggestion();
        pickDatesPage.clickConfigMenuButton();
        Assert.assertTrue(configMenuPage.checkIsNotSelectedLanguageNorwegian());
        Assert.assertTrue(configMenuPage.checkIsSelectedLanguageEnglish());
    }
  }
