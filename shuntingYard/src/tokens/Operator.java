package tokens;

import java.util.Stack;

/**
 * Class for tokens of type operator, which need a compareTo to function properly.
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

    @Override
    public void handle(Stack<Token> stack, Stack<Token> output) {

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


