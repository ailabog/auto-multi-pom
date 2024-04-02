package com.nagarro.driven.runner.cucumbertestng.base;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import com.nagarro.driven.core.config.CoreConfig;
import com.nagarro.driven.core.constant.FrameworkCoreConstant;
import com.nagarro.driven.core.guice.ThreadLocalScopeModule;
import com.nagarro.driven.core.reporting.api.TestReportLogger;
import com.nagarro.driven.core.reporting.api.IReportManager;
import com.nagarro.driven.core.reporting.api.TestReport;
import com.nagarro.driven.core.reporting.api.TestStatus;
import com.nagarro.driven.runner.testng.base.TestRecordMaintain;
import cucumber.api.Scenario;
import cucumber.api.guice.CucumberModules;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.testng.CucumberFeatureWrapper;
import cucumber.runtime.ScenarioImpl;
import cucumber.runtime.java.guice.InjectorSource;
import gherkin.formatter.model.Result;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static com.google.inject.Guice.createInjector;
import static java.lang.String.format;

/**
 * CucumberTestRunner is the parent test case for all BDD Cucumber-based test cases. It uses
 * dependency injection but it's active only in the scope of Cucumber's execution
 *
 * @author nagarro
 */
public abstract class CucumberTestRunner extends AbstractTestNGCucumberTests
    implements InjectorSource {
  private static final Logger LOG = LoggerFactory.getLogger(CucumberTestRunner.class);
  private String currentFeatureName;
  @Inject private TestReport testReport;
  @Inject private IReportManager reportManager;
  @Inject private TestReportLogger testReportLogger;

  /**
   * Before scenario hook which uses DI and should be overridden and should at least call {@link
   * #beforeScenario(Scenario)}
   */
  public abstract void before(Scenario scenario);

  /**
   * After scenario hook which uses DI and should be overridden and should at least call {@link
   * #afterScenario(Scenario)}
   */
  public abstract void after(Scenario scenario);

  @Override
  @Test(groups = "cucumber", description = "Runs Cucumber Feature", dataProvider = "features")
  public void feature(CucumberFeatureWrapper cucumberFeature) {
    super.feature(cucumberFeature);
    currentFeatureName = cucumberFeature.getCucumberFeature().getGherkinFeature().getName();
  }

  @Override
  @DataProvider
  public Object[][] features() {
    return new RiskBasedExecutionCucumber().arrangeFeatures(super.features());
  }

  /**
   * Maintaining test results in csv.
   *
   * @param result - test result
   */
  @AfterMethod(alwaysRun = true)
  public void maintainTestDetails(ITestResult result) {
    new TestRecordMaintain().maintain(currentFeatureName, result.getStatus());
  }

  /**
   * DI injector provider. Works only in the scope of Cucumber's execution
   *
   * @return - the initialized and configured injector
   */
  @Override
  public Injector getInjector() {
    return initializeInjector();
  }

  public void beforeScenario(Scenario scenario) {
    String scenarioId = scenario.getId();
    LOG.info("Creating the parent scenario node {} in report.", scenarioId);
    reportManager.createNode(scenarioId, true, false);
  }

  public void afterScenario(Scenario scenario) {
    scenario.write("Scenario Finished");
    String failureMessage = "";

    if (scenario.isFailed()) {
      try {
        failureMessage = getFailureMessage(scenario);
      } catch (IllegalArgumentException | IllegalAccessException ex) {
        LOG.info(ex.getMessage());
      }
      String screenshotname = getScreenshotName(scenario);
      if (CoreConfig.getInstance().screenshotOnFail()) {
        testReport.takeScreenshot(TestStatus.FAIL, "Failure screenshot", screenshotname);
      }
    }

    reportManager.getReporter().flush();
  }

  protected abstract Stage getInjectionStage();

  protected abstract CucumberBaseModule getInjectionModule();

  private Injector initializeInjector() {
    Injector newInjector =
        createInjector(getInjectionStage(), CucumberModules.SCENARIO, getInjectionModule());
    newInjector.injectMembers(this);
    return newInjector;
  }

  private String getIdOfIssueLinkedWithThisTestOnJira(Scenario scenario) {
    String idOfissueLinkedWithThisTestOnJira = "";
    Collection<String> annotations = scenario.getSourceTagNames();
    for (String a : annotations) {
      if (a.contains("Xray") || a.contains("JiraIssueNumber")) {
        idOfissueLinkedWithThisTestOnJira = a.split("=")[1];
        break;
      }
    }
    return idOfissueLinkedWithThisTestOnJira;
  }

  private String getScreenshotName(Scenario scenario) {
    String testname = scenario.getName();
    if (testname.length() > 100) testname = testname.substring(0, 99);
    return format(
        "%s%s_FAILED",
        testname,
        new SimpleDateFormat("_" + FrameworkCoreConstant.TIMESTAMP_FORMAT).format(new Date()));
  }

  @SuppressWarnings("unchecked")
  private String getFailureMessage(Scenario scenario) throws IllegalAccessException {
    String failureMessage = "";
    Field fields = FieldUtils.getField(((ScenarioImpl) scenario).getClass(), "stepResults", true);
    ArrayList<Result> results = (ArrayList<Result>) fields.get(scenario);
    for (Result result : results) {
      if (result.getError() != null) {
        failureMessage = result.getErrorMessage();
        testReport.error("Test failed: " + failureMessage);
        break;
      }
    }
    return failureMessage;
  }

  public abstract static class CucumberBaseModule extends AbstractModule {
    @Override
    protected void configure() {
      install(new ThreadLocalScopeModule());
      bind(IReportManager.class).to(getReportManagerImplementationClass()).in(Singleton.class);
    }

    public abstract Class<? extends IReportManager> getReportManagerImplementationClass();
  }
}
