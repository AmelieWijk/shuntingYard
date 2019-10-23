package formatter;

import java.util.regex.Pattern;

/**
 * Created by Benjamin Wijk on 2017-06-12.
 */
public class Formatter {

    //Matches at least one whitespace character
    private static final Pattern WHITESPACE = Pattern.compile("\\s+");

    //Matches a number. Can contain "." delimiter in between (to allow support of doubles)
    private static final Pattern NUMBER = Pattern.compile("\\d+(\\.\\d+)?");

    //Matches an operator (any single character between "[" and "]")
    private static final Pattern OPERATOR = Pattern.compile("[-+*^),/]");

    //Matches the start of a function (regular parentheses, can be prefixed with any amount of a-z characters)
    private static final Pattern FUNCTION = Pattern.compile("[a-zA-Z]*\\(");

    // Separates the various tokens by putting a space in between each one.
    // First removes all whitespace to make the formatting more unified
    public static String formatString(String toFormat){

        toFormat = WHITESPACE.matcher(toFormat).replaceAll("");

        toFormat = NUMBER.matcher(toFormat).replaceAll("$0 ");
        toFormat = OPERATOR.matcher(toFormat).replaceAll("$0 ");
        toFormat = FUNCTION.matcher(toFormat).replaceAll("$0 ");

        return toFormat.trim();
    }

}
