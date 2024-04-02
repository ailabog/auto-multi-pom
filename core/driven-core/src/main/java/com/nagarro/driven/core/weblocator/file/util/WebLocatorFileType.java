package com.nagarro.driven.core.weblocator.file.util;

import java.util.Map;

/**
 * Interface for all the file types used to find the web locator.
 *
 * @author nagarro
 */
public interface WebLocatorFileType {

  /**
   * Gets the locator value.
   *
   * @param pageName, name of the page
   * @param elementName, unique name of the element
   * @return map of locator type and value
   */
  Map<String, String> getLocatorValue(String pageName, String elementName);
}
