package com.nagarro.driven.client.appium;

import com.nagarro.driven.client.ui.api.Elements;
import com.nagarro.driven.core.driver.api.IDriver;
import com.nagarro.driven.core.reporting.api.TestReportLogger;
import com.nagarro.driven.core.reporting.api.TestStatus;
import com.nagarro.driven.core.util.AutomationFrameworkException;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.windows.WindowsDriver;
import io.selendroid.server.common.exceptions.NoSuchElementException;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static com.nagarro.driven.core.reporting.api.TestStatus.FAIL;
import static java.lang.String.format;

public class AppiumDriverWinApp extends AbstractWinAppAppiumDriver implements IDriver {

    private static final Logger LOG = LoggerFactory.getLogger(AppiumDriverWinApp.class);
    private final WindowsDriver<WebElement> winAppDriver;

    public AppiumDriverWinApp(AppiumDriver<WebElement> driver, TestReportLogger reportLog) {
        super(driver, reportLog);
        this.winAppDriver = (WindowsDriver<WebElement>) driver;
    }

    /**
     * Gives the driver for the already open app on the screen by passing the name of the window of
     * that app. Also to avoid the script failure that caused due to the splash screen of some
     * applications
     */
    public AppiumDriverWinApp getInstanceOfApp(String windowNameOfApp, TestReportLogger reportLog) {
        // Create a session for Desktop
        try {
            DesiredCapabilities desktopCapabilities = new DesiredCapabilities();
            desktopCapabilities.setCapability("app", "Root");
            WindowsDriver<WebElement> desktopSession =
                    new WindowsDriver<>(new URL("http://127.0.0.1:4723"), desktopCapabilities);

            WebElement appWindow = desktopSession.findElementByName(windowNameOfApp);
            String windowHandle =
                    Integer.toHexString(Integer.parseInt(appWindow.getAttribute("NativeWindowHandle")))
                            .toUpperCase();

            DesiredCapabilities appCapabilities = new DesiredCapabilities();
            appCapabilities.setCapability("appTopLevelWindow", "0x" + windowHandle);
            WindowsDriver<WebElement> appSession =
                    new WindowsDriver<>(new URL("http://127.0.0.1:4723"), appCapabilities);
            AppiumDriverFactory.addSnapshotDriver();
            return new AppiumDriverWinApp(appSession, reportLog);
        } catch (MalformedURLException ex) {
            getReportLog()
                    .reportLogger(
                            FAIL,
                            format(
                                    "Exception occurred while getting the driver for this app with window name %s : %s",
                                    windowNameOfApp, ex.getMessage()));
            throw new AutomationFrameworkException(
                    format(
                            "Exception occurred while getting the driver for this app with window name %s : %s",
                            windowNameOfApp, ex.getMessage()),
                    ex);
        } catch (NoSuchElementException ex) {
            getReportLog()
                    .reportLogger(
                            FAIL,
                            format(
                                    "Exception occurred while getting window with given window name %s : %s",
                                    windowNameOfApp, ex.getMessage()));
            throw new AutomationFrameworkException(
                    format(
                            "Exception occurred while getting window with given window name %s : %s",
                            windowNameOfApp, ex.getMessage()),
                    ex);
        }
    }

    /**
     * Switches to the element that currently has focus within the document currently "switched to",
     * or the body element if this cannot be detected. This matches the semantics of calling
     * "document.activeElement" in Javascript.Returns:The WebElement with focus, or the body element
     * if no element with focus can be detected.
     */
    public WebElement getActiveElement() {
        LOG.debug("Fetching the active element");
        WebElement activeElement = winAppDriver.switchTo().activeElement();
        getReportLog()
                .reportLogger(
                        TestStatus.PASS, "Active element value: " + "\"" + activeElement.getText() + "\"");
        return activeElement;
    }

    /**
     * Navigate forward on the windows browser application Microsoft Edge
     */
    public void forward() {
        LOG.debug("Navigating forward");
        winAppDriver.navigate().back();
        getReportLog().reportLogger(TestStatus.PASS, "Navigating forward ");
    }

