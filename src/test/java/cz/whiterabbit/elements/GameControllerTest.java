package cz.whiterabbit.elements;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {
    GameController gameController;
    TestBoards testBoards;

    @BeforeEach
    void beforeEach(){
        gameController = new GameController();
        testBoards = new TestBoards();
    }

    @Test
    @DisplayName("startGame")
    void startGame(){
        gameController.getBoard().setBoard(testBoards.getGetEnemiesRoyalTestBoard());
        gameController.setPlayerType(false);
        gameController.getMoveMemory().addMove(new byte[]{1,1,0,2,0,1,});
        gameController.getMoveMemory().addMove(new byte[]{1,1,0,2,0,1,});
        gameController.getMoveMemory().addMove(new byte[]{1,1,0,2,0,1,});
        assertTrue(gameController.getMoveMemory().getCurrentPossibleMoves().size() == 0);
        gameController.getAllValidMoves();//to update currant moves in memory
        assertTrue(gameController.getMoveMemory().getCurrentPossibleMoves().size() != 0);
        gameController.startGame();
        assertAll(
                ()->assertTrue(gameController.getMoveMemory().getCurrentPossibleMoves().size() == 0),
                ()->assertTrue(gameController.getMoveMemory().getMovesHistory().size() == 0),
                ()->assertEquals(gameController.gameState, GameState.IN_PROGRESS),
                ()->assertArrayEquals(gameController.getBoard().getBoardArr(), testBoards.getInitialPosition())
        );
    }

    @Nested
    @DisplayName("isValidMove")
    class isValidMove{
        @Test
        @DisplayName("Valid simple move from init position")
        void validSimpleMove(){
            gameController.startGame();//to reset all inner instances
            assertAll(
                    ()->assertTrue(gameController.isValidMove(new byte[]{8,1,0,16,0,1})),
                    ()->assertTrue(gameController.isValidMove(new byte[]{8,1,0,17,0,1})),
                    ()->assertTrue(gameController.isValidMove(new byte[]{15,1,0,22,0,1})),
                    ()->assertTrue(gameController.isValidMove(new byte[]{12,1,0,21,0,1}))
            );
        }

        @Test
        @DisplayName("Invalid Moves")
        void invalidSimpleMove(){
            gameController.startGame();//to reset all inner instances
            assertAll(
                    ()->assertFalse(gameController.isValidMove(new byte[]{0,1,0,16,0,1})),
                    ()->assertFalse(gameController.isValidMove(new byte[]{2,1,0,17,0,1})),
                    ()->assertFalse(gameController.isValidMove(new byte[]{5,1,0,22,0,1})),
                    ()->assertFalse(gameController.isValidMove(new byte[]{7,1,0,21,0,1}))
            );
        }

        @Test
        @DisplayName("Valid moves on non-initial Board")
        void validMovesNonInitialBoard(){
            gameController.getMoveMemory().invalidateMemory();/* to reset memory when comparisons is being done*/
            gameController.getBoard().setBoard(testBoards.getRandomJumpsRoyal());
            gameController.switchPlayerType();
            assertAll(
                    ()->assertTrue(gameController.isValidMove(new byte[]{15, 2, 0, 50, -1, 0, 57, 0, 2, 57, 2, 0, 49, -1, 0, 41, 0, 2, 41, 2, 0, 34, -1, 0, 27, 0, 2,})),
                    ()->assertTrue(gameController.isValidMove(new byte[]{15, 2, 0, 50, -1, 0, 57, 0, 2, 57, 2, 0, 49, -1, 0, 33, 0, 2, 33, 2, 0, 34, -1, 0, 35, 0, 2,})),
                    ()->assertTrue(gameController.isValidMove(new byte[]{15, 2, 0, 50, -1, 0, 57, 0, 2, 57, 2, 0, 49, -1, 0, 33, 0, 2, 33, 2, 0, 19, -1, 0, 12, 0, 2,})),
                    ()->assertFalse(gameController.isValidMove(new byte[]{15, 2, 0, 50, -1, 0, 57, 0, 2, 57, 2, 0, 49, -1, 0, 25, 0, 2,})),
                    ()->assertFalse(gameController.isValidMove(new byte[]{15, 2, 0, 50, -1, 0, 57, 0, 2, 57, 2, 0, 49, -1, 0, 17, 0, 2,})),
                    ()->assertTrue(gameController.isValidMove(new byte[]{11, 2, 0, 19, -1, 0, 35, 0, 2, 35, 2, 0, 34, -1, 0, 33, 0, 2, 33, 2, 0, 49, -1, 0, 57, 0, 2,}))
            );
        }

    }

    @Nested
    @DisplayName("applyMove")
    class applyMove{
        @Test
        @DisplayName("Invalid move")
        void invalidMove(){
            gameController.startGame();
            assertThrows(InvalidMoveException.class, ()-> gameController.applyMove(new byte[]{1,1,0,2,0,1}));
        }

        @Test
        @DisplayName("Move applied")
        void moveApplied(){
            gameController.startGame();
            try{
                gameController.applyMove(new byte[] {8,1,0,16,0,1});
            }catch (InvalidMoveException e){
                System.out.println(e.toString());
            }
            assertArrayEquals(gameController.getBoard().getBoardArr(), new byte[]{
                     1, 1, 1, 1, 1, 1, 1, 1,
                     0, 1, 1, 1, 1, 1, 1, 1,
                     1, 0, 0, 0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0, 0, 0, 0,
                     0, 0, 0, 0, 0, 0, 0, 0,
                    -1,-1,-1,-1,-1,-1,-1,-1,
                    -1,-1,-1,-1,-1,-1,-1,-1,});
        }

        @Test
        @DisplayName("Move is in memory")
        void moveInMemory(){
            gameController.startGame();
            try{
                gameController.applyMove(new byte[] {8,1,0,16,0,1});
            }catch (InvalidMoveException e){
                System.out.println(e.toString());
            }
            List<byte[]> expected = new ArrayList<>();
            expected.add(new byte[] {8,1,0,16,0,1});
            compareLists(gameController.getMoveMemory().getMovesHistory(), expected);
        }

        @Test
        @DisplayName("Memory invalidated")
        void memoryInvalidated(){
            gameController.startGame();
            try{
                gameController.applyMove(new byte[] {8,1,0,16,0,1});
            }catch (InvalidMoveException e){
                System.out.println(e.toString());
            }
            assertFalse(gameController.getMoveMemory().isValid());
        }

        @Test
        @DisplayName("Board validated")
        void boardValidated(){
            gameController.startGame();
            gameController.switchPlayerType();
            gameController.getBoard().setBoard(testBoards.getValidateBoardAfterMove());
            gameController.getMoveMemory().invalidateMemory();
            try {
                gameController.applyMove(new byte[]{8,-1,0,0,0,-1});
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
            assertArrayEquals(gameController.getBoard().getBoardArr(), new byte[]{
                    -2, 1, 1, 1, 1, 1, 0, 1,
                    0, 1, 1, 1, 1, 1, 0, 1,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    -1,-1,-1,-1,0,0,0,-1,
                    -1,-1,-1,-1,-1,-1,-1,-1,
            });

        }
    }

    @Test
    @DisplayName("switchPlayerType")
    void switchPlayerType(){
        gameController.startGame();
        List<byte[]> expected1 = new ArrayList<>();
        expected1.add(new byte[]{48,-1,0,40,0,-1});
        expected1.add(new byte[]{48,-1,0,41,0,-1});
        expected1.add(new byte[]{49,-1,0,40,0,-1});
        expected1.add(new byte[]{49,-1,0,41,0,-1});
        expected1.add(new byte[]{49,-1,0,42,0,-1});
        expected1.add(new byte[]{50,-1,0,41,0,-1});
        expected1.add(new byte[]{50,-1,0,42,0,-1});
        expected1.add(new byte[]{50,-1,0,43,0,-1});
        expected1.add(new byte[]{51,-1,0,42,0,-1});
        expected1.add(new byte[]{51,-1,0,43,0,-1});
        expected1.add(new byte[]{51,-1,0,44,0,-1});
        expected1.add(new byte[]{52,-1,0,43,0,-1});
        expected1.add(new byte[]{52,-1,0,44,0,-1});
        expected1.add(new byte[]{52,-1,0,45,0,-1});
        expected1.add(new byte[]{53,-1,0,44,0,-1});
        expected1.add(new byte[]{53,-1,0,45,0,-1});
        expected1.add(new byte[]{53,-1,0,46,0,-1});
        expected1.add(new byte[]{54,-1,0,45,0,-1});
        expected1.add(new byte[]{54,-1,0,46,0,-1});
        expected1.add(new byte[]{54,-1,0,47,0,-1});
        expected1.add(new byte[]{55,-1,0,46,0,-1});
        expected1.add(new byte[]{55,-1,0,47,0,-1});

        List<byte[]> expected2 = new ArrayList<>();
        expected2.add(new byte[]{8, 1,0, 16,0,1});
        expected2.add(new byte[]{8, 1,0, 17,0,1});
        expected2.add(new byte[]{9, 1,0, 16,0,1});
        expected2.add(new byte[]{9, 1,0, 18,0,1});
        expected2.add(new byte[]{9, 1,0, 17,0,1});
        expected2.add(new byte[]{10, 1,0, 18,0,1});
        expected2.add(new byte[]{10, 1,0, 19,0,1});
        expected2.add(new byte[]{10, 1,0, 17,0,1});
        expected2.add(new byte[]{11, 1,0, 19,0,1});
        expected2.add(new byte[]{11, 1,0, 20,0,1});
        expected2.add(new byte[]{11, 1,0, 18,0,1});
        expected2.add(new byte[]{12, 1,0, 20,0,1});
        expected2.add(new byte[]{12, 1,0, 21,0,1});
        expected2.add(new byte[]{12, 1,0, 19,0,1});
        expected2.add(new byte[]{13, 1,0, 21,0,1});
        expected2.add(new byte[]{13, 1,0, 22,0,1});
        expected2.add(new byte[]{13, 1,0, 20,0,1});
        expected2.add(new byte[]{14, 1,0, 22,0,1});
        expected2.add(new byte[]{14, 1,0, 23,0,1});
        expected2.add(new byte[]{14, 1,0, 21,0,1});
        expected2.add(new byte[]{15, 1,0, 22,0,1});
        expected2.add(new byte[]{15, 1,0, 23,0,1});

        //List<byte[]> given2 = gameController.getAllValidMoves();
        //printLists(expected2, gameController.getAllValidMoves());
        compareLists(expected2, gameController.getAllValidMoves());

        gameController.switchPlayerType();
        compareLists(gameController.getAllValidMoves(), expected1);
    }

    //TEST HELP METHODS
    private void compareLists(List<byte[]> l1, List<byte[]> l2) {
        l1.sort(new ArrayCountComparator());
        l2.sort(new ArrayCountComparator());

        for (int i = 0; i < l1.size(); i++) {
            assertArrayEquals(l1.get(i), l2.get(i));
        }
    }

    private void printLists(List<byte[]> l1, List<byte[]> l2) {
        //l1.sort(new ArrayCountComparator());
        //l2.sort(new ArrayCountComparator());

        System.out.println("Given");
        for (byte[] ba : l1) {
            for (byte b : ba) {
                System.out.print(b + ", ");
            }
            System.out.println();
        }

        System.out.println("Expected");
        for (byte[] ba : l2) {
            for (byte b : ba) {
                System.out.print(b + ", ");
            }
            System.out.println();
        }
    }
    @Nested
    @DisplayName("Game Circle")
    class gameCircle{
        @Test
        @DisplayName("Simple moves and jumps no game end")
        void simpleMovesJumps(){
            //StartGame firstROW
            gameController.startGame();
            try {
                gameController.applyMove(new byte[]{8,1,0,16,0,1});
                gameController.switchPlayerType();
                gameController.applyMove(new byte[]{52,-1,0,44,0,-1});
                gameController.switchPlayerType();
                gameController.applyMove(new byte[]{16,1,0,25,0,1});
                gameController.switchPlayerType();
                gameController.applyMove(new byte[]{44,-1,0,43,0,-1});
                gameController.switchPlayerType();
                gameController.applyMove(new byte[]{25,1,0,34,0,1});
                gameController.switchPlayerType();
                gameController.applyMove(new byte[]{43,-1,0,34,1,0,25,0,-1});
                gameController.switchPlayerType();

                //check board state
            }catch (InvalidMoveException e){
                System.out.println(e.toString());
            }

            assertTrue(gameController.canContinue());
            assertArrayEquals(gameController.getBoard().getBoardArr(), new byte[]{
                    1, 1, 1, 1, 1, 1, 1, 1,
                    0, 1, 1, 1, 1, 1, 1, 1,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0,-1, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0, 0, 0, 0, 0,
                    -1,-1,-1,-1, 0,-1,-1,-1,
                    -1,-1,-1,-1,-1,-1,-1,-1,
            });

        }

        @Test
        @DisplayName("Finish game after no peaces of one player")
        void finishGameNoPeaces(){
            gameController.startGame();
            gameController.getBoard().setBoard(testBoards.getFinishGame());
            gameController.getMoveMemory().invalidateMemory();
            try{
                gameController.applyMove(new byte[]{1,1,0,9,0,1});
                gameController.switchPlayerType();
                //gameController.getAllValidMoves();
                assertThrows(InvalidMoveException.class, ()->gameController.applyMove(new byte[]{48,1,0,40,0,0}));
                gameController.applyMove(new byte[]{8,-1,0,9,1,0,10,0,-1});
                gameController.switchPlayerType();
                gameController.applyMove(new byte[]{35,1,0,43,0,1});
                gameController.switchPlayerType();
                gameController.applyMove(new byte[]{51,-1,0,43,1,0,35,0,-1});
                assertFalse(gameController.canContinue());

            }catch (InvalidMoveException e){
                System.out.println(e.toString());
            }
        }

        @Test
        @DisplayName("No capture in last 30 moves")
        void noCaptureGameEnd(){
            gameController.startGame();
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getMoveMemory().addMove(new byte[]{8,1,0,16,0,1});
            gameController.getBoard().setBoard(testBoards.getNoMovesFinish());
            gameController.getMoveMemory().invalidateMemory();
            try{
                gameController.applyMove(new byte[]{8,1,0,16,0,1});
                gameController.switchPlayerType();
                gameController.applyMove(new byte[]{48,-1,0,40,0,-1});
                assertFalse(gameController.canContinue());
                assertEquals(gameController.gameState, GameState.NEGATIVE_WIN);
            }catch (InvalidMoveException e){
                System.out.println(e.toString());
            }

        }
    }

    class ArrayCountComparator implements Comparator<byte[]> {
        @Override
        public int compare(byte[] o1, byte[] o2) {
            int byteCount1 = 0;
            int byteCount2 = 0;
            for (byte b : o1) {
                byteCount1 += b;
            }
            for (byte b : o2) {
                byteCount2 += b;
            }
            return byteCount1 - byteCount2;
        }
    }

}

