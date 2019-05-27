package me.kix.uzi.api.util.interfaces;

public interface Configurable<T> {

    void save(T destination);

    void load(T source);

}
