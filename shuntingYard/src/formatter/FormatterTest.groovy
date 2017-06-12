package formatter
/**
 * Created by Elev1 on 2017-06-12.
 */
class FormatterTest extends GroovyTestCase {

    FormatterTest(){}

    void testFormatString() {
        Formatter formatter = new Formatter();
        assertEquals("32 - 123 * hej( 2 , ( 3 * 2 ) )",
                formatter.formatString("32-123*hej(2,(3*2))"));

        assertEquals("1112 + 32131232 - 123 * hej( 2 , ( 3 * 2 ) )",
                formatter.formatString("1112+32131232-123*hej(2,(3*2))"));

    }
}
