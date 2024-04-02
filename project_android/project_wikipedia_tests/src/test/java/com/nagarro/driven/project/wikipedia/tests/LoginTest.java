package com.nagarro.driven.project.wikipedia.tests;

import com.nagarro.driven.project.wikipedia.data.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.nagarro.driven.project.wikipedia.page_objects.LoginPage;
import com.nagarro.driven.project.wikipedia.tests.testbase.WikiTestBase;

public class LoginTest extends WikiTestBase {
    LoginPage login;

//    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void verifyLogin(String username, String password) {
        login.loginToWikiPedia(username, password);
        Assert.assertTrue(login.verifyLoggedIn(), "User not logged in");
        login.logout();
    }

//    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void invalidCredentialsLogin(String username, String password) {
        login.loginToWikiPedia(username, password);
        Assert.assertTrue(login.verifyLoginScreen(), "User logged in");
    }

//    @BeforeMethod
    public void loginObject() {
        login = new LoginPage(androidDriverProvider.get());
    }
}
