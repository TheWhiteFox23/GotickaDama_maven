package cz.whiterabbit.elements.computerplayer;

import java.util.List;

/**
 * Pick the move from the list, based on given value and evaluations
 */
public interface MovePicker {
    byte[] pickMove(List<byte[]> moves, int[] evaluations, int value);
}
