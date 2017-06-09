/**
 * Created by Elev1 on 2017-06-09.
 */
class ShuntingYardTest extends groovy.util.GroovyTestCase {

    void testSortToRPN() {
        ShuntingYard sy = new ShuntingYard();

        assertEquals("1 2 3 * 4 5 / 6 - 7 * - 8 * +",
                sy.sortToRPN("1 + ( 2 * 3 - ( 4 / 5 - 6 ) * 7 ) * 8"));

        assertEquals("3 4 2 * 1 5 - 2 3 ^ ^ / +",
                sy.sortToRPN("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3"));

       assertEquals("2 3 max( 3 / 3.1415 * sin(",
                sy.sortToRPN("sin( max( 2 , 3 ) / 3 * 3.1415 )"));
    }
}
