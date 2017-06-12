package tokens;

import java.util.Stack;

/**
 * Created by Elev1 on 2017-06-12.
 */
public class Function implements Token {
    private String string;

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
    }
}
