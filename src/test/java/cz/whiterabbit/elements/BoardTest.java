package cz.whiterabbit.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    TestBoards testBoards;
    Board board;

    @BeforeEach
    void beforeEach(){
        testBoards = new TestBoards();
        board = new Board();
    }
    /*
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 1, 0,
            0, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 0,-2, 0, 0, 0,
     */

    @Test
    @DisplayName("Apply move test")
    void applyMove(){
        board.setBoard(testBoards.getGetMovesFromPositionRoyal2());
        assertAll(
                ()->assertArrayEquals(board.applyMove(new byte[]{60,-2,0,52,1,0,44,0,-2}),
                        new byte[]{
                                0, 0, 0, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0, 0, 0, 0,
                                0, 1, 0, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 1, 0, 1, 0,
                                0, 0, 0, 1,-2, 0, 0, 0,
                                0, 0, 0, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0, 0, 0, 0,
                        }),
                ()->assertArrayEquals(board.applyMove(new byte[]{60,-2,0,52,1,0,44,0,-2,44,-2,0,43,1,0,42,0,-2}),
                        new byte[]{
                                0, 0, 0, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0, 0, 0, 0,
                                0, 1, 0, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 1, 0, 1, 0,
                                0, 0,-2, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0, 0, 0, 0,
                                0, 0, 0, 0, 0, 0, 0, 0
                        })
        );

    }

}