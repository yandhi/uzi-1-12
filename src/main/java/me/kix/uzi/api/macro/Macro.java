package me.kix.uzi.api.macro;

import me.kix.uzi.api.action.Action;
import me.kix.uzi.api.util.interfaces.Labeled;

public class Macro implements Labeled {

    private final String label;
    private String facet;
    private final Action action;

    public Macro(String label, String facet, Action action) {
        this.label = label;
        this.facet = facet;
        this.action = action;
    }

    public void performAction() {
        action.performAction();
    }

    @Override
    public String getLabel() {
        return label;
    }

    public String getFacet() {
        return facet;
    }

    public void setFacet(String facet) {
        this.facet = facet;
    }

    public Action getAction() {
        return action;
    }
}
