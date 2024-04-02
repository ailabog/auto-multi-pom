package com.nagarro.driven.client.appium;

import com.nagarro.driven.core.driver.api.IDriver;
import com.nagarro.driven.core.reporting.api.TestReportLogger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.WebElement;

public class AppiumDriverIos extends AbstractMobileAppiumDriver implements IDriver {

    IOSDriver<WebElement> driver;

    public AppiumDriverIos(AppiumDriver<WebElement> iosDriver, TestReportLogger reportLog) {
        super(iosDriver, reportLog);
        driver = (IOSDriver<WebElement>) iosDriver;
    }

    /**
     * returns the driver name
     */
    @Override
    public String driverName() {
        return this.getClass().getSimpleName();
    }

    /**
     * quits the driver
     */
    @Override
    public void cleanUp() {
        quit();
    }
}
