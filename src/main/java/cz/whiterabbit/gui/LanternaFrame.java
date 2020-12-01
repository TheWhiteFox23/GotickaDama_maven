package cz.whiterabbit.gui;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;
import java.util.ArrayList;

abstract class LanternaFrame implements GUIFrame {
    //Menu
    private ArrayList<LanternaMenuItem> lanternaMenu = new ArrayList<>();
    private int selectedMenuIndex = 0;
    //private boolean drawMenu = false;
    private int loopTimeout;
    private FrameListener frameListener;
    private Screen screen;
    private boolean valid = false;


    public LanternaFrame(Screen screen){
        this.screen = screen;
        loopTimeout = 1;
    }

    //todo improvise refactor later
    public void drawFrame() throws IOException {
        valid = false;
        while(true){
            if(!valid){
                onDraw();
                screen.refresh();
                valid = true;
            }
            onListen();
            try {
                Thread.sleep(loopTimeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    abstract void onDraw();

    abstract void onListen();


    public int getLoopTimeout() {
        return loopTimeout;
    }

    public void setLoopTimeout(int loopTimeout) {
        this.loopTimeout = loopTimeout;
    }

    public FrameListener getFrameListener() {
        return frameListener;
    }

    @Override
    public void setFrameListener(FrameListener frameListener) {
        this.frameListener = frameListener;
    }

    protected void drawInFrame(String text, int positionX, int positionY){
        //int offset = 0;
        //drawWithFrame(text, positionX, positionY,text.length() + 4,3, offset);
        paintBorder(text, positionX, positionY,text.length() + 4,3,0 );
    }

    protected void drawBorder(String borderText, int positionX, int positionY, int width, int height){
        //drawWithFrame(borderText, positionX, positionY, width, height, -1);
        paintBorder(borderText, positionX, positionY,width,height,-1 );
    }

    public  void paintBorder (String text, int positionX, int positionY, int width, int height, int offset) {
        //Initial positions
        TerminalPosition labelBoxTopLeft = new TerminalPosition(positionX, positionY);
        TerminalSize labelBoxSize = new TerminalSize(width, height);
        TerminalPosition labelBoxTopRightCorner = labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 1);

        //Reset background
        TextGraphics textGraphics = screen.newTextGraphics();
        textGraphics.fillRectangle(labelBoxTopLeft, labelBoxSize, ' ');

        //upper line
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeColumn(1),
                labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 2),
                Symbols.DOUBLE_LINE_HORIZONTAL);

        //bottom line
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeColumn(1).withRelativeRow(height -1),
                labelBoxTopLeft.withRelativeColumn(labelBoxSize.getColumns() - 2).withRelativeRow(height-1),
                Symbols.DOUBLE_LINE_HORIZONTAL);

        //left side
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeRow(1),
                labelBoxTopLeft.withRelativeRow(height-2),
                Symbols.DOUBLE_LINE_VERTICAL);

        //right side
        textGraphics.drawLine(
                labelBoxTopLeft.withRelativeRow(1).withRelativeColumn(labelBoxSize.getColumns()-1),
                labelBoxTopLeft.withRelativeRow(height-2).withRelativeColumn(labelBoxSize.getColumns()-1),
                Symbols.DOUBLE_LINE_VERTICAL);

