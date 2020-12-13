package cz.whiterabbit.elements.computerplayer;

import cz.whiterabbit.elements.TestBoards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MinimaxComputerPlayerTest {
    private MinimaxComputerPlayer minimaxComputerPlayer;
    private MovePicker movePicker;
    private TestBoards testBoards;
    private StateEvaluator stateEvaluator;

    @BeforeEach
    void beforeEach(){
        this.testBoards = new TestBoards();
        this.minimaxComputerPlayer = new MinimaxComputerPlayer();
        this.movePicker = new FirstInCollectionMovePicker();
        this.stateEvaluator = new SimpleEvaluator();

        this.minimaxComputerPlayer.setMovePicker(movePicker); //to assure consistency during tests
        this.minimaxComputerPlayer.setStateEvaluator(stateEvaluator); //to assure consistency during tests
    }

    @Nested
    @DisplayName("chooseMove tests")
    class chooseMoveTest{
        @Test
        @DisplayName("Single move selection test")
        void singleMoveSelection(){
            minimaxComputerPlayer.setDifficulty(3);
            byte[] move = minimaxComputerPlayer.chooseMove(testBoards.getMinimaxTest(), false);
            byte[] moveExpected = new byte[]{42,-1,0,43,0,-1};
            assertArrayEquals(move, moveExpected, "Moves should be equals");
        }

        @Test
        @DisplayName("Single move selection, more complicated setup")
        void singleMoveComplicatedSetup(){
            minimaxComputerPlayer.setDifficulty(3);
            byte[] move = minimaxComputerPlayer.chooseMove(testBoards.getMinimaxTest2(), false);
            byte[] moveExpected = new byte[]{42,-1,0,43,0,-1};
            assertArrayEquals(move, moveExpected, "Moves should be equals");
        }

        @Test
        @DisplayName("Single move selection, sacrifice")
        void singleMoveSacrifice(){
            minimaxComputerPlayer.setDifficulty(6);
            byte[] move = minimaxComputerPlayer.chooseMove(testBoards.getMinimaxTest3(), true);
            byte[] moveExpected = new byte[]{26,1,0,35,0,1};
            assertArrayEquals(move, moveExpected, "Moves should be equals");
        }

    }

}