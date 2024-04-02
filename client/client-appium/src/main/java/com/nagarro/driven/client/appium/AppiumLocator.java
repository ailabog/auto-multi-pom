package com.nagarro.driven.client.appium;

import com.nagarro.driven.client.ui.api.UnexpectedClientException;
import com.nagarro.driven.core.weblocator.file.util.WebLocatorFileTypeFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Finds the web locator element.
 *
 * @author nagarro
 */
public class AppiumLocator {

    /* The logger. */
    private static final Logger log = LoggerFactory.getLogger(AppiumLocator.class);

    /* The By instance. */
    private final By by;

    /*
     * Constructor to instantiate the by object.
     */
    public AppiumLocator(final By by) {
        this.by = by;
    }

    public static AppiumLocator findMobileLocatorElement(
            final String pageName, final String elementName, AppiumDriver<WebElement> driver,
            Object... dynamicLocatorValue) {
        return findMobileLocatorElement(pageName, elementName, driver, AppiumLocator::getElement, dynamicLocatorValue);
    }

    /**
     * Finds the page element.
     *
     * @param pageName    page name tag in web locator file.
     * @param elementName name of element.
     * @return instance of weblocator class.
     */
    private static AppiumLocator findMobileLocatorElement(
            final String pageName,
            final String elementName,
            AppiumDriver<WebElement> webdriver,
            BiFunction<String, String, AppiumLocator> appiumLocatorProvider, Object... dynamicLocatorValue) {
        AppiumLocator appiumLocator;
        final Map<String, String> locatorMap =
                WebLocatorFileTypeFactory.getInstance().getLocatorValue(pageName, elementName);
        if (!locatorMap.isEmpty()) {
            for (final Map.Entry<String, String> entry : locatorMap.entrySet()) {
                if (0 == dynamicLocatorValue.length) {
                    appiumLocator = appiumLocatorProvider.apply(entry.getValue(), entry.getKey());
                } else {
                    appiumLocator = appiumLocatorProvider.apply(String.format(entry.getValue(), dynamicLocatorValue),
                            entry.getKey());
                }
                try {
                    webdriver.findElement(appiumLocator.getBy());
                    return appiumLocator;
                } catch (NoSuchElementException e) {
                    log.info("Not able to find element: {} with locator : {}.", elementName, appiumLocator);
                }
            }
        } else {
            log.error(
                    "NameOfElement or SectionName is given wrong in object repository file or PageName in Page Object" +
                            " Class is wrong");
        }
        webdriver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        throw new NoSuchElementException(
                String.format(
                        "no valid WebLocator found in object repository for page: %s and element: %s",
                        pageName, elementName));
    }

    /**
     * Gets the weblocator on the basis of its type and locator value.
     *
     * @param locatorValue the value of locator
     * @param locatorType  the type of locator
     * @return the weblocator
     */
    public static AppiumLocator getElement(final String locatorValue, final String locatorType) {
        switch (locatorType.toLowerCase()) {
            case "id":
            case "accessibilityid":
            case "RuntimeId":
                return new AppiumLocator(By.id(locatorValue));
            case "name":
                return new AppiumLocator(By.name(locatorValue));
            case "classname":
            case "class":
                return new AppiumLocator(By.className(locatorValue));
            case "tagname":
            case "findelementbytagname":
                return new AppiumLocator(By.tagName(locatorValue));
            case "linktext":
                return new AppiumLocator(By.linkText(locatorValue));
            case "partiallinktext":
                return new AppiumLocator(By.partialLinkText(locatorValue));
            case "cssselector":
                return new AppiumLocator(By.cssSelector(locatorValue));
            case "xpath":
            case "findelementbyxpath":
                return new AppiumLocator(By.xpath(locatorValue));
            default:
                throw new UnexpectedClientException("Can't create locators for " + locatorValue);
        }
    }

    public static AppiumLocatorBuilder forType(String locatorType) {
        return new AppiumLocatorBuilder(locatorType);
    }

    /**
     * Gets the by object.
     *
     * @return the by object
     */
    public By getBy() {
        return by;
    }

    @Override
    public String toString() {
        return by.toString();
    }
}
