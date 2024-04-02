package com.nagarro.driven.project.wikipedia.page_objects;

import com.nagarro.driven.client.appium.AbstractMobileAppiumDriver;
import com.nagarro.driven.core.reporting.api.KeywordReporting;
import com.nagarro.driven.core.util.Sleeper;

import static com.nagarro.driven.core.util.Sleeper.silentSleep;
import static com.nagarro.driven.core.util.Waiter.waitFor;

public class SearchPage {
    private final AbstractMobileAppiumDriver androidClient;
    private static final String PAGE_NAME = "SearchPage";
    private static final String BOOKMARK_BUTTON_ELEMENT_NAME = "BookmarkButton";
	private static final String REMOVE_FROM_LIST_BUTTON_ELEMENT_NAME = "RemoveFromListButton";


    public SearchPage(AbstractMobileAppiumDriver client) {
        this.androidClient = client;
    }

    public void searchHeading(String searchData) {
        inputInSearchField(searchData);
    }

    @KeywordReporting({"Search data is : "})
    public void inputInSearchField(String searchData) {
        waitFor(() -> androidClient.element(PAGE_NAME, "SearchFieldButton").isDisplayed());
        androidClient.element(PAGE_NAME, "SearchFieldButton").click();
        waitFor(() -> androidClient.element(PAGE_NAME, "SearchField").isDisplayed());
        androidClient.element(PAGE_NAME, "SearchField").sendKeys(searchData);
    }

    @KeywordReporting
    public void clickSearchButton() {
        waitFor(() -> androidClient.element(PAGE_NAME, "SearchedItem").isDisplayed());
        androidClient.element(PAGE_NAME, "SearchedItem").click();
    }

    @KeywordReporting
    public String getContentHeading() {
        waitFor(() -> androidClient.element(PAGE_NAME, "SearchedItem").isDisplayed());
        return androidClient.element(PAGE_NAME, "SearchedItem").getText().toLowerCase();
    }

    @KeywordReporting
    public void bookmarkSearch() {
        waitFor(() -> androidClient.element(PAGE_NAME, BOOKMARK_BUTTON_ELEMENT_NAME).isDisplayed());
        androidClient.element(PAGE_NAME, BOOKMARK_BUTTON_ELEMENT_NAME).click();
        waitFor(() -> androidClient.element(PAGE_NAME, "ListButton").isDisplayed());
        androidClient.element(PAGE_NAME, "ListButton").click();
    }

    @KeywordReporting
    public Boolean isBookmarkAdded() {
        silentSleep(3000);
        //androidClient.element(PAGE_NAME, BOOKMARK_BUTTON_ELEMENT_NAME).click();
        androidClient.element(PAGE_NAME, "MyListButton").click();
        try {
            silentSleep(3000);
            //return androidClient.element(PAGE_NAME, REMOVE_FROM_LIST_BUTTON_ELEMENT_NAME).isDisplayed();
            return androidClient.element(PAGE_NAME, "SavedLink").isDisplayed();

        } catch (Exception e) {
            return false;
        }
    }

    @KeywordReporting
    public Boolean isBookmarkRemoved() {
        return !isBookmarkAdded();
    }

    @KeywordReporting
    public void deleteBookmark() {
//        waitFor(() -> androidClient.element(PAGE_NAME, BOOKMARK_BUTTON_ELEMENT_NAME).isDisplayed());
//        androidClient.element(PAGE_NAME, BOOKMARK_BUTTON_ELEMENT_NAME).click();
        waitFor(() -> androidClient.element(PAGE_NAME, REMOVE_FROM_LIST_BUTTON_ELEMENT_NAME).isDisplayed());
        androidClient.element(PAGE_NAME, REMOVE_FROM_LIST_BUTTON_ELEMENT_NAME).click();
    }

    @KeywordReporting
    public void navigateToStartScreen() {
        waitFor(() -> androidClient.element(PAGE_NAME, "BackButtonOne").isDisplayed());
        androidClient.element(PAGE_NAME, "BackButtonOne").click();
        silentSleep(3000);
        waitFor(() -> androidClient.element(PAGE_NAME, "BackButtonTwo").isDisplayed());
        androidClient.element(PAGE_NAME, "BackButtonTwo").click();
        silentSleep(3000);
    }

    public void clickSave() {
        Sleeper.silentSleep(2000);
        waitFor(() -> androidClient.element(PAGE_NAME, "SaveButton").isDisplayed());
        androidClient.element(PAGE_NAME, "SaveButton").click();
    }
}
