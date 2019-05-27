package me.kix.uzi.api.property;

public class Property<T> {

    private final String label;
    protected T value;


    public Property(String label, T value) {
        this.label = label;
        this.value = value;
    }


    public String getLabel() {
        return label;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
