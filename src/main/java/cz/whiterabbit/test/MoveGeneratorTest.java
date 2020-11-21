package cz.whiterabbit.test;

import cz.whiterabbit.elements.MoveGenerator;

import java.util.ArrayList;
import java.util.List;

public class MoveGeneratorTest {

    TestBoards testBoards = new TestBoards();
    byte[] surroundingsTestBoard = testBoards.getSurroundingsTestBoard();
    byte[] testBoard2 = testBoards.getTestBoard2();
    byte[] testBoard3 = testBoards.getTestBoard3();
    byte[] testBoard4 = testBoards.getTestBoard4();
    byte[] testBoard5 = testBoards.getTestBoard5();
    byte[] testBoard6 = testBoards.getTestBoard6();
    byte[] testBoard7 = testBoards.getTestBoard7();
    byte[] testBoard8 = testBoards.getTestBoard8();
    byte[] testBoardGetEnemiesRoyal = testBoards.getGetEnemiesRoyalTestBoard();
    byte[] testBoardGetAvailableLanding = testBoards.getGetLandingPositionsTestBoard();
    byte[] testGetMovesFromPositionRoyal1 = testBoards.getGetMovesFromPositionRoyal1();
    byte[] testGetMovesFromPositionRoyal2 = testBoards.getGetMovesFromPositionRoyal2();

    /**
     * test get player type method from class MoveGenerator
     */
    public void testGetPlayerType(){
        MoveGenerator moveGenerator = new MoveGenerator();
        System.out.println("Test 1: initial position without initial move");
        boolean test = true;
        try{
            test = moveGenerator.getPlayerType((byte) 53, surroundingsTestBoard,null);
        }catch (Exception e){
            System.out.println("Unable to determinate player type : " + e.getMessage());
        }
        if(test){
            System.out.println("Test failed -> result shoul be false - is true");
        }else{
            System.out.println("test OK");
        }

        System.out.println("Test 2: initial position wit initial move");
        try{
            test = moveGenerator.getPlayerType((byte)37, surroundingsTestBoard, new byte[]{52, -1, 0, 45,1,0,37,0,-1});
        }catch (Exception e){
            System.out.println("Unable to determinate player type : " + e.getMessage());
        }

        if(test){
            System.out.println("Test failed -> result shoul be false - is true");
        }else{
            System.out.println("test OK");
        }

        System.out.println("Test 3: invalid initial position");
        try{
            test = moveGenerator.getPlayerType((byte)2, surroundingsTestBoard, null);
        }catch (Exception e){
            System.out.println("Unable to determinate player type : " + e.getMessage());
        }

        System.out.println("Test 4: invalid initialMove");
        try{
            test = moveGenerator.getPlayerType((byte)57, surroundingsTestBoard, new byte[]{5, -1, 0, 45,1,0,37,0,-1});
        }catch (Exception e){
            System.out.println("Unable to determinate player type : " + e.getMessage());
        }
    }

