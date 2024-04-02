package com.nagarro.driven.project.rest.assured.espo.keywords.config;

import org.aeonbits.owner.Config;

/**
 * According to the documentation of aeonbits, the EspoConfig.properties file should be found
 * automatically if it is in the same package as its mapping interface. It contains properties
 * related to this project.
 *
 * @author nagarro
 */
public interface EspoConfig extends Config {

  @Key("username")
  @DefaultValue("admin")
  String username();

  @Key("password")
  @DefaultValue("nagarro@123")
  String password();

  @Key("acc_url")
  @DefaultValue("http://10.127.129.52/espocrm/api/v1/Account")
  String accUrl();
}
