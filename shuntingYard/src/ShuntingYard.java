import token.Function;
import token.Operator;
import token.Token;

import java.util.*;

/**
 * TODO use tokenizer for optimization?? (is<X> check only done once)
 * A class that sorts a string of infix to Reverse Polish Notation (RPN)
 * Created by Benjamin Wijk on 2017-05-29.
 */
public class ShuntingYard {
    private Stack<Token> output;
    private Stack<Token> stack;
    private Queue<Token> input;

    private Map<String, Operator> operators;

    public ShuntingYard() {
        createOperators();
    }

    /**
     * Begins the process of sorting token according to Reverse Polish Notation
     *
     * @param calculation Calculation to be sorted. Each token should be separated by whitespace.
     * @return String with token sorted by RPN
     */
    public String sortToRPN(String calculation) {
        output = new Stack<>();
        stack = new Stack<>();
        input = new ArrayDeque<>();

        try(Scanner in = new Scanner(calculation)) {
            while (in.hasNext()) {
                //tokenizer does stuff
                //add token to input
            }
        }

        //Do all the work here.
        try {
            handleTokens();
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }

        //output sorted, create string.
        StringJoiner sj = new StringJoiner(" ");
        for (Token t : output) {
            sj.add(t.getString());
        }
        return sj.toString();
    }

    /**
     * While input isn't empty, checks what type of token is at TOS and handles it accordingly.
     * Finally, pops operator stack to output as well.
     *
     * @throws InputMismatchException separator or parenthesis mismatch
     */
    private void handleTokens() throws InputMismatchException {
        while(!input.isEmpty()) {
            input.poll().handle(stack, output);
        }
        popEntireStack();
    }

    /*
     if the token is an operator, a, then:
     while there is an operator token b, at the top of the
     stack and either a is left-associative and its precedence is
     less than b, or a is right associative, and has precedence less than
     that of b:
     pop b off the stack, onto the output queue;
     at the end of iteration push a onto the stack.
     */

    /**
     * if the token is a right parenthesis (i.e. ")"):
     * until the token at the top of the stack is a left parenthesis,
     * pop operators off the stack onto the output queue.
     * pop the left parenthesis from the stack, but not onto the output queue.
     * if the token at the top of the stack is a function token, pop it onto the output queue.
     * if the stack runs out without finding a left parenthesis,
     * then there are mismatched parentheses.
     */
    private void handleParenthesisRight() {
        try {
            while (!isParenthesisLeft(stack.peek()) &&
                    !isFunction(stack.peek())) { //More operators "in" parenthesis, keep popping.

                output.add(
                        stack.pop());
            }
            //Loop done, remove parenthesis.
            if(isFunction(stack.peek())){
                output.add(stack.pop()); //add function token
            }else{
                stack.pop(); //Pop left parenthesis
            }
            input.poll(); //Pop right parenthesis
        } catch (EmptyStackException e) {
            e.printStackTrace();
            printStacks();
            System.exit(1); //To stop potential loops. One error is enough.
        }
    }

    /**
     * Called after all token in input have been handled. Pops operators from the stack until empty. <br>
     * Throws InputMismatchException if a parenthesis (or non-operator) is still found in stack.
     */
    private void popEntireStack() {
        while (!stack.isEmpty()) {
            if (!(stack.peek() instanceof Operator) && !(stack.peek() instanceof Function)) {
                printStacks();
                throw new InputMismatchException("Parenthesis mismatch.");
            }
            output.add(stack.pop());
        }
    }

    private boolean isArgSeparator(String token) {
        return token.equals(",");
    }

    /**
     * Try to parse token as double.
     * @param token
     * @return true if parse works, false otherwise.
     */
    private boolean isNumber(String token) {

        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFunction(String token) {
        return token.matches("[a-zA-Z]+\\(");
    }

    private boolean isOperator(String token) {
        return operators.containsKey(token);
    }

    private boolean isParenthesisLeft(String token) {
        return token.equals("(");
    }

    private boolean isParenthesisRight(String token) {
        return token.equals(")");
    }

    private void printStacks() {
        System.out.println("in: " + input);
        System.out.println("op: " + stack);
        System.out.println("out: " + output);
    }

    private void createOperators() {
        operators = new HashMap<>();

        operators.put("^", new Operator("^", 4, false));
        operators.put("*", new Operator("*", 3, true));
        operators.put("/", new Operator("/", 3, true));
        operators.put("+", new Operator("+", 2, true));
        operators.put("-", new Operator("-", 2, true));
    }


}
