package com.nagarro.driven.project.wikipedia.tests.testbase;

import com.google.inject.Provider;
import com.google.inject.Provides;
import com.nagarro.driven.client.appium.AbstractMobileAppiumDriver;
import com.nagarro.driven.client.appium.AbstractMobileAppiumDriverProvider;
import com.nagarro.driven.client.appium.AppiumDriverFactory;
import com.nagarro.driven.core.driver.api.DriverOptions;
import com.nagarro.driven.core.guice.ThreadLocalScope;
import com.nagarro.driven.core.guice.ThreadLocalScoped;
import com.nagarro.driven.core.reporting.api.IReportManager;
import com.nagarro.driven.core.webdriver.Driver;
import com.nagarro.driven.project.wikipedia.tests.config.WikiConfigHolder;
import com.nagarro.driven.project.wikipedia.tests.testbase.WikiTestBase.WikiTestModule;
import com.nagarro.driven.reporter.extentreport.ExtentReportManagerImpl;
import com.nagarro.driven.runner.testng.base.TestCaseBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Guice;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static java.nio.file.Files.find;
import static java.nio.file.Paths.get;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * The test base class for the espo project.
 *
 * @author nagarro
 */
@Guice(modules = WikiTestModule.class)
public class WikiTestBase extends TestCaseBase {
	@Inject protected Provider<AbstractMobileAppiumDriver> androidDriverProvider;
	@Inject private ThreadLocalScope threadLocalScope;

	/** Enters an injection scope */
	@BeforeMethod
	public void beforeSetup() {
		threadLocalScope.enter();
	}

	/** Quits the client and exits the injection scope */
	@AfterMethod(alwaysRun = true)
	public void methodTearDown() {
		try {
			androidDriverProvider.get().cleanUp();
		} finally {
			threadLocalScope.exit();
		}
	}

	public static class WikiTestModule extends TestCaseBaseModule {
		private static final String APPLICATION_NAME = WikiConfigHolder.getInstance().getDeviceName();
		private static final String DRIVER_NAME = Driver.APPIUM_DRIVER.toString();
		private static final String DEVICE_NAME = WikiConfigHolder.getInstance().getDeviceName();
		private static final String APP_PACKAGE = WikiConfigHolder.getInstance().getAppPackage();
		private static final String APP_ACTIVITY = WikiConfigHolder.getInstance().getAppActivity();
		private static final boolean NO_RESET_APP = WikiConfigHolder.getInstance().getNoResetApp();
		private static final String PLATFORM_NAME = WikiConfigHolder.getInstance().getPlatformName();
		private static final String PLATFORM_VERSION =
				WikiConfigHolder.getInstance().getPlatformVersion();
		private static final String APPLICATION_TYPE =
				WikiConfigHolder.getInstance().getApplicationType();
		private static final String NODE_JS_EXECUTABLE_PATH =
				WikiConfigHolder.getInstance().getNodeJsExecutablePath();

		@Override
		protected void configure() {
			super.configure();
			bind(AppiumDriverFactory.class).in(Singleton.class);
			bind(AbstractMobileAppiumDriver.class)
					.toProvider(AbstractMobileAppiumDriverProvider.class)
					.in(ThreadLocalScoped.class);
		}

		@Provides
		DriverOptions driverOptionsProvider() {
			return initializeDriverOptions();
		}

		@Override
		public Class<? extends IReportManager> getReportManagerImplementationClass() {
			return ExtentReportManagerImpl.class;
		}

		private DriverOptions initializeDriverOptions() {
			DriverOptions options = new DriverOptions();
			options.setApplicationName(APPLICATION_NAME);
			options.setDriverName(DRIVER_NAME);
			options.setDeviceName(DEVICE_NAME);
			options.setAppPackage(APP_PACKAGE);
			options.setAppActivity(APP_ACTIVITY);
			options.setNoResetApp(NO_RESET_APP);
			options.setPlatformName(PLATFORM_NAME);
			options.setPlatformVersion(PLATFORM_VERSION);
			options.setApplicationType(APPLICATION_TYPE);
			options.setNodeJsPath(
					convertNodeJsExecutablePathToAbsolutePathIfPossible(NODE_JS_EXECUTABLE_PATH));
			return options;
		}

		// Look for the NodeJS executable starting with the root of the parent project
		private String convertNodeJsExecutablePathToAbsolutePathIfPossible(String configuredPath) {
			checkArgument(
					isNotBlank(configuredPath), "Relevant path to the NodeJS executable can't be empty");
			try (Stream<Path> pathStream =
						 find(
								 get("").toAbsolutePath().getParent(),
								 10,
								 (path, attrs) -> path.endsWith(configuredPath))) {
				return pathStream
						.map(Path::toAbsolutePath)
						.map(Path::toString)
						.findAny()
						.orElse(configuredPath);
			} catch (IOException e) {
				return configuredPath;
			}
		}
	}
}