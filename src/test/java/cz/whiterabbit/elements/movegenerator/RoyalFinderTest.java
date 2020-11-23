package cz.whiterabbit.elements.movegenerator;

import cz.whiterabbit.elements.TestBoards;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoyalFinderTest {
    RoyalFinder moveFinder;
    TestBoards testBoards;
    @BeforeEach
    void beforeEach(){
        moveFinder = new RoyalFinder();
        testBoards = new TestBoards();
    }

    @Nested
    @DisplayName("getMovesFromPositionRoyal")
    class find {
        @Test
        @DisplayName("multipleDecisionAndDirections")
        void multipleDecisionAndDirections() {
            List<byte[]> expectedMoves = new ArrayList<>();
            expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 44, 0, -2, 44, -2, 0, 36, 1, 0, 28, 0, -2, 28, -2, 0, 25, 1, 0, 24, 0, -2});
            expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 44, 0, -2, 44, -2, 0, 36, 1, 0, 20, 0, -2});
            expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 44, 0, -2, 44, -2, 0, 36, 1, 0, 12, 0, -2});
            expectedMoves.add(new byte[]{60, -2, 0, 52, 1, 0, 44, 0, -2, 44, -2, 0, 36, 1, 0, 4, 0, -2, 4, -2, 0, 25, 1, 0, 32, 0, -2});
            List<byte[]> given = moveFinder.find((byte) 60, testBoards.getGetMovesFromPositionRoyal3());
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
            List<byte[]> given = moveFinder.find((byte) 60, testBoards.getOneJumpMultipleLandingVertical());
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
            List<byte[]> given = moveFinder.find((byte) 60, testBoards.getOneJumpMultipleLandingDiagonal());
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
            List<byte[]> given = moveFinder.find((byte) 60, testBoards.getOneJumpMultipleLandingHorizontal());
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
            List<byte[]> given = moveFinder.find((byte) 60, testBoards.getNoValidEnemies());
            //printLists(given, expectedMoves);
            compareLists(expectedMoves, given);
        }


    }

    @Test
    @Disabled
    void getCaptureEnemiesRoyal() {
    }

    @Test
    @DisplayName("Get available Landing locations")
    void getLandingLocations() {
        assertAll(
                () -> assertArrayEquals(moveFinder.getLandingLocations((byte) 18, (byte) -9, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{0, 9}),
                () -> assertArrayEquals(moveFinder.getLandingLocations((byte) 19, (byte) -8, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{3, 11}),
                () -> assertArrayEquals(moveFinder.getLandingLocations((byte) 20, (byte) -7, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{6, 13}),
                () -> assertArrayEquals(moveFinder.getLandingLocations((byte) 28, (byte) 1, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{}),
                () -> assertArrayEquals(moveFinder.getLandingLocations((byte) 36, (byte) 9, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{45}),
                () -> assertArrayEquals(moveFinder.getLandingLocations((byte) 35, (byte) 8, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{43, 51, 59}),
                () -> assertArrayEquals(moveFinder.getLandingLocations((byte) 34, (byte) 7, testBoards.getGetLandingPositionsTestBoard()),
                        new byte[]{41, 48})
        );
    }

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