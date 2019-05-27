package me.kix.uzi.api.game.accessors.client;

import net.minecraft.entity.Entity;
import net.minecraft.util.Session;
import net.minecraft.util.Timer;

public interface IMinecraft {

    Timer getTimer();

    void setSession(Session session);

    void setRightClickDelayTimer(int delay);

    void clickMouse(int button);

    Entity getRenderViewEntity();

    int getDebugFPS();
}
