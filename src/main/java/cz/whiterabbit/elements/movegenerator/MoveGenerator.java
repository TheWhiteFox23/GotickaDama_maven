package cz.whiterabbit.elements.movegenerator;

import java.util.ArrayList;
import java.util.List;


public class MoveGenerator {

    public List<byte[]> getAllMovesForPlayer(boolean playerType, byte[] board, MoveFilter filter) {
        List<byte []> moves = getAllMovesForPlayer(playerType, board);
        filter.filter(moves);
        return moves;
    }

    public List<byte[]> getAllMovesForPlayer(boolean playerType, byte[] board) {
        List<byte[]> moves = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            if (isPositive(board[i]) == playerType) {
                moves.addAll(getMovesFromPosition(i, board));
            }
        }
        return moves;
    }

    protected boolean isPositive(byte b) {
        return b > 0;
    }

    /**
     * Get all of the valid moves from given position (don't filter moves with most captures)
     *
     * @param position
     * @param board
     * @return
     */
    public List<byte[]> getMovesFromPosition(int position, byte[] board) {
        Finder finder;
        List<byte[]> moves = new ArrayList<>();
        switch (Math.abs(board[position])) {
            case 1: {
                finder = new RegularFinder();
                break;
            }
            case 2: {
                finder = new RoyalFinder();
                break;
            }
            default: {
                finder = null;
            }
        }
        if (finder != null) {
            List<byte[]> movesFromPosition = finder.find(position, board);
            if (movesFromPosition != null) moves.addAll(movesFromPosition);
        }
        return moves;
    }
}
