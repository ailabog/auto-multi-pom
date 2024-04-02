package com.nagarro.driven.client.selenium;

import com.nagarro.driven.client.ui.api.UnexpectedClientException;
import com.nagarro.driven.core.weblocator.file.util.WebLocatorFileTypeFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

public class WebLocatorLoader {

    /* The logger. */
    private static final Logger log = LoggerFactory.getLogger(WebLocatorLoader.class);

    private WebLocatorLoader() {
    }

    /**
     * Finds the page element.
     *
     * @param pageName    page name tag in web locator file.
     * @param elementName name of element.
     * @return instance of weblocator class.
     */
    public static WebLocator findWebLocatorForElement(
            final String pageName, final String elementName, final WebDriver webdriver,
            final Object... dynamicLocatorValue) {
        return findWebLocatorForElement(
                pageName, elementName, webdriver, WebLocatorLoader::getWebLocator, dynamicLocatorValue);
    }

    public static WebLocator findWebLocatorForElement(
            final String pageName,
            String elementName,
            WebDriver webdriver,
            BiFunction<String, String, WebLocator> webLocatorProvider, Object... dynamicLocatorValue) {
        WebLocator webLocator;
        final Map<String, String> locatorMap =
                WebLocatorFileTypeFactory.getInstance().getLocatorValue(pageName, elementName);
        log.info("Found following locator(s) for page: " + pageName + " and element: " + elementName);
        locatorMap.forEach((k, v) -> log.info("Key: " + k + ", Value: " + v));
        if (!locatorMap.isEmpty()) {
            for (final Map.Entry<String, String> entry : locatorMap.entrySet()) {
                if (0 == dynamicLocatorValue.length) {
                    webLocator = webLocatorProvider.apply(entry.getValue(), entry.getKey());
                } else {
                    webLocator = webLocatorProvider.apply(String.format(entry.getValue(), dynamicLocatorValue),
                            entry.getKey());
                }
                try {
                    webdriver.findElement(webLocator.getBy());
                } catch (NoSuchElementException e) {
                    log.info("Not able to find element: {} with locator : {}.", elementName, webLocator);
                } finally {
                    return webLocator;
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

    public static WebLocator getWebLocator(final String locatorValue, final String locatorType) {
        switch (locatorType.toLowerCase()) {
            case "id":
                return new WebLocator(By.id(locatorValue));
            case "name":
                return new WebLocator(By.name(locatorValue));
            case "classname":
            case "class":
                return new WebLocator(By.className(locatorValue));
            case "tagname":
                return new WebLocator(By.tagName(locatorValue));
            case "linktext":
                return new WebLocator(By.linkText(locatorValue));
            case "partiallinktext":
                return new WebLocator(By.partialLinkText(locatorValue));
            case "cssselector":
                return new WebLocator(By.cssSelector(locatorValue));
            case "xpath":
                return new WebLocator(By.xpath(locatorValue));
            default:
                throw new UnexpectedClientException("Can't create locators for " + locatorValue);
        }
    }
}
