package cz.whiterabbit.elements;

import cz.whiterabbit.elements.movegenerator.MostCaptureEnemiesFilter;
import cz.whiterabbit.elements.movegenerator.MoveGenerator;

import java.util.Arrays;
import java.util.List;

public class MoveChecker {

    private Board board;
    private MoveGenerator moveGenerator;

    public MoveChecker(){
        this.board = new Board();
        this.moveGenerator = new MoveGenerator();
    }

    /**
     * Create copy of input array with applied move
     * @param state byte[] representing state game state to apply move
     * @param move byte[] representing the move in form of {position_0, originalState_0, newState_0,...}
     * @return copy of array with applied move
     */
    public byte[] applyMove(byte[] state, byte[] move){
        byte[] stateCopy = Arrays.copyOf(state, state.length);
        board.applyMove(stateCopy, move);
        return  stateCopy;
    }

    /**
     * Returns list off all possible moves player given player can move. List is filtered to allow only most possible
     * captures.
     * @param state byte[] representing state game state
     * @param player true - positive player;
     * @return
     */
    public List<byte[]> getAllValidMoves(byte[] state, boolean player){
        return moveGenerator.getAllMovesForPlayer(player, state, new MostCaptureEnemiesFilter());
    }

    //todo method to detect the game finish
}
