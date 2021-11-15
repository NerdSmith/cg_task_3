package ru.vsu.cs.gui;

import ru.vsu.cs.function_interpreter.util.DrawUtils;
import ru.vsu.cs.gui.DrawingObject.Drawing;
import ru.vsu.cs.gui.DrawingObject.Line;
import ru.vsu.cs.gui.DrawingObject.Text;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private ScreenConverter screenConverter;
    private Line xAxis;
    private Line yAxis;
    private Font defaultFont = new Font("TimesRoman", Font.PLAIN, 18);

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

        DrawUtils.drawWithColor(graphics2D, Color.BLUE, () -> {
            updateAxes();
            xAxis.draw(graphics2D, screenConverter);
            yAxis.draw(graphics2D, screenConverter);
            drawAxisScale(graphics2D);
        });


        // drawLine(graphics2D, screenConverter, new Line(new RealPoint(0,0), new RealPoint(1, 1)));

        DrawUtils.drawWithColor(graphics2D, Color.RED, () -> {
            for (Drawing drawing: objectsToDraw) {
                drawing.draw(graphics2D, screenConverter);
            }
        });

        DrawUtils.drawWithColor(graphics2D, Color.BLUE, () -> {
            drawAxisScale(graphics2D);
        });


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
        RealPoint realX = screenConverter.convertScreenToReal(new ScreenPoint(mouseEvent.getX(), mouseEvent.getY()));
        System.out.print("\r" + realX.getX() + " | " + realX.getY());
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

        double firstLinePointY;
        double secondLinePointY;

        double textXShift;
        double textYShift;

        if (screenConverter.getStartY() > 0) {
            if (screenConverter.getStartY() < screenConverter.getRealHeight()) {
                firstLinePointY = -0.05;
                secondLinePointY = 0.05;

            }
            else {
                firstLinePointY = screenConverter.getStartY() - screenConverter.getRealHeight();
                secondLinePointY = screenConverter.getStartY() - screenConverter.getRealHeight() + 0.2;

            }
            textXShift = 0.04;
            textYShift = 0.06;
        }
        else {
            firstLinePointY = screenConverter.getStartY();
            secondLinePointY = screenConverter.getStartY() - 0.2;

            textXShift = 0.04;
            textYShift = -0.1;
        }
        for (int i = leftRealBound; i <= rightRealBound; i++) {
            new Line(new RealPoint(i, firstLinePointY), new RealPoint(i, secondLinePointY)).draw(graphics2D, screenConverter);
            new Text(Integer.toString(i), this.defaultFont, new RealPoint(i + textXShift, firstLinePointY + textYShift)).draw(graphics2D, screenConverter);
        }
    }

    private void drawAxisScaleY(Graphics2D graphics2D) {
        int upRealBound = (int) Math.ceil(screenConverter.getStartY());
        int downRealBound = (int) (screenConverter.getStartY() - screenConverter.getRealHeight());

        double firstLinePointX;
        double secondLinePointX;
        // System.out.println(screenConverter.getStartX() + " | " + screenConverter.getRealWidth());

        double textXShift;
        double textYShift;

        if (screenConverter.getStartX() < 0) {
            if (screenConverter.getStartX() > -screenConverter.getRealWidth()) {
                firstLinePointX = -0.05;
                secondLinePointX = 0.05;
            }
            else {
                firstLinePointX = screenConverter.getStartX() + screenConverter.getRealWidth();
                secondLinePointX = screenConverter.getStartX() + screenConverter.getRealWidth() - 0.1;
            }
            textXShift = -0.06;
            textYShift = 0.01;
        }
        else {
            firstLinePointX = screenConverter.getStartX();
            secondLinePointX = screenConverter.getStartX() + 0.1;

            textXShift = 0.04;
            textYShift = 0.06;
        }

        for (int i = downRealBound; i <= upRealBound; i++) {
            new Line(new RealPoint(firstLinePointX, i), new RealPoint(secondLinePointX, i)).draw(graphics2D, screenConverter);
            new Text(Integer.toString(i), this.defaultFont, new RealPoint(firstLinePointX + textXShift, i + textYShift)).draw(graphics2D, screenConverter);
        }
    }

    public void addDrawingObject(Drawing drawing) {
        objectsToDraw.add(drawing);
    }

    public void removeDrawingObject() {
        if (!objectsToDraw.isEmpty()) {
            objectsToDraw.remove(objectsToDraw.size() - 1);
            this.repaint();
        }
    }
}
