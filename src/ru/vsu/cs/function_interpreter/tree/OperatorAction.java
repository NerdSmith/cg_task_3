package ru.vsu.cs.function_interpreter.tree;

import ru.vsu.cs.function_interpreter.action.*;

import java.util.AbstractMap;
import java.util.Map;

public class OperatorAction {
    public static final double ACCURACY = 10000.0;
    // todo: create a constructor with functions objects
    private static final Map<String, Action> actions = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, Action>("+", new Sum()),
            new AbstractMap.SimpleEntry<String, Action>("-", new Subtract()),
            new AbstractMap.SimpleEntry<String, Action>("*", new Multiply()),
            new AbstractMap.SimpleEntry<String, Action>("/", new Divide()),
            new AbstractMap.SimpleEntry<String, Action>("^", new Power()),
            new AbstractMap.SimpleEntry<String, Action>("$", new Root())
    );

    public static Double performAnAction(String operator, Double firstOperand, Double secondOperand) {
        double computeRes = actions.get(operator).compute(firstOperand, secondOperand);
//        double res;
//        if (Double.isFinite(computeRes)) {
//            res = Math.round(computeRes * ACCURACY) / ACCURACY;
//        }
//        else {
//            res = computeRes;
//        }
//        return res;
        return computeRes;
    }
}
