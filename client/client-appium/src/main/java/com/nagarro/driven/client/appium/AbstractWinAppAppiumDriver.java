package com.nagarro.driven.client.appium;

import com.nagarro.driven.client.ui.api.*;
import com.nagarro.driven.core.reporting.api.TestReportLogger;
import com.nagarro.driven.core.reporting.api.TestStatus;
import com.nagarro.driven.core.util.Waiter;
import io.appium.java_client.AppiumDriver;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static java.util.stream.Collectors.toList;

/**
 * AbstractWinAppDriver is an abstract class which contains all the events/functions related to
 * winapp.
 *
 * @author nagarro
 */
public abstract class AbstractWinAppAppiumDriver extends AbstractAppiumDriver implements UiClientWindows<AppiumLocator> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractWinAppAppiumDriver.class);
    private final AppiumDriver<WebElement> winAppDriver;

    /**
     * Initializes the driver and reportLog
     */
    protected AbstractWinAppAppiumDriver(AppiumDriver<WebElement> driver, TestReportLogger reportLog) {
        super(driver, reportLog);
        this.winAppDriver = driver;
    }

    public TestReportLogger getReportLog() {
        return reportLog;
    }

    /**
     * Closes the current instance of winappdriver
     */
    public void close() {
        LOG.debug("Closing the windoow Applicaition");
        winAppDriver.close();
        reportLog.reportLogger(TestStatus.PASS, "Window Application Closed Successfully");
    }

    /**
     * Launches the app
     */
    public void launchApp() {
        LOG.debug("Launching the window Applicaition");
        winAppDriver.launchApp();
        reportLog.reportLogger(TestStatus.PASS, "Window Application Launched Successfully");
    }

    /**
     * *
     *
     * <p>Scrools the page upwards
     */
    @Override
    public void pageUp() {
        Dimension window = winAppDriver.manage().window().getSize();
        new Actions(winAppDriver)
                .moveByOffset(window.getWidth() / 2, window.getHeight())
                .clickAndHold()
                .moveByOffset(window.getWidth() / 2, 1)
                .release()
                .build()
                .perform();
    }

    /**
     * Scrolls the page downwards
     */
    @Override
    public void pageDown() {
        Dimension window = winAppDriver.manage().window().getSize();
        new Actions(winAppDriver)
                .moveByOffset(window.getWidth() / 2, 1)
                .clickAndHold()
                .moveByOffset(window.getWidth() / 2, window.getHeight())
                .release()
                .build()
                .perform();
    }

    public History history() {
        return new WindowsHistory();
    }

    @Override
    public void clickByCoordinate(int x, int y) {
        new Actions(winAppDriver).moveByOffset(x, y).click().build().perform();
    }

    public AbstractElements elements(String pageName, String elementName) {
        return new WindowElements(pageName, elementName);
    }

    public String driverName() {
        return this.getClass().getSimpleName();
    }

    @Override
    public ElementWindows element(AppiumLocator locator) {
        return new WindowElement(locator);
    }

    @Override
    public ElementWindows element(String pageName, String elementName) {
        return new WindowElement(pageName, elementName);
    }

    @Override
    public ElementWindows element(
            String pageName, String elementName, Object... dynamicLocatorValue) {
        return new WindowElement(pageName, elementName, dynamicLocatorValue);
    }

    /**
     * *
     *
     * <p>WindowElement contains the methods/events for windowsElement
     */
    public class WindowElement extends Element
            implements com.nagarro.driven.client.ui.api.ElementWindows {

        WebElement windowsElement;
        String elementName;

        public WindowElement(AppiumLocator locator) {
            this(winAppDriver.findElement(locator.getBy()));
        }

        private WindowElement(String pageName, String elementName, Object... dynamicLocatorValue) {
            this(
                    AppiumLocator.findMobileLocatorElement(
                            pageName, elementName, winAppDriver, dynamicLocatorValue));
            this.elementName = elementName;
            AbstractAppiumDriver.elementName = elementName;
        }

        private WindowElement(WebElement webElement) {
            super(webElement);
            this.windowsElement = webElement;
        }

        /**
         * Compare the two objects for equality
         */
        public boolean equalsByElementText(String obj) {
            LOG.debug("Validating the windowElement with expected data");
            boolean bool = windowsElement.getText().equals(obj);
            reportLog.reportLogger(TestStatus.PASS, "The element is verified" + "\"" + bool + "\"");

            return bool;
        }

        @Override
        public void swipeElement(int x, int y) {
            new Actions(winAppDriver)
                    .clickAndHold(windowsElement)
                    .moveByOffset(x, y)
                    .release()
                    .build()
                    .perform();
        }

        @Override
        public String getCssValue(String propertyName) {
            LOG.debug("Fetching the css value");
            reportLog.reportLogger(TestStatus.PASS, "Css value:" + "\"" + propertyName + "\"");
            return windowsElement.getCssValue(propertyName);
        }

        @Override
        public String elementTagName() {
            LOG.debug("Fetching element tagname");
            String tagName = windowsElement.getTagName();
            reportLog.reportLogger(TestStatus.PASS, "The element tagname:" + "\"" + tagName + "\"");

            return tagName;
        }

        @Override
        public void doubleClick() {
            LOG.debug("Double click action item");
            Actions action = new Actions(winAppDriver);
            action.doubleClick(windowsElement).perform();
            reportLog.reportLogger(
                    TestStatus.PASS,
                    "The operation double click was performed on:" + "\"" + this.elementName + "\"");
        }
    }

    public class WindowElements extends AbstractElements
            implements com.nagarro.driven.client.ui.api.Elements {

        private final List<WindowElement> elements;
        String elementName;

        public WindowElements(AppiumLocator mobileWebLocator) {
            super(mobileWebLocator);
            elements =
                    winAppDriver.findElements(mobileWebLocator.getBy()).stream()
                            .map(WindowElement::new)
                            .collect(toList());
            Waiter.waitFor(elements::isEmpty);
        }

        private WindowElements(String pageName, String elementName) {
            this(AppiumLocator.findMobileLocatorElement(pageName, elementName, winAppDriver));
            this.elementName = elementName;
            AbstractAppiumDriver.elementName = elementName;
        }

        public void clickByAttribute(String attribute, String value) {
            for (WindowElement windowElement : elements) {
                if (windowElement.getAttribute(attribute).equals(value)) {
                    windowElement.click();
                    break;
                }
            }
        }

        @Override
        public void selectFromOptions(String visibleText) {
            reportLog.reportLogger(
                    TestStatus.PASS, "Selecting an Option from dropdown " + "\"" + visibleText + "\"");
            elements.stream()
                    .filter(e -> e.getText().equals(visibleText))
                    .findFirst()
                    .ifPresent(Element::click);
        }

        @Override
        public List<String> getText() {
            return elements.stream()
                    .filter(Objects::nonNull)
                    .map(WindowElement::getText)
                    .filter(StringUtils::isNotEmpty)
                    .collect(toList());
        }
    }

    private class WindowsHistory implements History {

        private WindowsHistory() {
        }

        @Override
        public void back() {
            LOG.debug("Navigating back");
            winAppDriver.navigate().back();
            reportLog.reportLogger(TestStatus.PASS, "Navigating back ");
        }

        @Override
        public void forward() {
            LOG.debug("Navigating forward");
            winAppDriver.navigate().forward();
            reportLog.reportLogger(TestStatus.PASS, "Navigating forward ");
        }
    }

    @SuppressWarnings("unused")
    private class WinAppWindow implements WindowFrame {

        public WinAppWindow() {
            // nothing to do
        }

        @Override
        public void close() {
            LOG.debug("Closing browser window");
            winAppDriver.close();
            reportLog.reportLogger(TestStatus.PASS, "Closing browser window");
        }

        @Override
        public String getTitle() {
            LOG.debug("Retrieving window title");
            String title = winAppDriver.getTitle();
            LOG.debug("Title: {}", title);
            reportLog.reportLogger(TestStatus.PASS, "Retrieving window title " + "\"" + title + "\"");
            return title;
        }

        @Override
        public void maximize() {
            LOG.debug("Maximizing browser window");
            winAppDriver.manage().window().maximize();
            reportLog.reportLogger(TestStatus.PASS, "Maximizing browser window");
        }

        @Override
        public void fullscreen() {
            LOG.debug("Setting browser window to fullscreen");
            winAppDriver.manage().window().fullscreen();
            reportLog.reportLogger(TestStatus.PASS, "Setting browser window to fullscreen");
        }

        @Override
        public com.nagarro.driven.client.ui.api.Dimension getSize() {
            LOG.debug("Retrieving window size");
            org.openqa.selenium.Dimension size = winAppDriver.manage().window().getSize();
            LOG.debug("Window size - width: {}, height: {}", size.width, size.height);
            reportLog.reportLogger(
                    TestStatus.PASS,
                    "Getting window size - width "
                            + "\""
                            + size.width
                            + "\""
                            + " height "
                            + "\""
                            + size.height
                            + "\"");
            return new com.nagarro.driven.client.ui.api.Dimension(size.width, size.height);
        }

        @Override
        public void setSize(com.nagarro.driven.client.ui.api.Dimension size) {
            LOG.debug("Setting window size to {}", size);
            winAppDriver
                    .manage()
                    .window()
                    .setSize(new org.openqa.selenium.Dimension(size.getWidth(), size.getHeight()));
            reportLog.reportLogger(
                    TestStatus.PASS,
                    "Setting window size - width "
                            + "\""
                            + size.getWidth()
                            + "\""
                            + " height "
                            + "\""
                            + size.getHeight()
                            + "\"");
        }

        @Override
        public String getWindowHandle() {
            LOG.debug("Retreiving the window handle");
            String currentWindowHandle = winAppDriver.getWindowHandle();
            reportLog.reportLogger(
                    TestStatus.PASS, String.format("CurrentWindowHandle Val: %s", currentWindowHandle));
            return currentWindowHandle;
        }

        @Override
        public void switchToWindowHandle(int window) {
            LOG.debug("Switching to the window handle");
            Set<String> windowHandles = winAppDriver.getWindowHandles();
            winAppDriver.switchTo().window(String.valueOf(windowHandles.toArray()[window]));
            getReportLog()
                    .reportLogger(
                            TestStatus.PASS,
                            "CurrentWindowHandle Val:" + "\"" + winAppDriver.getWindowHandle() + "\"");
        }

        @Override
        public void closeAllwindowExceptTheCurrentOne() {
            LOG.debug("Closing all windows except the current one");
            String currentWindow = winAppDriver.getWindowHandle();
            for (String window : winAppDriver.getWindowHandles()) {
                if (!window.equals(currentWindow)) {
                    winAppDriver.switchTo().window(window).close();
                }
            }
            winAppDriver.switchTo().window(currentWindow);
            getReportLog()
                    .reportLogger(TestStatus.PASS, "CurrentWindowHandle Val:" + "\"" + currentWindow + "\"");
        }

        @Override
        public void windowHandleMaximize() {
            LOG.debug("Maximizing the window handle");
            String currentWindowHandle = getWindowHandle();
            winAppDriver.switchTo().window(currentWindowHandle).manage().window().maximize();
        }

        @Override
        public void windowHandleSetSize(
                String currentWindowHandle, com.nagarro.driven.client.ui.api.Dimension dim) {
            LOG.debug("Setting the size of the window handle");
            winAppDriver
                    .switchTo()
                    .window(currentWindowHandle)
                    .manage()
                    .window()
                    .setSize(new org.openqa.selenium.Dimension(dim.getWidth(), dim.getHeight()));
            reportLog.reportLogger(
                    TestStatus.PASS,
                    "Window Application Maximized to full size using windowHandleMaximize method");
        }

        @Override
        public void windowHandleSetPosition(String currentWindowHandle, Location location) {
            LOG.debug("Setting the top left position of window handle");
            winAppDriver
                    .switchTo()
                    .window(currentWindowHandle)
                    .manage()
                    .window()
                    .setPosition(new Point(location.getX(), location.getY()));
        }

        @Override
        public Location windowHandleGetPosition(String currentWindowHandle) {
            LOG.debug("Fetching the top left position of window handle");
            Point point =
                    winAppDriver.switchTo().window(currentWindowHandle).manage().window().getPosition();
            reportLog.reportLogger(TestStatus.PASS, "The top left position is:" + "\"" + point + "\"");
            return new Location(point.getX(), point.getY());
        }

        @Override
        public Set<String> getWindowHandleSet() {
            LOG.debug("Retreiving the window handle set hex values");
            Set<String> allWindowHandlesSet = winAppDriver.getWindowHandles();
            reportLog.reportLogger(
                    TestStatus.PASS,
                    "The hex value for getWindowHandleSet" + "\"" + allWindowHandlesSet + "\"");
            return allWindowHandlesSet;
        }

        @Override
        public int getWindowHandleSize(Set<String> windowHandlesSet) {
            LOG.debug("Fetching the size of the window handle");
            int size = windowHandlesSet.size();
            reportLog.reportLogger(TestStatus.PASS, "The size of the set is" + "\"" + size + "\"");
            return size;
        }
    }
}
