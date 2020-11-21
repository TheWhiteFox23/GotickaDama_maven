package cz.whiterabbit.test;

public class TestBoards {

    private byte[] surroundingsTestBoard = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,-1,1,0,0,0,0,0,-1,0,0,0,0,0,0,0,1,1,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,};

    private byte[] testBoard2 = new byte[]{0,0,0,0,0,0,0,0,0,0,0,-1,1,-1,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,-1,-1,0,0,0,};

    private byte[] testBoard3 = new byte[]{0,0,-1,1,-1,0,0,0,0,0,-1,-1,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,0,1,0,0,0,0,1,1,1,0,0,0,0,1,1,-1,1,1,0,0,};

    private byte[] testBoard4 = new byte[]{-1,0,0,-1,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,-1,0,0,-1,0,0,0,-1,};

    private byte[] testBoard5 = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,1,0,0,0,1,-1,0,0,-1,0,0,0,-1,};

    private byte[] testBoard6 = new byte[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,};

    private byte[] testBoard7 = new byte[]{0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,};

    private byte[] testBoard8 = new byte[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,1,1,1,0,0,0,1,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,0,-1,0,0,0,0,};

    //59,-1,0,51,1,0,43,0,-1,43,-1,0,44,1,0,45,0,-1,45-,1,0,36,1,0,27,0,-1
    //59,-1,0,51,1,0,43,0,-1,43,-1,0,44,1,0,45,0,-1,45,-1,0,38,1,0,31,0,-1
    //59,-1,0,51,1,0,43,0,-1,43,-1,0,44,1,0,45,0,-1,45,-1,0,37,1,0,29,0,-1
    //59,-1,0,51,1,0,43,0,-1,43,-1,0,44,1,0,45,0,-1,45,-1,0,46,1,0,47,0,-1,47,-1,0,38,1,0,29,0,-1
    //59,-1,0,51,1,0,43,0,-1,43,-1,0,42,1,0,41,0,-1,41,-1,0,33,1,0,25,0,-1
    //59,-1,0,51,1,0,43,0,-1,43,-1,0,42,1,0,41,0,-1,41,-1,0,34,1,0,27,0,-1

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
}
