package cz.whiterabbit.elements;

public class TestBoards {

    private byte[] surroundingsTestBoard = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,-1,1,0,0,0,0,0,-1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,};

    private byte[] testBoard2 = new byte[]{0,0,0,0,0,0,0,0,0,0,0,-1,1,-1,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,};

    private byte[] testBoard3 = new byte[]{0,0,-1,1,-1,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,1,1,1,0,0,0,0,1,1,-1,1,1,0,0,};

    private byte[] testBoard4 = new byte[]{-1,0,0,-1,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,-1,0,0,0,-1,};

    private byte[] testBoard5 = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,-1,0,0,-1,0,0,0,-1,};

    private byte[] testBoard6 = new byte[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,};

    private byte[] testBoard7 = new byte[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};

    private byte[] testBoard8 = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,0,0,0,1,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,-1,0,0,0,0,};
    /*
    {   0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0,};*/

    private byte[] getEnemiesRoyalTestBoard = new byte[]{
            0, 0, 0, 0, 2, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 1, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 0, 1,-2, 1, 0, 0,
            0, 0, 0, 0, 0, 1, 0, 0,
            0, 0, 1, 0, 1, 0, 0, 0,
            0, 2, 0, 0, 1, 0, 0, 1,
            0, 0, 0, 0, 0, 0, 0, 0,
    };

    private byte[] getLandingPositionsTestBoard = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 1, 1, 1, 0, 0, 0,
            1, 0, 1, 0, 1, 1, 0, 0,
            0, 0, 1, 1, 1, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 1, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
    };

    private byte[] getMovesFromPositionRoyal1 = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 0,-2, 0, 0, 0,
    };

    /*private byte[] getMovesFromPositionRoyal2 = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 0, 0, 0, 1, 0, 1,
            0, 1, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 1, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 0,-2, 0, 0, 0,
    };*/

    private byte[] getMovesFromPositionRoyal2 = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 1, 0,
            0, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 0,-2, 0, 0, 0,
    };

    private byte[] getMovesFromPositionRoyal3 = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 0,-2, 0, 0, 0,
    };

    private byte[] oneJumpMultipleLandingVertical = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0,
            0, 0, 0, 0,-2, 0, 0, 0,
    };

    private byte[] oneJumpMultipleLandingDiagonal = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0,-2, 0, 0, 0,
    };

    private byte[] oneJumpMultipleLandingHorizontal = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 1,-2, 0, 0, 0,
    };

    private byte[] noValidEnemies = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 1, 0, 0, 0,
            1, 0, 0, 0, 1, 0, 0, 0,
            0, 1, 0, 0, 0, 0, 0, 1,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 1, 1,-2, 0, 0, 1,
    };

    private byte[] initialPosition = new byte[]{
             1, 1, 1, 1, 1, 1, 1, 1,
             1, 1, 1, 1, 1, 1, 1, 1,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
            -1,-1,-1,-1,-1,-1,-1,-1,
            -1,-1,-1,-1,-1,-1,-1,-1,
    };

    private byte[] randomJumps = new byte[]{
             1, 1, 1, 1, 1, 1, 1, 1,
             1, 1, 1, 1, 0, 0, 1, 1,
             0, 0, 0,-1, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0,-1, 0, 1, 0, 0, 0,
             0, 0, 0, 1, 0, 0, 0, 0,
            -1,-1,-1,-1, 0,-1,-1,-1,
            -1, 0,-1,-1,-1,-1,-1,-1,
    };

    private byte[] randomJumpsRoyal = new byte[]{
             1, 1, 1, 1, 1, 1, 1, 1,
             1, 1, 1, 2, 0, 0, 1, 2,
             0, 0, 0,-1, 2, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0,-1, 0, 1, 0, 0, 0,
             0, 0, 0, 1, 0, 0, 0, 0,
            -1,-1,-1,-1, 0,-1,-1,-1,
            -1, 0,-1,-1,-2,-1,-1,-1,
    };

    private byte[] validateBoard = new byte[]{
            -1,-1,-1,-1,-1,-1,-1,-1,
             1, 1, 1, 2, 0, 0, 1, 2,
             0, 0, 0,-1, 2, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0,-1, 0, 1, 0, 0, 0,
             0, 0, 0, 1, 0, 0, 0, 0,
            -1,-1, 0, 0, 0, 0, 0, 0,
             1, 1, 1, 1, 0, 1, 1, 1,
    };

    private byte[] validateBoardAfterMove = new byte[]{
            0, 1, 1, 1, 1, 1, 0, 1,
            -1, 1, 1, 1, 1, 1, 0, 1,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            -1,-1,-1,-1,0,0,0,-1,
            -1,-1,-1,-1,-1,-1,-1,-1,
    };
    private byte[] finishGame = new byte[]{
             0, 1, 0, 0, 0, 0, 0, 0,
            -1, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
             0, 0, 0, 1, 0, 0, 0, 0,
             0, 0, 0, 0, 0, 0, 0, 0,
            -1,-1,-1,-1, 0, 0, 0,-1,
            -1,-1,-1,-1,-1,-1,-1,-1,

    };

    private byte[] noMovesFinish = new byte[]{
            1, 1, 1, 1, 1, 1, 1, 1,
            1, 1, 1, 1, 0, 0, 1, 1,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            -1,-1,-1,-1,-1,-1,-1,-1,
            -1,-1,-1,-1,-1,-1,-1,-1,
    };

    private byte[] minimaxTest = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 0, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            1, 0,-1, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,

    };

    private byte[] minimaxTest2 = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 1, 1, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            1, 0,-1, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,

    };
   private byte[] minimaxTest3 = new byte[]{
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 1, 1, 0,-1,0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            1, 0,-1, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0,

    };





    public byte[] getSurroundingsTestBoard() { return surroundingsTestBoard; }

    public byte[] getTestBoard2() { return testBoard2; }

    public byte[] getTestBoard3() { return testBoard3; }

    public byte[] getTestBoard4() { return testBoard4; }

    public byte[] getTestBoard5() { return testBoard5; }

    public byte[] getTestBoard6() { return testBoard6; }

    public byte[] getTestBoard7() { return testBoard7; }

    public byte[] getTestBoard8() { return testBoard8; }

    public byte[] getGetEnemiesRoyalTestBoard() { return getEnemiesRoyalTestBoard; }

    public byte[] getGetLandingPositionsTestBoard() { return getLandingPositionsTestBoard; }

    public byte[] getGetMovesFromPositionRoyal1() {
        return getMovesFromPositionRoyal1;
    }

    public byte[] getGetMovesFromPositionRoyal2() { return getMovesFromPositionRoyal2; }

    public byte[] getGetMovesFromPositionRoyal3() {
        return getMovesFromPositionRoyal3;
    }

    public byte[] getOneJumpMultipleLandingVertical() {
        return oneJumpMultipleLandingVertical;
    }

    public byte[] getOneJumpMultipleLandingDiagonal() {
        return oneJumpMultipleLandingDiagonal;
    }

    public byte[] getOneJumpMultipleLandingHorizontal() {
        return oneJumpMultipleLandingHorizontal;
    }

    public byte[] getNoValidEnemies() {
        return noValidEnemies;
    }

    public byte[] getInitialPosition() {
        return initialPosition;
    }

    public byte[] getRandomJumps() {
        return randomJumps;
    }

    public byte[] getRandomJumpsRoyal() {
        return randomJumpsRoyal;
    }

    public byte[] getValidateBoard() {
        return validateBoard;
    }

    public byte[] getValidateBoardAfterMove() {
        return validateBoardAfterMove;
    }

    public byte[] getFinishGame() {
        return finishGame;
    }

    public byte[] getNoMovesFinish() {
        return noMovesFinish;
    }

    public byte[] getMinimaxTest() {
        return minimaxTest;
    }

    public byte[] getMinimaxTest2() {
        return minimaxTest2;
    }

    public byte[] getMinimaxTest3() {
        return minimaxTest3;
    }
}
