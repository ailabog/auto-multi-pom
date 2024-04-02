package com.nagarro.driven.client.remote.ui.impl;

public class LaunchConfiguration {

  private String binaryName;
  private String binaryPath;
  private String startupArguments;
  private String processName;

  public String getBinaryPath() {
    return binaryPath;
  }

  public void setBinaryPath(String binaryPath) {
    this.binaryPath = binaryPath;
  }

  public String getStartupArguments() {
    return startupArguments;
  }

  public void setStartupArguments(String startupArguments) {
    this.startupArguments = startupArguments;
  }

  public String getProcessName() {
    return processName;
  }

  public void setProcessName(String processName) {
    this.processName = processName;
  }

  public String getBinaryName() {
    return binaryName;
  }

  public void setBinaryName(String binaryName) {
    this.binaryName = binaryName;
  }
}