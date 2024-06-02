/*
 * Copyright TechnicallyCoded 2024
 * See the LICENSE file for more information.
 */
package com.tcoded.clientbrandchanger.mixins;

import com.tcoded.clientbrandchanger.ClientBrandChangerClient;
import com.tcoded.clientbrandchanger.config.ClientBrandChangerModConfig;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Injects the desired client brand into the ClientBrandRetriever.
 */
@Mixin(ClientBrandRetriever.class)
public class ClientBrandRetrieverMixin {
	@Inject(at = @At("HEAD"), method = "getClientModName", cancellable = true, remap = false)
	private static void clientbrandchanger$getConfiguredClientBrand(CallbackInfoReturnable<String> info) {
		// prevent npe on client initialization
		if (ClientBrandChangerClient.getInstance() == null) {
			info.setReturnValue("fabric");
			return;
		}
		ClientBrandChangerModConfig config = ClientBrandChangerClient.getInstance().getConfig();
		// if custom brand is not enabled, return before setting
		if (!config.enable) {
			return;
		}
		info.setReturnValue(config.brandName);
	}
}
