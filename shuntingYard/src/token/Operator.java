package token;

import java.util.Stack;

/**
 * Class for token of type operator, which need a compareTo to function properly.
 * Created by Benjamin Wijk on 2017-06-08.
 */
public class Operator implements Comparable<Operator>, Token {
    private String name;
    private int precedence;
    private boolean leftAssociative;

    public Operator(String name, int precedence, boolean isLeftAssociative) {
        this.name = name;
        this.precedence = precedence;
        this.leftAssociative = isLeftAssociative;
    }

    @Override
    public String getString() {
        return name;
    }

    /*
    if the token is an operator, then:
		while there is an operator at the top of the operator stack with
			greater than or equal to precedence:
				pop operators from the operator stack, onto the output queue.
		push the read operator onto the operator stack.
     */

    @Override
    public void handle(Stack<Token> stack, Stack<Token> output) {
        if (stack.empty()) { //Nothing to compare, just add to stack
            stack.add(this);
            return;
        }

        while (!stack.empty() && (stack.peek() instanceof Operator)) { //While valid operator comparison can be made
            Operator o2 = (Operator)stack.peek();
            int compVal = this.compareTo(o2);

            if (compVal == 1) { //If precedence and leftAssociative prerequisites are "met", pop stack before input.
                output.add(
                        stack.pop());
            } else { //prerequisites not met, break loop and only pop input to output.

                break;
            }
        }
        stack.add(this);
    }

    /**
     * TODO change "direction"?
     * compares this operator with another operator o.
     *
     * @param o
     * @return 1 if o is "bigger", 0 if comparing with itself and -1 if this operator is "bigger"
     */
    @Override
    public int compareTo(Operator o) {
        if (name.equals(o.name)) { //Name is unique, comparing with self.
            return 0;
        }
        if ((leftAssociative && precedence <= o.precedence)
                || (!leftAssociative && precedence < o.precedence)) {
            return 1;
        }
        return -1;
    }

}


