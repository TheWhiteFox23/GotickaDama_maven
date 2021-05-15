package cz.whiterabbit.gui;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final FileManager fileManager = new FileManager();
    private FileManager(){ }

    public File createSaveGame(SaveFile saveFile, String filename) throws ParserConfigurationException, TransformerException {
        createSaveFolderIfNotExist();
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("save");
        doc.appendChild(rootElement);

        //REDO LIST
        Element redoList = createMoveList(doc, saveFile.getRedoList(), "redo_list");
        rootElement.appendChild(redoList);

        //UNDO LIST
        Element undoList = createMoveList(doc, saveFile.getUndoList(), "undo_list");
        rootElement.appendChild(undoList);

        //SETTINGS
        Element settings = createSettingsElement(doc, saveFile);
        rootElement.appendChild(settings);

        //BOARD STATE
        Element boardState = createBoardStateElement(doc, saveFile);
        rootElement.appendChild(boardState);


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("save/" + filename));
        transformer.transform(source, result);
        return null;
    }

    private Element createBoardStateElement(Document doc, SaveFile saveFile) {
        Element boardState = doc.createElement("board_state");
        boardState.setTextContent(moveToString(saveFile.getBoardState()));
        return  boardState;
    }

    private Element createMoveList(Document doc, List<byte[]> moves, String listName){
        Element moveList = doc.createElement(listName);

        Attr length= doc.createAttribute("length");
        length.setValue(String.valueOf(moves.size()));
        moveList.setAttributeNode(length);

        for(int i = 0; i< moves.size(); i++){
            Element item = parseMoveToElement(doc, moves.get(i), i);
            moveList.appendChild(item);
        }
        return  moveList;
    }

    private Element parseMoveToElement(Document doc, byte[] bytes, int i) {
        //Create move element
        Element move = doc.createElement("move");
        //Index attribute of the move
        Attr index = doc.createAttribute("index");
        index.setValue(String.valueOf(i));
        move.setAttributeNode(index);

        Attr length = doc.createAttribute("length");
        length.setValue(String.valueOf(bytes.length));
        move.setAttributeNode(length);
        //Text value of the move
        move.appendChild(doc.createTextNode(moveToString(bytes)));
        return  move;
    }

    private Element createSettingsElement(Document doc, SaveFile saveFile){
        Element settings = doc.createElement("settings");
        settings.appendChild(createSetting(doc, "white_player_type", String.valueOf(saveFile.getWhitePlayerType())));
        settings.appendChild(createSetting(doc, "white_player_difficulty", String.valueOf(saveFile.getWhitePlayerDifficulty())));
        settings.appendChild(createSetting(doc, "black_player_type", String.valueOf(saveFile.getBlackPlayerType())));
        settings.appendChild(createSetting(doc, "black_player_difficulty", String.valueOf(saveFile.getBlackPlayerDifficulty())));
        return  settings;

    }

    private Element createSetting(Document doc, String name, String value){
        Element setting = doc.createElement("setting");
        Attr settingName = doc.createAttribute("type");
        settingName.setValue(name);
        setting.setAttributeNode(settingName);
        setting.appendChild(doc.createTextNode(value));
        return setting;
    }

    private String moveToString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        if(bytes.length > 0){
            builder.append(bytes[0]);
        }
        for(int i = 1; i< bytes.length; i++){
            builder.append("," + bytes[i]);
        }
        return builder.toString();
    }

    private Document loadFileIntoDom(String filename) throws SaveFileException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try (InputStream is = readXmlFileIntoInputStream(filename)) {

            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();

            // read from a project's resources folder
            return db.parse(is);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new SaveFileException("Incorrect file type");
        }

    }

    public SaveFile parseXmlToSaveFile(String fileName) throws SaveFileException {
        Document doc = loadFileIntoDom("save/" + fileName);
        SaveFile saveFile = new SaveFile();
        List<byte[]> undoList = getListFromDoc(doc, "undo_list");
        List<byte[]> redoList = getListFromDoc(doc, "redo_list");
        Settings settings = getSettingsFromDoc(doc);
        byte[] boardState = getBoardState(doc);
        saveFile.setRedoList(redoList);
        saveFile.setUndoList(undoList);
        saveFile.setBlackPlayerDifficulty(settings.blackPlayerDifficulty);
        saveFile.setBlackPlayerType(settings.blackPlayerType);
        saveFile.setWhitePlayerDifficulty(settings.whitePlayerDifficulty);
        saveFile.setWhitePlayerType(settings.whitePlayerType);
        saveFile.setBoardState(boardState);

        return saveFile;
    }

    private byte[] getBoardState(Document doc) throws SaveFileException {
        Element rootTag = doc.getDocumentElement();
        NodeList boardStateList = doc.getElementsByTagName("board_state");
        if(boardStateList.getLength() != 1){
            throw new SaveFileException("Wrong number of settings element");
        }
        String[] boardStateString = boardStateList.item(0).getTextContent().split(",");
        if(boardStateString.length != 64){
            throw new SaveFileException("Invalid length of the board array");
        }
        byte[] boardState = new byte[boardStateString.length];
        for(int i = 0; i< boardStateString.length; i++){
            boardState[i] = Byte.parseByte(boardStateString[i]);
        }

        return boardState;
    }

    private Settings getSettingsFromDoc(Document doc) throws SaveFileException {
        Settings settingsToReturn = new Settings();
        Element rootTag = doc.getDocumentElement();
        NodeList settings = rootTag.getElementsByTagName("settings");
        if(settings.getLength()!=1){
            throw new SaveFileException("More than one setting element");
        }

        Node setting = settings.item(0);
        NodeList settingsList = setting.getChildNodes();

        for(int i = 0; i< settingsList.getLength(); i++){
            try{
                Node s = settingsList.item(i);
                NamedNodeMap attr = s.getAttributes();
                if(attr.getNamedItem("type").getTextContent().equals("black_player_difficulty")){
                    settingsToReturn.blackPlayerDifficulty = Integer.valueOf(s.getTextContent());
                }else if(attr.getNamedItem("type").getTextContent().equals("black_player_type")){
                    settingsToReturn.blackPlayerType = Integer.valueOf(s.getTextContent());
                }else if(attr.getNamedItem("type").getTextContent().equals("white_player_difficulty")){
                    settingsToReturn.whitePlayerDifficulty = Integer.valueOf(s.getTextContent());
                }else if(attr.getNamedItem("type").getTextContent().equals("white_player_type")){
                    settingsToReturn.whitePlayerType = Integer.valueOf(s.getTextContent());
                }
            }catch (Exception e){
                throw new SaveFileException("Save file is corrupted");
            }

        }

        return settingsToReturn;
    }

    private List<byte[]> getListFromDoc(Document doc, String list) throws SaveFileException {
        Element rootTag = doc.getDocumentElement();
        if(!rootTag.getTagName().equals("save")){
            throw new SaveFileException("Invalid Root Tag: Save files have should contain tag 'save'");
        }

        NodeList listsWithTag = rootTag.getElementsByTagName(list);

        if (listsWithTag.getLength() != 1) {
            throw new SaveFileException("More than one list of the same type");
        }

        Node listElement = listsWithTag.item(0);
        NodeList moves = listElement.getChildNodes();
        NamedNodeMap listAttributes = listElement.getAttributes();
        int length = Integer.parseInt(listAttributes.getNamedItem("length").getTextContent());

        if(length != moves.getLength()){
            throw new SaveFileException("Not corresponding length of the list");
        }

        List<byte[]> moveList = new ArrayList<>();

        for(int i = 0; i< length; i++){
            //Parse move
            Node move = moves.item(i);
            int moveLength = Integer.parseInt(move.getAttributes().getNamedItem("length").getTextContent());
            int index = Integer.parseInt(move.getAttributes().getNamedItem("index").getTextContent());

            if(index != i){
                throw new SaveFileException("Item index not corresponding with expected value");
            }
            String[] parsedMove = move.getTextContent().split(",");

            if(parsedMove.length != moveLength){
                throw new SaveFileException("Item length not corresponding with expected value");
            }

            byte[] returnMove = new byte[parsedMove.length];
            for(int j = 0; j< returnMove.length; j++){
                returnMove[j] = Byte.parseByte(parsedMove[j]);
            }

            moveList.add(returnMove);

        }


        return moveList;
    }

    private static InputStream readXmlFileIntoInputStream(final String fileName) throws FileNotFoundException {
        File initialFile = new File(fileName);
        return new FileInputStream(initialFile);
    }



    public static FileManager getInstance(){
        return fileManager;
    }

    class Settings{
        int whitePlayerType=0;
        int blackPlayerType=0;
        int whitePlayerDifficulty=0;
        int blackPlayerDifficulty=0;
    }

    private void createSaveFolderIfNotExist(){
        File file = new File("save");
        if(!file.exists() || !file.isDirectory()){
            file.mkdir();
        }
    }

}
