import java.util.LinkedList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Benjamin Wijk on 2017-06-12.
 */
public class Formatter {
    List<Pattern> patterns;
    StringJoiner sj;

    public Formatter(){
        setupPatterns();
    }

    public String formatString(String string) {
        sj = new StringJoiner(" ");
        Matcher match = null;

        while(!string.isEmpty()){
            for (Pattern p : patterns) {
                match = p.matcher(string);
                if (match.find()) {
                    break;
                }
            }
            sj.add(match.group());
            string = string.substring(match.end());
        }

       return sj.toString();
    }

    public void setupPatterns(){
        Pattern number = Pattern.compile("^-?\\d+(\\.\\d+)?"); //Optional "-" at start, allows integer or double.
        Pattern operator = Pattern.compile("^[-+*^/]");
        Pattern function = Pattern.compile("^[a-zA-Z]*\\(");
        //Pattern leftParenth = Pattern.compile("^\\("); //Not needed, covered by function.
        Pattern rightParenthesis = Pattern.compile("\\)");
        Pattern separator = Pattern.compile(",");

        patterns = new LinkedList<>();
        patterns.add(number);
        patterns.add(operator);
        patterns.add(function);
        patterns.add(rightParenthesis);
        patterns.add(separator);
    }

    public static void main (String[] args){
    Formatter form = new Formatter();
    System.out.println(form.formatString("321312-32.423(hej(3+3))"));
        
    }

}
