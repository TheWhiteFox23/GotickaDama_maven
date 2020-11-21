package cz.whiterabbit.elements;

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
