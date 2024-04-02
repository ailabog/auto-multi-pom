package com.nagarro.driven.project.espo.test.pageobjects.pages;

import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.core.util.Waiter;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PaymentPage {

    private final Logger log = LoggerFactory.getLogger(PaymentPage.class);

    /**
     * The web driver client.
     */
    SeleniumAbstractDriver seleniumClient;

    /* The name of the page. */
    private static final String PAGE_NAME = "PaymentPage";

    /**
     * The home page constructor
     */
    public PaymentPage(SeleniumAbstractDriver client) {
        seleniumClient = client;
    }

    public void setCreditCardNumber(String cardNumber) {
        waitForPageFramesToLoad();
        seleniumClient.getWebDriver().switchTo().frame(0);
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CreditCardNumber").isDisplayed());
        seleniumClient.element(PAGE_NAME, "CreditCardNumber").clear();
        seleniumClient.element(PAGE_NAME, "CreditCardNumber").sendKeys(cardNumber);
        seleniumClient.getWebDriver().switchTo().defaultContent();
    }

    public void setExpiryDateMMYY(String expiryDate) {
        waitForPageFramesToLoad();
        seleniumClient.getWebDriver().switchTo().frame(1);
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "ExpiryDate").isDisplayed());
        seleniumClient.element(PAGE_NAME, "ExpiryDate").clear();
        seleniumClient.element(PAGE_NAME, "ExpiryDate").sendKeys(expiryDate);
        seleniumClient.getWebDriver().switchTo().defaultContent();
    }

    public void setSecurityCode(String securityCode) {
        waitForPageFramesToLoad();
        seleniumClient.getWebDriver().switchTo().frame(2);
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "SecurityCode").isDisplayed());
        seleniumClient.element(PAGE_NAME, "SecurityCode").clear();
        seleniumClient.element(PAGE_NAME, "SecurityCode").sendKeys(securityCode);
        seleniumClient.getWebDriver().switchTo().defaultContent();
    }

    public void setNameOnCard(String nameOnCard) {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "NameOnCard").isDisplayed());
        seleniumClient.element(PAGE_NAME, "NameOnCard").clear();
        seleniumClient.element(PAGE_NAME, "NameOnCard").sendKeys(nameOnCard);
    }

    public void clickPayButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "PayButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "PayButton").click();
        Waiter.waitFor(() -> !seleniumClient.element(PAGE_NAME, "PayButton").getAttribute("class").contains("button--loading"));
    }

    public void clickPurchaseConditionsRadioButton() {
        seleniumClient.element(PAGE_NAME, "PurchaseConditionsRadioButton").click();
    }

    public boolean checkPurchaseConditionsIsCorrectlyDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "PurchaseConditionsLink").isDisplayed());
        return (seleniumClient.element(PAGE_NAME, "PurchaseConditionsLink").getText().contains("purchase conditions") &&
                seleniumClient.element(PAGE_NAME, "PurchaseConditionsLink").getAttribute("href").equals("https://qaenv.flyrstage.com/en/terms-and-conditions"));
    }

    public boolean checkPaymentConditionsErrorMessageIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "PurchaseConditionsErrorMessage").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "PurchaseConditionsErrorMessage").size()==1;
    }

    public boolean checkCardNumberIncompleteErrorMessageIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CardNumberIncompleteFieldErrorMessage").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "CardNumberIncompleteFieldErrorMessage").size()==1;
    }

    public boolean checkCardNumberInvalidErrorMessageIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "CardNumberInvalidFieldErrorMessage").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "CardNumberInvalidFieldErrorMessage").size()==1;
    }

    public boolean checkSecurityCodeIncompleteErrorMessageIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "SecurityCodeIncompleteFieldErrorMessage").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "SecurityCodeIncompleteFieldErrorMessage").size()==1;
    }

    public boolean checkPaymentRefusedErrorMessageIsDisplayed() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "PaymentRefusedErrorMessage").isDisplayed());
        return seleniumClient.elements(PAGE_NAME, "PaymentRefusedErrorMessage").size()==1;
    }

    public void clickRetryButton() {
        Waiter.waitFor(() -> seleniumClient.element(PAGE_NAME, "RetryButton").isDisplayed());
        seleniumClient.element(PAGE_NAME, "RetryButton").click();
    }

    private void waitForPageFramesToLoad() {
        Waiter.waitFor(() -> seleniumClient.getWebDriver().findElements(By.xpath("//iframe[@class='js-iframe']")).size()==3);
    }
}