package tokens;

import java.util.Stack;

/**
 * Created by Benjamin Wijk on 2017-06-12.
 */
public class Number implements Token {
    private String string;

    public Number(String string){
        this.string = string;
    }

    @Override
    public String getString() {
        return string;
    }

    @Override
    public void handle(Stack<Token> stack, Stack<Token> output) {

    }

}