    /**
     * test getAvailableSurroundings method from MoveGenerator class
     */
    public void getAvailableSurroundingsTest(){
        MoveGenerator moveGenerator = new MoveGenerator();
        System.out.println("Negative player in the field");
        testSurroundings(moveGenerator.getAvailableSurroundings((byte)57, surroundingsTestBoard, false), new byte[]{
                56,58,48,49,50
        });
        System.out.println("Positive player in the field");
        testSurroundings(moveGenerator.getAvailableSurroundings((byte)12, surroundingsTestBoard, true), new byte[]{
                11,13,19,20,21
        });
        System.out.println("Negative player on the edge");
        testSurroundings(moveGenerator.getAvailableSurroundings((byte)48, surroundingsTestBoard, false), new byte[]{
                40,41,49
        });
        System.out.println("Positive player on the edge");
        testSurroundings(moveGenerator.getAvailableSurroundings((byte)8, surroundingsTestBoard, true), new byte[]{
               9,17,16
        });
        System.out.println("Negative player in the corner");
        testSurroundings(moveGenerator.getAvailableSurroundings((byte)63, surroundingsTestBoard, false), new byte[]{
                54,55,62
        });
        System.out.println("Positive player in the corner");
        testSurroundings(moveGenerator.getAvailableSurroundings((byte)7, surroundingsTestBoard, true), new byte[]{
                6,14,15
        });
        System.out.println("Negative player on far other side");
        testSurroundings(moveGenerator.getAvailableSurroundings((byte)3, surroundingsTestBoard, false), new byte[]{
                2,4
        });
        System.out.println("Positive player on far other side");
        testSurroundings(moveGenerator.getAvailableSurroundings((byte)61, surroundingsTestBoard, true), new byte[]{
                60,62
        });
        System.out.println("Negative player in the oposite corner");
        testSurroundings(moveGenerator.getAvailableSurroundings((byte)0, surroundingsTestBoard, false), new byte[]{
                1
        });
        System.out.println("Positive player in the oposite corner");
        testSurroundings(moveGenerator.getAvailableSurroundings((byte)63, surroundingsTestBoard, true), new byte[]{
                62
        });


    }

    /**
     * helper method for function getAvailableSurroundingsTest(), compare given array and expected array and write out
     * result of the comparison
     * @param surroundings array generated by method getAvailableSurroundings
     * @param expected expected array for given position
     */
    private void testSurroundings(byte[] surroundings, byte[] expected) {
        boolean testPass = true;
        for (byte b : expected) {
            boolean cotains = false;
            for (byte by : surroundings) {
                if (by == b) {
                    cotains = true;
                    break;
                }
            }
            if (!cotains) {
                testPass = false;
                break;
            }
        }
        if (testPass) {
            System.out.println("Test Passed");
        } else {
            System.out.println("Test Failed");
            System.out.print("given field : " );
            for(byte b : surroundings){
                System.out.print(b + " ");
            }
            System.out.println();
            System.out.print("expected field : " );
            for(byte b : expected){
                System.out.print(b + " ");
            }
            System.out.println();
        }
    }

    /**
     * test get enemies method from class MoveGenerator
     */
    public void testGetEnemies(){
        MoveGenerator moveGenerator = new MoveGenerator();
        System.out.println("Negative player no enemies");
        byte[] enemies = moveGenerator.getEnemies(moveGenerator.getAvailableSurroundings((byte)59, testBoard2, false),testBoard2, false);
        testEnemies(enemies, new byte[0]);

        System.out.println("Negative player three enemies");

        enemies = moveGenerator.getEnemies(moveGenerator.getAvailableSurroundings((byte)43, testBoard2, false),testBoard2, false);
        testEnemies(enemies, new byte[]{34,35,36});

        System.out.println("Negative player one enemy");

        enemies = moveGenerator.getEnemies(moveGenerator.getAvailableSurroundings((byte)20, testBoard2, false),testBoard2, false);
        testEnemies(enemies, new byte[]{12});

        System.out.println("positive player five enemies");
        enemies = moveGenerator.getEnemies(moveGenerator.getAvailableSurroundings((byte)12, testBoard2, true),testBoard2, true);
        testEnemies(enemies, new byte[]{11,13,19,20,21});

        System.out.println("positive player tree enemies");
        enemies = moveGenerator.getEnemies(moveGenerator.getAvailableSurroundings((byte)35, testBoard2, true),testBoard2, true);
        testEnemies(enemies, new byte[]{42,43,44});

    }

    /**
     * Helper method for testGetEnemies(), compare given and expected array and print result
     * @param givenArray array returned from getEnemies method;
     * @param expectedArray expected result of getEnemies from given position
     */
    private void testEnemies(byte[] givenArray, byte[] expectedArray){
        boolean testPass = true;
        for(byte b: expectedArray){
            boolean elementFound = false;
            for(byte by : givenArray){
               if(by == b){
                   elementFound = true;
                   break;
               }
            }
            if(!elementFound) {
                testPass = false;
                break;
            }
        }
        if(testPass){
            System.out.println("test OK");
        }else{
            System.out.println("test failed");
            System.out.print("Given Array : ");
            for(byte b: givenArray){
                System.out.print(b + " ");
            }
            System.out.println();
            System.out.print("Expected Array : ");
            for(byte b: expectedArray){
                System.out.print(b + " ");
            }
            System.out.println();
        }
    }

