package cz.whiterabbit.elements.computerplayer;

import cz.whiterabbit.elements.MoveChecker;

import java.util.List;
import java.util.Random;

public class RandomComputerPlayer implements ComputerPlayer {
    private MoveChecker moveChecker;
    private Random random;

    public RandomComputerPlayer(){
        this.random = new Random();
        this.moveChecker = new MoveChecker();
    }

    @Override
    public byte[] chooseMove(byte[] state, boolean player) {
        List<byte[]> moves = moveChecker.getAllValidMoves(state, player);
        return moves.get(random.nextInt(moves.size()));
    }

    @Override
    public void setDifficulty(int difficulty) {

    }

}
