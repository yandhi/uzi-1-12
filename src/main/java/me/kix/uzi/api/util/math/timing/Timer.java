package me.kix.uzi.api.util.math.timing;

public class Timer {

    private long now = System.currentTimeMillis();

    public boolean completed(int delay) {
        return System.currentTimeMillis() - now >= delay;
    }

    public void reset() {
        now = System.currentTimeMillis();
    }

}
