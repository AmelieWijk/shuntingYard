import formatter.Formatter;
import formatterComplicated.FormatterComplicatedForBinaryMinus;

/**
 * For lazy testing only
 * Created by Benjamin Wijk on 2017-06-09.
 */
public class Main {
    public static void main (String[] args){
        ShuntingYard sy = new ShuntingYard();
        FormatterComplicatedForBinaryMinus formC = new FormatterComplicatedForBinaryMinus();
        Formatter form = new Formatter();
        System.out.println(sy.sortToRPN(form.formatString("-5+32-123*hej(2,(3*2))")));
      //  System.out.println(sy.sortToRPN(form.formatString("2-3*4*(5*7)/2")));
      //  System.out.println(sy.sortToRPN("2 - 3 * 4 * ( 5 * 7 ) / 2"));
    }
}
