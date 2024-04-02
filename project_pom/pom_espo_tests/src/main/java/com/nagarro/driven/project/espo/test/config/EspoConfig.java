package com.nagarro.driven.project.espo.test.config;

import com.nagarro.driven.core.webdriver.Browser;
import java.net.URL;
import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

/**
 * According to the documentation of aeonbits, the EspoConfig.properties file should be found
 * automatically if it is in the same package as its mapping interface. It contains properties
 * related to this project.
 *
 * @author nagarro
 */
@LoadPolicy(LoadType.MERGE)
@Sources({"classpath:com/nagarro/driven/project/espo/test/config/EspoConfig.properties"})
public interface EspoConfig extends Config {

  @Key("initiate.driver.class")
  @DefaultValue("true")
  boolean initiateClassDriver();

  @Key("application.url")
  @DefaultValue("https://qaenv.flyrstage.com/en")
  String applicationURL();

  @Key("initialize.selenium.grid")
  @DefaultValue("false")
  boolean initializeSeleniumGrid();

  @Key("browser")
  @DefaultValue("CHROME")
  Browser initiateBrowser();

  @Key("gridUrl")
  @DefaultValue("http://localhost:4444/wd/hub")
  URL gridUrl();

  @Key("application.name")
  @DefaultValue("Flyr")
  String applicationName();

  @Key("sikuli.screenshot.path")
  @DefaultValue("\\src\\main\\resources\\TestData\\images")
  String getImagespath();
}
