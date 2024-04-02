package com.nagarro.driven.project.rest.assured.espo.test;

import com.nagarro.driven.project.rest.assured.espo.keywords.AccountKeywords;
import com.nagarro.driven.project.rest.assured.espo.keywords.model.Account;
import com.nagarro.driven.project.rest.assured.espo.test.base.EspoTestBase;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class POST_CreateAccountTest extends EspoTestBase {
  private final List<Account> createdAccounts = new CopyOnWriteArrayList<>();

//  @Test
  public void createAccountTest() {
    Account account =
        new AccountKeywords(restAssuredClientProvider.get())
            .createAccount(
                new Account("abcForCreate", "abc@gmail.com", "111111111", "www" + ".abc.com"));
    Assert.assertNotNull(account);
    createdAccounts.add(account);
  }

//  @AfterMethod
  public void deleteAccountsIfNeeded() {
    createdAccounts.removeIf(
        account ->
            new AccountKeywords(restAssuredClientProvider.get()).deleteAccount(account).code()
                == 200);
  }
}
