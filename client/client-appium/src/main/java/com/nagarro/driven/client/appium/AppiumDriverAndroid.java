package com.nagarro.driven.client.appium;

import com.nagarro.driven.client.ui.api.AndroidActions;
import com.nagarro.driven.core.driver.api.IDriver;
import com.nagarro.driven.core.reporting.api.TestReportLogger;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.battery.BatteryInfo;
import org.openqa.selenium.WebElement;

public class AppiumDriverAndroid extends AbstractMobileAppiumDriver
        implements IDriver, AndroidActions {

    private AndroidDriver<WebElement> driver;

    public AppiumDriverAndroid(AppiumDriver<WebElement> androidDriver, TestReportLogger reportLog) {
        super(androidDriver, reportLog);
        driver = (AndroidDriver<WebElement>) androidDriver;
    }

    /**
     * gets the current page URL
     */
    @Override
    public String getURL() {
        return driver.getCurrentUrl();
    }

    /**
     * press the back key of mobile
     */
    @Override
    public void pressAndroidBackKey() {
        driver.pressKey(new KeyEvent(AndroidKey.BACK));
    }

    /**
     * Hides keyboard of device
     */
    @Override
    public void hideKeyboard() {
        driver.hideKeyboard();
    }

    /**
     * Closes the current app
     */
    @Override
    public void closeApp() {
        driver.closeApp();
    }

    // A

    /**
     * Returns battery level of phone
     */
    @Override
    public double batteryLevel() {
        BatteryInfo battery = driver.getBatteryInfo();
        return battery.getLevel();
    }

    /**
     * Check if the keyboard is displayed. Returns:true if keyboard is displayed. False otherwise
     */
    @Override
    public boolean isKeyboardShown() {
        return driver.isKeyboardShown();
    }

    // A

    /**
     * Open the notification shade, on Android devices.
     */
    @Override
    public void openNotifications() {
        driver.openNotifications();
    }

    @Override
    public void unlockDevice() {
        driver.unlockDevice();
    }

    @Override
    public boolean isDeviceLocked() {
        return driver.isDeviceLocked();
    }

    @Override
    public void pressEnter() {
        driver.pressKey(new KeyEvent(AndroidKey.ENTER));
    }

    /**
     * Scroll the container whose id is given upto the element whose text is provided
     */
    @Override
    public void scrollByIdUptoText(String idOfElementToScroll, String text) {
        driver.findElementByAndroidUIAutomator(
                "UiScrollable(UiSelector().resourceId(\""
                        + idOfElementToScroll
                        + "\")).scrollIntoView(UiSelector().textContains(\""
                        + text
                        + "\"))");
    }

    /**
     * Scroll the container whose class is given upto the element whose text is provided
     */
    @Override
    public void scrollByClassNameUptoText(String classOfElementToScroll, String text) {
        driver.findElementByAndroidUIAutomator(
                "UiScrollable(UiSelector().className(\""
                        + classOfElementToScroll
                        + "\")).scrollIntoView(UiSelector().textContains(\""
                        + text
                        + "\"))");
    }

    /**
     * Scroll the container whose id is given upto the element whose description is provided
     */
    @Override
    public void scrollByIdUptoDescription(String idOfElementToScroll, String description) {
        driver.findElementByAndroidUIAutomator(
                "UiScrollable(UiSelector().resourceId(\""
                        + idOfElementToScroll
                        + "\")).scrollIntoView(UiSelector().descriptionContains(\""
                        + description
                        + "\"))");
    }

    /**
     * Scroll the container whose classname is given upto the element whose description is provided
     */
    @Override
    public void scrollByClassNameUptoDescription(String classOfElementToScroll, String description) {
        driver.findElementByAndroidUIAutomator(
                "UiScrollable(UiSelector().className(\""
                        + classOfElementToScroll
                        + "\")).scrollIntoView(UiSelector().descriptionContains(\""
                        + description
                        + "\"))");
    }

    /**
     * returns driver name
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
