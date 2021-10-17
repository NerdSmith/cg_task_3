package ru.vsu.cs.graph;

import java.util.Map;

public class ExpressionNode {
    private double value = 0;
    private SplitExpression splitExpression;
    private Map<String, String> neighbors;

    public ExpressionNode(String expression) {
        this.splitExpression = new SplitExpression(expression);
    }

}
