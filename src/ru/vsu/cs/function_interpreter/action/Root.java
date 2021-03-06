package ru.vsu.cs.function_interpreter.action;

public class Root extends Action {
    @Override
    public Double compute(Double firstOperand, Double secondOperand) {
        return Math.pow(secondOperand, 1/firstOperand);
    }
}
