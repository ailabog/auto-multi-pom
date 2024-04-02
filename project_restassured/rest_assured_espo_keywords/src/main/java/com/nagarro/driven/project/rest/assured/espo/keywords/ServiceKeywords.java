package com.nagarro.driven.project.rest.assured.espo.keywords;

import com.nagarro.driven.client.rest.assured.impl.IRestAssured;
import com.nagarro.driven.project.rest.assured.espo.keywords.config.EspoConfigHolder;

public class ServiceKeywords {
  protected static final String LOGIN = EspoConfigHolder.getInstance().username();
  protected static final String PASSWORD = EspoConfigHolder.getInstance().password();
  protected static final String ACC_URL = EspoConfigHolder.getInstance().accUrl();
  protected final IRestAssured client;

  public ServiceKeywords(IRestAssured client) {
    this.client = client;
  }

  public ServiceKeywords addAuthorisationForAllRequests() {
    client.setAuthenticationForAll(LOGIN, PASSWORD);
    return this;
  }

  public ServiceKeywords setBaseUrlForRequests() {
    client.setBaseUrl(ACC_URL);
    return this;
  }
}
