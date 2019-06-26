package me.kix.uzi.api.manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListManager<T> {

    /**
     * The collection of items in the manager.
     */
    private final List<T> contents = new CopyOnWriteArrayList<>();

    public List<T> getContents() {
        return contents;
    }
}
