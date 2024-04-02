package com.nagarro.driven.project.rest.assured.espo.keywords.model;

import java.util.ArrayList;
import java.util.List;

public class Account {
  private String id;
  private String name;
  private boolean deleted;
  private String website;
  private String emailAddress;
  private final List<PhoneNumberData> phoneNumberData;
  private String phoneNumber;
  private String type;
  private String industry;
  private String createdAt;
  private String modifiedAt;
  private String createdById;
  private String assignedUserName;

  public Account(
      final String name,
      final String emailAddress,
      final String phoneNumber,
      final String website) {
    this.name = name;
    this.emailAddress = emailAddress;
    this.phoneNumber = phoneNumber;
    this.website = website;
    final PhoneNumberData data = new PhoneNumberData(phoneNumber, "Office");
    final List<PhoneNumberData> list = new ArrayList<>();
    list.add(data);
    this.phoneNumberData = list;
  }

  public String getId() {
    return id;
  }

  public List<PhoneNumberData> getPhonedata() {
    return phoneNumberData;
  }

  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(final boolean deleted) {
    this.deleted = deleted;
  }

  public String getWebsite() {
    return website;
  }

  public void setWebsite(final String website) {
    this.website = website;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(final String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(final String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getType() {
    return type;
  }

  public void setType(final String type) {
    this.type = type;
  }

  public String getIndustry() {
    return industry;
  }

  public void setIndustry(final String industry) {
    this.industry = industry;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(final String createdAt) {
    this.createdAt = createdAt;
  }

  public String getModifiedAt() {
    return modifiedAt;
  }

  public void setModifiedAt(final String modifiedAt) {
    this.modifiedAt = modifiedAt;
  }

  public String getCreatedById() {
    return createdById;
  }

  public void setCreatedById(final String createdById) {
    this.createdById = createdById;
  }

  public String getAssignedUserName() {
    return assignedUserName;
  }

  public void setAssignedUserName(final String assignedUserName) {
    this.assignedUserName = assignedUserName;
  }

@Override
public String toString() {
	return "[id=" + id + ", name=" + name + ", deleted=" + deleted + ", website=" + website + ", emailAddress="
			+ emailAddress + ", phoneNumberData=" + phoneNumberData + ", phoneNumber=" + phoneNumber + ", type=" + type
			+ ", industry=" + industry + ", createdAt=" + createdAt + ", modifiedAt=" + modifiedAt + ", createdById="
			+ createdById + ", assignedUserName=" + assignedUserName + "]";
}
  
  
}
