package ru.vsu.cs.function_interpreter.action;

public class Power extends Action {
    @Override
    public Double compute(Double firstOperand, Double secondOperand) {
        return Math.pow(firstOperand, secondOperand);
    }
}
