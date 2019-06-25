package me.kix.uzi.api.game.impl.client;

import me.kix.uzi.Uzi;
import me.kix.uzi.api.game.accessors.client.Game;
import me.kix.uzi.management.event.input.key.EventKeyPressed;
import me.kix.uzi.management.event.input.mouse.EventMousePressed;
import me.kix.uzi.management.event.misc.EventTick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.entity.Entity;
import net.minecraft.util.*;
import net.minecraftforge.fml.client.ExtendedServerListData;
import net.minecraftforge.fml.client.GuiAccessDenied;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements Game {

    @Shadow
    public GuiScreen currentScreen;

    @Accessor
    @Override
    public abstract Timer getTimer();

    @Accessor
    @Override
    public abstract void setSession(Session session);

    @Accessor
    @Override
    public abstract void setRightClickDelayTimer(int delay);

    @Accessor
    @Override
    public abstract Entity getRenderViewEntity();

    @Override
    @Accessor
    public abstract Minecraft getInstance();

    @Shadow
    private void clickMouse() {
    }

    @Shadow
    private void rightClickMouse() {
    }

    @Shadow
    private void middleClickMouse() {
    }

    @Shadow
    public GuiIngame ingameGUI;

    @Override
    public void clickMouse(int button) {
        if (button == 0)
            clickMouse();
        if (button == 1)
            rightClickMouse();
        if (button == 2)
            middleClickMouse();
    }

    @ModifyVariable(method = "displayGuiScreen", at = @At("HEAD"))
    private GuiScreen displayGuiScreen(GuiScreen screen) {
        return screen;
    }

    @Inject(method = "clickMouse", at = @At("HEAD"))
    private void leftClick(CallbackInfo ci) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventMousePressed(0));
    }

    @Inject(method = "rightClickMouse", at = @At("HEAD"))
    private void rightClick(CallbackInfo ci) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventMousePressed(1));
    }

    @Inject(method = "middleClickMouse", at = @At("HEAD"))
    private void middleClick(CallbackInfo ci) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventMousePressed(2));
    }

    @Inject(method = "runTick", at = @At("HEAD"))
    private void onTick(CallbackInfo info) {
        Uzi.INSTANCE.getEventManager().dispatch(new EventTick());
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        Uzi.INSTANCE.init();
    }

    @Inject(method = "runTickKeyboard", at = @At(value = "INVOKE_ASSIGN", target = "org/lwjgl/input/Keyboard.getEventKeyState()Z", remap = false))
    private void onKeyEvent(CallbackInfo ci) {
        if (currentScreen != null)
            return;

        boolean down = Keyboard.getEventKeyState();
        int key = Keyboard.getEventKey();
        char ch = Keyboard.getEventCharacter();

        if (down) {
            Uzi.INSTANCE.getEventManager().dispatch(new EventKeyPressed(key));
        }
    }
}