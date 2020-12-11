package cz.whiterabbit.elements;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MinimaxComputerPlayer implements ComputerPlayer {
    private MoveChecker moveChecker;
    private Random random;
    private int difficulty;


    public MinimaxComputerPlayer() {
        this.random = new Random();
        this.moveChecker = new MoveChecker();
        this.difficulty = 6;
    }

    @Override
    public byte[] chooseMove(byte[] state, boolean player) {
        List<byte[]> moves = moveChecker.getAllValidMoves(state, player);
        int[] evaluations = new int[moves.size()];

        int value = getValue(state, player, Integer.MIN_VALUE, Integer.MAX_VALUE, moves, evaluations);

        return getRandomMoveOfValue(moves, evaluations, value);
    }

    private int getValue(byte[] state, boolean player, int alfa, int beta, List<byte[]> moves, int[] evaluations) {
        int value = Integer.MAX_VALUE;
        if(player) value = Integer.MIN_VALUE;

        for(int i = 0; i< moves.size(); i++){
            int eval = minimax(moveChecker.applyMove(state, moves.get(i)), difficulty, alfa, beta, !player);
            evaluations[i] = eval;
            if(!player && eval < value){
                value = eval;
                beta = eval;
            }else if (player && eval>value){
                value = eval;
                alfa = eval;
            }
            if(beta <= alfa)break;
        }
        return value;
    }

    private byte[] getRandomMoveOfValue(List<byte[]> moves, int[] evaluations, int value) {
        int[] indexesOfValue = getAllCorrespondingIndexes(value, evaluations);
        System.out.println("indexesOfValue length: " + indexesOfValue.length);
        if(indexesOfValue.length > 0){
            return moves.get(indexesOfValue[random.nextInt(indexesOfValue.length)]);
        }else {
            return moves.get(0);
        }
    }

    private int evaluateState(byte[] state){
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

    //todo refactor
    private int minimax(byte[] state, int depth,int alfa, int beta, boolean activePlayer){
        if(depth == 0 ) {
            return evaluateState(state);
        }

        List<byte[]> moves = moveChecker.getAllValidMoves(state, activePlayer);
        if(activePlayer){
            int max = Integer.MIN_VALUE;
            for(byte[] by: moves){
                int eval = minimax(moveChecker.applyMove(state, by), depth-1,alfa, beta, !activePlayer);
                max = Integer.max(max, eval);
                alfa = Integer.max(max, eval);
                if(beta<=alfa)break;
            }
            return max;
        }else{
            int min = Integer.MAX_VALUE;
            for(byte[] by: moves){
                int eval = minimax(moveChecker.applyMove(state, by), depth-1,alfa, beta, !activePlayer);
                min = Integer.min(min, eval);
                beta =  Integer.min(min, eval);
                if(beta <= alfa)break;
            }
            return min;
        }
    }

    private int[] getAllCorrespondingIndexes(int element, int[] evaluations){
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


}
