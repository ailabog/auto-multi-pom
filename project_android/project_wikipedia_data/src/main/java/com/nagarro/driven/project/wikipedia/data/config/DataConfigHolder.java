package com.nagarro.driven.project.wikipedia.data.config;

import org.aeonbits.owner.ConfigFactory;

/**
 * This class is used to create the instance of the data config.
 *
 * @author nagarro
 */
public class DataConfigHolder {

    /* The instance of DataConfig */
    private static final DataConfig INSTANCE = ConfigFactory.create(DataConfig.class);

    // Singleton behavior
    private DataConfigHolder() {
    }

    /**
     * Gets the instance of DataConfig.
     *
     * @return the instance of DataConfig.
     */
    public static DataConfig getInstance() {
        return INSTANCE;
    }
}
