package token;

import java.util.Stack;

/**
 * Created by Benjamin Wijk on 2017-06-12.
 */
public class Function implements Token {
    private String string;

    boolean isParenthesisLeft = string.charAt(0) == '(';
    boolean isParenthesisRight = string.charAt(0) == ')';
    boolean isSeparator = string.charAt(0) == ',';
    boolean isFunction = !(isParenthesisLeft || isParenthesisRight || isSeparator);

    public Function(String string){
        this.string = string;
    }

    @Override
    public String getString() {
        return string;
    }

    @Override
    public void handle(Stack<Token> stack, Stack<Token> output) {
        stack.add(this);

        if(isParenthesisLeft){
            stack.add(this);
        }

        if(isParenthesisRight){

        }

    }
}
