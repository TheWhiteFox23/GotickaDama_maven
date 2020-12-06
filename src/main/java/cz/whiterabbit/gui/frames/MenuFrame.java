package cz.whiterabbit.gui.frames;

import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.elements.GameState;

import java.io.IOException;

public class MenuFrame extends LanternaFrame implements GUIFrame{

    //Todo replace with property file
    //Gui text strings
    private String title = "Gotická dáma";
    private String hint = "Pro výběr v menu stiskněte odpovídající klávesu";
    private String menuSettings =   "Nastavení   (S)";
    private String menuNewGame =    "Nová hra    (N)";
    private String menuContinue=    "Pokračovat  (P)";

    private GameController gameController;
    public MenuFrame(Screen screen, GameController gameController){
        super(screen);
        this.gameController = gameController;
    }

    @Override
    public void onDraw() {
        drawInFrame(title, 1,1);
        drawSimpleText(hint, 2, 3);

        drawSimpleText(menuSettings, 2, 6);
        drawSimpleText(menuNewGame, 2, 7);
        if(gameController.getGameState() == GameState.IN_PROGRESS)drawSimpleText(menuContinue, 2, 8);
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
                    if(gameController.getGameState() == GameState.IN_PROGRESS) getFrameListener().onContinue();
                }
            }
        }
    }

    @Override
    public void setLoopTimeout(int loopTimeout) {
        super.setLoopTimeout(loopTimeout);
    }
}
