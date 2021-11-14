package ru.vsu.cs.gui.DrawingObject;

import ru.vsu.cs.gui.RealPoint;
import ru.vsu.cs.gui.ScreenConverter;
import ru.vsu.cs.gui.ScreenPoint;

import java.awt.*;

public class Line implements Drawing {
    private RealPoint p1;
    private RealPoint p2;

    public Line(RealPoint p1, RealPoint p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void setP1(RealPoint p1) {
        this.p1 = p1;
    }

    public void setP2(RealPoint p2) {
        this.p2 = p2;
    }

    public RealPoint getP1() {
        return p1;
    }

    public RealPoint getP2() {
        return p2;
    }

    @Override
    public void draw(Graphics2D graphics2D, ScreenConverter screenConverter) {
        ScreenPoint point1 = screenConverter.convertRealToScreen(p1);
        ScreenPoint point2 = screenConverter.convertRealToScreen(p2);
        graphics2D.drawLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
    }
}
