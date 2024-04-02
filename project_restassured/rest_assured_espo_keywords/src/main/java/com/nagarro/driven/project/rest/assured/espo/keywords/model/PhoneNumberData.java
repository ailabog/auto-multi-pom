package com.nagarro.driven.project.rest.assured.espo.keywords.model;

public class PhoneNumberData {

  private String phoneNumber;
  private boolean primary;
  private String type;

  public PhoneNumberData(final String phone, final String type) {
    this.phoneNumber = phone;
    this.type = type;
    this.primary = true;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public boolean isPrimary() {
    return primary;
  }

  public void setPrimary(final boolean primary) {
    this.primary = primary;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

@Override
public String toString() {
	return "[phoneNumber=" + phoneNumber + ", primary=" + primary + ", type=" + type + "]";
}
  
  
}
