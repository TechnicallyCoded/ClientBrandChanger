/*
 * Copyright TechnicallyCoded 2024
 * See the LICENSE file for more information.
 */
package com.tcoded.clientbrandchanger.mixins;

import com.tcoded.clientbrandchanger.config.ClientBrandChangerModConfig;
import com.tcoded.clientbrandchanger.ClientBrandChangerClient;
import net.minecraft.network.packet.c2s.login.LoginQueryResponseC2SPacket;
import net.minecraft.network.packet.c2s.login.LoginQueryResponsePayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Prevents the client from sending a response to the server when ghost mode is
 * enabled.
 */
@Mixin(LoginQueryResponseC2SPacket.class)
public class LoginQueryResponseC2SPacketMixin {
	@Inject(at = @At("TAIL"), method = "response()Lnet/minecraft/network/packet/c2s/login/LoginQueryResponsePayload;")
	private void clientbrandchanger$response(CallbackInfoReturnable<LoginQueryResponsePayload> info) {
		// default to ghost mode if the mod is not initialized - shouldn't occur!
		if (ClientBrandChangerClient.getInstance() == null) {
			info.setReturnValue(null);
		}
		ClientBrandChangerModConfig config = ClientBrandChangerClient.getInstance().getConfig();
		if (!config.enable || !config.ghostMode) {
			return;
		}
		info.setReturnValue(null);
	}

}
