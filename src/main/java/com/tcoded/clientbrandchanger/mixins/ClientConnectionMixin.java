/*
 * Copyright TechnicallyCoded 2024
 * See the LICENSE file for more information.
 */
package com.tcoded.clientbrandchanger.mixins;

import com.tcoded.clientbrandchanger.config.ClientBrandChangerModConfig;
import com.tcoded.clientbrandchanger.ClientBrandChangerClient;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.BrandCustomPayload;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.common.CustomPayloadC2SPacket;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevents the client from sending custom payload packets ( except vanilla ones
 * that aren't (un)register ) when ghost mode is enabled.
 */
@Mixin(ClientConnection.class)
public class ClientConnectionMixin {
    @Inject(method = "send(Lnet/minecraft/network/packet/Packet;Lnet/minecraft/network/PacketCallbacks;Z)V", at = @At("HEAD"), cancellable = true)
    public void clientbrandchanger$send(Packet<?> packet, @Nullable PacketCallbacks callbacks, boolean flush,
                                        CallbackInfo ci) {
        // default to ghost mode if the mod is not initialized - shouldn't occur!
        if (ClientBrandChangerClient.getInstance() == null) {
            ci.cancel();
        }

        ClientBrandChangerModConfig config = ClientBrandChangerClient.getInstance().getConfig();

        // Ignore packets if the mod is disabled
        if (!config.enable) {
            return;
        }

        // Ignore packets if ghost mode is disabled
        if (!config.ghostMode) {
            return;
        }

        // Ignore non-custom payload packets
        if (!(packet instanceof CustomPayloadC2SPacket payloadC2SPacket)) {
            return;
        }

        // Ignore vanilla channels - except for register/unregister
        String packedChannelId = payloadC2SPacket.payload().getId().toString();
        if (packedChannelId.startsWith("minecraft:") &&
                !packedChannelId.equals("minecraft:register") &&
                !packedChannelId.equals("minecraft:unregister")
        ) {
            return;
        }

        // Cancel packet sending if ghost mode is enabled & the register/unregister channels are used
        ci.cancel();
    }
}
