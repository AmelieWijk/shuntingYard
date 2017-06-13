/**
 * Currently contains 2 tests: testSortToRPN tests a few basic cases, and makes sure the output is correct (according to other sources on shunting yards).
 * testTokenHandling generates sufficiently correct infix statements, to test that sortToRPN can process them.
 * Created by Benjamin Wijk on 2017-06-09.
 */
class ShuntingYardTest extends groovy.util.GroovyTestCase {
    Set<String> validTokens;
    Set<String> functionTokens;

    Random rand;
    public ShuntingYardTest(){
        rand = new Random();
        setupTokens();
    }

    void testSortToRPN() {
        ShuntingYard sy = new ShuntingYard();

        assertEquals("1 2 3 * 4 5 / 6 - 7 * - 8 * +",
                sy.sortToRPN("1 + ( 2 * 3 - ( 4 / 5 - 6 ) * 7 ) * 8"));

        assertEquals("3 4 2 * 1 5 - 2 3 ^ ^ / +",
                sy.sortToRPN("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3"));

        assertEquals("2 3 max( 3 / 3.1415 * sin(",
                sy.sortToRPN("sin( max( 2 , 3 ) / 3 * 3.1415 )"));
    }

    /**
     * Generates random infix strings to test. The strings should be good enough to properly test the infix to RPN algorithm, but aren't always using correct syntax atm. <br>
     *     (It is currently possible for a function to only contain an operator for example)
     */
    void testTokenHandling(){
        final int n = 1000;
        final int maxLength = 1000;
        ShuntingYard sy = new ShuntingYard();

        for(int i=0; i<n; i++){
            try {
                String infix = generateInfix(rand.nextInt(maxLength) + 1);
                sy.sortToRPN(infix);
                assert true;
            } catch(Exception e){
                e.printStackTrace();
                assert false;
            }
        }
    }

    String generateInfix(int counter){
        StringJoiner sj = new StringJoiner(" ");

        while(counter > 0) {
            if (rand.nextInt(10) + 1 < 2 && counter > 3) {
                int i = rand.nextInt(counter - 2) + 1;
                sj.add(getValidFunction(i));
                counter = i;
            } else{
                sj.add(getValidToken());
                counter--;
            }
        }
        return sj.toString();
    }

    //TODO add support for arg separator
    //TODO make sure internal part is a correct operation (e.g. not just an operator)
    /**
     * Creates a function with token inside of a closed parenthesis. this function can contain other functions. <br>
     *     statements inside a function can currently be invalid (for example, a single operator with no other token). <br>
     *     The statement should however be sufficiently correct to be able to be sorted by sortToRPN().
     * @param tokenCount number of token that will be used inside function statement
     * @return String of infix token separated by whitespace.
     */
    String getValidFunction(int tokenCount){
        StringJoiner sj = new StringJoiner(" ");
        sj.add("sin(");

        for(int i=0; i<tokenCount; i++){
            int tokensLeft = tokenCount - i - 1;
            int tokenOrFunction = rand.nextInt(2);

            if(tokensLeft >= 3 && tokenOrFunction == 0) { //Enough token to create a function inside this function
                    int internalTokenCount = rand.nextInt(tokensLeft-2)+1;
                    sj.add(getValidFunction(internalTokenCount))
                    i = i + internalTokenCount + 2; //"Shift" i to match the amount of token added
            } else {
                sj.add(getValidToken());
            }
        }

        sj.add(")");

        return sj.toString();

    }

    String getValidToken() {
        int index = rand.nextInt(3);

        switch(index){
            case 0:
                return rand.nextDouble();
            case 1:
                return rand.nextInt();
            case 2:
                return getRandomOperator();
        }
    }

    String getRandomOperator(){
        int index = rand.nextInt(validTokens.size());

        return validTokens.getAt(index);
    }

    void setupTokens(){
        setupValidTokens();
        setupFunctionTokens();
    }

    void setupFunctionTokens() {
        functionTokens = new HashSet<>();

        functionTokens.add("max(");
        functionTokens.add("sin(");
        functionTokens.add("cos(");
        functionTokens.add("tan(");
        functionTokens.add("cot(");

    }

    void setupValidTokens() {
        validTokens = new HashSet<>();

        validTokens.add("^");
        validTokens.add("*");
        validTokens.add("/");
        validTokens.add("+");
        validTokens.add("-");
    }


}
