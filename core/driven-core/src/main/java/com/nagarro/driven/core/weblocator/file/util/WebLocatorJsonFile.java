package com.nagarro.driven.core.weblocator.file.util;

import com.nagarro.driven.core.constant.FrameworkCoreConstant;
import com.nagarro.driven.core.util.AutomationFrameworkException;
import com.nagarro.driven.core.weblocator.model.Element;
import com.nagarro.driven.core.weblocator.model.ElementProperty;
import com.nagarro.driven.core.weblocator.model.Modules;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.UnmarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Read and return the value of web locator for xml file type.
 *
 * @author nagarro
 */
public class WebLocatorJsonFile implements WebLocatorFileType {

  protected static final Map<String, Modules> webLocatorMap = new HashMap<>();
  private final Logger log = LoggerFactory.getLogger(WebLocatorJsonFile.class);

  @Override
  public Map<String, String> getLocatorValue(String pageName, String elementName) {
    Map<String, String> valueOfElement = new HashMap<>();
    Modules modules;
    // Read the json file and put the page name and module object in
    // webLocatorMap map.
    if (!webLocatorMap.containsKey(pageName)) {
      JAXBContext jc;
      try {
        jc = JAXBContextFactory.createContext(new Class[]{Modules.class}, null);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        unmarshaller.setProperty(
            UnmarshallerProperties.MEDIA_TYPE, FrameworkCoreConstant.JSON_MEDIA_TYPE);
        StringBuilder jsonFilePath = new StringBuilder();
        WebLocatorFiles locatorFile =
            new WebLocatorFiles(FrameworkCoreConstant.JSON_FILE_EXTENSION);
        for (File file : locatorFile.getFiles()) {
          if (file.getName().split("[.]")[0].equals(pageName)) {
            jsonFilePath.append(file.getAbsolutePath());
            log.debug("Reading the json file from the path {}", jsonFilePath);
            break;
          }
        }

        File jsonFile = new File(jsonFilePath.toString());
        modules = (Modules) unmarshaller.unmarshal(jsonFile);
        log.debug("Adding the entry in webLocatorMap for pagename {}", pageName);
        webLocatorMap.put(pageName, modules);
      } catch (JAXBException e) {
        log.error("Object Repository file name should be same as page name");
        log.error(
            "Exception occured while reading the object repository file for element {} : {}",
            pageName,
            elementName);
        throw new AutomationFrameworkException("Couldn't load or serialize object repository", e);
      }
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
