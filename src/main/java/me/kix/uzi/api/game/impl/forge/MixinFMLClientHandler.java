package me.kix.uzi.api.game.impl.forge;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.client.ExtendedServerListData;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.GuiAccessDenied;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * @author Kix
 * Created in 06 2019.
 */
@Mixin(FMLClientHandler.class)
public abstract class MixinFMLClientHandler {

    @Shadow
    private Map<ServerData, ExtendedServerListData> serverDataTag;

    @Shadow
    public abstract void showGuiScreen(@Nullable Object clientGuiElement);

    @Shadow
    private Minecraft client;
}
