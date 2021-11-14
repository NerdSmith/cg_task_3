package ru.vsu.cs.gui.DrawingObject;

import ru.vsu.cs.function_interpreter.tree.ExpressionTree;
import ru.vsu.cs.gui.RealPoint;
import ru.vsu.cs.gui.ScreenConverter;
import ru.vsu.cs.gui.ScreenPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph implements Drawing {
    private ExpressionTree expressionTree;

    private final double ACCURACY_MULTIPLIER = 10000;
    private final double STEP_DELTA = 1;
    private final double EPSILON = 10E-4;

    public Graph(String strFunction) throws Exception {
        this.expressionTree = new ExpressionTree(strFunction);
    }
    //TODO delete line in 1/x
    public ArrayList<RealPoint> computeRealCoordinates(double leftRealBound, double rightRealBound) {
        Map<Character, Double> vars = new HashMap<>();
        ArrayList<RealPoint> realCoordinates = new ArrayList<>();

        double multipliedLeftBound = leftRealBound * ACCURACY_MULTIPLIER;
        double multipliedRightBound = rightRealBound * ACCURACY_MULTIPLIER;
        for (
                double currMultipliedVal = multipliedLeftBound;
                currMultipliedVal < multipliedRightBound;
                currMultipliedVal += STEP_DELTA
        ) {
            double currRealVal = currMultipliedVal / ACCURACY_MULTIPLIER;

            if (Math.abs(currRealVal % 1) < EPSILON) {
                vars.put('x', (double) ((int) currRealVal));
            }
            else {
                vars.put('x', currRealVal);
            }

            double expressionResult;
            try {
                expressionResult = expressionTree.compute(vars);
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }

            if (Double.isFinite(expressionResult)) {
                realCoordinates.add(new RealPoint(currRealVal, expressionResult));
            }
            else {
                realCoordinates.add(new RealPoint());
            }
        }
        return realCoordinates;
    }

    public void draw(Graphics2D graphics2D, ScreenConverter screenConverter) {
        double leftRealBound = screenConverter.getStartX();
        double rightRealBound = leftRealBound + screenConverter.getRealWidth();
        ArrayList<RealPoint> realCoordinates = computeRealCoordinates(leftRealBound, rightRealBound);

        RealPoint currCoordinate = realCoordinates.get(0);
        for (int i = 1; i < realCoordinates.size(); i++) {
            RealPoint nextCoordinate = realCoordinates.get(i);
            if (!currCoordinate.isUndefined() && !nextCoordinate.isUndefined()) {
                ScreenPoint currScreenCoordinate = screenConverter.convertRealToScreen(currCoordinate);
                ScreenPoint nextScreenCoordinate = screenConverter.convertRealToScreen(nextCoordinate);
                graphics2D.drawLine(
                        currScreenCoordinate.getX(), currScreenCoordinate.getY(),
                        nextScreenCoordinate.getX(), nextScreenCoordinate.getY()
                );
            }
            currCoordinate = nextCoordinate;
        }
    }

    public static void main(String[] args) throws Exception {
        Graph g = new Graph("x*2-1");
        // g.getLines();
        System.out.println('a');
    }
}
