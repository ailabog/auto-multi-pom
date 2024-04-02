package com.nagarro.driven.client.rest.assured.impl;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.util.Base64;
import java.util.Map;

public class RestAssuredRequestBuilder {

  private RequestSpecification request;

  public RestAssuredRequestBuilder() {
    this.request = RestAssured.given();
  }

  public static String getEncodedUsernameAndPasswordForHeader(String login, String password) {
    return Base64.getEncoder().encodeToString((login + ":" + password).getBytes());
  }

  public RestAssuredRequestBuilder addBasicAuthorisation(String username, String password) {
    this.request.auth().basic(username, password);
    return this;
  }

  public RestAssuredRequestBuilder addNtlmAuthorisation(
      String username, String password, String workstation, String domain) {
    this.request.auth().ntlm(username, password, workstation, domain);
    return this;
  }

  public RestAssuredRequestBuilder addOauthAuthorisation(
      String consumerKey, String consumerSecret, String accessToken) {
    this.request.auth().oauth(consumerKey, consumerKey, consumerSecret, accessToken);
    return this;
  }

  public RestAssuredRequestBuilder addOauth2Authorisation(String accessToken) {
    this.request.auth().oauth2(accessToken);
    return this;
  }

  public RestAssuredRequestBuilder addDigestAuthorisation(String username, String password) {
    this.request.auth().digest(username, password);
    return this;
  }

  public RestAssuredRequestBuilder withHeaders(Map<String, String> headers) {
    this.request.headers(headers);
    return this;
  }

  public RestAssuredRequestBuilder addmultipart(String key, File file, String fileType) {
    this.request.multiPart(key, file, fileType);
    return this;
  }

  public RestAssuredRequestBuilder contentType(String contentType) {
    this.request.contentType(contentType);
    return this;
  }

  public RestAssuredRequestBuilder addmultipart(String key, File file) {
    this.request.multiPart(key, file);
    return this;
  }

  public RestAssuredRequestBuilder addmultipart(String key, String value) {
    this.request.multiPart(key, value);
    return this;
  }

  public RestAssuredRequestBuilder withParameters(Map<String, String> params) {
    this.request.params(params);
    return this;
  }

  public RestAssuredRequestBuilder withPayload(String contentType, Object payload) {
    this.request.with().contentType(contentType).body(payload);
    return this;
  }

  public RestAssuredRequest build() {
    return new RestAssuredRequest(this.request);
  }
}
