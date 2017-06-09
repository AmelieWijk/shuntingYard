import java.util.*;

/**
 * Created by Benjamin Wijk on 2017-05-29.
 */
public class ShuntingYard {
    Stack<String> output;
    Stack<String> operatorStack;
    Queue<String> input;

    Map<String, Operator> operators;
    Set<String> functions;

    public ShuntingYard() {
        createOperators();
        createFunctions();
    }

    /**
     * Begins the process of sorting tokens according to Reverse Polish Notation
     *
     * @param calculation Calculation to be sorted. Each token should be separated by whitespace.
     * @return String with tokens sorted by RPN
     */
    public String sortToRPN(String calculation) {
        output = new Stack<>();
        operatorStack = new Stack<>();
        input = new ArrayDeque<>();

        Scanner in = new Scanner(calculation);

        while (in.hasNext()) {
            input.add(in.next());
        }
        in.close();

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
            } else if (isFunction(token)) { //NOT IMPLEMENTED
                handleFunction();
            } else if (isArgSeparator(token)) { //Weird implementation probably, NOT TESTED.
                handleArgSeparator();
            }
        }
        //input == empty
        popOperatorStack();
    }

    /**
     * if the token is a function argument separator (e.g., a comma):
     * until the token at the top of the stack is a left parenthesis,
     * pop operators off the stack onto the output queue.
     * if no left parentheses are encountered, either the separator was misplaced
     * or parentheses were mismatched.
     * finally, remove comma from input. (Or should it be pushed to output???)
     */
    private void handleArgSeparator() {
        while (!isParenthesisLeft(operatorStack.peek()) &&
                !isFunction(operatorStack.peek())) {
            output.add(
                    operatorStack.pop());
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
        operatorStack.add(
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
     operator stack and either a is left-associative and its precedence is
     less than b, or a is right associative, and has precedence less than
     that of b:
     pop b off the operator stack, onto the output queue;
     at the end of iteration push a onto the operator stack.
     */
    private void handleOperator() {
        if (operatorStack.empty()) { //Nothing to compare, just add to stack
            operatorStack.add(
                    input.poll());
            return;
        }

        while (!operatorStack.empty() && isOperator(operatorStack.peek())) { //While valid operator comparison can be made
            Operator o1 = operators.get(input.peek());
            Operator o2 = operators.get(operatorStack.peek());

            if (o1.compareTo(o2) == 1) { //If precedence and leftAssociative prerequisites are "met", pop operatorstack before input.
                output.add(
                        operatorStack.pop());
            } else { //prerequisites not met, break loop and only pop input to output.

                break;
            }
        }
        operatorStack.add(
                input.poll());


    }

    //If the token is a left parenthesis (i.e. "("), then push it onto the stack.
    private void handleParenthesisLeft() {
        operatorStack.add(
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
            while (!isParenthesisLeft(operatorStack.peek()) &&
                    !isFunction(operatorStack.peek())) { //More operators "in" parenthesis, keep popping.

                output.add(
                        operatorStack.pop());
            } //Loop done, remove parenthesis.

            if(isFunction(operatorStack.peek())){
                output.add(operatorStack.pop()); //add function token
            }else{
                operatorStack.pop(); //Pop left parenthesis
            }
            input.poll(); //Pop right parenthesis
        } catch (EmptyStackException e) {
            e.printStackTrace();
            printStacks();
            System.exit(1); //To stop potential loops. One error is enough.
        }
    }

    /**
     * Called after all tokens in input have been handled. Pops operators from the stack until empty. <br>
     * Throws InputMismatchException if a parenthesis (or non-operator) is still found in stack.
     */
    private void popOperatorStack() {
        while (!operatorStack.isEmpty()) {
            if (!isOperator(operatorStack.peek()) && !isFunction(operatorStack.peek())) {
                printStacks();
                throw new InputMismatchException("Parenthesis mismatch.");
            }
            output.add(operatorStack.pop());
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

    //NOT IMPLEMENTED
    private boolean isFunction(String token) {
        return functions.contains(token);
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
        System.out.println("op: " + operatorStack);
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

    private void createFunctions(){
        functions = new HashSet<>();

        functions.add("sin(");
        functions.add("abs(");
        functions.add("cos(");
        functions.add("tan(");
        functions.add("max(");
    }

}
