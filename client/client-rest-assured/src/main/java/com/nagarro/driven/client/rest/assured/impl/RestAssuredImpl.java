package com.nagarro.driven.client.rest.assured.impl;

import com.nagarro.driven.core.reporting.api.TestReportLogger;
import com.nagarro.driven.core.reporting.api.TestStatus;
import com.nagarro.driven.core.rest.api.IApiResponse;
import io.restassured.RestAssured;

import static io.restassured.RestAssured.authentication;
import static io.restassured.RestAssured.baseURI;

public class RestAssuredImpl implements IRestAssured {
  private final TestReportLogger reportLog;

  public RestAssuredImpl(TestReportLogger reportLog) {
    this.reportLog = reportLog;
  }

  @Override
  public RestAssuredImpl setBaseUrl(String uri) {
    initializeBaseUri(uri);
    return this;
  }

  @Override
  public RestAssuredImpl setAuthenticationForAll(String username, String password) {
    setBasicAuthentication(username, password);
    return this;
  }

  @Override
  public IApiResponse get(RestAssuredRequest request, String url) {
    try {
      logRequestUrl(baseURI + url);
      IApiResponse response = new RestAssuredResponse(request.getRequest().get(url));
      logResponseCode(response);
      return response;
    } catch (Exception ex) {
      reportLog.reportLogger(
          TestStatus.ERROR,
          "Failed to execute the Get request created due to : " + ex.getMessage());
      reportLog.reportErrorLogger(TestStatus.ERROR, ex);
      return null;
    }
  }

  @Override
  public IApiResponse get(RestAssuredRequest request) {
    try {
      logRequestUrl(baseURI);
      IApiResponse response = new RestAssuredResponse(request.getRequest().get());
      logResponseCode(response);
      return response;
    } catch (Exception ex) {
      reportLog.reportLogger(
          TestStatus.ERROR,
          "Failed to execute the Get request created due to : " + ex.getMessage());
      reportLog.reportErrorLogger(TestStatus.ERROR, ex);
      return null;
    }
  }

  @Override
  public IApiResponse post(RestAssuredRequest request, String url) {
    try {
      logRequestUrl(baseURI + url);
      IApiResponse response = new RestAssuredResponse(request.getRequest().post(url));
      logResponseCode(response);
      return response;
    } catch (Exception ex) {
      reportLog.reportLogger(
          TestStatus.ERROR,
          "Failed to execute the Post request created due to : " + ex.getMessage());
      reportLog.reportErrorLogger(TestStatus.ERROR, ex);
      return null;
    }
  }

  @Override
  public IApiResponse post(RestAssuredRequest request) {
    try {
      logRequestUrl(baseURI);
      IApiResponse response = new RestAssuredResponse(request.getRequest().post());
      logResponseCode(response);
      return response;
    } catch (Exception ex) {
      reportLog.reportLogger(
          TestStatus.ERROR,
          "Failed to execute the Post request created due to : " + ex.getMessage());
      reportLog.reportErrorLogger(TestStatus.ERROR, ex);
      return null;
    }
  }

  @Override
  public IApiResponse put(RestAssuredRequest request, String url) {
    try {
      logRequestUrl(baseURI + url);
      IApiResponse response = new RestAssuredResponse(request.getRequest().put(url));
      logResponseCode(response);
      return response;
    } catch (Exception ex) {
      reportLog.reportLogger(
          TestStatus.ERROR,
          "Failed to execute the Put request created due to : " + ex.getMessage());
      reportLog.reportErrorLogger(TestStatus.ERROR, ex);
      return null;
    }
  }

  @Override
  public IApiResponse put(RestAssuredRequest request) {
    try {
      logRequestUrl(baseURI);
      IApiResponse response = new RestAssuredResponse(request.getRequest().put());
      logResponseCode(response);
      return response;
    } catch (Exception ex) {
      reportLog.reportLogger(
          TestStatus.ERROR,
          "Failed to execute the Put request created due to : " + ex.getMessage());
      reportLog.reportErrorLogger(TestStatus.ERROR, ex);
      return null;
    }
  }

  @Override
  public IApiResponse delete(RestAssuredRequest request, String url) {
    try {
      logRequestUrl(baseURI + url);
      IApiResponse response = new RestAssuredResponse(request.getRequest().delete(url));
      logResponseCode(response);
      return response;
    } catch (Exception ex) {
      reportLog.reportLogger(
          TestStatus.ERROR,
          "Failed to execute the Delete request created due to : " + ex.getMessage());
      reportLog.reportErrorLogger(TestStatus.ERROR, ex);
      return null;
    }
  }

  @Override
  public IApiResponse head(RestAssuredRequest request, String url) {
    try {
      logRequestUrl(baseURI + url);
      IApiResponse response = new RestAssuredResponse(request.getRequest().head(url));
      logResponseCode(response);
      return response;
    } catch (Exception ex) {
      reportLog.reportLogger(
          TestStatus.ERROR,
          "Failed to execute the Head request created due to : " + ex.getMessage());
      reportLog.reportErrorLogger(TestStatus.ERROR, ex);
      return null;
    }
  }

  @Override
  public IApiResponse head(RestAssuredRequest request) {
    try {
      logRequestUrl(baseURI);
      IApiResponse response = new RestAssuredResponse(request.getRequest().head());
      logResponseCode(response);
      return response;
    } catch (Exception ex) {
      reportLog.reportLogger(
          TestStatus.ERROR,
          "Failed to execute the Head request created due to : " + ex.getMessage());
      reportLog.reportErrorLogger(TestStatus.ERROR, ex);
      return null;
    }
  }

  private void logResponseCode(IApiResponse response) {
    reportLog.reportLogger(TestStatus.INFO, "Response code received is :" + response.code());
  }

  private void logRequestUrl(String url) {
    reportLog.reportLogger(TestStatus.INFO, "Request url is: " + url);
  }

  private static void setBasicAuthentication(String username, String password) {
    authentication = RestAssured.basic(username, password);
  }

  private static void initializeBaseUri(String uri) {
    baseURI = uri;
  }
}
