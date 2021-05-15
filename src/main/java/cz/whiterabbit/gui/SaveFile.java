package cz.whiterabbit.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SaveFile {
    private List<byte[]> undoList;
    private byte[] boardState;
    private List<byte[]> redoList;

    private int blackPlayerType;
    private int whitePlayerType;
    private int blackPlayerDifficulty;
    private int whitePlayerDifficulty;

    public SaveFile(){
        undoList = new ArrayList<>();
        redoList = new ArrayList<>();
        boardState = new byte[64];
        blackPlayerType = 0;
        whitePlayerType = 0;
        blackPlayerDifficulty = 0;
        whitePlayerDifficulty = 0;
    }

    public List<byte[]> getUndoList() {
        return undoList;
    }

    public void setUndoList(List<byte[]> undoList) {
        this.undoList = undoList;
    }

    public byte[] getBoardState() {
        return boardState;
    }

    public void setBoardState(byte[] boardState) {
        this.boardState = boardState;
    }

    public List<byte[]> getRedoList() {
        return redoList;
    }

    public void setRedoList(List<byte[]> redoList) {
        this.redoList = redoList;
    }

    public int getBlackPlayerType() {
        return blackPlayerType;
    }

    public void setBlackPlayerType(int blackPlayerType) {
        this.blackPlayerType = blackPlayerType;
    }

    public int getWhitePlayerType() {
        return whitePlayerType;
    }

    public void setWhitePlayerType(int whitePlayerType) {
        this.whitePlayerType = whitePlayerType;
    }

    public int getBlackPlayerDifficulty() {
        return blackPlayerDifficulty;
    }

    public void setBlackPlayerDifficulty(int blackPlayerDifficulty) {
        this.blackPlayerDifficulty = blackPlayerDifficulty;
    }

    public int getWhitePlayerDifficulty() {
        return whitePlayerDifficulty;
    }

    public void setWhitePlayerDifficulty(int whitePlayerDifficulty) {
        this.whitePlayerDifficulty = whitePlayerDifficulty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaveFile saveFile = (SaveFile) o;
        if(!(whitePlayerDifficulty == saveFile.whitePlayerDifficulty &&
                blackPlayerDifficulty == saveFile.blackPlayerDifficulty &&
                whitePlayerType == saveFile.whitePlayerType &&
                blackPlayerType == saveFile.blackPlayerType)){
            //System.out.println("NOT All right");
        }
        return (compareList(undoList, saveFile.undoList) &&
                (compareList(redoList, saveFile.redoList)) &&
                (compareArrays(boardState, saveFile.boardState)) &&
                whitePlayerDifficulty == saveFile.whitePlayerDifficulty &&
                blackPlayerDifficulty == saveFile.blackPlayerDifficulty &&
                whitePlayerType == saveFile.whitePlayerType &&
                blackPlayerType == saveFile.blackPlayerType);

    }

    private boolean compareArrays(byte[] arr1, byte[] arr2){
        if(arr1.length != arr2.length)return false;
        for(int i = 0; i< arr1.length; i++){
            if(arr1[i] != arr2[i]){
                System.out.println(arr1[i] + " != " +  arr2[i]);
                return false;
            }
        }
        return true;
    }

    private boolean compareList(List<byte[]> list1, List<byte[]> list2){
        if(list1.size() != list2.size())return false;
        for(int i = 0; i< list1.size(); i++){
            if(!compareArrays(list1.get(i), list2.get(i))){
                System.out.println(list1.get(i) + " != " +  list2.get(i));
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(undoList, redoList, blackPlayerType, whitePlayerType, blackPlayerDifficulty, whitePlayerDifficulty);
        result = 31 * result + Arrays.hashCode(boardState);
        return result;
    }
}
