package a4;

import java.util.ArrayDeque;

/**
 * The Infix class converts arithmetic expressions from infix notation (e.g.,
 * "(3+2)*5")
 * into postfix notation (e.g., "3 2 + 5 *") using the shunting-yard algorithm.
 * 
 * This class processes a queue of tokens (which includes numbers and operators)
 * generated
 * by the Tokenizer. It handles operator precedence, associativity (including
 * right-associative
 * exponentiation), and parentheses. Once the conversion is complete, it calls
 * the {@code Postfix.postfix}
 * method to compute the final result.
 **/
public class Infix {
    /**
     *
     * @param tokens an {@code ArrayDeque} of {@code Object} tokens representing the
     *               input queue.
     *               Numeric tokens should be instances of {@code Double}, and
     *               operator tokens should be
     *               instances of {@code Character}.
     * @return the computed result of the expression as a {@code Double}.
     * @throws IllegalArgumentException if the expression is malformed (e.g.,
     *                                  mismatched parentheses)
     *                                  or contains insufficient operands for an
     *                                  operator.
     */
    public static Double infixToPostfix(ArrayDeque<Object> tokens) {
        ArrayDeque<Object> output = new ArrayDeque<>();
        ArrayDeque<Object> stack = new ArrayDeque<>();
        stack.addFirst('a');
        for (Object token : tokens) {
            //If the token is a number, then add it to the output queue.
            if (token instanceof Double) {
                output.addLast((Double) token);
            } else {
                if (token.equals('^')) {
                    stack.addFirst(token);
                } else if (token.equals('(')) { //If the token is a left parenthesis, then push it onto the stack.
                    stack.addFirst(token);
                } else if (token.equals(')')) { //If the token is a right parenthesis:
                    if(stack.contains('(')){
                        while ((Character) stack.peek() != '(') { //Until the token at the top of the stack is a left parenthesis, pop operators off the stack onto the output queue.
                            output.addLast(stack.removeFirst());
                        }
    
                        stack.removeFirst(); //Pop the left parenthesis from the stack, but not onto the output queue.
                    } else{
                        throw new IllegalArgumentException("mismatched parentheses");
                    }

                } else { 
                    while (stack.size() > 1 && precedence((Character) stack.peek()) >= precedence((Character) token)) { // //while there is an operator token at the top of the stack (the "stack operator"), and the stack operator has GREATER THAN OR EQUAL precedence than the queue operator,
                        output.addLast(stack.removeFirst()); //pop the stack operator off the stack and add it to the output queue;
                    }
                    stack.addFirst(token);//when no more high-precedence stack operators remain, finally push the queue operator onto the stack.
                }

            }
        }

        while (stack.size() > 1) { //While there are still tokens in the stack besides the initial a:
            if (stack.contains('(') ){
                //If the token on the top of the stack is a parenthesis, then there are mismatched parentheses.
                throw new IllegalArgumentException("mismatched parentheses");
            }
            output.addLast(stack.removeFirst()); //If it is an operator, pop it onto the output queue.
        }
        return Postfix.postfix(output); 
    }

    /**
     * Returns the precedence value for a given operator.
     * Higher values indicate higher precedence.
     *
     * @param operator the operator for which precedence is determined
     * @return an integer representing the operator's precedence
     */
    public static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 2;
            case '*':
            case '/':
                return 3;
            case '^':
                return 4;
            default:
                return 0;
        }
    }

    // public static void main(String[] args) {
    // // String[] expressions = {
    // // "6+1", "2+3+2", "1+1+1+1+1+1+1", "10-3", "15-6-2", "12-1-1-1-1-1",
    // // "7*1", "0.2*35", "7*1*1*1*1*1*1", "14/2", "70/5/2", "1+2*3",
    // // "0-1-2*3+4*5-6", "2*4-1", "15-2*4", "18/2-2", "11-8/2", "10+2-5",
    // "10-5+2",
    // // "70*4/40",
    // // "70/5*0.5", "100/10-9/3"
    // // };
    // // for (int i = 0; i < expressions.length; i++) {
    // //
    // System.out.println(Infix.infixToPostfix(Tokenizer.readTokens(expressions[i])));
    // // }

    // String[] expressions = {
    // "4^2"
    // };

    // for (int i = 0; i < expressions.length; i++) {
    // System.out.println(Infix.infixToPostfix(Tokenizer.readTokens(expressions[i])));
    // }

    // }
}