package com.nagarro.driven.project.rest.assured.espo.test;

import com.nagarro.driven.project.rest.assured.espo.keywords.AccountKeywords;
import com.nagarro.driven.project.rest.assured.espo.keywords.model.Account;
import com.nagarro.driven.project.rest.assured.espo.test.base.EspoTestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DELETE_DeleteAccountTest extends EspoTestBase {

 @Test
  public void DeleteAccountTest() {
    AccountKeywords accountKeywords = new AccountKeywords(restAssuredClientProvider.get());
    Account account =
        accountKeywords.createAccount(
            new Account("xyzForDeletion", "abc@gmail.com", "111111111", "www.abc.com"));
    Assert.assertEquals(accountKeywords.deleteAccount(account).code(), 200);
  }
}
