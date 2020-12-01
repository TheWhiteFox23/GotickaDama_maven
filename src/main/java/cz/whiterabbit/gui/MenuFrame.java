package cz.whiterabbit.gui;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;

import java.io.IOException;

public class MenuFrame extends LanternaFrame implements GUIFrame{

    private String title = "Gotická dáma";
    private String hint = "Pro výběr v menu stiskněte odpovídající klávesu";
    private String menuSettings =   "Nastavení   (S)";
    private String menuNewGame =    "Nová hra    (N)";
    private String menuContinue=    "Pokračovat  (P)";
    private boolean continueVisible = false;

    public MenuFrame(Screen screen){
        super(screen);
    }

    @Override
    public void onDraw() {
        drawInFrame(title, 1,1);
        drawSimpleText(hint, 2, 3);

        drawSimpleText(menuSettings, 2, 6);
        drawSimpleText(menuNewGame, 2, 7);
        if(continueVisible)drawSimpleText(menuContinue, 2, 8);
    }

    @Override
    public void onListen(){
        KeyStroke keyStroke = null;
        try {
            keyStroke = getScreen().pollInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(keyStroke != null && keyStroke.getKeyType() == KeyType.Character){
            switch (keyStroke.getCharacter()){
                case 's':{
                    getFrameListener().onSettings();
                    break;
                } case 'n':{
                    getFrameListener().onNewGame();
                    break;
                } case 'p':{
                    if(continueVisible) getFrameListener().onContinue();
                }
            }
        }
    }

    @Override
    public void setLoopTimeout(int loopTimeout) {
        super.setLoopTimeout(loopTimeout);
    }

    public void showResponseText(String text){
        drawSimpleText(text, 2, 10);
        invalidate();
    }
}
