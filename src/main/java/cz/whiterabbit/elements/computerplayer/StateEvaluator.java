package cz.whiterabbit.elements.computerplayer;

/**
 * evaluate given state of the board
 */
public interface StateEvaluator {
    int evaluate(byte[] state);
}
