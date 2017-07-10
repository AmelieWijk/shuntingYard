package formatter;

import java.util.regex.Pattern;

/**
 * Created by Benjamin Wijk on 2017-06-12.
 */
public class Formatter {

    public static String formatString(String toFormat){
        Pattern whitespace = Pattern.compile("\\s+");

        Pattern number = Pattern.compile("\\d+(\\.\\d+)?"); //allows integer or double.
        Pattern operator = Pattern.compile("[-+*^),/]");
        Pattern function = Pattern.compile("[a-zA-Z]*\\(");

        toFormat = whitespace.matcher(toFormat).replaceAll("");

        toFormat = number.matcher(toFormat).replaceAll("$0 ");
        toFormat = operator.matcher(toFormat).replaceAll("$0 ");
        toFormat = function.matcher(toFormat).replaceAll("$0 ");

        return toFormat.trim();
    }

    public static void main(String []args){
        String s = Formatter.formatString("52.75     +      55-(55*32.2)+max(44/2)");
        System.out.println(s);

    }

}