    /**
     * Scroll to element and click
     */
    public void scrollToElement(WebElement windowElement) {
        new Actions(winAppDriver).moveToElement(windowElement).build().perform();
    }

    /**
     * Click on the window Element
     */
    public void click(WebElement windowElement) {
        LOG.debug("Clicking on element: {}", windowElement);
        windowElement.click();
        getReportLog()
                .reportLogger(
                        TestStatus.PASS, "Clicking on the element: " + "\"" + windowElement.getText() + "\"");
    }

    /**
     * Close the window application
     */
    public void closeWindowApp() {
        LOG.debug("Closing the windoow Applicaition");
        winAppDriver.close();
        getReportLog().reportLogger(TestStatus.PASS, "Window Application Closed Successfully");
    }

    /**
     * Launching/relaunching the window application
     */
    public void launchWindowApp() {
        LOG.debug("Launching the window Applicaition");
        winAppDriver.launchApp();
        getReportLog().reportLogger(TestStatus.PASS, "Window Application Launched Successfully");
    }

    //

    /**
     * Returns the window size and save it in Dimension data type.
     */
    public Dimension getWindowSize() {
        LOG.debug("Retrieving window size");
        Dimension winDimension = winAppDriver.manage().window().getSize();
        getReportLog().reportLogger(TestStatus.PASS, "Window size:" + "\"" + winDimension + "\"");
        return winDimension;
    }

    /**
     * Returns the windowPosition left hand side top co-ordinates
     */
    public Point getWindowPositionXY() {
        LOG.debug("Retrieving the window xy LHS co-ordinates");
        Point point = winAppDriver.manage().window().getPosition();
        getReportLog()
                .reportLogger(TestStatus.PASS, "Extreme LHS window co-ordinates:" + "\"" + point + "\"");
        return point;
    }

    /**
     * maximize the window application to full size
     */
    public void maximizeWindow() {
        LOG.debug("Maximizing the window application");
        winAppDriver.manage().window().maximize();
        getReportLog().reportLogger(TestStatus.PASS, "Window Application Maximized to full size");
    }

    /**
     * Returns the hex value for one window handle
     */
    public String getWindowHandle() {
        LOG.debug("Retreiving the window handle");
        String currentWindowHandle = winAppDriver.getWindowHandle();
        getReportLog()
                .reportLogger(
                        TestStatus.PASS, "CurrentWindowHandle Val:" + "\"" + currentWindowHandle + "\"");
        return currentWindowHandle;
    }

    /**
     * Maximizes the current window handle. It is an alternative to maximizeWindow() method.
     */
    public void windowHandleMaximize() {
        LOG.debug("Maximizing the window handle");
        String currentWindowHandle = getWindowHandle();
        winAppDriver.switchTo().window(currentWindowHandle).manage().window().maximize();
    }

    /**
     * Sets the size of the window handle. currentWindowHandle parameter can be passed from method
     * getWindowHandle() dim parameter can be passed getWindowSize() method
     */
    public void windowHandleSetSize(String currentWindowHandle, Dimension dim) {
        LOG.debug("Setting the size of the window handle");
        winAppDriver.switchTo().window(currentWindowHandle).manage().window().setSize(dim);
        getReportLog()
                .reportLogger(
                        TestStatus.PASS,
                        "Window Application Maximized to full size using windowHandleMaximize method");
    }

    /**
     * Gets the top left position of the window handle. currentWindowHandle parameter can be passed
     * from method getWindowHandle() method. The point parameter can be passed from
     * windowHandleGetPosition() method.
     */
    public void windowHandleSetPosition(String currentWindowHandle, Point point) {
        LOG.debug("Setting the top left position of window handle");
        winAppDriver.switchTo().window(currentWindowHandle).manage().window().setPosition(point);
    }

    /**
     * Gets the top left position of the window handle. currentWindowHandle parameter can be passed
     * from method getWindowHandle()
     */
    public Point windowHandleGetPosition(String currentWindowHandle) {

        LOG.debug("Fetching the top left position of window handle");
        Point point =
                winAppDriver.switchTo().window(currentWindowHandle).manage().window().getPosition();
        getReportLog().reportLogger(TestStatus.PASS, "The top left position is:" + "\"" + point + "\"");
        return point;
    }

