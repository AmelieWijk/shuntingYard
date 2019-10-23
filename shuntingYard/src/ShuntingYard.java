import formatter.Formatter;
import token.Function;
import token.Operator;
import token.Token;
import token.Tokenizer;

import java.util.InputMismatchException;
import java.util.Queue;
import java.util.Stack;
import java.util.StringJoiner;

/**
 * A class that sorts a string of infix to Reverse Polish Notation (RPN)
 * Created by Benjamin Wijk on 2017-05-29.
 */
public class ShuntingYard {
    private Stack<Token> output;
    private Stack<Token> stack;
    private Queue<Token> input;

    /**
     * Begins the process of sorting token according to Reverse Polish Notation
     *
     * @param calculation Calculation to be sorted. Each token should be separated by whitespace.
     * @return String with token sorted by RPN
     */
    public String sortToRPN(String calculation) {
        calculation = Formatter.formatString(calculation);

        output = new Stack<>();
        stack = new Stack<>();
        input = new Tokenizer().tokenize(calculation);

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
        while (!input.isEmpty()) {
            input.poll().handle(stack,output);
        }
        //input == empty
        popEntireStack();
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

    private void printStacks() {
        System.out.println("in: " + input);
        System.out.println("op: " + stack);
        System.out.println("out: " + output);
    }

}
