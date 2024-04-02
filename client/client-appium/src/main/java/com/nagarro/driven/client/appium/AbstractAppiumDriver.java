package com.nagarro.driven.client.appium;

import com.nagarro.driven.client.ui.api.*;
import com.nagarro.driven.core.driver.api.IDriver;
import com.nagarro.driven.core.reporting.api.TestReportLogger;
import com.nagarro.driven.core.reporting.api.TestStatus;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AbstractDriver is an abstract class which contains all the events/functions related to appium.
 *
 * @author nagarro
 */
public abstract class AbstractAppiumDriver implements UiClientAppium, IDriver {

    private static final Logger log = LoggerFactory.getLogger(AbstractAppiumDriver.class);
    static String elementName;
    protected final TestReportLogger reportLog;
    private final AppiumDriver<WebElement> driver;

    /**
     * Assigns the driver and creates the object for TestReportLogger.
     */
    protected AbstractAppiumDriver(
            AppiumDriver<WebElement> appiumDriver, TestReportLogger reportLog) {
        this.driver = appiumDriver;
        this.reportLog = reportLog;
    }

    /**
     * Quits the driver instance
     */
    public void quit() {
        log.debug("Closing AppiumDriver");
        driver.quit();
        reportLog.reportLogger(TestStatus.PASS, "Closed Appium Driver");
    }

    @Override
    public void cleanUp() {
        quit();
    }

    /**
     * ** Element class contains all the common elements and their methods for appium
     *
     * @author nagarro
     */
    public class Element implements ElementAppium {

        private final WebElement appiumElement;

        public Element(AppiumLocator locator) {
            this(driver.findElement(locator.getBy()));
        }

        protected Element(WebElement webElement) {
            this.appiumElement = webElement;
        }

        /**
         * This method checks if the element is selected
         */
        @Override
        public boolean isSelected() {
            log.debug("Retrieving select status for element: {}", appiumElement);
            boolean selected = appiumElement.isSelected();
            log.debug("Selected: {}", selected);
            reportLog.reportLogger(
                    TestStatus.PASS,
                    "Select status of element: "
                            + "\""
                            + elementName
                            + "\""
                            + " is "
                            + "\""
                            + selected
                            + "\"");
            return selected;
        }

        /**
         * This method gets the attribute of the element
         */
        @Override
        public String getAttribute(String attributeName) {
            log.debug("Retrieving attribute {} from element: {}", attributeName, appiumElement);
            String attribute = appiumElement.getAttribute(attributeName);
            log.debug("{}: {}", attributeName, attribute);
            reportLog.reportLogger(
                    TestStatus.PASS,
                    "Attribute of element: " + "\"" + elementName + "\"" + " is " + "\"" + attribute + "\"");
            return attribute;
        }

        /**
         * This method gets the text value of the element
         */
        @Override
        public String getText() {
            log.debug("Retrieving text from element: {}", appiumElement);
            String text = appiumElement.getText();
            log.debug("Text: {}", text);
            reportLog.reportLogger(
                    TestStatus.PASS,
                    "Text of element: " + "\"" + elementName + "\"" + " is " + "\"" + text + "\"");
            return text;
        }

        /**
         * This method gets the type of element
         */
        @Override
        public String getElementType() {
            log.debug("Retrieving tag name from element: {}", appiumElement);
            String tagName = appiumElement.getTagName();
            log.debug("Tag name: {}", tagName);
            reportLog.reportLogger(
                    TestStatus.PASS,
                    "Tag name of element: " + "\"" + elementName + "\"" + " is " + "\"" + tagName + "\"");
            return tagName;
        }

        /**
         * *
         *
         * <p>This method checks if the element is enabled
         *
         * <p>*
         */
        @Override
        public boolean isEnabled() {
            log.debug("Retrieving enabled status from element: {}", appiumElement);
            boolean enabled = appiumElement.isEnabled();
            log.debug("Enabled: {}", enabled);
            reportLog.reportLogger(
                    TestStatus.PASS,
                    "Enabled status of element: "
                            + "\""
                            + elementName
                            + "\""
                            + " is "
                            + "\""
                            + enabled
                            + "\"");
            return enabled;
        }

