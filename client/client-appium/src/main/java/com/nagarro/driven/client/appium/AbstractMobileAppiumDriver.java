package com.nagarro.driven.client.appium;

import com.nagarro.driven.client.ui.api.ElementMobile;
import com.nagarro.driven.client.ui.api.UiClientMobile;
import com.nagarro.driven.core.reporting.api.TestReportLogger;
import com.nagarro.driven.core.reporting.api.TestStatus;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.nagarro.driven.client.appium.MobileService.isServerRunning;
import static com.nagarro.driven.client.appium.MobileService.stopAppiumServer;
import static java.util.stream.Collectors.toList;

/**
 * AbstractMobileDriver is an abstract class which contains all the events/functions related to
 * mobile.
 *
 * @author nagarro
 */
public abstract class AbstractMobileAppiumDriver extends AbstractAppiumDriver
        implements UiClientMobile<AppiumLocator> {

    private static final Logger log = LoggerFactory.getLogger(AbstractMobileAppiumDriver.class);
    private final AppiumDriver<WebElement> driver;
    private final int MAXIMUM_SCRIPT_LOG_LENGTH = 100;

    protected AbstractMobileAppiumDriver(
            AppiumDriver<WebElement> driver, TestReportLogger reportLog) {
        super(driver, reportLog);
        this.driver = driver;
    }

    @Override
    public void cleanUp() {
        super.cleanUp();
        if (isServerRunning()) {
            stopAppiumServer();
        }
    }

    @Override
    public void go(String url) {
        log.info("Navigating to URL: {}", url);
        driver.get(url);
        reportLog.reportLogger(TestStatus.PASS, "Navigating to URL: " + "\"" + url + "\"");
    }

    @Override
    public String getDeviceTime() {
        return driver.getDeviceTime();
    }

    @Override
    public void waitForAppLoad() {
        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*")));
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void pageUp() {
        Dimension window = driver.manage().window().getSize();
        new TouchAction(driver)
                .longPress(PointOption.point(window.getWidth() / 2, window.getHeight()))
                .moveTo(PointOption.point(window.getWidth() / 2, 1))
                .release()
                .perform();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void pageDown() {
        Dimension window = driver.manage().window().getSize();
        new TouchAction(driver)
                .longPress(PointOption.point(window.getWidth() / 2, 1))
                .moveTo(PointOption.point(window.getWidth() / 2, window.getHeight()))
                .release()
                .perform();
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void clickByCoordinates(int x, int y) {
        new TouchAction(driver).tap(PointOption.point(x, y)).perform();
    }

    @Override
    public ElementMobile element(AppiumLocator locator) {
        return new MobileElement(locator);
    }

    @Override
    public ElementMobile element(String pageName, String elementName) {
        return new MobileElement(pageName, elementName);
    }

    @Override
    public ElementMobile element(String pageName, String elementName, Object... dynamicLocatorValue) {
        return new MobileElement(pageName, elementName, dynamicLocatorValue);
    }

    @Override
    public AbstractElements elements(AppiumLocator locator) {
        return new MobileElements(locator);
    }

    @Override
    public AbstractElements elements(String pageName, String elementName) {
        return new MobileElements(pageName, elementName);
    }

    @Override
    public Set<String> getContextHandles() {
        Set<String> handles = driver.getContextHandles();
        log.info("Found {} context handles : {}", handles.size(), handles);
        return handles;
    }

    @Override
    public void switchContext(String context) {
        log.info("Switching to : {}", context);
        driver.context(context);
    }

    @Override
    public void switchToNativeContext() {
        log.info("Switching to : Native Context");
        switchContext("NATIVE_APP");
    }

    public AppiumDriver<WebElement> getDriver() {
        log.warn("Fetching directly Appium driver. This approach should be used only if required.");
        return driver;
    }

    @Override
    public Object executeScript(String script, Object... arguments) {
        log.info("Executing {}",
                script.length() > MAXIMUM_SCRIPT_LOG_LENGTH ?
                        script.substring(0, MAXIMUM_SCRIPT_LOG_LENGTH) : script);
        return driver.executeScript(script, arguments);
    }

    /**
     * *
     *
     * <p>MobileElementt contains mobile elements and their corresponding methods
     */
    public class MobileElement extends Element implements ElementMobile {

        private final WebElement foundElement;
        String elementName;

        public MobileElement(AppiumLocator locator) {
            this(driver.findElement(locator.getBy()));
        }

        private MobileElement(String pageName, String elementName, Object... dynamicLocatorValue) {
            this(
                    AppiumLocator.findMobileLocatorElement(
                            pageName, elementName, driver, dynamicLocatorValue));
            this.elementName = elementName;
            AbstractAppiumDriver.elementName = elementName;
        }

        private MobileElement(WebElement webElement) {
            super(webElement);
            this.foundElement = webElement;
        }

        @Override
        public boolean waitForVisibility(int timeout) {

            try {
                WebDriverWait wait = new WebDriverWait(driver, timeout);
                wait.until(ExpectedConditions.visibilityOf(foundElement));
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        public void clickOnCenterOfElement() {
            Point p = ((io.appium.java_client.MobileElement) foundElement).getCenter();
            clickByCoordinates(p.x, p.y);
        }

        @SuppressWarnings("rawtypes")
        @Override
        public void swipeElement(int x, int cordinateY) {
            Point p = foundElement.getLocation();
            new TouchAction(driver)
                    .longPress(PointOption.point(p.x, p.y))
                    .moveTo(PointOption.point(x, cordinateY))
                    .release()
                    .perform();
        }
    }

    /**
     * *
     *
     * <p>MobileElements class contains list of mobile elements and their corresponding methods
     */
    @SuppressWarnings("rawtypes")
    public class MobileElements extends AbstractElements
            implements com.nagarro.driven.client.ui.api.Elements {

        String elementName;
        private List<MobileElement> elements;

        public MobileElements(AppiumLocator elementLocator) {
            super(elementLocator);
            elements =
                    driver.findElements(elementLocator.getBy()).stream()
                            .map(MobileElement::new)
                            .collect(toList());
        }

        private MobileElements(String pageName, String elementName) {
            this(AppiumLocator.findMobileLocatorElement(pageName, elementName, driver));
            this.elementName = elementName;
            AbstractAppiumDriver.elementName = elementName;
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
                    .map(Element::getText)
                    .filter(StringUtils::isNotEmpty)
                    .collect(toList());
        }

        @Override
        public int size() {
            reportLog.reportLogger(TestStatus.PASS, "Element size is " + "\"" + elements.size() + "\"");
            return elements.size();
        }
    }
}
