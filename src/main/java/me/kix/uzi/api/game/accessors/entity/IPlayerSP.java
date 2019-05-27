package me.kix.uzi.api.game.accessors.entity;

public interface IPlayerSP {

    boolean isInLiquid();

    boolean isOnLiquid();

    boolean isMoving();

    void setSpeed(double speed);

    double getSpeed();

}