    /**
     * Test of the canBeCaptured method from class MoveGenerator
     */
    public void canBeCapturedTest(){
        MoveGenerator moveGenerator = new MoveGenerator();
        testCapturePosition((byte) 3, (byte) 11, testBoard3, (byte) 19, moveGenerator);
        testCapturePosition((byte) 3, (byte) 2, testBoard3, (byte) 1, moveGenerator);
        testCapturePosition((byte) 3, (byte) 4, testBoard3, (byte) 5, moveGenerator);
        testCapturePosition((byte) 3, (byte) 10, testBoard3, (byte) 17, moveGenerator);
        testCapturePosition((byte) 3, (byte) 12, testBoard3, (byte) 21, moveGenerator);
        testCapturePosition((byte) 59, (byte) 51, testBoard3, (byte) -1, moveGenerator);
        testCapturePosition((byte) 59, (byte) 58, testBoard3, (byte) -1, moveGenerator);
        testCapturePosition((byte) 59, (byte) 60, testBoard3, (byte) -1, moveGenerator);
        testCapturePosition((byte) 59, (byte) 50, testBoard3, (byte) -1, moveGenerator);
        testCapturePosition((byte) 59, (byte) 52, testBoard3, (byte) -1, moveGenerator);
    }

    /**
     * Helper method for canBeCapturedTest() generate array and compare it with expected result
     * @param peacePosition position of the peace on the playboard
     * @param enemyPosition position of the enemy on the board
     * @param board play board
     * @param expectedResult Expected result of the method
     * @param moveGenerator Move generator class to call
     */
    private void testCapturePosition(byte peacePosition, byte enemyPosition, byte[] board, byte expectedResult, MoveGenerator moveGenerator){
        byte result = moveGenerator.canBeCaptured(peacePosition, enemyPosition, board);
        if(result == expectedResult){
            System.out.println("Test OK");
        }else{
            System.out.println("Test FAILED  ::  Given result : " + result + " Expected result : " + expectedResult);
        }
    }

