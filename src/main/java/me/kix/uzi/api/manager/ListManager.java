package me.kix.uzi.api.manager;

import java.util.ArrayList;
import java.util.List;

public class ListManager<T> {
    private final List<T> contents = new ArrayList<>();

    public List<T> getContents() {
        return contents;
    }
}
