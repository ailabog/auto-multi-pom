package com.nagarro.driven.project.wikipedia.tests;

import com.nagarro.driven.project.wikipedia.data.TestDataProvider;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.nagarro.driven.project.wikipedia.page_objects.SearchPage;
import com.nagarro.driven.project.wikipedia.tests.testbase.WikiTestBase;

public class BookmarkTest extends WikiTestBase {
    SearchPage search;

//    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void addBookmarkTest(String searchData) {
        search.searchHeading(searchData);
        search.clickSearchButton();
        search.clickSave();
        search.navigateToStartScreen();
        Assert.assertTrue(search.isBookmarkAdded());
    }

//    @Test(dataProvider = "TestData", dataProviderClass = TestDataProvider.class)
    public void removeBookmarkTest(String searchData) {
        search.searchHeading(searchData);
        search.clickSearchButton();
        search.clickSave();
        search.deleteBookmark();
        search.navigateToStartScreen();
        Assert.assertTrue(search.isBookmarkRemoved());
    }

//    @BeforeMethod
    public void loginObject() {
        search = new SearchPage(androidDriverProvider.get());
    }

}
