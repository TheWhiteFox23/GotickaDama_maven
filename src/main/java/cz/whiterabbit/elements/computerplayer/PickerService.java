package cz.whiterabbit.elements.computerplayer;

import java.util.Arrays;

public final class PickerService {

    private static final PickerService pickerService = new PickerService();

    private PickerService(){

    }

    public int[] getAllCorrespondingIndexes(int element, int[] evaluations){
        int[] indexes = new int[evaluations.length];
        int arrIndex = 0;
        for(int i = 0; i< evaluations.length; i++){
            if(evaluations[i]==element){
                indexes[arrIndex] = i;
                arrIndex++;
            }
        }
        return Arrays.copyOf(indexes, arrIndex);
    }


    public static PickerService getInstance(){
        return pickerService;
    }
}
