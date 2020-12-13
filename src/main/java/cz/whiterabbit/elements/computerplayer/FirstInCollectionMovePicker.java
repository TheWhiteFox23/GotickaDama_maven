package cz.whiterabbit.elements.computerplayer;

import java.util.List;

/**
 * Move picker used primary for testing, always return firs value in collection
 */
public class FirstInCollectionMovePicker implements MovePicker {
    @Override
    public byte[] pickMove(List<byte[]> moves, int[] evaluations, int value) {
        int[] indexesOfValue = PickerService.getInstance().getAllCorrespondingIndexes(value, evaluations);
        return moves.get(indexesOfValue[0]);
    }
}
