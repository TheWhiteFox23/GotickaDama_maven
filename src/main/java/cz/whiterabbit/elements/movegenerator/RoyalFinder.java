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


        byte[] captureEnemiesInSurrounding = getCaptureEnemiesRoyal(position, board, activePeace);

        if(captureEnemiesInSurrounding.length != 0){ //found valid enemy
            manageCaptureEnemies(position, board, initialMove, moves, activePeace, captureEnemiesInSurrounding);
        }else{
            if(initialMove == null){
                byte[] directionsToTest = new byte[]{1,-1,7,-7,8,-8,9,-9};

                for(byte b : directionsToTest){
                    byte[] emptySpacesInDirections = getLandingLocations(position, b, board);
                    for(byte s:emptySpacesInDirections){
                        moves.add(new byte[]{position, activePeace, 0, s,0, activePeace});
                    }
                }
            }else{
                moves.add(initialMove);
            }
        }
        return moves;
    }


    /**
     * Add capture moves for all possible enemies
     * @param position
     * @param board
     * @param initialMove
     * @param moves
     * @param activePeace
     * @param captureEnemiesInSurrounding
     */
    private void manageCaptureEnemies(byte position, byte[] board, byte[] initialMove, List<byte[]> moves, byte activePeace, byte[] captureEnemiesInSurrounding) {
        boolean foundNonCapturedEnemies = false;
        for(int i = 0; i< captureEnemiesInSurrounding.length; i+=2 ){
            if(initialMove != null){
                boolean enemyAlreadyCaptured = false;
                for(int j = 0; j< initialMove.length; j+=3){
                    if(initialMove[j]== captureEnemiesInSurrounding[i]){
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
            for(byte b: landingLocations){
                byte[] newInitialMove = new byte[]{position, activePeace, 0, captureEnemiesInSurrounding[i], board[captureEnemiesInSurrounding[i]], 0, b, 0, activePeace};
                if(initialMove != null){
                    byte[] merge = new byte[initialMove.length+newInitialMove.length];
                    System.arraycopy(initialMove, 0, merge, 0, initialMove.length);
                    System.arraycopy(newInitialMove, 0, merge, initialMove.length, newInitialMove.length);
                    newInitialMove = merge;
                }
                moves.addAll(getMovesFromPositionRoyal(b, board, newInitialMove));
            }
        }
        if(!foundNonCapturedEnemies){
            moves.add(initialMove);
        }
    }

    /**
     * return array containing position of all the enemies that can be captured -  modified for royal Figures
     * @param position peace position
     * @param board playBoard
     * @return byte[] with position of the enemies and directions in format {enemy_0, direction_0, enemy_1, direction_1,... enemy_n, direction_n}
     */
    protected byte[] getCaptureEnemiesRoyal(byte position, byte[] board, byte activePeace) {
        byte[] enemiesCapture = new byte[16];
        int arrayPointer = 0;
        byte[] directionsToCheck= new byte[]{-1,7,8,9,1,-9,-8,-7};
        for(byte b: directionsToCheck){

            byte directionIncrement = 1;
            if(b == 1 || b == -1)directionIncrement = 0;
            byte currentPosition = position;
            boolean enemyFound = false;

            while(currentPosition+b < 64 && currentPosition+b >= 0 && Math.abs(currentPosition/8 - (currentPosition+b)/8) == directionIncrement){


                currentPosition += b;
                if(((activePeace < 0 && board[currentPosition] > 0) || (activePeace > 0 && board[currentPosition] < 0))&&!enemyFound){

                    enemyFound = true;
                }else if(enemyFound && board[currentPosition] == 0){

                    enemiesCapture[arrayPointer] = (byte)(currentPosition-b);
                    arrayPointer++;
                    enemiesCapture[arrayPointer] = b;
                    arrayPointer++;
                    break;
                }else if (enemyFound && board[currentPosition] != 0){

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
