package com.nagarro.driven.project.wikipedia.page_objects;

import com.nagarro.driven.client.appium.AbstractMobileAppiumDriver;
import com.nagarro.driven.core.reporting.api.KeywordReporting;
import com.nagarro.driven.core.util.Sleeper;
import com.nagarro.driven.core.util.Waiter;

public class LoginPage {
    private static final String OPTIONS_ELEMENT_NAME = "Options";
    AbstractMobileAppiumDriver androidClient;
    String pageName = "LoginPage";

    public LoginPage(AbstractMobileAppiumDriver client) {
        this.androidClient = client;
    }


    public void loginToWikiPedia(String username, String password) {
        clickoption();
        clickLoginTab();
        clickLoginButton();
        inputUsername(username);
        inputPassword(password);
        clickSubmitButton();
    }

    private void clickLoginButton() {
        Waiter.waitFor(() -> androidClient.element(pageName, "CreateAccLoginBtn").isDisplayed());
        androidClient.element(pageName, "CreateAccLoginBtn").click();
    }

    public void logout() {
        clickLoginTab();
        clickLogoutConfirm();
    }

    @KeywordReporting
    private void clickLogoutConfirm() {
        Waiter.waitFor(() -> androidClient.element(pageName, "LogoutButton").isDisplayed());
        androidClient.element(pageName, "LogoutButton").click();
    }

    @KeywordReporting
    private void clickoption() {
        Waiter.waitFor(() -> androidClient.element(pageName, "Options").isDisplayed());
        androidClient.element(pageName, "Options").click();
    }

    @KeywordReporting
    private void clickLoginTab() {
        Waiter.waitFor(() -> androidClient.element(pageName, "LoginTab").isDisplayed());
        androidClient.element(pageName, "LoginTab").click();
    }

    @KeywordReporting
    private void inputUsername(String username) {
        Waiter.waitFor(() -> androidClient.element(pageName, "UserNameField").isDisplayed());
        androidClient.element(pageName, "UserNameField").sendKeys(username);
    }

    @KeywordReporting
    private void inputPassword(String password) {
        Waiter.waitFor(() -> androidClient.element(pageName, "PasswordField").isDisplayed());
        androidClient.element(pageName, "PasswordField").sendKeys(password);
    }

    @KeywordReporting
    private void clickSubmitButton() {
        Waiter.waitFor(() -> androidClient.element(pageName, "SubmitButton").isDisplayed());
        androidClient.element(pageName, "SubmitButton").click();
    }

    @KeywordReporting
    public boolean verifyLoggedIn() {
        Sleeper.silentSleep(4000);
        try {
            clickoption();
            return androidClient.element(pageName, "LogoutButton").isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean verifyLoginScreen() {
        try{
            return androidClient.element(pageName, "SubmitButton").isDisplayed();
        }catch(Exception e){
            return false;
        }
    }
}


