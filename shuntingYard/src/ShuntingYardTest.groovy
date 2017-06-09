/**
 * Created by Elev1 on 2017-06-09.
 */
class ShuntingYardTest extends groovy.util.GroovyTestCase {

    void testSortToRPN() {
        ShuntingYard sy = new ShuntingYard();
        assertEquals("1 2 3 * 4 5 / 6 - 7 * - 8 * +",sy.sortToRPN("1 + ( 2 * 3 - ( 4 / 5 - 6 ) * 7 ) * 8"));

    }
}