    /**
     * Test of the method getMovesFromPosition
     */
    public void getMovesFromPositionTest(){
        MoveGenerator moveGenerator = new MoveGenerator();
        List<byte[]> expected = new ArrayList<>();
        //testTestMethod();
        System.out.println("TEST BASIC MOVES WITH NO JUMP");

        expected.add(new byte[]{56,-1,0,57,0,-1});
        expected.add(new byte[]{56,-1,0,48,0,-1});
        expected.add(new byte[]{56,-1,0,49,0,-1});
        System.out.println("Player in the right left bottom corner");
        testPosition(moveGenerator.gerMovesFromPosition((byte)56, testBoard4, null), expected);
        expected.clear();
        expected.add(new byte[]{59,-1,0,58,0,-1});
        expected.add(new byte[]{59,-1,0,60,0,-1});
        expected.add(new byte[]{59,-1,0,50,0,-1});
        expected.add(new byte[]{59,-1,0,51,0,-1});
        expected.add(new byte[]{59,-1,0,52,0,-1});
        System.out.println("Player in the bottom no restrictions");
        testPosition(moveGenerator.gerMovesFromPosition((byte)59, testBoard4, null), expected);
        expected.clear();
        expected.add(new byte[]{63,-1,0,62,0,-1});
        expected.add(new byte[]{63,-1,0,55,0,-1});
        expected.add(new byte[]{63,-1,0,54,0,-1});
        System.out.println("Player in the bottom right corner");
        testPosition(moveGenerator.gerMovesFromPosition((byte)63, testBoard4, null), expected);
        expected.clear();
        expected.add(new byte[]{0,-1,0,1,0,-1});
        System.out.println("Player in the upper left corner");
        testPosition(moveGenerator.gerMovesFromPosition((byte)0, testBoard4, null), expected);
        expected.clear();
        expected.add(new byte[]{3,-1,0,2,0,-1});
        expected.add(new byte[]{3,-1,0,4,0,-1});
        System.out.println("Player in the upper field");
        testPosition(moveGenerator.gerMovesFromPosition((byte)3, testBoard4, null), expected);
        expected.clear();
        expected.add(new byte[]{7,-1,0,6,0,-1});
        System.out.println("Player in the upper right corner");
        testPosition(moveGenerator.gerMovesFromPosition((byte)7, testBoard4, null), expected);
        System.out.println("FORWARD ONLY JUMPS");
        expected.clear();
        expected.add(new byte[]{59,-1,0,51,1,0,43,0,-1});
        System.out.println("single forward jump");
        testPosition(moveGenerator.gerMovesFromPosition((byte)59, testBoard5, null), expected);
        expected.clear();
        expected.add(new byte[]{63,-1,0,55,1,0,47,0,-1,47,-1,0,39,1,0,31,0,-1});
        System.out.println("2-chain forward jump");
        testPosition(moveGenerator.gerMovesFromPosition((byte)63, testBoard5, null), expected);
        expected.clear();
        expected.add(new byte[]{56,-1,0,48,1,0,40,0,-1,40,-1,0,32,1,0,24,0,-1,24,-1,0,16,1,0,8,0,-1});
        System.out.println("3-chain forward jump");
        testPosition(moveGenerator.gerMovesFromPosition((byte)56, testBoard5, null), expected);
        expected.clear();
        expected.add(new byte[]{56,-1,0,48,1,0,40,0,-1,40,-1,0,41,1,0,42,0,-1,42,-1,0,43,1,0,44,0,-1,44,-1,0,37,1,0,30,0,-1,30,-1,0,21,1,0,12,0,-1,12,-1,0,11,1,0,10,0,-1});
        //                      56,-1,0,48,1,0,40,0,-1,40,-1,0,41,1,0,42,0,-1,42,-1,0,43,1,0,44,0,-1,
        System.out.println("multi-chain multi-directive one-choice jump ");
        testPosition(moveGenerator.gerMovesFromPosition((byte)56, testBoard6, null), expected);
        expected.clear();
        expected.add(new byte[]{44,-1,0,37,1,0,30,0,-1,30,-1,0,21,1,0,12,0,-1,12,-1,0,11,1,0,10,0,-1});
        System.out.println("multi-chain multi-directive one-choice jump ");
        testPosition(moveGenerator.gerMovesFromPosition((byte)44, testBoard7, null), expected);
        expected.clear();
        expected.add(new byte[]{59,-1,0,51,1,0,43,0,-1,43,-1,0,44,1,0,45,0,-1,45,-1,0,36,1,0,27,0,-1});
        expected.add(new byte[]{59,-1,0,51,1,0,43,0,-1,43,-1,0,44,1,0,45,0,-1,45,-1,0,38,1,0,31,0,-1});
        expected.add(new byte[]{59,-1,0,51,1,0,43,0,-1,43,-1,0,44,1,0,45,0,-1,45,-1,0,37,1,0,29,0,-1});
        expected.add(new byte[]{59,-1,0,51,1,0,43,0,-1,43,-1,0,44,1,0,45,0,-1,45,-1,0,46,1,0,47,0,-1,47,-1,0,38,1,0,29,0,-1});
        expected.add(new byte[]{59,-1,0,51,1,0,43,0,-1,43,-1,0,42,1,0,41,0,-1,41,-1,0,33,1,0,25,0,-1});
        expected.add(new byte[]{59,-1,0,51,1,0,43,0,-1,43,-1,0,42,1,0,41,0,-1,41,-1,0,34,1,0,27,0,-1});
        expected.add(new byte[]{59,-1,0,51,1,0,43,0,-1,43,-1,0,36,1,0,29,0,-1});
        expected.add(new byte[]{59,-1,0,51,1,0,43,0,-1,43,-1,0,34,1,0,25,0,-1});

        System.out.println("multi-chain multi-directive multi-choice jump ");
        testPosition(moveGenerator.gerMovesFromPosition((byte)59, testBoard8, null), expected);




    }

