package com.nagarro.driven.project.rest.assured.espo.test.base;

import com.google.inject.Provider;
import com.nagarro.driven.client.rest.assured.impl.IRestAssured;
import com.nagarro.driven.client.rest.assured.impl.RestAssuredClientProvider;
import com.nagarro.driven.core.guice.ThreadLocalScope;
import com.nagarro.driven.core.guice.ThreadLocalScoped;
import com.nagarro.driven.core.reporting.api.IReportManager;
import com.nagarro.driven.project.rest.assured.espo.keywords.ServiceKeywords;
import com.nagarro.driven.reporter.extentreport.ExtentReportManagerImpl;
import com.nagarro.driven.runner.testng.base.TestCaseBase;
import org.testng.annotations.*;

import javax.inject.Inject;

import static com.nagarro.driven.project.rest.assured.espo.test.base.EspoTestBase.*;

@Guice(modules = EspoTestBaseModule.class)
public class EspoTestBase extends TestCaseBase {
  @Inject private ThreadLocalScope threadLocalScope;
  @Inject protected Provider<IRestAssured> restAssuredClientProvider;

  /** Enters the injection scope and configures the client */
  @BeforeSuite
  public void beforeSuiteSetup() {
    threadLocalScope.enter();
    new ServiceKeywords(restAssuredClientProvider.get())
        .addAuthorisationForAllRequests()
        .setBaseUrlForRequests();
  }

  /** Exits the injection scope */
  @AfterSuite(alwaysRun = true)
  public void afterSuiteTeardown() {
    threadLocalScope.exit();
  }

  public static class EspoTestBaseModule extends TestCaseBaseModule {
    @Override
    protected void configure() {
      super.configure();
      bind(IRestAssured.class)
          .toProvider(RestAssuredClientProvider.class)
          .in(ThreadLocalScoped.class);
    }

    @Override
    public Class<? extends IReportManager> getReportManagerImplementationClass() {
      return ExtentReportManagerImpl.class;
    }
  }
}
