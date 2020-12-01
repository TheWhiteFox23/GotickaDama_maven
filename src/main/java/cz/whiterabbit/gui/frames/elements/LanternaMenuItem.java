package cz.whiterabbit.gui.frames.elements;

import com.googlecode.lanterna.TextColor;
import cz.whiterabbit.gui.listeners.MenuItemListener;

public class LanternaMenuItem {
    private String text;
    private int positionX;
    private int positionY;
    private MenuItemListener menuItemListener;
    private TextColor.ANSI highlightBackgroundColor;
    private TextColor.ANSI highlightTextColor;
    private int index;
    private boolean visible;
    private LanternaToggleMenuItem toggleMenuItem;

    public LanternaMenuItem(String text, int positionX, int positionY, int index) {
        this.index = index;
        this.text = text;
        this.positionX = positionX;
        this.positionY = positionY;
        this.highlightBackgroundColor = TextColor.ANSI.WHITE;
        this.highlightTextColor = TextColor.ANSI.BLACK;
        this.visible = true;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public MenuItemListener getMenuItemListener() {
        return menuItemListener;
    }

    public void setMenuItemListener(MenuItemListener menuItemListener) {
        this.menuItemListener = menuItemListener;
    }

    public TextColor.ANSI getHighlightBackgroundColor() {
        return highlightBackgroundColor;
    }

    public void setHighlightBackgroundColor(TextColor.ANSI highlightBackgroundColor) {
        this.highlightBackgroundColor = highlightBackgroundColor;
    }

    public TextColor.ANSI getHighlightTextColor() {
        return highlightTextColor;
    }

    public void setHighlightTextColor(TextColor.ANSI highlightTextColor) {
        this.highlightTextColor = highlightTextColor;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public LanternaToggleMenuItem getToggleMenuItem() {
        return toggleMenuItem;
    }

    public void setToggleMenuItem(LanternaToggleMenuItem toggleMenuItem) {
        this.toggleMenuItem = toggleMenuItem;
    }
}
