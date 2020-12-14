package cz.whiterabbit.gui.frames;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import cz.whiterabbit.elements.GameController;
import cz.whiterabbit.gui.frames.elements.GameSettings;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class GameLoopFrameTest {
    GameLoopFrame gameLoopFrame;
    Screen screen;

    @BeforeEach
    void beforeEach() throws IOException {
        screen = new DefaultTerminalFactory().createScreen();
        gameLoopFrame = new GameLoopFrame(screen, new GameController(), new GameSettings());
    }

    @AfterEach
    void afterEach() throws IOException {
        screen.close();
    }

    @Nested
    @DisplayName("Validate moves tests")
    class ValidateMoves{
        @Test
        @DisplayName("Simple move validation - valid move")
        void simpleMoveValidation(){
            String move = "g2 g3";
            byte[] result = gameLoopFrame.validateMove(move);
            byte[] expected = new byte[]{14,22};
            assertArrayEquals(result, expected, "Arrays should be equal");
        }

        @Test
        @DisplayName("Simple move validation - valid move")
        void simpleMoveValidation_2(){
            String move = "a2 g3";
            byte[] result = gameLoopFrame.validateMove(move);
            byte[] expected = new byte[]{8,22};
            assertArrayEquals(result, expected, "Arrays should be equal");
        }
        @Test
        @DisplayName("Simple move validation - valid move")
        void invalidMove(){
            String move = "a2g3";
            byte[] result = gameLoopFrame.validateMove(move);
            //byte[] expected = new byte[]{8,1,0,22,0,0};
            assertNull(result);
        }
    }

}