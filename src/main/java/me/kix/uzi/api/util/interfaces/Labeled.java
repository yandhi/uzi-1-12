package me.kix.uzi.api.util.interfaces;

public interface Labeled {

    String getLabel();

    interface Mutable extends Labeled {
        void setLabel(String label);
    }

}
