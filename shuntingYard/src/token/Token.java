package token;

import java.util.Stack;

/**
 * Created by Benjamin Wijk on 2017-06-12.
 */
public interface Token {
    public String getString();
    public void handle(Stack<Token> stack, Stack<Token> output);
}
