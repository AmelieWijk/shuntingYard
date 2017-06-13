package formatter;

import java.util.regex.Pattern;

/**
 * Created by Benjamin Wijk on 2017-06-12.
 */
public class Formatter {

    public static String formatString(String toFormat){
       // Pattern minusNumber = Pattern.compile("-\\d+(\\.\\d+)?"); //allows integer or double.
        Pattern number = Pattern.compile("\\d+(\\.\\d+)?"); //allows integer or double.
        Pattern operator = Pattern.compile("[-+*^),/]");
        Pattern function = Pattern.compile("[a-zA-Z]*\\(");
        
        //toFormat = minusNumber.matcher(toFormat).replaceAll("+(0$0)");
        toFormat = number.matcher(toFormat).replaceAll("$0 ");
        toFormat = operator.matcher(toFormat).replaceAll("$0 ");
        toFormat = function.matcher(toFormat).replaceAll("$0 ");

        return toFormat;
    }

    public static void main (String[] args){
        Formatter form = new Formatter();
        System.out.println(form.formatString("-5+32-123*hej(2,(3*2))"));

    }

}
