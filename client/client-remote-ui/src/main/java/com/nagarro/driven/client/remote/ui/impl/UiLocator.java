package com.nagarro.driven.client.remote.ui.impl;

import com.nagarro.driven.core.weblocator.file.util.WebLocatorFileTypeFactory;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UiLocator {

  private static final Logger log = LoggerFactory.getLogger(UiLocator.class);
  private ElementType element;
  private String locator;

  private static UiLocator createLocator(
          ElementType type, String locatorTemplate, String... locatorArguments) {
    UiLocator l = new UiLocator();
    l.setElement(type);
    String finalLocator = String.format(locatorTemplate, (Object[]) locatorArguments);
    l.setLocator(finalLocator);
    return l;
  }

  public static UiLocator findWebLocatorElement(final String pageName, final String elementName) {
    try {
      final Map<String, String> locatorMap =
              WebLocatorFileTypeFactory.getInstance().getLocatorValue(pageName, elementName);
      if (!locatorMap.isEmpty()) {
        Map.Entry<String,String> entry = locatorMap.entrySet().iterator().next();
        return createLocator(
                ElementType.valueOf(entry.getKey().toUpperCase()), entry.getValue(), "");

      } else {
        log.error(
                "NameOfElement or SectionName is given wrong in object repository file or PageName in Page Object Class is wrong");
      }
    } catch (Exception e) {
      log.error("Object Repository file name should be same as page name");
      log.error(
              "Exception occurred while reading the object repository file for element {} : {}", elementName, pageName, e);
    }
    return null;
  }

  public static UiLocator button(String locator) {
    return UiLocator.createLocator(ElementType.BUTTON, locator);
  }

  public static UiLocator cell(String locator) {
    return UiLocator.createLocator(ElementType.CELL, locator);
  }

  public static UiLocator cell(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.CELL, locatorTemplate, locatorArguments);
  }

  public static UiLocator text(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.TEXT, locatorTemplate, locatorArguments);
  }

  public static UiLocator form(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.FORM, locatorTemplate, locatorArguments);
  }

  public static UiLocator comboBox(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.COMBOBOX, locatorTemplate, locatorArguments);
  }

  public static UiLocator sapElement(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.SAPELEMENT, locatorTemplate, locatorArguments);
  }

  public static UiLocator sapItem(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.SAPITEM, locatorTemplate, locatorArguments);
  }

  public static UiLocator sapWindow(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.SAPWINDOW, locatorTemplate, locatorArguments);
  }

  public static UiLocator statusbar(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.STATUSBAR, locatorTemplate, locatorArguments);
  }

  public static UiLocator table(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.TABLE, locatorTemplate, locatorArguments);
  }

  public static UiLocator titlebar(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.TITLEBAR, locatorTemplate, locatorArguments);
  }

  public static UiLocator toolbar(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.TOOLBAR, locatorTemplate, locatorArguments);
  }

  public static UiLocator treeItem(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.TREEITEM, locatorTemplate, locatorArguments);
  }

  public static UiLocator unknown(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.UNKNOWN, locatorTemplate, locatorArguments);
  }

  public static UiLocator checkbox(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.CHECKBOX, locatorTemplate, locatorArguments);
  }

  public static UiLocator container(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.CONTAINER, locatorTemplate, locatorArguments);
  }

  public static UiLocator menubar(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.MENUBAR, locatorTemplate, locatorArguments);
  }

  public static UiLocator row(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.ROW, locatorTemplate, locatorArguments);
  }

  public static UiLocator deskop(String locatorTemplate, String... locatorArguments) {
    return UiLocator.createLocator(ElementType.DESKTOP, locatorTemplate, locatorArguments);
  }

  public ElementType getElement() {
    return element;
  }

  public void setElement(ElementType element) {
    this.element = element;
  }

  public String getLocator() {
    return locator;
  }

  public void setLocator(String locator) {
    this.locator = locator;
  }

  @Override
  public String toString() {
    return String.format("UiLocator {element='%s', locator='%s'}", this.element, this.locator);
  }
}