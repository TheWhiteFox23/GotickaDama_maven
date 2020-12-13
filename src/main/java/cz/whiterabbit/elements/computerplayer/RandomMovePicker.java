package cz.whiterabbit.elements.computerplayer;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Pick random move with given value
 */
public class RandomMovePicker  implements MovePicker{
    private Random random;

    public RandomMovePicker(){
        this.random = new Random();
    }

    @Override
    public byte[] pickMove(List<byte[]> moves, int[] evaluations, int value) {
        int[] indexesOfValue = PickerService.getInstance().getAllCorrespondingIndexes(value, evaluations);
        if(indexesOfValue.length > 0){
            return moves.get(indexesOfValue[random.nextInt(indexesOfValue.length)]);
        }else {
            return moves.get(0);
        }
    }

    /*private int[] getAllCorrespondingIndexes(int element, int[] evaluations){
        int[] indexes = new int[evaluations.length];
        int arrIndex = 0;
        for(int i = 0; i< evaluations.length; i++){
            if(evaluations[i]==element){
                indexes[arrIndex] = i;
                arrIndex++;
            }
        }
        return Arrays.copyOf(indexes, arrIndex);
    }*/
}
