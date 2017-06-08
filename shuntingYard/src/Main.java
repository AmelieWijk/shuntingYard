
/**
 * Created by Benjamin Wijk on 2017-05-29.
 */
public class Main {
    public static void main(String[] args) {
        ShuntingYard sy = new ShuntingYard();
        System.out.println(sy.sortToRPN("3 + 4 * 2 / ( 1 - 5 ) ^ 2 ^ 3")); //Expected answer: 3 4 2 * 1 5 - 2 3 ^ ^ / +
        System.out.println(sy.sortToRPN("2 + 1 - 12 / 3"));
        System.out.println(sy.sortToRPN("2 + 5 * 5 / 2"));
    }
}
