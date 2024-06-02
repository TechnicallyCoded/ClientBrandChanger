/*
 * Copyright TechnicallyCoded 2024
 * See the LICENSE file for more information.
 */
package com.tcoded.clientbrandchanger;

import com.tcoded.clientbrandchanger.config.ClientBrandChangerModConfig;
import com.tcoded.clientbrandchanger.config.ClientBrandChangerModConfigLoader;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientBrandChangerClient implements ClientModInitializer {
	private final Logger logger = LogManager.getLogger("ClientBrandChanger");
	/**
	 * Get the mod logger.
	 * 
	 * @return The mod logger.
	 */
	public Logger getLogger() {
		return logger;
	}
	/**
	 * The mod configuration.
	 */
	private ClientBrandChangerModConfig config = new ClientBrandChangerModConfig();
	/**
	 * Get the mod configuration.
	 * 
	 * @return The mod configuration.
	 */
	public ClientBrandChangerModConfig getConfig() {
		return config;
	}
	private static ClientBrandChangerClient instance;
	/**
	 * Get the mod instance.
	 * 
	 * @return The mod instance.
	 */
	public static ClientBrandChangerClient getInstance() {
		return instance;
	}
	@Override
	public void onInitializeClient() {
		// set the singleton instance
		instance = this;
		// initialize the mod configuration
		config = ClientBrandChangerModConfigLoader.tryLoad();
		this.logger.info("ClientBrandChanger initialized - Brand is set to: '" + config.brandName + "'");
	}
}
