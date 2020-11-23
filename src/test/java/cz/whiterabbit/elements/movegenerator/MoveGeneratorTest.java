package cz.whiterabbit.elements.movegenerator;

import cz.whiterabbit.elements.TestBoards;
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

    //todo move class and test into utility class


    @Nested
    @DisplayName("getMovesFromPosition")
    class getMovesFromPosition {
        @Nested
        @DisplayName("ROYAL")
        class Royal {

            @Test
            @DisplayName("multipleDecisionAndDirections")
            void multipleDecisionAndDirections() {
                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 44, 0, -2, 44, -2, 0, 36, 1, 0, 28, 0, -2, 28, -2, 0, 25, 1, 0, 24, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 44, 0, -2, 44, -2, 0, 36, 1, 0, 20, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 44, 0, -2, 44, -2, 0, 36, 1, 0, 12, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 44, 0, -2, 44, -2, 0, 36, 1, 0, 4, 0, -2, 4, -2, 0, 25, 1, 0, 32, 0, -2});
                List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 60, testBoards.getGetMovesFromPositionRoyal3());
                //printLists(given, expectedMoves);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("oneJumpMultipleLandings vertical")
            void oneJumpMultipleLandings_vertical() {
                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 44, 0, -2,});
                expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 36, 0, -2,});
                expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 28, 0, -2,});
                expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 20, 0, -2,});
                expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 12, 0, -2,});
                expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 4, 0, -2,});
                List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 60, testBoards.getOneJumpMultipleLandingVertical());
                //printLists(given, expectedMoves);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("oneJumpMultipleLandings diagonal")
            void oneJumpMultipleLandingsDiagonal() {
                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{60, -2, 0, 51, 1, 0, 42, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 51, 1, 0, 33, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 51, 1, 0, 24, 0, -2});
                List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 60, testBoards.getOneJumpMultipleLandingDiagonal());
                //printLists(given, expectedMoves);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("oneJumpMultipleLandings horizontal")
            void oneJumpMultipleLandingsHorizontal() {
                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{60, -2, 0, 59, 1, 0, 58, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 59, 1, 0, 57, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 59, 1, 0, 56, 0, -2});
                List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 60, testBoards.getOneJumpMultipleLandingHorizontal());
                //printLists(given, expectedMoves);
                compareLists(expectedMoves, given);
            }

            @Test
            @DisplayName("no Valid Enemies")
            void noValidEnemies() {
                List<byte[]> expectedMoves = new ArrayList<>();
                expectedMoves.add(new byte[]{60, -2, 0, 51, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 42, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 52, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 44, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 36, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 61, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 62, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 53, 0, -2});
                expectedMoves.add(new byte[]{60, -2, 0, 46, 0, -2});
                List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 60, testBoards.getNoValidEnemies());
                //printLists(given, expectedMoves);
                compareLists(expectedMoves, given);
            }

        }

        @Nested
        @DisplayName("REGULAR")
        class Regular{
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
                    List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 56, testBoards.getTestBoard4());
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
                    List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 59, testBoards.getTestBoard4());
                    compareLists(expectedMoves, given);
                }

                @Test
                @DisplayName("bottom right corner")
                void basicMovesWithJump3() {

                    List<byte[]> expectedMoves = new ArrayList<>();
                    expectedMoves.add(new byte[]{63, -1, 0, 62, 0, -1});
                    expectedMoves.add(new byte[]{63, -1, 0, 55, 0, -1});
                    expectedMoves.add(new byte[]{63, -1, 0, 54, 0, -1});
                    List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 63, testBoards.getTestBoard4());
                    compareLists(expectedMoves, given);
                }

                @Test
                @DisplayName("upper left corner")
                void basicMovesWithJump4() {

                    List<byte[]> expectedMoves = new ArrayList<>();
                    expectedMoves.add(new byte[]{0, -1, 0, 1, 0, -1});
                    List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 0, testBoards.getTestBoard4());
                    compareLists(expectedMoves, given);
                }

                @Test
                @DisplayName("upper field")
                void basicMovesWithJump5() {

                    List<byte[]> expectedMoves = new ArrayList<>();
                    expectedMoves.add(new byte[]{3, -1, 0, 2, 0, -1});
                    expectedMoves.add(new byte[]{3, -1, 0, 4, 0, -1});
                    List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 3, testBoards.getTestBoard4());
                    compareLists(expectedMoves, given);
                }

                @Test
                @DisplayName("upper right corner")
                void basicMovesWithJump6() {

                    List<byte[]> expectedMoves = new ArrayList<>();
                    expectedMoves.add(new byte[]{7, -1, 0, 6, 0, -1});
                    List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 7, testBoards.getTestBoard4());
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
                    List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 59, testBoards.getTestBoard5());
                    compareLists(expectedMoves, given);
                }

                @Test
                @DisplayName("2-chain forward jump")
                void basicMovesWithJump2() {

                    List<byte[]> expectedMoves = new ArrayList<>();
                    expectedMoves.add(new byte[]{63, -1, 0, 55, 1, 0, 47, 0, -1, 47, -1, 0, 39, 1, 0, 31, 0, -1});
                    List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 63, testBoards.getTestBoard5());
                    compareLists(expectedMoves, given);
                }

                @Test
                @DisplayName("3-chain forward jump")
                void basicMovesWithJump3() {

                    List<byte[]> expectedMoves = new ArrayList<>();
                    expectedMoves.add(new byte[]{56, -1, 0, 48, 1, 0, 40, 0, -1, 40, -1, 0, 32, 1, 0, 24, 0, -1, 24, -1, 0, 16, 1, 0, 8, 0, -1});
                    List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 56, testBoards.getTestBoard5());
                    compareLists(expectedMoves, given);
                }

                @Test
                @DisplayName("multi-chain multi-directive one-choice jump")
                void basicMovesWithJump4() {

                    List<byte[]> expectedMoves = new ArrayList<>();
                    expectedMoves.add(new byte[]{56, -1, 0, 48, 1, 0, 40, 0, -1, 40, -1, 0, 41, 1, 0, 42, 0, -1, 42, -1, 0, 43, 1, 0, 44, 0, -1, 44, -1, 0, 37, 1, 0, 30, 0, -1, 30, -1, 0, 21, 1, 0, 12, 0, -1, 12, -1, 0, 11, 1, 0, 10, 0, -1});
                    List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 56, testBoards.getTestBoard6());
                    compareLists(expectedMoves, given);
                }

                @Test
                @DisplayName("multi-chain multi-directive one-choice jump")
                void basicMovesWithJump5() {

                    List<byte[]> expectedMoves = new ArrayList<>();
                    expectedMoves.add(new byte[]{44, -1, 0, 37, 1, 0, 30, 0, -1, 30, -1, 0, 21, 1, 0, 12, 0, -1, 12, -1, 0, 11, 1, 0, 10, 0, -1});
                    List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 44, testBoards.getTestBoard7());
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
                    List<byte[]> given = moveGenerator.getMovesFromPosition((byte) 59, testBoards.getTestBoard8());
                    compareLists(expectedMoves, given);
                }
            }
        }

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