package com.nagarro.driven.project.espo.test.base;

import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.nagarro.driven.client.selenium.SeleniumAbstractDriver;
import com.nagarro.driven.client.selenium.SeleniumAbstractDriverFactory;
import com.nagarro.driven.client.selenium.SeleniumDriverFactory;
import com.nagarro.driven.client.selenium.SeleniumDriverProvider;
import com.nagarro.driven.client.selenium.util.VideoRecordingUtil;
import com.nagarro.driven.core.driver.api.DriverOptions;
import com.nagarro.driven.core.guice.ThreadLocalScope;
import com.nagarro.driven.core.guice.ThreadLocalScoped;
import com.nagarro.driven.core.reporting.api.IReportManager;
import com.nagarro.driven.core.reporting.api.TestReportLogger;
import com.nagarro.driven.core.webdriver.Browser;
import com.nagarro.driven.core.webdriver.Driver;
import com.nagarro.driven.project.espo.test.config.EspoConfigHolder;
import com.nagarro.driven.reporter.extentreport.ExtentReportManagerImpl;
import com.nagarro.driven.runner.testng.base.TestCaseBase;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;
import javax.inject.Inject;
import java.net.URL;

import static com.nagarro.driven.project.espo.test.base.EspoPOMTestBase.EspoPomTestBaseModule;

/**
 * The test base class for the espo project.
 *
 * @author nagarro
 */
@Guice(modules = EspoPomTestBaseModule.class)
public class EspoPOMTestBase extends TestCaseBase {
  private static final boolean INITIALIZE_GRID =
      EspoConfigHolder.getInstance().initializeSeleniumGrid();
  private static final String IMAGES_PATH = EspoConfigHolder.getInstance().getImagespath();
  private static final String APP_URL = EspoConfigHolder.getInstance().applicationURL();

  @Inject private ThreadLocalScope threadLocalScope;
  @Inject protected Provider<SeleniumAbstractDriver> seleniumAbstractDriverProvider;
  @Inject private TestReportLogger testReportLogger;

  protected String defaultDepartureAirport = "Oslo";
//  protected String defaultDestinationAirport = "Tromsø";
  protected String defaultDestinationAirport = "Bodø";

  @BeforeSuite
  public void startRecorder() {
    VideoRecordingUtil.startRecorder(INITIALIZE_GRID);
  }

  /** Enters an injection scope and opens the application url */
  @BeforeMethod
  public void beforeMethod() {
    threadLocalScope.enter();
    seleniumAbstractDriverProvider.get().go(APP_URL);
  }

  /** Quits the client and exits the injection scope */
  @AfterMethod(alwaysRun = true)
  public void afterMethod() {
    try {
      seleniumAbstractDriverProvider.get().cleanUp();
    } finally {
      threadLocalScope.exit();
    }
  }

  @AfterSuite
  public void storRecorder() {
    VideoRecordingUtil.stopRecorder(INITIALIZE_GRID);
  }

  public static class EspoPomTestBaseModule extends TestCaseBaseModule {
    private static final Browser BROWSER = EspoConfigHolder.getInstance().initiateBrowser();
    private static final String APP_NAME = EspoConfigHolder.getInstance().applicationName();
    private static final URL GRID_URL = EspoConfigHolder.getInstance().gridUrl();

    @Override
    protected void configure() {
      super.configure();
      bind(SeleniumAbstractDriverFactory.class).to(SeleniumDriverFactory.class).in(Singleton.class);
      bind(SeleniumAbstractDriver.class)
          .toProvider(SeleniumDriverProvider.class)
          .in(ThreadLocalScoped.class);
    }

    @Override
    public Class<? extends IReportManager> getReportManagerImplementationClass() {
      return ExtentReportManagerImpl.class;
    }

    @Provides
    DriverOptions driverOptionsProvider() {
      return initializeDriverOptions();
    }

    private DriverOptions initializeDriverOptions() {
      DriverOptions options = new DriverOptions();
      options.setApplicationName(APP_NAME);
      options.setBrowser(BROWSER);
      options.setDriverName(Driver.SELENIUM_WEBDRIVER.toString());
      options.setCapabilities(DesiredCapabilities.chrome());
      if (INITIALIZE_GRID) {
        options.setGridUrl(GRID_URL);
      }
      return options;
    }
  }
}
