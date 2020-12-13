package cz.whiterabbit.elements.computerplayer;
//todo test this class so it has same result as original method
/**
 * Basic state evaluator. Count only type and number of peaces.
 */
public class SimpleEvaluator implements StateEvaluator{
    /**
     * Evaluate given board state
     * @param state state of the board to evaluate
     * @return int representing evaluation
     */
    @Override
    public int evaluate(byte[] state) {
        int evaluation = 0;
        for(byte b: state){
            switch (b){
                case 1-> evaluation += 25;
                case 2-> evaluation += 100;
                case -1 -> evaluation -= 25;
                case -2 -> evaluation -= 100;
            }
        }
        return evaluation;
    }
}
