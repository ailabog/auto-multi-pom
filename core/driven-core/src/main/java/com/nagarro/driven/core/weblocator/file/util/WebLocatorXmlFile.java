package com.nagarro.driven.core.weblocator.file.util;

import com.nagarro.driven.core.constant.FrameworkCoreConstant;
import com.nagarro.driven.core.util.AutomationFrameworkException;
import com.nagarro.driven.core.weblocator.model.Element;
import com.nagarro.driven.core.weblocator.model.ElementProperty;
import com.nagarro.driven.core.weblocator.model.Modules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Read and return the value of web locator for xml file type.
 *
 * @author nagarro
 */
public class WebLocatorXmlFile implements WebLocatorFileType {

  protected static final Map<String, Modules> webLocatorMap = new HashMap<>();
  private final Logger log = LoggerFactory.getLogger(WebLocatorXmlFile.class);

  @Override
  public Map<String, String> getLocatorValue(String pageName, String elementName) {
    Map<String, String> valueOfElement = new HashMap<>();
    Modules modules;
    // Read the xml file and put the page name and module object in
    // webLocatorMap map.
    try {
      if (!webLocatorMap.containsKey(pageName)) {
        JAXBContext jc = JAXBContext.newInstance(Modules.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        StringBuilder xmlFilePath = new StringBuilder();
        WebLocatorFiles locatorFile = new WebLocatorFiles(FrameworkCoreConstant.XML_FILE_EXTENSION);
        for (File file : locatorFile.getFiles()) {
          if (file.getName().split("[.]")[0].equals(pageName)) {
            xmlFilePath.append(file.getAbsolutePath());
            log.debug("Reading the xml file from the path {}", xmlFilePath);
            break;
          }
        }

        File xmlFile = new File(xmlFilePath.toString());
        modules = (Modules) unmarshaller.unmarshal(xmlFile);
        log.debug("Adding the entry in webLocatorMap for pagename {}", pageName);
        webLocatorMap.put(pageName, modules);
      }
    } catch (final JAXBException e) {
      log.error("Object Repository file name should be same as page name");
      log.error(
          "Exception occurred while reading the object repository file for element {} : {}",
          pageName,
          elementName);
      throw new AutomationFrameworkException("Couldn't load or serialize object repository", e);
    }

    // Read the webLocatorMap and extract the element property to put it in
    // valueOfElement map.
    modules = webLocatorMap.get(pageName);
    if (modules == null) {
      log.debug("No element found in map for pagename {}", pageName);
    } else {
      for (Element entry : modules.getElement()) {
        if (entry.getNameOfElement().equals(elementName)) {
          valueOfElement =
              entry.getElementPropertyList().stream()
                  .collect(Collectors.toMap(ElementProperty::getType, ElementProperty::getValue));
        }
      }
    }
    return valueOfElement;
  }
}
