package cz.whiterabbit.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JudgeTest {
    Judge judge;
    TestBoards boards;

    @BeforeEach
    void beforeEach(){
        judge = new Judge();
        boards = new TestBoards();
    }

    @Test
    void isValidMove() {
    }

    @Test
    void listContains() {
        List<byte[]> expected = new ArrayList<>();
        expected.add(new byte[]{48,-1,0,40,0,-1});
        expected.add(new byte[]{48,-1,0,41,0,-1});
        expected.add(new byte[]{49,-1,0,40,0,-1});
        expected.add(new byte[]{49,-1,0,41,0,-1});
        expected.add(new byte[]{49,-1,0,42,0,-1});
        expected.add(new byte[]{50,-1,0,41,0,-1});
        expected.add(new byte[]{50,-1,0,42,0,-1});
        expected.add(new byte[]{50,-1,0,43,0,-1});
        expected.add(new byte[]{51,-1,0,42,0,-1});
        expected.add(new byte[]{51,-1,0,43,0,-1});
        expected.add(new byte[]{51,-1,0,44,0,-1});
        expected.add(new byte[]{52,-1,0,43,0,-1});
        expected.add(new byte[]{52,-1,0,44,0,-1});
        expected.add(new byte[]{52,-1,0,45,0,-1});
        expected.add(new byte[]{53,-1,0,44,0,-1});
        expected.add(new byte[]{53,-1,0,45,0,-1});
        expected.add(new byte[]{53,-1,0,46,0,-1});
        expected.add(new byte[]{54,-1,0,45,0,-1});
        expected.add(new byte[]{54,-1,0,46,0,-1});
        expected.add(new byte[]{54,-1,0,47,0,-1});
        expected.add(new byte[]{55,-1,0,46,0,-1});
        expected.add(new byte[]{55,-1,0,47,0,-1});

        assertAll(
                ()->assertTrue(judge.listContains(expected, new byte[]{55,-1,0,47,0,-1})),
                ()->assertFalse(judge.listContains(expected, new byte[]{64,-1,0,47,0,-1}))
        );
    }
    @Test
    @DisplayName("Validate board test")
    void validateBoard(){
        Board board = new Board();
        board.setBoard(boards.getValidateBoard());
        byte[] expected = new byte[]{
                -2,-2,-2,-2,-2,-2,-2,-2,
                 1, 1, 1, 2, 0, 0, 1, 2,
                 0, 0, 0,-1, 2, 0, 0, 0,
                 0, 0, 0, 0, 0, 0, 0, 0,
                 0, 0,-1, 0, 1, 0, 0, 0,
                 0, 0, 0, 1, 0, 0, 0, 0,
                -1,-1, 0, 0, 0, 0, 0, 0,
                 2, 2, 2, 2, 0, 2, 2, 2,
        };
        judge.validateBoard(board);
        assertArrayEquals(board.getBoardArr(), expected);
    }

    @Test
    @DisplayName("getPeaceCount")
    void getPeaceCount(){
        Board board = new Board();
        board.setBoard(boards.getInitialPosition());
        int[] given = judge.getPeaceCount(board);
        int[] expected = new int[]{16,16};
        assertArrayEquals(given, expected);

        board.setBoard(new byte[]{
                -2,-2,-2,-2,-2,-2,-2,-2,
                1, 1, 1, 2, 0, 0, 1, 2,
                0, 0, 0,-1, 2, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0,-1, 0, 1, 0, 0, 0,
                0, 0, 0, 1, 0, 0, 0, 0,
                -1,-1, 0, 0, 0, 0, 0, 0,
                2, 2, 2, 2, 0, 2, 2, 2,
        });
        given = judge.getPeaceCount(board);
        expected = new int[]{12,16};

        assertArrayEquals(given, expected);
    }

    @Nested
    @DisplayName("getWiningPlayer")
    class getWiningPlayer {
        @Test
        @DisplayName("DRAW")
        void getWiningPlayerDraw(){
            Board board = new Board();
            board.setBoard(boards.getInitialPosition());
            GameState state = judge.getWiningPlayer(board);
            assertEquals(GameState.DRAW, state);
        }

        @Test
        @DisplayName("POSITIVE")
        void getWiningPlayerPositive(){
            Board board = new Board();
            board.setBoard(new byte[]{
                    -2,-2,-2,-2,-2,-2,-2,-2,
                    1, 1, 1, 2, 0, 0, 1, 2,
                    0, 0, 0,-1, 2, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0,-1, 0, 1, 0, 0, 0,
                    0, 0, 0, 1, 0, 0, 0, 0,
                    -1,-1, 0, 0, 0, 0, 0, 0,
                    2, 2, 2, 2, 0, 2, 2, 2,
            });
            GameState state = judge.getWiningPlayer(board);
            assertEquals(GameState.POSITIVE_WIN, state);
        }

        @Test
        @DisplayName("NEGATIVE")
        void getWiningPlayerNegative(){
            Board board = new Board();
            board.setBoard(new byte[]{
                    -2,-2, -2,-2,-2,-2,-2,-2,
                    -1, -1, -1, 2, 0, 0, 1, 2,
                    0, 0, 0,-1, 2, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0,-1, 0, -1, 0, 0, 0,
                    0, 0, 0, 1, 0, 0, 0, 0,
                    -1,-1, 0, 0, 0, 0, 0, 0,
                    2, 2, 2, 2, 0, 2, 2, 2,
            });
            GameState state = judge.getWiningPlayer(board);
            assertEquals(GameState.NEGATIVE_WIN, state);
        }
    }

    @Nested
    @DisplayName("isCaptureInLast30")
    class isCaptureInLast30{

        @Test
        @DisplayName("isCaptureInLast30 FALSE")
        void isCaptureInLast30False(){
            MoveMemory moveMemory = new MoveMemory();
            moveMemory.addMove(new byte[]{48,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,47,0,-1});
            moveMemory.addMove(new byte[]{55,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{55,-1,0,47,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,40,1,0,32,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,47,0,-1});
            moveMemory.addMove(new byte[]{55,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{55,-1,0,47,0,-1});
            assertFalse(judge.isCaptureInLast30(moveMemory));
        }

        @Test
        @DisplayName("isCaptureInLast30 TRUE")
        void isCaptureInLast30True(){
            MoveMemory moveMemory = new MoveMemory();
            moveMemory.addMove(new byte[]{48,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,47,0,-1});
            moveMemory.addMove(new byte[]{55,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{55,-1,0,47,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,40,1,0,32,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,47,0,-1});
            moveMemory.addMove(new byte[]{55,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{55,-1,0,47,0,-1});
            assertTrue(judge.isCaptureInLast30(moveMemory));
        }

    }

    @Nested
    @DisplayName("getCurrentGameState")
    class getCurrentGameState{
        @Test
        @DisplayName("IN_PROGRESS")
        void gameInProgress() {
            Board board = new Board();
            board.setBoard(boards.getInitialPosition());
            MoveMemory moveMemory = new MoveMemory();
            assertEquals(GameState.IN_PROGRESS, judge.getCurrentGameState(board, moveMemory));
        }

        @Test
        @DisplayName("no moves NEGATIVE_WIN")
        void noMovesNegativeWin() {
            Board board = new Board();
            board.setBoard(new byte[]{
                    -2,-2, -2,-2,-2,-2,-2,-2,
                    -1, -1, -1, 2, 0, 0, 1, 2,
                    0, 0, 0,-1, 2, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0,-1, 0, -1, 0, 0, 0,
                    0, 0, 0, 1, 0, 0, 0, 0,
                    -1,-1, 0, 0, 0, 0, 0, 0,
                    2, 2, 2, 2, 0, 2, 2, 2,
            });
            MoveMemory moveMemory = new MoveMemory();
            moveMemory.addMove(new byte[]{48,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,47,0,-1});
            moveMemory.addMove(new byte[]{55,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{55,-1,0,47,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,43,0,-1});
            assertEquals(GameState.NEGATIVE_WIN, judge.getCurrentGameState(board, moveMemory));
        }

        @Test
        @DisplayName("no moves POSITIVE_WIN")
        void noMovesPositiveWin() {
            Board board = new Board();
            board.setBoard(new byte[]{
                    -2,-2,-2,-2,-2,-2,-2,-2,
                    1, 1, 1, 2, 0, 0, 1, 2,
                    0, 0, 0,-1, 2, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0,-1, 0, 1, 0, 0, 0,
                    0, 0, 0, 1, 0, 0, 0, 0,
                    -1,-1, 0, 0, 0, 0, 0, 0,
                    2, 2, 2, 2, 0, 2, 2, 2,
            });
            MoveMemory moveMemory = new MoveMemory();
            moveMemory.addMove(new byte[]{48,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,47,0,-1});
            moveMemory.addMove(new byte[]{55,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{55,-1,0,47,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,43,0,-1});
            assertEquals(GameState.POSITIVE_WIN, judge.getCurrentGameState(board, moveMemory));
        }

        @Test
        @DisplayName("no negative peaces POSITIVE_WIN")
        void noPeacesPositiveWin() {
            Board board = new Board();
            board.setBoard(new byte[]{
                    2, 0, 0, 0, 0, 0, 0, 0,
                    1, 1, 1, 2, 0, 0, 1, 2,
                    0, 0, 0, 0, 2, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 1, 0, 0, 0,
                    0, 0, 0, 1, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    2, 2, 2, 2, 0, 2, 2, 2,
            });
            MoveMemory moveMemory = new MoveMemory();
            moveMemory.addMove(new byte[]{48,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,47,0,-1});
            assertEquals(GameState.POSITIVE_WIN, judge.getCurrentGameState(board, moveMemory));
        }

        @Test
        @DisplayName("no negative peaces NEGATIVE_WIN")
        void noPeacesNegativeWin() {
            Board board = new Board();
            board.setBoard(new byte[]{
                    -2, 0, 0, 0, 0, 0, 0, 0,
                    -1,-1,-1,-2, 0, 0,-1,-2,
                     0, 0, 0, 0,-2, 0, 0, 0,
                     0, 0, 0, 0, 0, 0, 0, 0,
                     0, 0, 0, 0,-1, 0, 0, 0,
                     0, 0, 0,-1, 0, 0, 0, 0,
                     0, 0, 0, 0, 0, 0, 0, 0,
                    -2,-2,-2,-2, 0,-2,-2,-2,
            });
            MoveMemory moveMemory = new MoveMemory();
            moveMemory.addMove(new byte[]{48,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,47,0,-1});
            assertEquals(GameState.NEGATIVE_WIN, judge.getCurrentGameState(board, moveMemory));
        }

        @Test
        @DisplayName("no moves DRAW")
        void noMovesDraw() {
            Board board = new Board();
            board.setBoard(boards.getInitialPosition());
            MoveMemory moveMemory = new MoveMemory();
            moveMemory.addMove(new byte[]{48,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,47,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{48,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,40,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{49,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,41,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{50,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,42,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{51,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,43,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{52,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,44,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{53,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,45,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,46,0,-1});
            moveMemory.addMove(new byte[]{54,-1,0,47,0,-1});
            assertEquals(GameState.DRAW, judge.getCurrentGameState(board, moveMemory));
        }
    }




}