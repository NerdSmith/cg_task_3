package ru.vsu.cs.gui;

public class ScreenPoint {
    private final Integer x;
    private final Integer y;
    private boolean isUndefined = false;

    public ScreenPoint(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public ScreenPoint() {
        this(null, null);
        this.isUndefined = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isUndefined() {
        return isUndefined;
    }
}
