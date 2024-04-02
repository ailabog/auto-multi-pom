package com.nagarro.driven.client.appium;

public class AppiumLocatorBuilder {

    private String locatorType;

    public AppiumLocatorBuilder(String locatorType) {
        this.locatorType = locatorType;
    }

    public AppiumLocator withLocator(String locatorValue) {
        return AppiumLocator.getElement(locatorValue, locatorType);
    }
}
