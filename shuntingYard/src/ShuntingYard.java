import token.Operator;

import java.util.*;

/**
 * TODO use tokenizer for optimization?? (is<X> check only done once)
 * A class that sorts a string of infix to Reverse Polish Notation (RPN)
 * Created by Benjamin Wijk on 2017-05-29.
 */
public class ShuntingYard {
    private Stack<String> output;
    private Stack<String> stack;
    private Queue<String> input;

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
                input.add(in.next());
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
        for (String s : output) {
            sj.add(s);
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
        while (!input.isEmpty()) {
            String token = input.peek();

            if (isNumber(token)) {
                handleNumber();
            } else if (isOperator(token)) {
                handleOperator();
            } else if (isParenthesisLeft(token)) {
                handleParenthesisLeft();
            } else if (isParenthesisRight(token)) {
                handleParenthesisRight();
            } else if (isFunction(token)) {
                handleFunction();
            } else if (isArgSeparator(token)) {
                handleArgSeparator();
            }
        }
        //input == empty
        popEntireStack();
    }

    /**
     * if the token is a function argument separator (e.g., a comma):
     * until the token at the top of the stack is a left parenthesis,
     * pop operators off the stack onto the output queue.
     * if no left parentheses are encountered, either the separator was misplaced
     * or parentheses were mismatched.
     * finally, remove comma from input.
     */
    private void handleArgSeparator() {
        while (!isParenthesisLeft(stack.peek()) &&
                !isFunction(stack.peek())) {
            output.add(
                    stack.pop());
            if (input.isEmpty()) {
                throw new InputMismatchException("ERROR: Separator misplaced or parenthesis mismatch");
            }
        }
        input.poll(); // remove comma
    }

    /**
     * If the token is a function token, then push it onto the stack.
     */
    private void handleFunction() {
        stack.add(
                input.poll());
    }

    /**
     * If the token is a number, then push it to the output queue.
     */
    private void handleNumber() {
        output.add(
                input.poll());
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
    private void handleOperator() {
        if (stack.empty()) { //Nothing to compare, just add to stack
            stack.add(
                    input.poll());
            return;
        }

        while (!stack.empty() && isOperator(stack.peek())) { //While valid operator comparison can be made
            Operator o1 = operators.get(input.peek());
            Operator o2 = operators.get(stack.peek());
            int compVal = o1.compareTo(o2);

            if (compVal == 1) { //If precedence and leftAssociative prerequisites are "met", pop stack before input.
                output.add(
                        stack.pop());
            } else { //prerequisites not met, break loop and only pop input to output.

                break;
            }
        }
        stack.add(
                input.poll());

    }

    //If the token is a left parenthesis (i.e. "("), then push it onto the stack.
    private void handleParenthesisLeft() {
        stack.add(
                input.poll());
    }

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
            if (!isOperator(stack.peek()) && !isFunction(stack.peek())) {
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
