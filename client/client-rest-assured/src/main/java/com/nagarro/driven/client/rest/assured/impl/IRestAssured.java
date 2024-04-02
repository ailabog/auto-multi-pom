package com.nagarro.driven.client.rest.assured.impl;

import com.nagarro.driven.core.rest.api.IApiResponse;

public interface IRestAssured {

  IApiResponse head(RestAssuredRequest request);

  IApiResponse head(RestAssuredRequest request, String url);

  IApiResponse delete(RestAssuredRequest request, String url);

  IApiResponse put(RestAssuredRequest request);

  IApiResponse put(RestAssuredRequest request, String url);

  IApiResponse post(RestAssuredRequest request);

  IApiResponse post(RestAssuredRequest request, String url);

  IApiResponse get(RestAssuredRequest request);

  IApiResponse get(RestAssuredRequest request, String url);

  RestAssuredImpl setAuthenticationForAll(String username, String password);

  RestAssuredImpl setBaseUrl(String url);
}
