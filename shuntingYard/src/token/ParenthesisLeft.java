package token;

import java.util.Stack;

/**
 * Created by Benjamin Wijk on 2017-07-10.
 */
public class ParenthesisLeft implements Token {

    @Override
    public String getString() {
        return null;
    }

    @Override
    public void handle(Stack<Token> stack, Stack<Token> output) {
        stack.add(this);
    }
}
