package token;

import java.util.Stack;

/**
 * Created by Benjamin Wijk on 2017-06-12.
 */
public class Separator implements Token {
    String string;

    public Separator(String string) {
        this.string = string;
    }

    @Override
    public String getString() {
        return string;
    }

    /**
     * if the token is a function argument separator (e.g., a comma):
     * until the token at the top of the stack is a left parenthesis,
     * pop operators off the stack onto the output queue.
     * if no left parentheses are encountered, either the separator was misplaced
     * or parentheses were mismatched.
     * finally, remove comma from input.
     */
    @Override
    public void handle(Stack<Token> stack, Stack<Token> output) {
        while (!(stack.peek().getString().equals("(")) &&
                !(stack.peek() instanceof Function)) {
            output.add(
                    stack.pop());
        }
    }
}
