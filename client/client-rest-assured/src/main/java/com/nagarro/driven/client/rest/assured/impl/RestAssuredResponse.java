package com.nagarro.driven.client.rest.assured.impl;

import com.nagarro.driven.core.rest.api.IApiResponse;
import com.nagarro.driven.core.util.AutomationFrameworkException;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implement the api response for rest Assured.
 *
 * @author nagarro
 */
public class RestAssuredResponse implements IApiResponse {

  private final int code;
  private final String message;
  private final Headers headers;
  private final ResponseBody<?> body;

  /**
   * Constructor to set the values from the response.
   *
   * @param response the response
   */
  RestAssuredResponse(final Response response) {
    this.code = response.getStatusCode();
    this.message = response.getStatusLine();
    this.headers = response.headers();
    this.body = response.body();
  }

  @Override
  public int code() {
    return code;
  }

  @Override
  public String message() {
    return message;
  }

  @Override
  public Map<String, String> getHeader() {
    final Map<String, String> map = new HashMap<>();
    List<Header> listOfHeaders = headers.asList();
    for (Header listOfHeader : listOfHeaders) {
      map.put(listOfHeader.getName(), listOfHeader.getValue());
    }
    return map;
  }

  @Override
  public String getPayload() {
    try {
      return body.asString();
    } catch (Exception ex) {
      throw new AutomationFrameworkException("Could not get payload from response body", ex);
    }
  }

  @Override
  public boolean isRedirect() {
    return false;
  }
}
