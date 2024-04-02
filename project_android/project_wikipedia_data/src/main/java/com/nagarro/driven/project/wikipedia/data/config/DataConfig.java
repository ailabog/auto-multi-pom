package com.nagarro.driven.project.wikipedia.data.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

/**
 * According to the documentation of aeonbits, the DataConfig.properties file should be found
 * automatically if it is in the same package as its mapping interface. It contains properties
 * related to this project.
 *
 * @author nagarro
 */
@LoadPolicy(LoadType.MERGE)
@Sources({"classpath:com/nagarro/driven/espo/data/config/DataConfig.properties"})
public interface DataConfig extends Config {

  @Key("client.data.csv.delimiter")
  @DefaultValue(",")
  String csvDelimiter();

  @Key("csvFilePath")
  @DefaultValue("\\project_wikipedia_data\\src\\test\\resources\\TestData\\")
  String csvFilePath();
}
