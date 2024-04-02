package com.nagarro.driven.project.rest.assured.espo.keywords;

import com.nagarro.driven.client.rest.assured.impl.IRestAssured;
import com.nagarro.driven.client.rest.assured.impl.RestAssuredRequest;
import com.nagarro.driven.client.rest.assured.impl.RestAssuredRequestBuilder;
import com.nagarro.driven.core.rest.api.IApiResponse;
import com.nagarro.driven.project.rest.assured.espo.keywords.model.Account;
import com.nagarro.driven.project.rest.assured.espo.keywords.model.AccountList;

import static com.nagarro.driven.core.rest.api.ApiUtil.fromJson;

public class AccountKeywords extends ServiceKeywords {

  public AccountKeywords(IRestAssured client) {
    super(client);
  }

  // POST REQUEST
  public Account createAccount(Account account) {
    RestAssuredRequest r =
        new RestAssuredRequestBuilder().withPayload("application/json", account).build();
    IApiResponse response = client.post(r);
    return fromJson(response.getPayload(), Account.class);
  }

  // DELETE REQUEST
  public IApiResponse deleteAccount(Account account) {
    RestAssuredRequest r = new RestAssuredRequestBuilder().build();
    return client.delete(r, "/" + account.getId());
  }

  // GET REQUEST
  public AccountList getAllAccounts() {
    RestAssuredRequest r = new RestAssuredRequestBuilder().build();
    IApiResponse response = client.get(r);

    return fromJson(response.getPayload(), AccountList.class);
  }

  // PUT REQUEST
  public Account editAccount(Account account) {
    RestAssuredRequest r =
        new RestAssuredRequestBuilder().withPayload("application/json", account).build();
    IApiResponse response = client.put(r, "/" + account.getId());
    return fromJson(response.getPayload(), Account.class);
  }
}