        /**
         * This method checks if the element is displayed *
         */
        @Override
        public boolean isDisplayed() {
            log.debug("Retrieving displayed status from element: {}", appiumElement);
            try {
                boolean displayed = appiumElement.isDisplayed();
                log.debug("Displayed: {}", displayed);
                reportLog.reportLogger(
                        TestStatus.PASS,
                        "Display status of element: "
                                + "\""
                                + elementName
                                + "\""
                                + " is "
                                + "\""
                                + displayed
                                + "\"");
                return displayed;
            } catch (NoSuchElementException e) {
                log.error("Displayed: Couldn't find element");
                reportLog.reportLogger(TestStatus.ERROR, "Displayed: Couldn't find element");
                return false;
            }
        }

        /**
         * This method clicks on the element
         */
        @Override
        public void click() {
            log.debug("Clicking on element: {}", appiumElement);
            appiumElement.click();
            reportLog.reportLogger(TestStatus.PASS, "clicking the element: " + "\"" + elementName + "\"");
        }

        /**
         * This method clears the element
         */
        @Override
        public void clear() {
            log.debug("Clearing element: {}", appiumElement);
            appiumElement.clear();
            reportLog.reportLogger(TestStatus.PASS, "Clearing element " + "\"" + elementName + "\"");
        }

        /**
         * *
         *
         * <p>This method send text to an element
         */
        @Override
        public void sendKeys(String keys) {
            log.debug("Sending keys {} to element: {}", keys, appiumElement);
            appiumElement.sendKeys(keys);
            reportLog.reportLogger(
                    TestStatus.PASS, "Sending Keys " + keys + " to element: " + "\"" + elementName + "\"");
        }

        /**
         * This method takes screenshot
         */
        @Override
        public File takeScreenshot() {
            log.debug("Taking screenshot");
            reportLog.reportLogger(TestStatus.PASS, "Taking screenshot ");
            return appiumElement.getScreenshotAs(OutputType.FILE);
        }

        /**
         * This method selects dropdown
         */
        @Override
        public void selectDropdown(String visibleText) {
            Select select = new Select(appiumElement);
            select.selectByVisibleText(visibleText);
            reportLog.reportLogger(
                    TestStatus.PASS, "Selecting dropdown for " + "\"" + visibleText + "\"");
        }

        /**
         * *
         *
         * <p>This method hovers on the element
         */
        @Override
        public void hover() {
            Actions action = new Actions(driver);
            action.moveToElement(appiumElement).perform();
        }

        /**
         * This methos gets the location of the element
         */
        @Override
        public Location getLocation() {
            Point p = appiumElement.getLocation();
            return new Location(p.x, p.y);
        }

        /**
         * This method scrolls the page upto the particular mobileElement given.
         */
        @Override
        public void scrollToElement() {
            log.debug("Scrolling to element: {}", appiumElement);
            TouchActions action = new TouchActions(driver);
            action.scroll(appiumElement, 10, 100);
            action.perform();
            reportLog.reportLogger(
                    TestStatus.PASS,
                    "Scrolling to element: " + "\"" + elementName + "\"" + " is done " + "\"");
        }
    }

    /**
     * Elements class is an abstract class that contains the list of elements
     */
    public abstract class AbstractElements implements Elements {

        private final List<Element> foundElements;

        protected AbstractElements(AppiumLocator mobileWebLocator) {
            foundElements =
                    driver.findElements(mobileWebLocator.getBy()).stream()
                            .map(Element::new)
                            .collect(Collectors.toList());
        }

        @Override
        public void click() {
            foundElements.forEach(Interactable::click);
            reportLog.reportLogger(TestStatus.PASS, "Clicking the elements ");
        }

        @Override
        public void clear() {
            foundElements.forEach(Interactable::clear);
            reportLog.reportLogger(TestStatus.PASS, "Clearing the elements ");
        }

        @Override
        public void sendKeys(String keys) {
            reportLog.reportLogger(TestStatus.PASS, "Sending key " + "\"" + keys + "\"");
            foundElements.forEach(e -> e.sendKeys(keys));
        }

        @Override
        public void selectDropdown(String visibleText) {
            reportLog.reportLogger(
                    TestStatus.PASS, "Selecting dropdown for " + "\"" + visibleText + "\"");
            foundElements.forEach(e -> e.selectDropdown(visibleText));
        }

        @Override
        public int size() {
            reportLog.reportLogger(
                    TestStatus.PASS, "Element size is " + "\"" + foundElements.size() + "\"");
            return foundElements.size();
        }

        @Override
        public List<Element> getElementList() {
            return foundElements;
        }
    }
}
