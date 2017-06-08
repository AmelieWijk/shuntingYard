/**
 * Created by Benjamin Wijk on 2017-06-08.
 */
public class Operator implements Comparable<Operator> {
        String operatorName;
        int precedence;
        boolean leftAssociative; //Left associative if true, right associative if false.

        public Operator(String operatorName, int precedence, boolean isLeftAssociative) {
            this.operatorName = operatorName;
            this.precedence = precedence;
            this.leftAssociative = isLeftAssociative;
        }

        /**
         * compares this operator with another operator o.
         * @param o
         * @return 1 if o is "bigger", 0 if comparing with itself and -1 if this operator is "bigger"
         */
        @Override
        public int compareTo(Operator o) {
            if(operatorName.equals(o.operatorName)){ //Name is unique, comparing with self.
                return 0;
            }
            if ((leftAssociative && precedence <= o.precedence)
                    || (!leftAssociative && precedence < o.precedence)) {
                return 1;
            }
            return -1;
        }

        @Override
        public String toString() {
            return operatorName;
        }
}


