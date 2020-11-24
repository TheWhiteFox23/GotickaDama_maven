package cz.whiterabbit.elements.movegenerator;

import java.util.ArrayList;
import java.util.List;

public class MostCaptureEnemiesFilter implements MoveFilter{

    @Override
    public void filter(List<byte[]> moves) {
        int longest = 0;
        for(byte[] ba: moves){
            if(ba.length > longest) longest = ba.length;
        }
        List<byte[]> toRemove = new ArrayList<>();
        for(byte[] by : moves){
            if(by.length != longest) toRemove.add(by);
        }
        moves.removeAll(toRemove);
    }
}

