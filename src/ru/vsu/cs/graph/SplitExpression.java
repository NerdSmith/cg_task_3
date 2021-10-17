package ru.vsu.cs.graph;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

public class SplitExpression {
    private final Map<String, String> parts;
    private final Method sign;

    public SplitExpression(String left, String right, Method sign) {
        this.parts = Map.ofEntries(
                Map.entry("left", left),
                Map.entry("right", right)
        );
        this.sign = sign;
    }

    public SplitExpression(String expression) {


        this.parts = Map.ofEntries(
                Map.entry("left", "l"),
                Map.entry("right", "r")
        );
        this.sign = null;
    }

    public ArrayList<String> getPartsList() {
        return new ArrayList<>(parts.values());
    }

    public Method getSign() {
        return sign;
    }
}
