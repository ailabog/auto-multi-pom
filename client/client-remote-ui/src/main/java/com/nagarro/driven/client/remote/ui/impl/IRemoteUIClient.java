package com.nagarro.driven.client.remote.ui.impl;

public interface IRemoteUIClient {

  Response ping();

  Response switchApplication(LaunchConfiguration config);

  Response switchForm(UiLocator uiLocator);

  Response getText(UiLocator uiLocator);

  Response clickAt(UiLocator uiLocator);

  Response doubleClickAt(UiLocator uiLocator);

  Response rightClickAt(UiLocator uiLocator);

  Response type(Request request);

  Response startApplication(LaunchConfiguration launchConfiguration);

  Response quitApplication();

  Response takeScreenshot(UiLocator locator);

  Response takeDesktopScreenshot();

  Response type(String inputText, UiLocator target);

  Response sendKeyboardShortcut(KeyboardShortcutRequest request);

  Response sendKeyboardShortcut(UiLocator target, String... key);
}
