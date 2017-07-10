package token;

import java.util.*;

/**
 * Created by Benjamin Wijk on 2017-06-12.
 */
public class Tokenizer {
    Map<String, Operator> operators;

    public Tokenizer(){
        createOperators();

    }

    private Queue<Token> input = new ArrayDeque<>();

    String s;

    public Queue<Token> tokenize(String string){
        try(Scanner in = new Scanner(string)) {
            while (in.hasNext()) {
                s = in.next();

                if (isNumber(s)) {
                    input.add(new Number(s));
                } else if (isOperator(s)) {
                    input.add(operators.get(s));
                } else if (isParenthesisLeft(s)) {
                    input.add(new ParenthesisLeft());
                } else if (isParenthesisRight(s)) {
                    input.add(new ParenthesisRight());
                } else if (isFunction(s)) {
                    input.add(new Function(s));
                } else if (isSeparator(s)) {
                    input.add(new Separator(s));
                }
            }
        }
        return input;
    }


    private boolean isSeparator(String token) {
        return token.equals(",");
    }

    private boolean isNumber(String token) {

        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFunction(String token) {
        return token.matches("[a-zA-Z]+\\(");
    }

    private boolean isOperator(String token) {
        return operators.containsKey(token);
    }

    private boolean isParenthesisLeft(String token) {
        return token.equals("(");
    }

    private boolean isParenthesisRight(String token) {
        return token.equals(")");
    }

    private void createOperators() {
        operators = new HashMap<>();

        operators.put("^", new Operator("^", 4, false));
        operators.put("*", new Operator("*", 3, true));
        operators.put("/", new Operator("/", 3, true));
        operators.put("+", new Operator("+", 2, true));
        operators.put("-", new Operator("-", 2, true));
    }

}
