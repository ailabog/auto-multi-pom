package com.nagarro.driven.project.wikipedia.tests.config;

import org.aeonbits.owner.ConfigFactory;

/**
 * This class is used to create the instance of the espo config.
 * 
 * @author nagarro
 */
public class WikiConfigHolder {
	
    /* The instance of EspoConfig */
	private static final WikiConfig INSTANCE = ConfigFactory.create(WikiConfig.class);

	private WikiConfigHolder(){
	}
	/**
	 * Gets the instance of EspoConfig.
	 * 
	 * @return the instance of EspoConfig.
	 */
	public static WikiConfig getInstance() {
		return INSTANCE;
	}
}
