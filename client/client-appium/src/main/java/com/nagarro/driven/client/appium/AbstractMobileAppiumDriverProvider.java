package com.nagarro.driven.client.appium;

import com.nagarro.driven.core.driver.api.DriverOptions;
import com.nagarro.driven.core.reporting.api.TestReportLogger;

import javax.inject.Inject;

/**
 * A concrete implementation of {@link AbstractAppiumDriverProvider} Guice provider which is responsible for
 * the instances of {@link AbstractMobileAppiumDriver}
 *
 * @author nagarro
 */
public class AbstractMobileAppiumDriverProvider
        extends AbstractAppiumDriverProvider<AbstractMobileAppiumDriver> {

    @Inject
    public AbstractMobileAppiumDriverProvider(
            TestReportLogger testReportLogger,
            DriverOptions driverOptions,
            AppiumDriverFactory appiumDriverFactory) {
        super(testReportLogger, driverOptions, appiumDriverFactory);
    }

    @Override
    protected Class<AbstractMobileAppiumDriver> getExpectedConcreteInstanceClass() {
        return AbstractMobileAppiumDriver.class;
    }
}
