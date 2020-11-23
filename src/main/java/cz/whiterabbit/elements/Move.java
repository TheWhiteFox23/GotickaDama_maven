package cz.whiterabbit.elements;

import cz.whiterabbit.elements.movegenerator.MoveGenerator;

public class Move {
    MoveGenerator moveGenerator = new MoveGenerator();
    private void test(){
    }

    private byte[] move;

    public Move(byte[] move) {
        this.move = move;
    }

    public byte[] getMoveArr(){
        return move;
    }
}
