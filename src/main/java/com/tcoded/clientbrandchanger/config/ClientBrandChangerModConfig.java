/*
 * Copyright TechnicallyCoded 2024
 * See the LICENSE file for more information.
 */
package com.tcoded.clientbrandchanger.config;

/**
 * A data class containing the ClientBrandChanger mod configuration.
 */
public class ClientBrandChangerModConfig {
	/**
	 * The name of the brand that is sent to the server. Defaults to vanilla.
	 */
	public String brandName = "vanilla";
	/**
	 * Enables the sending of the custom brand.
	 */
	public Boolean enable = true;

	/**
	 * Enables ghost mode. This disables the custom plugin login flow used by
	 * various mod loaders to test for active mods.
	 */
	public Boolean ghostMode = false;
}
