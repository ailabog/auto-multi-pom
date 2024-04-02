package com.nagarro.driven.client.appium.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.LoadPolicy;
import org.aeonbits.owner.Config.LoadType;
import org.aeonbits.owner.Config.Sources;
import org.aeonbits.owner.ConfigFactory;

/**
 * This class is used to create the instance of the appium config and contains interface of
 * AppiumConfigSpec.
 *
 * @author nagarro
 */
public class AppiumConfig {

    /* The instance of AppiumConfigSpec */
    private static final AppiumConfigSpec INSTANCE =
            ConfigFactory.create(AppiumConfigSpec.class, System.getProperties(), System.getenv());

    /**
     * Gets the instance of AppiumConfigSpec.
     *
     * @return the instance of AppiumConfigSpec.
     */
    public static AppiumConfigSpec getInstance() {
        return INSTANCE;
    }

    @LoadPolicy(LoadType.MERGE)
    @Sources({"classpath:com/nagarro/driven/client/appium/config/AppiumConfig.properties"})
    public interface AppiumConfigSpec extends Config {

        @Key("selenium.report.video.recording")
        @DefaultValue("false")
        boolean videoRecording();
    }
}
