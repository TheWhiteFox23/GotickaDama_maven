package cz.whiterabbit.gui;

import com.googlecode.lanterna.TextColor;

import java.util.ArrayList;
import java.util.List;

public class LanternaToggleMenuItem {
    private List<String> optionsList;
    private int selectedIndex;
    private int positionX;
    private int positionY;
    private TextColor.ANSI selectedBackground = TextColor.ANSI.BLUE;
    private TextColor.ANSI selectedForeground = TextColor.ANSI.WHITE;

    private ToggleMenuListener toggleMenuListener;

    public LanternaToggleMenuItem(int positionX, int positionY){
        initialize(positionX, positionY);
    }

    public LanternaToggleMenuItem(int positionX, int positionY, List<String> optionsList, ToggleMenuListener toggleMenuListener){
        initialize(positionX, positionY);
        this.optionsList = optionsList;
        this.toggleMenuListener = toggleMenuListener;
    }

    private void initialize(int positionX, int positionY) {
        selectedIndex = 0;
        optionsList = new ArrayList<>();
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void addListItem(String listItem){
        optionsList.add(listItem);
    }

    public void turnRight(){
        selectedIndex = (selectedIndex+1)%optionsList.size();
    }

    public void turnLeft(){
        if(selectedIndex != 0){
            selectedIndex--;
        }else{
            selectedIndex = optionsList.size()-1;
        }
    }

    public String getSelectedItem(){
        return optionsList.get(selectedIndex);
    }

    public List<String> getOptionsList() {
        return optionsList;
    }

    public void setOptionsList(List<String> optionsList) {
        this.optionsList = optionsList;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public TextColor.ANSI getSelectedBackground() {
        return selectedBackground;
    }

    public void setSelectedBackground(TextColor.ANSI selectedBackground) {
        this.selectedBackground = selectedBackground;
    }

    public TextColor.ANSI getSelectedForeground() {
        return selectedForeground;
    }

    public void setSelectedForeground(TextColor.ANSI selectedForeground) {
        this.selectedForeground = selectedForeground;
    }

    public ToggleMenuListener getToggleMenuListener() {
        return toggleMenuListener;
    }
}
