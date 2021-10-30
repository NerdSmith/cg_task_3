package ru.vsu.cs.tree;

import ru.vsu.cs.action.*;

import java.util.AbstractMap;
import java.util.Map;

public class OperatorAction {
    private static final Map<String, Action> actions = Map.ofEntries(
            new AbstractMap.SimpleEntry<String, Action>("+", new Sum()),
            new AbstractMap.SimpleEntry<String, Action>("-", new Subtract()),
            new AbstractMap.SimpleEntry<String, Action>("*", new Multiply()),
            new AbstractMap.SimpleEntry<String, Action>("/", new Divide()),
            new AbstractMap.SimpleEntry<String, Action>("^", new Power()),
            new AbstractMap.SimpleEntry<String, Action>("$", new Root())
    );

    public static Double performAnAction(String operator, Double firstOperand, Double secondOperand) {
        return actions.get(operator).compute(firstOperand, secondOperand);
    }
}
