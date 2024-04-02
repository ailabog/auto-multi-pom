package com.nagarro.driven.project.wikipedia.tests.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;

/**
 * According to the documentation of aeonbits, the EspoConfig.properties file should be found automatically if it is in
 * the same package as its mapping interface. It contains properties related to this project.
 * 
 * @author nagarro
 */
@LoadPolicy(LoadType.MERGE)
@Sources({"classpath:com/nagarro/driven/project/amcTheatre/tests/config/AmcConfig.properties"})
public interface WikiConfig extends Config {

	@Key("mobile.device.name")
	@DefaultValue("xyz")
	String getDeviceName();
	
	@Key("mobile.app.name")
	@DefaultValue("Nedbank")
	String getApplicationName();
	
	@Key("mobile.app.package")
	@DefaultValue("org.wikipedia")
	String getAppPackage();
	
	@Key("mobile.app.activity")
	@DefaultValue("org.wikipedia.main.MainActivity")
	String getAppActivity();
	
	@Key("mobile.app.noReset")
	@DefaultValue("true")
	boolean getNoResetApp();
	
	@Key("mobile.app.path")
	@DefaultValue("wikiLatest.apk")
	String getAppPath();
	
	@Key("mobile.platform.name")
	@DefaultValue("Android")
	String getPlatformName();

	@Key("mobile.platform.version")
	@DefaultValue("10")
	String getPlatformVersion();

	@Key("mobile.applicationType")
	@DefaultValue("AndroidNativeApp")
	String getApplicationType();

	@Key("mobile.node.js.executable.path")
	@DefaultValue("node/node.exe")
	String getNodeJsExecutablePath();
}