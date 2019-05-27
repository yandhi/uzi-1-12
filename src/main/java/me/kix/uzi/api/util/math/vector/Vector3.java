package me.kix.uzi.api.util.math.vector;

public class Vector3<T extends Number> {

    private T x, y, z;

    public T getX() {
        return x;
    }

    public Vector3(T x, T y, T z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3() {
    }

    public Vector3 setX(T x) {
        this.x = x;
        return this;
    }

    public T getY() {
        return y;
    }

    public Vector3 setY(T y) {
        this.y = y;
        return this;
    }

    public T getZ() {
        return z;
    }

    public Vector3 setZ(T z) {
        this.z = z;
        return this;
    }
}
