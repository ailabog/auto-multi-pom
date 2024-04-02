package com.nagarro.driven.client.appium;

import com.nagarro.driven.core.driver.api.DriverOptions;
import com.nagarro.driven.core.guice.AbstractClientProvider;
import com.nagarro.driven.core.reporting.api.TestReportLogger;
import com.nagarro.driven.core.util.AutomationFrameworkException;

import static java.lang.String.format;

/**
 * An abstract Guice provider for all the instances of {@link AbstractAppiumDriver}. A concrete
 * implementation must be used in order to provide a binding to the requested type
 *
 * @param <T> - the actual type which needs to be provided
 * @author nagarro
 */
public abstract class AbstractAppiumDriverProvider<T extends AbstractAppiumDriver>
        extends AbstractClientProvider<T> {
    private final AppiumDriverFactory appiumDriverFactory;
    private final DriverOptions driverOptions;

    protected AbstractAppiumDriverProvider(
            TestReportLogger testReportLogger,
            DriverOptions driverOptions,
            AppiumDriverFactory appiumDriverFactory) {
        super(testReportLogger);
        this.driverOptions = driverOptions;
        this.appiumDriverFactory = appiumDriverFactory;
    }

    protected abstract Class<T> getExpectedConcreteInstanceClass();

    @Override
    public T get() {
        AbstractAppiumDriver abstractAppiumDriver =
                appiumDriverFactory.createConcrete(driverOptions, testReportLogger);
        if (!getExpectedConcreteInstanceClass().isAssignableFrom(abstractAppiumDriver.getClass())) {
            throw new AutomationFrameworkException(
                    format(
                            "Application type in driver's options doesn't presume using %s. Configured app type: %s",
                            getExpectedConcreteInstanceClass(), driverOptions.getApplicationType()));
        }
        @SuppressWarnings("unchecked")
        T expectedConcreteAppiumDriverInstance = (T) abstractAppiumDriver;
        return expectedConcreteAppiumDriverInstance;
    }
}
