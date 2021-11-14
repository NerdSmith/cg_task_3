package ru.vsu.cs.gui;

public class ScreenConverter {
    private double startX, startY, realWidth, realHeight;
    private int screenWidth;
    private int screenHeight;

    public ScreenConverter(double startX, double startY, double realWidth, double realHeight, int screenWidth, int screenHeight) {
        this.startX = startX;
        this.startY = startY;
        this.realWidth = realWidth;
        this.realHeight = realHeight;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public ScreenPoint convertRealToScreen(RealPoint realPoint) {
        double x = (realPoint.getX() - startX) / realWidth * screenWidth;
        double y = (startY - realPoint.getY()) / realHeight * screenHeight;
        return new ScreenPoint((int) x, (int) y);
    }

    public RealPoint convertScreenToReal(ScreenPoint screenPoint) {
        double x = startX + screenPoint.getX() * realWidth / screenWidth;
        double y = startY - screenPoint.getY() * realHeight / screenHeight;
        return new RealPoint(x, y);
    }

    public void moveStartPoint(RealPoint delta) {
        startX += delta.getX();
        startY += delta.getY();
    }

    public void changeScale(double newScale) {
        double deltaX = (realWidth - realWidth * newScale) / 2;
        double deltaY = (realHeight - realHeight * newScale) / 2;
//        System.out.println(deltaX + " | " + deltaY);
        startX += deltaX;
        startY -= deltaY;

        realWidth *= newScale;
        realHeight *= newScale;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public double getRealWidth() {
        return realWidth;
    }

    public double getRealHeight() {
        return realHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }

    public void setRealWidth(double realWidth) {
        this.realWidth = realWidth;
    }

    public void setRealHeight(double realHeight) {
        this.realHeight = realHeight;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
