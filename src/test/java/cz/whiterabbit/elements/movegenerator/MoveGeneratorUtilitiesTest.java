package cz.whiterabbit.elements.movegenerator;

import cz.whiterabbit.elements.TestBoards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveGeneratorUtilitiesTest {
    //MoveGeneratorUtilities moveGeneratorUtilities;
    TestBoards testBoards;
    @BeforeEach
    void beforeEach(){
        //moveGeneratorUtilities = new MoveGeneratorUtilities();
        testBoards = new TestBoards();
    }
    @Nested
    @DisplayName("getPlayerType test")
    class getPlayerType {
        @Test
        @DisplayName("Initial position without initialMove")
        void noInitialMove() {
            try {
                assertFalse(MoveGeneratorUtilities.moveGeneratorUtilities().getPlayerType((byte) 53, testBoards.getSurroundingsTestBoard(), null));
            } catch (Exception e) {

            }
        }

        @Test
        @DisplayName("Initial position with initial move")
        void initialPositionAndMove() {
            try {
                assertFalse(MoveGeneratorUtilities.moveGeneratorUtilities().getPlayerType((byte) 37, testBoards.getSurroundingsTestBoard(), new byte[]{52, -1, 0, 45, 1, 0, 37, 0, -1}));
            } catch (Exception e) {

            }
        }

        @Test
        @DisplayName("Invalid initial position")
        void invalidInitialPosition() {
            assertThrows(Exception.class, () -> MoveGeneratorUtilities.moveGeneratorUtilities().getPlayerType((byte) 2, testBoards.getSurroundingsTestBoard(), null));
        }

        @Test
        @DisplayName("Invalid initial move")
        void invalidInitialMove() {
            assertThrows(Exception.class, () -> MoveGeneratorUtilities.moveGeneratorUtilities().getPlayerType((byte) 57, testBoards.getSurroundingsTestBoard(), new byte[]{5, -1, 0, 45, 1, 0, 37, 0, -1}));
        }
    }
}