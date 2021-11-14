package ru.vsu.cs.gui;

import ru.vsu.cs.gui.DrawingObject.Drawing;
import ru.vsu.cs.gui.DrawingObject.Line;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private ScreenConverter screenConverter;
    private Line xAxis;
    private Line yAxis;

    private ArrayList<Drawing> objectsToDraw = new ArrayList<>();

    public DrawPanel() {
        screenConverter = new ScreenConverter(-2, 2, 4, 4, 800, 600);
        xAxis = new Line(new RealPoint(-2, 0), new RealPoint(2, 0));
        yAxis = new Line(new RealPoint(0, -1), new RealPoint(0, 1));


        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
    }

    @Override
    public void paintComponent(Graphics originalGraphics) {
        screenConverter.setScreenWidth(this.getWidth());
        screenConverter.setScreenHeight(this.getHeight());

        BufferedImage bufferedImage = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();

        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, this.getWidth(), this.getHeight());

        graphics2D.setColor(Color.BLUE);
        updateAxes();
        xAxis.draw(graphics2D, screenConverter);
        yAxis.draw(graphics2D, screenConverter);
        drawAxisScale(graphics2D);

        // drawLine(graphics2D, screenConverter, new Line(new RealPoint(0,0), new RealPoint(1, 1)));

        graphics2D.setColor(Color.RED);
        for (Drawing drawing: objectsToDraw) {
            drawing.draw(graphics2D, screenConverter);
        }

//        for (Line l: allLines) {
//            drawLine(graphics2D, screenConverter, l);
//        }
//        if (currentLine != null) {
//            graphics2D.setColor(Color.RED);
//            drawLine(graphics2D, screenConverter, currentLine);
//        }

        originalGraphics.drawImage(bufferedImage, 0 , 0, null);
        graphics2D.dispose();
    }


    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

    }

    private ScreenPoint prevPoint = null;
    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        prevPoint = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        prevPoint = null;
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        ScreenPoint currPoint = new ScreenPoint(mouseEvent.getX(), mouseEvent.getY());
        RealPoint p1 = screenConverter.convertScreenToReal(currPoint);
        RealPoint p2 = screenConverter.convertScreenToReal(prevPoint);
        RealPoint delta = p2.findDelta(p1);

        screenConverter.moveStartPoint(delta);
        prevPoint = currPoint;
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

    }

    private static final double SCALE_STEP = 0.01;

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        int clicks = mouseWheelEvent.getWheelRotation();
        double coef = 1 + SCALE_STEP * (clicks < 0 ? -1 : 1);
        double scale = 1;
        for (int i = Math.abs(clicks); i > 0; i--) {
            scale *= coef;
        }
        screenConverter.changeScale(scale);
        repaint();
    }

    private void updateAxes() {
        xAxis.setP1(new RealPoint(screenConverter.getRealWidth() + screenConverter.getStartX(), 0));
        xAxis.setP2(new RealPoint(screenConverter.getStartX(), 0));

        yAxis.setP1(new RealPoint(0, screenConverter.getStartY() - screenConverter.getRealHeight()));
        yAxis.setP2(new RealPoint(0, screenConverter.getStartY()));
    }

    private void drawAxisScale(Graphics2D graphics2D) {
        drawAxisScaleX(graphics2D);
        drawAxisScaleY(graphics2D);
    }

    private void drawAxisScaleX(Graphics2D graphics2D) {
        int leftRealBound = (int) screenConverter.getStartX();
        int rightRealBound = (int) Math.ceil(screenConverter.getStartX() + screenConverter.getRealWidth());

        if (screenConverter.getStartY() > 0 && screenConverter.getStartY() < screenConverter.getRealHeight()) {
            for (int i = leftRealBound; i <= rightRealBound; i++) {
                new Line(new RealPoint(i, -0.05), new RealPoint(i, 0.05)).draw(graphics2D, screenConverter);
            }
        }
    }

    private void drawAxisScaleY(Graphics2D graphics2D) {
        int upRealBound = (int) Math.ceil(screenConverter.getStartY());
        int downRealBound = (int) (screenConverter.getStartY() - screenConverter.getRealHeight());

        if (screenConverter.getStartX() < 0 && screenConverter.getStartX() < screenConverter.getScreenWidth()) {
            for (int i = downRealBound; i <= upRealBound; i++) {
                new Line(new RealPoint(-0.05, i), new RealPoint(0.05, i)).draw(graphics2D, screenConverter);
            }
        }
    }

    public void addDrawingObject(Drawing drawing) {
        objectsToDraw.add(drawing);
    }

    public void removeDrawingObject() {
        objectsToDraw.remove(objectsToDraw.size() - 1);
    }
}