    /**
     * Returns the hex value for all window handle in Set&lt;String&gt;
     */
    public Set<String> getWindowHandleSet() {
        LOG.debug("Retreiving the window handle set hex values");
        Set<String> allWindowHandlesSet = winAppDriver.getWindowHandles();
        getReportLog()
                .reportLogger(
                        TestStatus.PASS,
                        "The hex value for getWindowHandleSet" + "\"" + allWindowHandlesSet + "\"");
        return allWindowHandlesSet;
    }

    /**
     * Returns size of the set for windowHandle.
     */
    public int getWindowHandleSize(Set<String> windowHandlesSet) {
        LOG.debug("Fetching the size of the window handle");
        int size = windowHandlesSet.size();
        getReportLog().reportLogger(TestStatus.PASS, "The size of the set is" + "\"" + size + "\"");
        return size;
    }

    /**
     * Get the id of the session created using desired capabilities
     */
    public String getSessionID() {
        LOG.debug("Retrieving Session ID");
        String sessionId = winAppDriver.getSessionId().toString();
        getReportLog().reportLogger(TestStatus.PASS, "The session id value:" + "\"" + sessionId + "\"");
        return sessionId;
    }

    /**
     * Get the entire page source
     */
    public String getPageSource() {
        LOG.debug("Retrieving getPageSource code");
        String getPageSource = winAppDriver.getPageSource();
        getReportLog()
                .reportLogger(TestStatus.PASS, "The whole page source:" + "\"" + getPageSource + "\"");
        return getPageSource;
    }

    /**
     * Get the session details of the parameters to winappdriver to create driver
     */
    public Map<String, Object> getSessionDetails() {
        LOG.debug("Retrieving getSessionDetails in Map");
        Map<String, Object> getSessionDetails = winAppDriver.getSessionDetails();
        getReportLog()
                .reportLogger(
                        TestStatus.PASS,
                        "The session details of the parameters to winappdriver to create driver"
                                + "\""
                                + getSessionDetails
                                + "\"");
        return getSessionDetails;
    }

    /**
     * Fetch the app orientation i.e LANDSCAPE or POTRAITS
     */
    public ScreenOrientation getWindowAppOrientation() {
        LOG.debug("Fetching window app orientation");
        ScreenOrientation orientation = winAppDriver.getOrientation();
        getReportLog()
                .reportLogger(TestStatus.PASS, "Window App orientation is:" + "\"" + orientation + "\"");
        return orientation;
    }

    public String getWindowTitle() {
        LOG.debug("Fetching window app title");
        String appTitle = winAppDriver.getTitle();
        getReportLog().reportLogger(TestStatus.PASS, "Window App title is:" + "\"" + appTitle + "\"");
        return appTitle;
    }

    /**
     * Get the window position extreme upper LHS co-ordinates
     */
    public Point getWindowPosition() {
        LOG.debug("Retrieving the window position");
        Point getPosition = winAppDriver.manage().window().getPosition();
        getReportLog()
                .reportLogger(TestStatus.PASS, "getPosition value is" + "\"" + getPosition + "\"");
        return getPosition;
    }

    /**
     * Set the window postion on the screen
     */
    public void setWindowPosition(Point targetPosition) {
        LOG.debug("Setting the position of the window on the screen");
        winAppDriver.manage().window().setPosition(targetPosition);
    }

    /**
     * Implicit wait
     */
    public void implicitlyWait() {
        LOG.debug("Implicit wait from applied to the suite");
        winAppDriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    public void back() {
        LOG.debug("Navigating back on the page");
        winAppDriver.navigate().back();
        getReportLog().reportLogger(TestStatus.PASS, "The user navigated back on the page");
    }

    @SuppressWarnings("rawtypes")
    @Override
    public Elements elements(AppiumLocator locator) {
        return new WindowElements(locator);
    }
}
