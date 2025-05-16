package a4;

import java.util.ArrayDeque;

public class Postfix {
    ArrayDeque<Object> tokens = new ArrayDeque<>();

    /**
     * Evaluates a postfix expression represented as a queue of tokens.
     * 
     * The method processes each token in the queue:
     * 
     *   If the token is a {@code Double}, it is pushed onto the stack.
     *   If the token is an operator, the method pops two numbers off the stack,
     *       applies the operator, and pushes the result back onto the stack.
     * 
     * At the end of the evaluation, the stack should contain exactly one element,
     * which is the result of the expression.
     * 
     *
     * @param tokens a queue of tokens representing a valid postfix expression
     * @return the computed result of the expression as a {@code Double}
     * @throws IllegalArgumentException if the expression is malformed or contains insufficient operands
     */
    public static Double postfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Double> stack = new ArrayDeque<>();
        for (Object token : tokens) {
            if (token instanceof Double) {
                stack.addFirst((Double) token);
            } else {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Insufficient operands for operator: " + token);
                }
                Double operand2 = stack.removeFirst();
                Double operand1 = stack.removeFirst();
                switch ((Character) token) {
                    case '+':
                        stack.addFirst(operand1 + operand2);
                        break;
                    case '-':
                        stack.addFirst(operand1 - operand2);
                        break;
                    case '*':
                        stack.addFirst(operand1 * operand2);
                        break;
                    case '/':
                        stack.addFirst(operand1 / operand2);
                        break;
                    case '^':
                        stack.addFirst(Math.pow(operand1, operand2));
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operator: " + token);
                }
                
                
            }
        }
        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid postfix expression");
        }
        return stack.removeFirst();
    }
    
}