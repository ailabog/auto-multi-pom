package com.nagarro.driven.project.wikipedia.tests;

import com.nagarro.driven.project.wikipedia.data.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.nagarro.driven.project.wikipedia.page_objects.SearchPage;
import com.nagarro.driven.project.wikipedia.tests.testbase.WikiTestBase;

public class SearchTest extends WikiTestBase {
    SearchPage search;

//    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void verifySearchResult(String searchData) {
        search.searchHeading(searchData);
        Assert.assertEquals(search.getContentHeading(), searchData);
    }

//    @BeforeMethod
    public void loginObject() {
        search = new SearchPage(androidDriverProvider.get());
    }
}
