package com.nagarro.driven.client.rest.assured.impl;

import com.nagarro.driven.core.guice.AbstractClientProvider;
import com.nagarro.driven.core.reporting.api.TestReportLogger;

import javax.inject.Inject;

/**
 * A concrete implementation of {@link AbstractClientProvider} Guice provider which is responsible for the
 * instances of {@link RestAssuredImpl}
 *
 * @author nagarro
 */
public class RestAssuredClientProvider extends AbstractClientProvider<RestAssuredImpl> {
  @Inject
  public RestAssuredClientProvider(TestReportLogger testReportLogger) {
    super(testReportLogger);
  }

  @Override
  public RestAssuredImpl get() {
    return new RestAssuredImpl(testReportLogger);
  }
}
