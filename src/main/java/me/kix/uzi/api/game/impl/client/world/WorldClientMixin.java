package me.kix.uzi.api.game.impl.client.world;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import me.kix.uzi.Uzi;
import me.kix.uzi.management.event.entity.EventEntityChunk;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;

@Mixin(WorldClient.class)
public class WorldClientMixin {

    @Inject(method = "onEntityAdded", at = @At("HEAD"))
    private void onEntityAdded(Entity entity, CallbackInfo info) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventEntityChunk.Enter(entity));
    }

    @Inject(method = "onEntityRemoved", at = @At("HEAD"))
    private void onEntityRemoved(Entity entity, CallbackInfo info) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventEntityChunk.Leave(entity));
    }

}