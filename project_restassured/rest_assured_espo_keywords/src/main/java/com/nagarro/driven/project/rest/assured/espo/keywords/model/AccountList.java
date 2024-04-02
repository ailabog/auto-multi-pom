package com.nagarro.driven.project.rest.assured.espo.keywords.model;

import java.util.List;

public class AccountList {

  private String total;
  private List<Account> list;

  public List<Account> getAccounts() {
    return list;
  }

  public String gettotal() {
    return total;
  }

@Override
public String toString() {
	return "[total=" + total + ", list=" + list + "]";
}
  
  
}
