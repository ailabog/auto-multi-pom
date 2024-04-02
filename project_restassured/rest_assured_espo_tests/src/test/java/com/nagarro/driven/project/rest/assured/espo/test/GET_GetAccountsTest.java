package com.nagarro.driven.project.rest.assured.espo.test;

import com.google.gson.JsonElement;
import com.nagarro.driven.project.rest.assured.espo.keywords.AccountKeywords;
import com.nagarro.driven.project.rest.assured.espo.keywords.model.AccountList;
import com.nagarro.driven.project.rest.assured.espo.test.base.EspoTestBase;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.google.gson.JsonParser.parseString;
import static com.nagarro.driven.core.rest.api.ApiUtil.fromJson;
import static com.nagarro.driven.core.rest.api.ApiUtil.toJson;
import static org.testng.Assert.assertNotNull;

public class GET_GetAccountsTest extends EspoTestBase {

 @Test
  public void GetAccountsTest() {
    assertNotNull(new AccountKeywords(restAssuredClientProvider.get()).getAllAccounts());
  }

  @DataProvider
  public Object[][] getAccounts() {
    JsonElement accountsJson =
        parseString(toJson(new AccountKeywords(restAssuredClientProvider.get()).getAllAccounts()));
    Object[][] accountsValue;
    AccountList accountsData = fromJson(accountsJson.toString(), AccountList.class);
    accountsValue = new Object[1][1];
    for (Object[] each : accountsValue) {
      each[0] = accountsData;
    }
    return accountsValue;
  }
}
