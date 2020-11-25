package cz.whiterabbit.elements;

public class InvalidMoveException extends Exception {

    Move move;
    String message = "Move is nod valid, try to insert different move or revalidate the MoveMemory";

    public InvalidMoveException(Move move){
        this.move = move;
    }

    public InvalidMoveException(){

    }

    public InvalidMoveException(Move move, String message){
        this.move = move;
        this.message = message;
    }
}