    /**
     * Helper method, test functionality of the getMovesFromPositionTest
     */
    private void testTestMethod() {
        List<byte[]> test1 = new ArrayList<>();
        test1.add(new byte[]{1,2,3,4,5,6,7,8,9});
        test1.add(new byte[]{2,3,4,5,6,7,8,9,10});
        List<byte[]> test2 = new ArrayList<>();
        test2.add(new byte[]{2,3,4,5,6,7,8,9,10});
        test2.add(new byte[]{1,2,3,4,5,6,7,8,9});
        List<byte[]> test3 = new ArrayList<>();
        test3.add(new byte[]{2,3,4,5,6,7,8,9,10});
        test3.add(new byte[]{1,2,3,4,5,6,7,8,9});
        test3.add(new byte[]{1,2,3,4,5,6,7,8,9});
        List<byte[]> test4 = new ArrayList<>();
        test4.add(new byte[]{1,2,3,4,5,6,7,8,9});
        test4.add(new byte[]{1,2,3,4,5,6,7,8,9});
        List<byte[]> test5 = new ArrayList<>();
        test5.add(new byte[]{6,7,8,9,10,11,12,13,14});
        test5.add(new byte[]{6,7,8,9,10,11,12,13,14});


        System.out.println("test identical list should pass");
        testPosition(test1, test1);
        System.out.println("test two list with same elements in different order should pass");
        testPosition(test1, test2);
        System.out.println("different number of elements should fail");
        testPosition(test2, test3);
        System.out.println("one element matching other not should fail");
        testPosition(test2, test4);
        System.out.println("All element are different");
        testPosition(test2, test5);
    }

    /**
     * Helper method  for getMovesFromPositionTest compares given and expected array
     * @param givenResult
     * @param expectedResult
     */
    private void testPosition(List<byte[]> givenResult, List<byte[]> expectedResult){
        boolean allMovesFound = isAllMovesFound(expectedResult, givenResult);
        if(allMovesFound){
            allMovesFound = isAllMovesFound(givenResult, expectedResult);
        }
        if(allMovesFound){
            System.out.println("TEST OK");
        }else{
            System.out.println("TEST FAILED");
            System.out.println("Given length : " + givenResult.size() + " Expected length : " + expectedResult.size());
            for(byte[] byt : givenResult){
                for(byte b : byt){
                    System.out.print(b + ",");
                }
                System.out.println();
            }
        }
    }

    /**
     * helper method for test position -> actually compares arrays (one direction compare)
     * @param givenResult
     * @param expectedResult
     * @return
     */
    private boolean isAllMovesFound(List<byte[]> givenResult, List<byte[]> expectedResult) {
        boolean allMovesFound = true;
        for(byte[] expectedArray : givenResult){ //ned to find every single expected move
            //iter through given result
            boolean moveFound = false;
            for(byte[] givenArray : expectedResult){
                //compare byte to byte
                if(givenArray.length != expectedArray.length){
                    continue;
                }else{
                    boolean allMatching = true;
                    for(int i = 0; i< givenArray.length; i++){
                        if(givenArray[i] != expectedArray[i]){
                            allMatching = false;
                            break;
                        }
                    }
                    if(allMatching){
                        moveFound = true;
                    }
                }
            }
            if(!moveFound){
                allMovesFound = false;
                break;
            }
        }
        return allMovesFound;
    }

    /**
     * Test for method getCaptureEnemiesRoyal
     */
    public void getCaptureEnemiesRoyalTest(){
        MoveGenerator moveGenerator = new MoveGenerator();
        System.out.println("Test getCaptureEnemiesRoyal");
        byte[] result = moveGenerator.getCaptureEnemiesRoyal((byte)28, testBoardGetEnemiesRoyal,(byte)-2);
        byte[] expected = new byte[]{29,27,37,14};
        testSimpleArray(expected, result);
    }

