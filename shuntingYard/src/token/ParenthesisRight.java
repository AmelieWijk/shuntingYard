package token;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Created by Benjamin Wijk on 2017-07-10.
 */
public class ParenthesisRight implements Token {

    private String string = ")";

    @Override
    public String getString() {
        return null;
    }

    @Override
    public void handle(Stack<Token> stack, Stack<Token> output) {
        try {
            while (!(stack.peek() instanceof ParenthesisLeft) &&
                    !(stack.peek() instanceof Function)) { //More operators "in" parenthesis, keep popping.

                output.add(
                        stack.pop());
            }
            //Loop done, remove parenthesis.
            if(stack.peek() instanceof Function){
                output.add(stack.pop()); //add function token
            }else{
                stack.pop(); //Pop left parenthesis
            }

        } catch (EmptyStackException e) {
            e.printStackTrace();
            System.exit(1); //To stop potential loops. One error is enough.
        }
    }
}
