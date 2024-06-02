/*
 * Copyright TechnicallyCoded 2024
 * See the LICENSE file for more information.
 */
package com.tcoded.clientbrandchanger.config;

import com.moandjiezana.toml.Toml;
import com.moandjiezana.toml.TomlWriter;
import com.tcoded.clientbrandchanger.ClientBrandChangerClient;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.IOException;

/**
 * Represents the mod configuration.
 */
public class ClientBrandChangerModConfigLoader {
	/**
	 * The configuration file name.
	 */
	static String CONFIG_NAME = "clientbrandchanger.toml";
	/**
	 * The TOML writer.
	 */
	static TomlWriter TOML_WRITER = new TomlWriter();
	/**
	 * Attempts to load the mod configuration.
	 * 
	 * @return The mod configuration or the default config if loading fails.
	 */
	public static ClientBrandChangerModConfig tryLoad() {
		File file = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_NAME).toFile();
		ClientBrandChangerModConfig defaults = new ClientBrandChangerModConfig();
		// if file doesn't exist, copy the defaults
		if (!file.exists()) {
			try {
				TOML_WRITER.write(defaults, file);
			} catch (IOException ex) {
				// warn if writing fails
				ClientBrandChangerClient.getInstance().getLogger()
						.warn("Failed to load configuration from filesystem, using defaults. More info is below.");
				ClientBrandChangerClient.getInstance().getLogger().warn(ex);
			}
			return defaults;
		}
		// parse TOML and cast to config class
		return new Toml().read(file).to(ClientBrandChangerModConfig.class);
	}
	/**
	 * Save the in-memory config to disk
	 * 
	 * @return True if the action was successful, false otherwise.
	 */
	public static Boolean saveConfig() {
		ClientBrandChangerClient.getInstance().getLogger().info("Saving configuration to disk...");
		ClientBrandChangerClient.getInstance().getLogger()
				.info("Brand name = " + ClientBrandChangerClient.getInstance().getConfig().brandName);
		File file = FabricLoader.getInstance().getConfigDir().resolve(CONFIG_NAME).toFile();
		try {
			TOML_WRITER.write(ClientBrandChangerClient.getInstance().getConfig(), file);
		} catch (IOException ex) {
			ClientBrandChangerClient.getInstance().getLogger()
					.error("Failed to save configuration to disk - more info below");
			ClientBrandChangerClient.getInstance().getLogger().error(ex);
			return false;
		}
		return true;
	}
}