    /**
     * test two simple arrays against each other (both direction)
     * @param expectedArray
     * @param givenArray
     */
    private void testSimpleArray(byte[] expectedArray, byte[] givenArray){

        //expected against given
        if(     simpleArrayComparison(expectedArray, givenArray) /*&&
                simpleArrayComparison(givenArray, expectedArray)*/){
            System.out.println("TEST OK");
        }else{
            System.out.println("TEST FAILED");
            System.out.print("\\__Given array : ");
            for(byte b : givenArray){
                System.out.print(b + ", ");
            }
            System.out.println();
            System.out.print("\\__Expected array : ");
            for(byte b : expectedArray){
                System.out.print(b + ", ");
            }
            System.out.println();
        }




    }
    /**
     * Unidirectional comparison of the byte arrays
     * @param expectedArray
     * @param givenArray
     * @return
     */
    private boolean simpleArrayComparison(byte[] expectedArray, byte[] givenArray) {
        for(byte b : expectedArray){
            boolean byteFound = false;
            for(byte by: givenArray){
                if(b == by){
                    byteFound = true;
                    break;
                }
            }
            if(!byteFound){
                return false;
            }
        }
        return true;
    }

    /**
     * Test of the method getAvailableLandingPositions
     */
    public void getAvailableLandingPositionsTest(){
        MoveGenerator moveGenerator = new MoveGenerator();
        System.out.println("Test 1 : 26, -1" );
        byte[] result = moveGenerator.getLandingLocations((byte)26,(byte)-1, testBoardGetAvailableLanding);
        byte[] expected = new byte[]{25};
        testSimpleArray(result, expected);

        System.out.println("Test 2 : 18, -9" );
        result = moveGenerator.getLandingLocations((byte)18,(byte)-9, testBoardGetAvailableLanding);
        expected = new byte[]{9,0};
        testSimpleArray(result, expected);

        System.out.println("Test 3 : 19, -8" );
        result = moveGenerator.getLandingLocations((byte)19,(byte)-8, testBoardGetAvailableLanding);
        expected = new byte[]{11,3};
        testSimpleArray(result, expected);

        System.out.println("Test 4 : 20, -7" );
        result = moveGenerator.getLandingLocations((byte)20,(byte)-7, testBoardGetAvailableLanding);
        expected = new byte[]{13,6};
        testSimpleArray(result, expected);

        System.out.println("Test 5 : 28, 1" );
        result = moveGenerator.getLandingLocations((byte)28,(byte)1, testBoardGetAvailableLanding);
        expected = new byte[]{};
        testSimpleArray(result, expected);

        System.out.println("Test 6 : 36, 9" );
        result = moveGenerator.getLandingLocations((byte)36,(byte)9, testBoardGetAvailableLanding);
        expected = new byte[]{45};
        testSimpleArray(result, expected);

        System.out.println("Test 7 : 35, 8" );
        result = moveGenerator.getLandingLocations((byte)35,(byte)8, testBoardGetAvailableLanding);
        expected = new byte[]{43,51,59};
        testSimpleArray(result, expected);

        System.out.println("Test 8 : 34, 7" );
        result = moveGenerator.getLandingLocations((byte)34,(byte)7, testBoardGetAvailableLanding);
        expected = new byte[]{41,48};
        testSimpleArray(result, expected);
    }

    public void getMovesFromPositionRoyalTest(){
        MoveGenerator moveGenerator = new MoveGenerator();
        List<byte[]> moves = moveGenerator.getMovesFromPositionRoyal((byte)60, testGetMovesFromPositionRoyal2,null);
        System.out.println(moves);
        for(byte[] ba : moves){
            for(byte b :ba){
                System.out.print(b + ", ");
            }
            System.out.println();
        }
    }
}
