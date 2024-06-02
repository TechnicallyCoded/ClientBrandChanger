/*
 * Copyright TechnicallyCoded 2024
 * See the LICENSE file for more information.
 */
package com.tcoded.clientbrandchanger.modmenu;

import com.tcoded.clientbrandchanger.ClientBrandChangerClient;
import com.tcoded.clientbrandchanger.config.ClientBrandChangerModConfig;
import com.tcoded.clientbrandchanger.config.ClientBrandChangerModConfigLoader;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

/**
 * Configuration menu for when the ModMenu API is available.
 */
public class ClientBrandChangerModMenu implements ModMenuApi {
	/**
	 * Stored default config options.
	 */
	private static final ClientBrandChangerModConfig DEFAULTS = new ClientBrandChangerModConfig();
	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> {
			ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent)
					.setTitle(Text.translatable("title.clientbrandchanger.config"));
			// get the entry builder
			ConfigEntryBuilder entryBuilder = builder.entryBuilder();
			ConfigCategory general = builder
					.getOrCreateCategory(Text.translatable("category.clientbrandchanger.general"));
			// add the enabled config
			general.addEntry(entryBuilder
					.startBooleanToggle(Text.translatable("option.clientbrandchanger.enable"),
							ClientBrandChangerClient.getInstance().getConfig().enable)
					.setDefaultValue(DEFAULTS.enable).setTooltip(Text.translatable("tooltip.clientbrandchanger.enable"))
					.setSaveConsumer(newValue -> ClientBrandChangerClient.getInstance().getConfig().enable = newValue)
					.build());
			// add the brand name config
			general.addEntry(entryBuilder
					.startStrField(Text.translatable("option.clientbrandchanger.brandName"),
							ClientBrandChangerClient.getInstance().getConfig().brandName)
					.setDefaultValue(DEFAULTS.brandName)
					.setTooltip(Text.translatable("tooltip.clientbrandchanger.brandName"))
					.setSaveConsumer(
							newValue -> ClientBrandChangerClient.getInstance().getConfig().brandName = newValue)
					.build());
			// add the ghost mode config
			general.addEntry(entryBuilder
					.startBooleanToggle(Text.translatable("option.clientbrandchanger.ghostMode"),
							ClientBrandChangerClient.getInstance().getConfig().ghostMode)
					.setDefaultValue(DEFAULTS.ghostMode)
					.setTooltip(Text.translatable("tooltip.clientbrandchanger.ghostMode"))
					.setSaveConsumer(
							newValue -> ClientBrandChangerClient.getInstance().getConfig().ghostMode = newValue)
					.build());
			// save the config
			builder.setSavingRunnable(ClientBrandChangerModConfigLoader::saveConfig);
			return builder.build();
		};
	}
}
