package com.nagarro.driven.client.remote.ui.impl;

public class Request {

  private UiLocator by;
  private String additionalParameter;

  public String getAdditionalParameter() {
    return additionalParameter;
  }

  public void setAdditionalParameter(String additionalParameter) {
    this.additionalParameter = additionalParameter;
  }

  public UiLocator getBy() {
    return by;
  }

  public void setBy(UiLocator by) {
    this.by = by;
  }
}
