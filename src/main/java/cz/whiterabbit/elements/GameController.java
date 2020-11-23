package cz.whiterabbit.elements;

import cz.whiterabbit.elements.movegenerator.MoveGenerator;

public class GameController {
    Board board;
    MoveGenerator moveGenerator;
    Judge judge;

    public GameController(Board board, MoveGenerator moveGenerator, Judge judge) {
        this.board = board;
        this.moveGenerator = moveGenerator;
        this.judge = judge;
    }

    public void applyMove(Move move){
        board.applyMove(move);
    }
}
