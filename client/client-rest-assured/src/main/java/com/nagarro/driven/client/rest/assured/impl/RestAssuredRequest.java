package com.nagarro.driven.client.rest.assured.impl;

import io.restassured.specification.RequestSpecification;

public class RestAssuredRequest {

  private RequestSpecification request;

  public RestAssuredRequest(RequestSpecification req) {
    this.request = req;
  }

  public RequestSpecification getRequest() {
    return request;
  }
}
