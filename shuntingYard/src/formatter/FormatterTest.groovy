package formatter
/**
 * Created by Benjamin Wijk on 2017-07-10.
 */
class FormatterTest extends groovy.util.GroovyTestCase {
    Map<String, String> formatStrings = new HashMap<>();
    Formatter formatter = new Formatter();

    FormatterTest(){
        init();
    }

    void testFormatter(){
        for(String s: formatStrings.keySet()){
            assert (Formatter.formatString(s).equals(formatStrings.get(s)));
        }
    }

    void init(){
        formatStrings.put("-5+32-123*hej(2,(3*2))", "- 5 + 32 - 123 * hej( 2 , ( 3 * 2 ) )");
        formatStrings.put("52.75+55-(55*32.2)+max(44/2)", "52.75 + 55 - ( 55 * 32.2 ) + max( 44 / 2 )");
        formatStrings.put("52.75     +      55-(55*32.2)+max(44/2)", "52.75 + 55 - ( 55 * 32.2 ) + max( 44 / 2 )");
    }

}
