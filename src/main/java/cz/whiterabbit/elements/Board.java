package cz.whiterabbit.elements;

/**
 * Byte array representing play board
 */

//todo make singleton --- no need actually can be handled by dependency injection
public class Board {

    private byte[] boardArr;

    public Board(){
        boardArr = new byte[64];
        initBoard();
    }

    private void initBoard(){
        for(int i = 0; i<16; i++){
            boardArr[i] = 1;
        }
        for(int i =64-16; i<64; i++){
            boardArr[i] = -1;
        }
    }

    public byte[] getBoardArr() {
        return boardArr;
    }

    public void applyMove(byte[] move){
        for(int i = 0; i< move.length; i+=3){
            boardArr[move[i]]=move[i+3];
        }
    }

    public void applyMove(Move move){
        byte[] moveArr = move.getMoveArr();
        applyMove(moveArr);
    }




}