        //corners
        textGraphics.setCharacter(labelBoxTopLeft, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
        textGraphics.setCharacter(labelBoxTopRightCorner, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
        textGraphics.setCharacter(labelBoxTopLeft.withRelativeRow(height -1), Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
        textGraphics.setCharacter(labelBoxTopRightCorner.withRelativeRow(height-1), Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);

        textGraphics.putString(labelBoxTopLeft.withRelative(2 , 1 + offset), text);

    }

    protected void drawSimpleText(String text, int positionX, int positionY){
        TerminalPosition rulesText = new TerminalPosition(positionX, positionY);
        TextGraphics rulesBox = screen.newTextGraphics();
        rulesBox.putString(rulesText.withRelative(1,1), text);
    }

    protected void drawSimpleText(String text, int positionX, int positionY, SGR textSTyle){
        TerminalPosition rulesText = new TerminalPosition(positionX, positionY);
        TextGraphics rulesBox = screen.newTextGraphics();
        rulesBox.putString(rulesText.withRelative(1,1), text, textSTyle);
    }

    protected void drawMenu(){
        if(lanternaMenu.size() != 0){
            for(int i = 0; i< lanternaMenu.size(); i++){
                LanternaMenuItem menuItem = lanternaMenu.get(i);
                if(menuItem.isVisible()){
                    TextGraphics textGraphics = screen.newTextGraphics();

                    if(menuItem.getIndex() == selectedMenuIndex){
                        //System.out.println("menu index: " + menuItem.getIndex());
                        textGraphics.setForegroundColor(menuItem.getHighlightTextColor());
                        textGraphics.setBackgroundColor(menuItem.getHighlightBackgroundColor());
                    }else{
                        textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                        textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
                    }
                    textGraphics.putString(menuItem.getPositionX(), menuItem.getPositionY(), menuItem.getText());

                    if(menuItem.getToggleMenuItem() != null){
                        LanternaToggleMenuItem toggleMenuItem = menuItem.getToggleMenuItem();
                        if(menuItem.getIndex() == selectedMenuIndex){
                            //System.out.println("menu index: " + menuItem.getIndex());
                            textGraphics.setForegroundColor(toggleMenuItem.getSelectedForeground());
                            textGraphics.setBackgroundColor(toggleMenuItem.getSelectedBackground());
                        }else{
                            textGraphics.setForegroundColor(TextColor.ANSI.WHITE);
                            textGraphics.setBackgroundColor(TextColor.ANSI.BLACK);
                        }
                        textGraphics.putString(toggleMenuItem.getPositionX(), toggleMenuItem.getPositionY(), toggleMenuItem.getSelectedItem());
                    }
                }
            }
        }
    }

    protected void menuOnListen(KeyStroke keyStroke){
        if(keyStroke != null && (keyStroke.getKeyType() != KeyType.Escape || keyStroke.getKeyType() !=KeyType.EOF)){
            if(keyStroke.getKeyType()==KeyType.ArrowDown){
                selectedMenuIndex = (selectedMenuIndex+1)%lanternaMenu.size();
                invalidate();
            }else if(keyStroke.getKeyType()==KeyType.ArrowUp){
                if(selectedMenuIndex != 0){
                    selectedMenuIndex = (selectedMenuIndex-1)%lanternaMenu.size();
                }else{
                    selectedMenuIndex = lanternaMenu.size()-1;
                }
                invalidate();
            }else if(keyStroke.getKeyType()==KeyType.Enter){
               for(int i = 0; i< lanternaMenu.size(); i++){
                   LanternaMenuItem menuItem = lanternaMenu.get(i);
                   if(menuItem.getIndex() == selectedMenuIndex){
                       if(menuItem.getMenuItemListener() != null){
                           menuItem.getMenuItemListener().onSelect();
                       }
                   }
               }
            }else if(keyStroke.getKeyType()==KeyType.ArrowRight){
                for(int i = 0; i< lanternaMenu.size(); i++){
                    if(lanternaMenu.get(i).getIndex() == selectedMenuIndex){
                        LanternaToggleMenuItem toggleMenuItem = lanternaMenu.get(i).getToggleMenuItem();
                        if(toggleMenuItem != null){
                            toggleMenuItem.turnRight();
                            if(toggleMenuItem.getToggleMenuListener() != null){
                                toggleMenuItem.getToggleMenuListener().onToggle();
                            }
                        }
                    }
                }
                invalidate();
            }else if(keyStroke.getKeyType()==KeyType.ArrowLeft){
                for(int i = 0; i< lanternaMenu.size(); i++){
                    if(lanternaMenu.get(i).getIndex() == selectedMenuIndex){
                        LanternaToggleMenuItem toggleMenuItem = lanternaMenu.get(i).getToggleMenuItem();
                        if(toggleMenuItem != null){
                            toggleMenuItem.turnLeft();
                            if(toggleMenuItem.getToggleMenuListener() != null){
                                toggleMenuItem.getToggleMenuListener().onToggle();
                            }
                        }
                    }
                }
                invalidate();
            }
        }
    }

    public void addMenuItem(LanternaMenuItem menuItem){
        lanternaMenu.add(menuItem);
    }

    protected void redraw() throws IOException {
        onDraw();
        screen.refresh();
    }


    protected void invalidate(){
        valid = false;
    }

    public Screen getScreen() {
        return screen;
    }

    public boolean isValid() {
        return valid;
    }
}
