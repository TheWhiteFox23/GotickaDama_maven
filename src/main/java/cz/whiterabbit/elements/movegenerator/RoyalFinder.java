package cz.whiterabbit.elements.movegenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoyalFinder implements Finder {
    private boolean log = false;

    @Override
    public List<byte[]> find(int position, byte[] board) {
        List<byte[]> moves = new ArrayList<>();
        moves.addAll(getMovesFromPositionRoyal((byte)position,board, null));
        return moves;
    }

    /**
     * Get all possible moves from given position for royalPeaces
     * @param position peace position
     * @param board play board
     * @param initialMove move that lead to this position - if non than null
     * @return List<byte> representing all possible moves</>
     */
    protected List<byte[]> getMovesFromPositionRoyal(byte position, byte[] board, byte[] initialMove){
        //logging
        if(log) System.out.println("Starting get moves from position ROYAL");

        List<byte[]> moves = new ArrayList<>();

        boolean playerType;
        try{
            playerType = MoveGeneratorUtilities.moveGeneratorUtilities().getPlayerType(position,board,initialMove);
        }catch (Exception E){
            System.out.println("Unable to determinate active peace");
            return null;
        }
        byte activePeace = -2;
        if(playerType)activePeace = 2;

        //logging
        if(log) System.out.println("  \\__Getting player type nad active peace : " + activePeace);

        byte[] captureEnemiesInSurrounding = getCaptureEnemiesRoyal(position, board, activePeace);

        //logging
        if(log) System.out.println("  \\__Getting available enemies. Array length : " + captureEnemiesInSurrounding.length);
        if(captureEnemiesInSurrounding.length != 0){ //found valid enemy
            //logging
            if(log) System.out.println("     \\__Valid enemies found");
            boolean foundNonCapturedEnemies = false;


            for(int i = 0; i<captureEnemiesInSurrounding.length; i+=2 ){

                byte[] nonCapturedEnemies = filterCapturedEnemies(captureEnemiesInSurrounding,initialMove);

                //todo replace block with filterCapturedEnemies function (!!!!After method is covered with tests!!!!)
                if(initialMove != null){
                    //logging
                    if(log) System.out.println("        \\__initialMove != null searching already captured enemies");
                    boolean enemyAlreadyCaptured = false;
                    for(int j = 0; j< initialMove.length; j+=3){
                        //System.out.println("i : " + i);
                        //System.out.println("j : " + j);
                        if(initialMove[j]==captureEnemiesInSurrounding[i]){
                            //System.out.println("initial move length : " + initialMove.length);
                            //logging
                            if(log) System.out.println("        \\__found captured enemy, continue " + initialMove + "position : " +captureEnemiesInSurrounding[i]);
                            enemyAlreadyCaptured = true;
                            break;
                        }
                    }
                    if(enemyAlreadyCaptured){
                        continue;
                    }else{
                        foundNonCapturedEnemies = true;
                    }
                }else{
                    foundNonCapturedEnemies = true;
                }
                byte[] landingLocations = getLandingLocations(captureEnemiesInSurrounding[i], captureEnemiesInSurrounding[i+1], board);
                //logging
                if(log) System.out.println("        \\__search for available landing positions ");
                for(byte b: landingLocations){
                    byte[] newInitialMove = new byte[]{position, activePeace, 0, captureEnemiesInSurrounding[i], board[captureEnemiesInSurrounding[i]], 0, b, 0, activePeace};
                    if(initialMove != null){
                        //logging
                        if(log) System.out.println("        \\__merging new move with initial move ");
                        byte[] merge = new byte[initialMove.length+newInitialMove.length];
                        System.arraycopy(initialMove, 0, merge, 0, initialMove.length);
                        System.arraycopy(newInitialMove, 0, merge, initialMove.length, newInitialMove.length);
                        newInitialMove = merge;
                    }
                    //logging
                    if(log) System.out.println("        \\__asking for new query");
                    moves.addAll(getMovesFromPositionRoyal(b,board, newInitialMove));
                }
            }
            if(!foundNonCapturedEnemies){
                //logging
                if(log) System.out.println("        \\__all found enemies was already captured");
                moves.add(initialMove);
            }

            //initialize moves from every possible landing location
        }else{ // no valid enemy
            //logging
            if(log) System.out.println("        \\__no enemies found ");
            if(initialMove == null){
                //logging
                if(log) System.out.println("        \\__initial move is null ");
                byte[] directionsToTest = new byte[]{1,-1,7,-7,8,-8,9,-9};

                //logging
                if(log) System.out.println("        \\__adding available landing position in to move ");
                for(byte b : directionsToTest){
                    byte[] emptySpacesInDirections = getLandingLocations(position, b, board);
                    for(byte s:emptySpacesInDirections){
                        //logging
                        if(log) System.out.println("        \\__adding position");
                        moves.add(new byte[]{position, activePeace, 0, s,0, activePeace});
                    }
                }
            }else{
                //logging
                if(log) System.out.println("        \\__initial move not null, adding initial move");
                moves.add(initialMove);
            }
        }
        return moves;
    }

    /**
     * return array containing position of all the enemies that can be captured -  modified for royal Figures
     * @param position peace position
     * @param board playBoard
     * @return byte[] with position of the enemies and directions in format {enemy_0, direction_0, enemy_1, direction_1,... enemy_n, direction_n}
     */
    protected byte[] getCaptureEnemiesRoyal(byte position, byte[] board, byte activePeace) {
        //logging
        if(log) System.out.println("getCaptureEnemiesRoyal");
        byte[] enemiesCapture = new byte[16];
        int arrayPointer = 0;
        //modified flood-fill algorithm - for every possible direction goes until it founds space different from empty,
        //or until it is out of the board. If the different box is enemy, look if enemy can be captured, if not, move to another direction.
        byte[] directionsToCheck= new byte[]{-1,7,8,9,1,-9,-8,-7};
        for(byte b: directionsToCheck){
            //logging
            if(log) System.out.println(" \\__Initialize values --- direction : " + b);

            byte directionIncrement = 1;
            if(b == 1 || b == -1)directionIncrement = 0;
            byte currentPosition = position;
            boolean enemyFound = false;
            //logging
            if(log) System.out.println("  \\__Entering Loop");

            while(currentPosition+b < 64 && currentPosition+b >= 0 && Math.abs(currentPosition/8 - (currentPosition+b)/8) == directionIncrement){
                //logging
                if(log) System.out.println("      \\__increment position new position = " + (currentPosition + b));

                currentPosition += b;
                if(((activePeace < 0 && board[currentPosition] > 0) || (activePeace > 0 && board[currentPosition] < 0))&&!enemyFound){
                    //logging
                    if(log) System.out.println("         \\__found enemy on current position");

                    enemyFound = true;
                }else if(enemyFound && board[currentPosition] == 0){
                    //logging
                    if(log) System.out.println("         \\__zero after enemy writing to array and continue");

                    enemiesCapture[arrayPointer] = (byte)(currentPosition-b);
                    arrayPointer++;
                    enemiesCapture[arrayPointer] = b;
                    arrayPointer++;
                    break;
                }else if (enemyFound && board[currentPosition] != 0){
                    //logging
                    if(log) System.out.println("         \\__enemy can't be captured, break");

                    break;
                }
            }
        }

        //concatArray
        byte[] toReturn = new byte[arrayPointer];
        System.arraycopy(enemiesCapture, 0, toReturn, 0, arrayPointer);

        return toReturn;
    }

    /**
     * find available landing locations from given position in given direction. If enemy location is insert, return all
     * possible locations to land after jump. If peace position is given, return all empty spaces in given direction
     * @param position
     * @param d
     * @param board
     * @return
     */
    protected byte[] getLandingLocations(byte position, byte d, byte[] board){
        byte[] possibleLanding = new byte[10];
        int arrayPointer = 0;
        int jumpIncrement = 1;
        if(d == 1|| d == -1) jumpIncrement = 0;

        int currentPosition = position +d;
        while(currentPosition >= 0 && currentPosition < 64 && board[currentPosition]==0 && (Math.abs(currentPosition/8 - (currentPosition-d)/8)==jumpIncrement)){
            possibleLanding[arrayPointer] =  (byte)currentPosition;
            arrayPointer++;
            currentPosition += d;
        }

        //concatArray
        byte[] toReturn = new byte[arrayPointer];
        System.arraycopy(possibleLanding, 0, toReturn, 0, arrayPointer);

        Arrays.sort(toReturn);

        return toReturn;
    }

    /**
     * Filter already captured enemies out of the array
     * @param captureEnemiesInSurrounding
     * @param initialMove
     * @return
     */
    private byte[] filterCapturedEnemies(byte[] captureEnemiesInSurrounding, byte[] initialMove) {
        if(initialMove == null)return captureEnemiesInSurrounding;

        byte[] nonCapturedEnemies = new byte[captureEnemiesInSurrounding.length];
        int arrayPointer = 0;
        for(byte b : captureEnemiesInSurrounding){
            boolean found = false;
            for(int i = 0; i< initialMove.length; i+= 3){
                if(b == initialMove[i]){
                    found = true;
                    break;
                }
            }
            if(!found){
                nonCapturedEnemies[arrayPointer] = b;
                arrayPointer++;
            }
        }

        byte[] toReturn = new byte[arrayPointer];
        System.arraycopy(nonCapturedEnemies, 0, toReturn, 0, arrayPointer);
        return toReturn;
    }
}
