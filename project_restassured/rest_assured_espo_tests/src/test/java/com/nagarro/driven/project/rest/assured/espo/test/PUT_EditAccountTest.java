package com.nagarro.driven.project.rest.assured.espo.test;

import com.nagarro.driven.project.rest.assured.espo.keywords.AccountKeywords;
import com.nagarro.driven.project.rest.assured.espo.keywords.model.Account;
import com.nagarro.driven.project.rest.assured.espo.test.base.EspoTestBase;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.util.Objects.requireNonNull;
import static org.testng.Assert.assertEquals;

public class PUT_EditAccountTest extends EspoTestBase {
  private final List<Account> createdAccounts = new CopyOnWriteArrayList<>();
  private static final String NEW_NAME = "xyz";

//  @Test
  public void EditAccountTest() {
    AccountKeywords accountKeywords = new AccountKeywords(restAssuredClientProvider.get());
    Account account =
        accountKeywords.createAccount(
            new Account("abcForEdit", "abc@gmail.com", "111111111", "www.abc.com"));
    createdAccounts.add(requireNonNull(account));
    account.setName(NEW_NAME);
    account = accountKeywords.editAccount(account);
    assertEquals(account.getName(), NEW_NAME);
  }

//  @AfterMethod
  public void deleteAccountsIfNeeded() {
    createdAccounts.removeIf(
        account ->
            new AccountKeywords(restAssuredClientProvider.get()).deleteAccount(account).code()
                == 200);
  }
}
