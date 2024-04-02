package com.nagarro.driven.project.espo.test.config;

import org.aeonbits.owner.ConfigFactory;

/**
 * This class is used to create the instance of the espo config.
 *
 * @author nagarro
 */
public class EspoConfigHolder {

    /* The instance of EspoConfig */
    private static final EspoConfig INSTANCE = ConfigFactory.create(EspoConfig.class);

    // Singleton behavior
    private EspoConfigHolder() {
    }

    /**
     * Gets the instance of EspoConfig.
     *
     * @return the instance of EspoConfig.
     */
    public static EspoConfig getInstance() {
        return INSTANCE;
    }
}
