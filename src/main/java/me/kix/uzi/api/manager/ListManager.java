package me.kix.uzi.api.manager;

import java.util.ArrayList;
import java.util.List;

public class ListManager<T> {

    /**
     * The collection of items in the manager.
     */
    private final List<T> contents = new ArrayList<>();

    public List<T> getContents() {
        return contents;
    }
}
