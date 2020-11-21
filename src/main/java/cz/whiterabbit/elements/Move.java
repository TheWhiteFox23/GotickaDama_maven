package cz.whiterabbit.elements;

public class Move {

    private byte[] move;

    public Move(byte[] move) {
        this.move = move;
    }

    public byte[] getMoveArr(){
        return move;
    }
}
