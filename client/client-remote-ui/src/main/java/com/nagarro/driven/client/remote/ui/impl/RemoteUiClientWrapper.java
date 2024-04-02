package com.nagarro.driven.client.remote.ui.impl;

import com.nagarro.driven.core.reporting.api.TestReportLogger;
import com.nagarro.driven.core.reporting.api.TestStatus;
import com.nagarro.driven.core.webdriver.AbstractWebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoteUiClientWrapper extends AbstractWebDriver implements IRemoteUIClient {
  private static final String TYPING_OVER_LOCATION = " over ";
  private final IRemoteUIClient uiClientInstance;

  public RemoteUiClientWrapper(IRemoteUIClient uiClientInstance, TestReportLogger reportLog) {
    super(reportLog);
    this.uiClientInstance = uiClientInstance;
  }

  @Override
  public Response ping() {
    return uiClientInstance.ping();
  }

  @Override
  public Response switchApplication(LaunchConfiguration config) {
    reportLog.reportLogger(TestStatus.INFO, "Switching Application");
    return uiClientInstance.switchApplication(config);
  }

  @Override
  public Response switchForm(UiLocator uiLocator) {
    return uiClientInstance.switchForm(uiLocator);
  }

  public Response switchForm(String elementName) {
    Response response = new Response();
    String pageName;
    try {
      String className =
          Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).toString();
      pageName = className.substring(className.lastIndexOf(".") + 1);
      response = switchForm(UiLocator.findWebLocatorElement(pageName, elementName));
      reportLog.reportLogger(TestStatus.PASS, "Switched Form on element " + elementName);
    } catch (Exception e) {
      reportLog.reportLogger(TestStatus.FAIL, "Unable to Switch Form on element " + elementName);
    }

    return response;
  }

  @Override
  public Response getText(UiLocator uiLocator) {
    return uiClientInstance.getText(uiLocator);
  }

  @Override
  public Response clickAt(UiLocator uiLocator) {
    return uiClientInstance.clickAt(uiLocator);
  }

  @Override
  public Response doubleClickAt(UiLocator uiLocator) {
    return uiClientInstance.doubleClickAt(uiLocator);
  }

  @Override
  public Response rightClickAt(UiLocator uiLocator) {
    return uiClientInstance.rightClickAt(uiLocator);
  }

  public Response getText(String elementName) {
    Response response = new Response();
    String pageName;
    try {
      String className =
          Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).toString();
      pageName = className.substring(className.lastIndexOf(".") + 1);
      response = getText(UiLocator.findWebLocatorElement(pageName, elementName));
      reportLog.reportLogger(TestStatus.PASS, "Get text of " + elementName);
    } catch (Exception e) {
      reportLog.reportLogger(TestStatus.FAIL, "Get text of " + elementName);
    }
    return response;
  }

  public Response clickAt(String elementName) {
    Response response = new Response();
    String pageName;
    try {
      String className =
          Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).toString();
      pageName = className.substring(className.lastIndexOf(".") + 1);
      response = clickAt(UiLocator.findWebLocatorElement(pageName, elementName));
      reportLog.reportLogger(TestStatus.PASS, "Click at " + elementName);
    } catch (Exception e) {
      reportLog.reportLogger(TestStatus.FAIL, "Click at " + elementName);
    }
    return response;
  }

  public Response doubleClickAt(String elementName) {
    Response response = new Response();
    String pageName;
    try {
      String className =
          Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).toString();
      pageName = className.substring(className.lastIndexOf(".") + 1);
      response = doubleClickAt(UiLocator.findWebLocatorElement(pageName, elementName));
      reportLog.reportLogger(TestStatus.PASS, "Double Click at " + elementName);
    } catch (Exception e) {
      reportLog.reportLogger(TestStatus.FAIL, "Double Click at " + elementName);
    }
    return response;
  }

  public Response rightClickAt(String elementName) {
    Response response = new Response();
    String pageName;
    try {
      String className =
          Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).toString();
      pageName = className.substring(className.lastIndexOf(".") + 1);
      response = rightClickAt(UiLocator.findWebLocatorElement(pageName, elementName));
      reportLog.reportLogger(TestStatus.PASS, "Right Click at " + elementName);
    } catch (Exception e) {
      reportLog.reportLogger(TestStatus.FAIL, "Right Click at " + elementName);
    }
    return response;
  }

  @Override
  public Response type(Request request) {
    return uiClientInstance.type(request);
  }

  @Override
  public Response startApplication(LaunchConfiguration launchConfiguration) {
    reportLog.reportLogger(TestStatus.INFO, "Starting Application");
    return uiClientInstance.startApplication(launchConfiguration);
  }

  @Override
  public Response quitApplication() {
    reportLog.reportLogger(TestStatus.INFO, "Closing Application");
    return uiClientInstance.quitApplication();
  }

  @Override
  public Response takeScreenshot(UiLocator locator) {
    return uiClientInstance.takeScreenshot(locator);
  }

  public Response takeScreenshot(String elementName) {
    Response response = new Response();
    String pageName;
    try {
      String className =
          Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).toString();
      pageName = className.substring(className.lastIndexOf(".") + 1);
      response = takeScreenshot(UiLocator.findWebLocatorElement(pageName, elementName));
      reportLog.reportLogger(TestStatus.PASS, "Taking screenshot of element " + elementName);
    } catch (Exception e) {
      reportLog.reportLogger(TestStatus.FAIL, "Taking screenshot of element " + elementName);
    }

    return response;
  }

  public Response takeDesktopScreenshot() {
    reportLog.reportLogger(TestStatus.INFO, "Taking Desktop screenshot");
    return uiClientInstance.takeDesktopScreenshot();
  }

  @Override
  public Response type(String inputText, UiLocator target) {

    Request r = new Request();
    r.setAdditionalParameter(inputText);
    r.setBy(target);
    return this.type(r);
  }

  public Response type(String inputText, String elementName) {
    Response response = new Response();
    String pageName;
    try {
      String className =
          Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).toString();
      pageName = className.substring(className.lastIndexOf(".") + 1);
      response = type(inputText, UiLocator.findWebLocatorElement(pageName, elementName));
      reportLog.reportLogger(
          TestStatus.PASS, "Typing " + inputText + TYPING_OVER_LOCATION + elementName);
    } catch (Exception e) {
      reportLog.reportLogger(
          TestStatus.FAIL, "Typing " + inputText + TYPING_OVER_LOCATION + elementName);
    }
    return response;
  }

  @Override
  public Response sendKeyboardShortcut(KeyboardShortcutRequest request) {
    return this.uiClientInstance.sendKeyboardShortcut(request);
  }

  @Override
  public Response sendKeyboardShortcut(UiLocator target, String... keys) {
    KeyboardShortcutRequest request = new KeyboardShortcutRequest();
    request.setBy(target);
    List<String> keyList = new ArrayList<>(Arrays.asList(keys));
    request.setKeys(keyList);
    return this.sendKeyboardShortcut(request);
  }

  public Response sendKeyboardShortcut(String elementName, String... keys) {
    Response response = new Response();
    String pageName;
    try {
      String className =
          Class.forName(Thread.currentThread().getStackTrace()[2].getClassName()).toString();
      pageName = className.substring(className.lastIndexOf(".") + 1);
      response = sendKeyboardShortcut(UiLocator.findWebLocatorElement(pageName, elementName), keys);
      reportLog.reportLogger(
          TestStatus.PASS,
          "Presssing " + Arrays.toString(keys) + TYPING_OVER_LOCATION + elementName);
    } catch (Exception e) {
      reportLog.reportLogger(
          TestStatus.FAIL,
          "Presssing " + Arrays.toString(keys) + TYPING_OVER_LOCATION + elementName);
    }
    return response;
  }

  @Override
  public String driverName() {
    return "RemoteUIClient";
  }

  @Override
  public void cleanUp() {
    quitApplication();
  }
}
