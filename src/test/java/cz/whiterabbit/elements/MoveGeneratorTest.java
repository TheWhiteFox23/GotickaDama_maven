package cz.whiterabbit.elements;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MoveGeneratorTest {

    MoveGenerator moveGenerator;
    TestBoards testBoards;

    @BeforeEach
    void beforeEach() {
        moveGenerator = new MoveGenerator();
        testBoards = new TestBoards();
    }

    @Nested
    @DisplayName("getMovesFromPosition test")
    class getMovesFromPosition {

        @Nested
        @DisplayName("NO JUMPS")
        class noJumpsTest {

            @Test
            @DisplayName("upper right corner")
            void basicMovesWithJump1() {

                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{56, -1, 0, 57, 0, -1});
                expectedMoves.add(new byte[]{56, -1, 0, 48, 0, -1});
                expectedMoves.add(new byte[]{56, -1, 0, 49, 0, -1});
                List<byte[]> given = moveGenerator.gerMovesFromPosition((byte) 56, testBoards.getTestBoard4(), null);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("bottom no restrictions")
            void basicMovesWithJump2() {

                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{59, -1, 0, 58, 0, -1});
                expectedMoves.add(new byte[]{59, -1, 0, 60, 0, -1});
                expectedMoves.add(new byte[]{59, -1, 0, 50, 0, -1});
                expectedMoves.add(new byte[]{59, -1, 0, 51, 0, -1});
                expectedMoves.add(new byte[]{59, -1, 0, 52, 0, -1});
                List<byte[]> given = moveGenerator.gerMovesFromPosition((byte) 59, testBoards.getTestBoard4(), null);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("bottom right corner")
            void basicMovesWithJump3() {

                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{63, -1, 0, 62, 0, -1});
                expectedMoves.add(new byte[]{63, -1, 0, 55, 0, -1});
                expectedMoves.add(new byte[]{63, -1, 0, 54, 0, -1});
                List<byte[]> given = moveGenerator.gerMovesFromPosition((byte) 63, testBoards.getTestBoard4(), null);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("upper left corner")
            void basicMovesWithJump4() {

                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{0, -1, 0, 1, 0, -1});
                List<byte[]> given = moveGenerator.gerMovesFromPosition((byte) 0, testBoards.getTestBoard4(), null);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("upper field")
            void basicMovesWithJump5() {

                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{3, -1, 0, 2, 0, -1});
                expectedMoves.add(new byte[]{3, -1, 0, 4, 0, -1});
                List<byte[]> given = moveGenerator.gerMovesFromPosition((byte) 3, testBoards.getTestBoard4(), null);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("upper right corner")
            void basicMovesWithJump6() {

                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{7, -1, 0, 6, 0, -1});
                List<byte[]> given = moveGenerator.gerMovesFromPosition((byte) 7, testBoards.getTestBoard4(), null);
                compareLists(expectedMoves, given);
            }

        }

        @Nested
        @DisplayName("FORWARD ONLY JUMPS")
        class ForwardOnly {
            @Test
            @DisplayName("single forward jump")
            void basicMovesWithJump1() {

                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{59, -1, 0, 51, 1, 0, 43, 0, -1});
                List<byte[]> given = moveGenerator.gerMovesFromPosition((byte) 59, testBoards.getTestBoard5(), null);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("2-chain forward jump")
            void basicMovesWithJump2() {

                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{63, -1, 0, 55, 1, 0, 47, 0, -1, 47, -1, 0, 39, 1, 0, 31, 0, -1});
                List<byte[]> given = moveGenerator.gerMovesFromPosition((byte) 63, testBoards.getTestBoard5(), null);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("3-chain forward jump")
            void basicMovesWithJump3() {

                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{56, -1, 0, 48, 1, 0, 40, 0, -1, 40, -1, 0, 32, 1, 0, 24, 0, -1, 24, -1, 0, 16, 1, 0, 8, 0, -1});
                List<byte[]> given = moveGenerator.gerMovesFromPosition((byte) 56, testBoards.getTestBoard5(), null);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("multi-chain multi-directive one-choice jump")
            void basicMovesWithJump4() {

                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{56, -1, 0, 48, 1, 0, 40, 0, -1, 40, -1, 0, 41, 1, 0, 42, 0, -1, 42, -1, 0, 43, 1, 0, 44, 0, -1, 44, -1, 0, 37, 1, 0, 30, 0, -1, 30, -1, 0, 21, 1, 0, 12, 0, -1, 12, -1, 0, 11, 1, 0, 10, 0, -1});
                List<byte[]> given = moveGenerator.gerMovesFromPosition((byte) 56, testBoards.getTestBoard6(), null);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("multi-chain multi-directive one-choice jump")
            void basicMovesWithJump5() {

                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{44, -1, 0, 37, 1, 0, 30, 0, -1, 30, -1, 0, 21, 1, 0, 12, 0, -1, 12, -1, 0, 11, 1, 0, 10, 0, -1});
                List<byte[]> given = moveGenerator.gerMovesFromPosition((byte) 44, testBoards.getTestBoard7(), null);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("multi-chain multi-directive multi-choice jump")
            void basicMovesWithJump6() {

                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{59, -1, 0, 51, 1, 0, 43, 0, -1, 43, -1, 0, 44, 1, 0, 45, 0, -1, 45, -1, 0, 36, 1, 0, 27, 0, -1});
                expectedMoves.add(new byte[]{59, -1, 0, 51, 1, 0, 43, 0, -1, 43, -1, 0, 44, 1, 0, 45, 0, -1, 45, -1, 0, 38, 1, 0, 31, 0, -1});
                expectedMoves.add(new byte[]{59, -1, 0, 51, 1, 0, 43, 0, -1, 43, -1, 0, 44, 1, 0, 45, 0, -1, 45, -1, 0, 37, 1, 0, 29, 0, -1});
                expectedMoves.add(new byte[]{59, -1, 0, 51, 1, 0, 43, 0, -1, 43, -1, 0, 44, 1, 0, 45, 0, -1, 45, -1, 0, 46, 1, 0, 47, 0, -1, 47, -1, 0, 38, 1, 0, 29, 0, -1});
                expectedMoves.add(new byte[]{59, -1, 0, 51, 1, 0, 43, 0, -1, 43, -1, 0, 42, 1, 0, 41, 0, -1, 41, -1, 0, 33, 1, 0, 25, 0, -1});
                expectedMoves.add(new byte[]{59, -1, 0, 51, 1, 0, 43, 0, -1, 43, -1, 0, 42, 1, 0, 41, 0, -1, 41, -1, 0, 34, 1, 0, 27, 0, -1});
                expectedMoves.add(new byte[]{59, -1, 0, 51, 1, 0, 43, 0, -1, 43, -1, 0, 36, 1, 0, 29, 0, -1});
                expectedMoves.add(new byte[]{59, -1, 0, 51, 1, 0, 43, 0, -1, 43, -1, 0, 34, 1, 0, 25, 0, -1});
                List<byte[]> given = moveGenerator.gerMovesFromPosition((byte) 59, testBoards.getTestBoard8(), null);
                compareLists(expectedMoves, given);
            }
        }
    }

    @Nested
    @DisplayName("getPlayerType test")
    class getPlayerType {
        @Test
        @DisplayName("Initial position without initialMove")
        void noInitialMove() {
            try {
                assertFalse(moveGenerator.getPlayerType((byte) 53, testBoards.getSurroundingsTestBoard(), null));
            } catch (Exception e) {

            }
        }

        @Test
        @DisplayName("Initial position with initial move")
        void initialPositionAndMove() {
            try {
                assertFalse(moveGenerator.getPlayerType((byte) 37, testBoards.getSurroundingsTestBoard(), new byte[]{52, -1, 0, 45, 1, 0, 37, 0, -1}));
            } catch (Exception e) {

            }
        }

        @Test
        @DisplayName("Invalid initial position")
        void invalidInitialPosition() {
            assertThrows(Exception.class, () -> moveGenerator.getPlayerType((byte) 2, testBoards.getSurroundingsTestBoard(), null));
        }

        @Test
        @DisplayName("Invalid initial move")
        void invalidInitialMove() {
            assertThrows(Exception.class, () -> moveGenerator.getPlayerType((byte) 57, testBoards.getSurroundingsTestBoard(), new byte[]{5, -1, 0, 45, 1, 0, 37, 0, -1}));
        }
    }


    @Test
    @DisplayName("getAvailableSurroundings")
    void getAvailableSurroundings() {
        assertAll(
                () -> assertArrayEquals(moveGenerator.getAvailableSurroundings((byte) 57, testBoards.getSurroundingsTestBoard(), false),
                        new byte[]{48, 49, 50, 56, 58}, "Negative player in the field"),
                () -> assertArrayEquals(moveGenerator.getAvailableSurroundings((byte) 12, testBoards.getSurroundingsTestBoard(), true),
                        new byte[]{11, 13, 19, 20, 21}, "Positive player in the field"),
                () -> assertArrayEquals(moveGenerator.getAvailableSurroundings((byte) 48, testBoards.getSurroundingsTestBoard(), false),
                        new byte[]{40, 41, 49}, "Negative player on the edge"),
                () -> assertArrayEquals(moveGenerator.getAvailableSurroundings((byte) 8, testBoards.getSurroundingsTestBoard(), true),
                        new byte[]{9, 16, 17}, "Positive player on the edge"),
                () -> assertArrayEquals(moveGenerator.getAvailableSurroundings((byte) 63, testBoards.getSurroundingsTestBoard(), false),
                        new byte[]{54, 55, 62}, "Negative player in the corner"),
                () -> assertArrayEquals(moveGenerator.getAvailableSurroundings((byte) 7, testBoards.getSurroundingsTestBoard(), true),
                        new byte[]{6, 14, 15}, "Positive player in the corner"),
                () -> assertArrayEquals(moveGenerator.getAvailableSurroundings((byte) 3, testBoards.getSurroundingsTestBoard(), false),
                        new byte[]{2, 4}, "Negative player on far other side"),
                () -> assertArrayEquals(moveGenerator.getAvailableSurroundings((byte) 61, testBoards.getSurroundingsTestBoard(), true),
                        new byte[]{60, 62}, "Positive player on far other side"),
                () -> assertArrayEquals(moveGenerator.getAvailableSurroundings((byte) 0, testBoards.getSurroundingsTestBoard(), false),
                        new byte[]{1}, "Negative player in the opposite corner"),
                () -> assertArrayEquals(moveGenerator.getAvailableSurroundings((byte) 63, testBoards.getSurroundingsTestBoard(), true),
                        new byte[]{62}, "Positive player in the opposite corner")
        );
    }

    @Test
    @DisplayName("get enemiesTest")
    void getEnemies(){
        assertAll(
                () -> assertArrayEquals(moveGenerator.getEnemies(new byte[] {50, 51, 52, 58, 60}, testBoards.getTestBoard2(), false),
                        new byte[]{}, "Negative player no enemies"),
                () -> assertArrayEquals(moveGenerator.getEnemies(new byte[] {34, 35, 36, 42, 44}, testBoards.getTestBoard2(), false),
                        new byte[]{34,35,36}, "Negative player three enemies"),
                () -> assertArrayEquals(moveGenerator.getEnemies(new byte[]{11, 12, 13, 19, 21}, testBoards.getTestBoard2(), false),
                        new byte[]{12}, "Negative player three enemies"),
                () -> assertArrayEquals(moveGenerator.getEnemies(new byte[]{11, 13, 19, 20, 21}, testBoards.getTestBoard2(), true),
                        new byte[]{11,13,19,20,21}, "positive player five enemies"),
                () -> assertArrayEquals(moveGenerator.getEnemies(new byte[]{34, 36, 42, 43, 44}, testBoards.getTestBoard2(), true),
                        new byte[]{42,43,44}, "positive player five enemies")
        );
    }

    @Test
    @DisplayName("canBeCaptured")
    void canBeCaptured(){
        assertAll(
                () ->  assertEquals(moveGenerator.canBeCaptured((byte)3, (byte)11, testBoards.getTestBoard3()),19),
                () ->  assertEquals(moveGenerator.canBeCaptured((byte)3, (byte)2, testBoards.getTestBoard3()),1),
                () ->  assertEquals(moveGenerator.canBeCaptured((byte)3, (byte)4, testBoards.getTestBoard3()),5),
                () ->  assertEquals(moveGenerator.canBeCaptured((byte)3, (byte)10, testBoards.getTestBoard3()),17),
                () ->  assertEquals(moveGenerator.canBeCaptured((byte)3, (byte)12, testBoards.getTestBoard3()),21),
                () ->  assertEquals(moveGenerator.canBeCaptured((byte)59, (byte)51, testBoards.getTestBoard3()),-1),
                () ->  assertEquals(moveGenerator.canBeCaptured((byte)59, (byte)58, testBoards.getTestBoard3()),-1),
                () ->  assertEquals(moveGenerator.canBeCaptured((byte)59, (byte)60, testBoards.getTestBoard3()),-1),
                () ->  assertEquals(moveGenerator.canBeCaptured((byte)59, (byte)50, testBoards.getTestBoard3()),-1),
                () ->  assertEquals(moveGenerator.canBeCaptured((byte)59, (byte)52, testBoards.getTestBoard3()),-1)
        );
    }

    @Test
    @DisplayName("getAvailableLandingPositions")
    void getAvailableLandingPositions(){
        assertAll(
                () -> assertArrayEquals(moveGenerator.getLandingLocations((byte)18,(byte)-9, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{0,9}),
                () -> assertArrayEquals(moveGenerator.getLandingLocations((byte)19,(byte)-8, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{3,11}),
                () -> assertArrayEquals(moveGenerator.getLandingLocations((byte)20,(byte)-7, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{6,13}),
                () -> assertArrayEquals(moveGenerator.getLandingLocations((byte)28,(byte)1, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{}),
                () -> assertArrayEquals(moveGenerator.getLandingLocations((byte)36,(byte)9, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{45}),
                () -> assertArrayEquals(moveGenerator.getLandingLocations((byte)35,(byte)8, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{43,51,59}),
                () -> assertArrayEquals(moveGenerator.getLandingLocations((byte)34,(byte)7, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{41,48})
        );
    }


    //TEST HELP METHODS
    private void compareLists(List<byte[]> l1, List<byte[]> l2) {
        l1.sort(new ArrayCountComparator());
        l2.sort(new ArrayCountComparator());

        for (int i = 0; i < l1.size(); i++) {
            assertArrayEquals(l1.get(i), l2.get(i));
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