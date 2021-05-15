package cz.whiterabbit.gui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {
    Random random;
    FileManager fileManager;
    private SaveFile saveFile;
    private List<byte[]> undoList;
    private List<byte[]> redoList;

    @BeforeEach
    void setUp() {
        random = new Random();
        fileManager = FileManager.getInstance();

        undoList = new ArrayList<>();
        for(int i = 0; i< 30; i++){
            undoList.add(new byte[]{10,1,0,20,1,0,30,1,0,40,1,0});
        }

        redoList = new ArrayList<>();
        for(int i =0; i< 5; i++){
            redoList.add(new byte[]{10,1,0,20,1,0,});
        }
        saveFile = new SaveFile();
        saveFile.setUndoList(undoList);
        saveFile.setRedoList(redoList);


    }

    @Test
    void testDomParser() throws ParserConfigurationException, TransformerException {
        fileManager.createSaveGame(saveFile, "fileName3.xml");
    }

    @Test
    void saveFile() throws SaveFileException {
        fileManager.parseXmlToSaveFile("fileName3.xml");
    }

    @Test
    void loadSaveDataConsistency() throws TransformerException, ParserConfigurationException, SaveFileException {
        SaveFile saveFile = generateRandomSaveFile();
        fileManager.createSaveGame(saveFile, "testSaveFile.xml");
        SaveFile saveFileFromXml = fileManager.parseXmlToSaveFile("testSaveFile.xml");
        assertEquals(saveFileFromXml, saveFile);
    }

    private SaveFile generateRandomSaveFile(){
        SaveFile saveFile = new SaveFile();
        List<byte[]> undoList = generateRandomList(1,30);
        List<byte[]> redoList = generateRandomList(1,20);
        byte[] boardState = generateRandomByteArr(1,64, 64);
        saveFile.setUndoList(undoList);
        saveFile.setRedoList(redoList);
        saveFile.setBoardState(boardState);
        saveFile.setWhitePlayerType(generateIntInBounds(0, 1));
        saveFile.setBlackPlayerType(generateIntInBounds(0, 1));
        saveFile.setWhitePlayerDifficulty(generateIntInBounds(0, 2));
        saveFile.setBlackPlayerDifficulty(generateIntInBounds(0, 2));
        return  saveFile;
    }

    private List<byte[]> generateRandomList(int min, int max) {
        List<byte[]> moveList = new ArrayList<>();
        int listLength = generateIntInBounds(min, max);
        for(int i = 0; i<listLength; i++){
            moveList.add(generateRandomByteArr(min, max, generateIntInBounds(min, max)));
        }
        return moveList;

    }

    private int generateIntInBounds(int min, int max){
        return random.nextInt((max - min) + 1) + min;
    }

    private byte[] generateRandomByteArr(int min, int max, int length){
        byte[] arr = new byte[length];
        for(int i = 0; i< length; i++){
            arr[i] = (byte)(random.nextInt((max - min) + 1) + min);
        }
        return  arr;
    }
    @Test
    void invalidFileTest_incorrectType() throws SaveFileException {
        Exception exception = assertThrows(SaveFileException.class, () -> {
            fileManager.parseXmlToSaveFile("testSaveFile.xml");
        });
        System.out.println(exception.toString());
    }
}