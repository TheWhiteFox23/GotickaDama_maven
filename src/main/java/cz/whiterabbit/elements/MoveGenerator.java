package cz.whiterabbit.elements;

import java.util.ArrayList;
import java.util.List;


//todo: both methods can be refactored to be more simple, look at it after testing if there is still time
public class MoveGenerator {
    boolean log = false;

    public List<byte[]> gerMovesFromPosition(byte position, byte[] board, byte[] initialMove){
        //System.out.println("Begin search from position : " + position + " with initial move : " + initialMove);
        List<byte[]> moves = new ArrayList<>();
        //type of value to check (positive or negative)

        boolean positive;
        try{
            positive = getPlayerType(position, board, initialMove);
        }catch (Exception e){
            System.out.println("unable to determinate layer type from given values : " + e.getMessage());
            return null;
        }
        byte activePeace = 1;
        if(!positive)activePeace = -1;
        // getting peace surrounding
        byte[] availableSurrounding = getAvailableSurroundings(position, board, positive);

        byte[] enemiesInSurrounding = getEnemies(availableSurrounding, board, positive);
        byte[] emptySpacesInSurrounding = getEmptySpaces(availableSurrounding, board);
        if(enemiesInSurrounding.length == 0){
            if(log)System.out.println("No enemies in Surrounding");
            if(emptySpacesInSurrounding.length == 0){
                if(log)System.out.println("No empty spaces in surrounding");
                if(initialMove == null){
                    if(log)System.out.println("No initial move - return NULL");
                    return null;
                }else{
                    if(log)System.out.println("Initial move found - add initial move");
                    moves.add(initialMove);
                    return moves;
                }
            }else{
                if(log)System.out.println("Found empty spaces in surrounding");
                if(initialMove == null){
                    if(log)System.out.println("No initial move found");
                    for(byte b : emptySpacesInSurrounding){
                        if(log)System.out.println("Adding moves");
                        moves.add(new byte[]{position, activePeace, 0, b,board[b], board[position]});
                    }
                }else{
                    if(log)System.out.println("Initial Move found -> adding Initial Move");
                    moves.add(initialMove);
                    return moves;
                }
            }

        }else{
            if(log)System.out.println("Enemies found");
            byte[] capturedEnemies = getCaptureEnemies(enemiesInSurrounding, position, board);
            if(capturedEnemies.length == 0){
                if(log)System.out.println("No Enemies can Be Captured");
                if(emptySpacesInSurrounding.length == 0){
                    if(log)System.out.println("No empty spaces in surrounding");
                    if(initialMove == null){
                        if(log)System.out.println("figure cant move");
                        return null;
                    }else{
                        if(log)System.out.println("adding initial move");
                        moves.add(initialMove);
                        return moves;
                    }
                }else{
                    if(log)System.out.println("found empty spaces in surrounding");
                    if(initialMove == null){
                        if(log)System.out.println("No initial move");
                        for(byte b : emptySpacesInSurrounding){
                            if(log)System.out.println("adding moves");
                            moves.add(new byte[]{position, activePeace, 0, b, 0, activePeace});
                        }
                    }else{
                        if(log)System.out.println("Found initial move -> adding move");
                        moves.add(initialMove);
                    }
                    return moves;
                }
            }else{
                if(log)System.out.println("Found enemies that can be captured");
                //enemy position was already captured
                //look if enemy was already captured

                if(initialMove != null){
                    //todo problem is right here find it !!!!!
                    if(log)System.out.println("Initial move found -> searching enemies");
                    boolean foundValidEnemy = false;
                    for(int i = 0; i<capturedEnemies.length; i+=2){
                        boolean alreadyCaptured = false;
                        for(int j = 0; j< initialMove.length; j += 3) {
                            if(capturedEnemies[i] == initialMove[j]){
                                if(log)System.out.println("Enemy already captured");
                                alreadyCaptured = true;
                                break;
                            }
                        }
                        if(!alreadyCaptured){
                            foundValidEnemy = true;
                            if(log)System.out.println("Enemy wasn't already captured -> capturing enemy");
                            byte[] newMove = new byte[]{position, activePeace, 0,capturedEnemies[i], board[capturedEnemies[i]], 0, capturedEnemies[i+1], board[capturedEnemies[i+1]], activePeace };
                            byte[] newInitialMove = new byte[initialMove.length + newMove.length];
                            System.arraycopy(initialMove, 0, newInitialMove, 0, initialMove.length);;
                            System.arraycopy(newMove, 0, newInitialMove, initialMove.length, newMove.length);
                            //System.out.println();
                            //System.out.println("Asking for new query from new position");
                            List<byte[]> queryResult = gerMovesFromPosition(capturedEnemies[i+1], board, newInitialMove);
                            //System.out.println(queryResult);
                            moves.addAll(gerMovesFromPosition(capturedEnemies[i+1], board, newInitialMove));
                        }
                    }
                    if(!foundValidEnemy){
                        if(log)System.out.println("No valid enemies found -> inserting initial move");
                        moves.add(initialMove);
                    }
                }else{
                    if(log)System.out.println("No initial move found, generating initial moves");
                    for(int i = 0; i<capturedEnemies.length; i+=2){
                        byte[] newMove = new byte[]{position, activePeace, 0,capturedEnemies[i], board[capturedEnemies[i]], 0, capturedEnemies[i+1], board[capturedEnemies[i+1]], activePeace };
                        if(log)System.out.println("Asking for the query from the new point");
                        moves.addAll(gerMovesFromPosition(capturedEnemies[i+1], board, newMove));
                    }
                }
            }
        }
        return moves;
    }

    public boolean getPlayerType(byte position, byte[] board, byte[] initialMove) throws Exception {
        boolean positive = false;
        if(initialMove == null){
            if(board[position]>0){
                positive = true;
            }else if (board[position] < 0){
                positive = false;
            }else{
                //System.out.println("Box don't contains any play peace");
                throw new Exception("Box don't contains any play peace");
            }
        }else{
            if(initialMove.length > 1){
                if(board[initialMove[0]]>0){
                    positive = true;
                }else if (board[initialMove[0]]<0){
                    positive = false;
                }else{
                    throw new Exception("Invalid initial move, initial move cant be initialized from empty space");
                }
            }
        }
        return positive;
    }

    public byte[] getAvailableSurroundings(byte position, byte[] board, boolean playerType) {
        //array of the positions to check
        byte[] availableSurroundings = new byte[5];
        int arrayPointer = 0;

        byte[] toCheck = new byte[]{1,-1,7,8,9};
        if(!playerType)toCheck = new byte[]{1,-1,-7,-8,-9};
        for(byte b : toCheck){
            //check if surrounding is in corresponding level
            int levelChange = 0;
            if(position+b < 64 && position + b >=0){
                levelChange = Math.abs((position/8 - (position+b)/8));
            }else{
                continue;
            }
            if(position + b >= 0 && position+b <64 && (b == 1 || b == -1) && levelChange==0){
                availableSurroundings[arrayPointer] = (byte)(position+b);
                arrayPointer++;
            }else if(position + b >= 0 && position+b <64 && (b != 1 && b != -1) && levelChange==1){
                availableSurroundings[arrayPointer] = (byte)(position+b);
                arrayPointer++;
            }
        }

        byte[] toReturn = new byte[arrayPointer];
        System.arraycopy(availableSurroundings, 0, toReturn, 0, arrayPointer);

        return toReturn;
    }

    public byte[] getEnemies(byte[] availableSurroundings, byte[] board, boolean positive){
        byte[] enemiesArray = new byte[availableSurroundings.length];
        int arrayPointer = 0;
        for(byte b : availableSurroundings){
            //System.out.println("content of the tested box : "+ board[b]);
            if(positive){
                if(board[b] < 0){
                    enemiesArray[arrayPointer] = b;
                    arrayPointer++;
                }
            }else{
                if(board[b] > 0){
                    enemiesArray[arrayPointer] = b;
                    arrayPointer++;
                }
            }
        }

        //process Array
        byte[] toReturn = new byte[arrayPointer];
        System.arraycopy(enemiesArray,0, toReturn, 0 , arrayPointer);
        return  toReturn;
    }

    public byte[] getEmptySpaces(byte[] availableSurroundings, byte[] board){
        byte[] emptySurrounding = new byte[availableSurroundings.length];
        int arrayPointer = 0;
        for(byte b : availableSurroundings){
            if(board[b] == 0){
                emptySurrounding[arrayPointer] = b;
                arrayPointer ++;
            }
        }

        byte[] arrayToReturn = new byte[arrayPointer];
        System.arraycopy(emptySurrounding, 0, arrayToReturn, 0, arrayPointer);
        return  arrayToReturn;
    }

    public byte canBeCaptured(byte peacePosition, byte enemyPosition, byte[] board){
        //byte finalPosition = -1;
        int levelChangePeace = Math.abs(peacePosition/8 - enemyPosition/8);
        if(levelChangePeace == 0){
            byte newPosition = (byte)(enemyPosition + (enemyPosition - peacePosition));
            if(newPosition > 0 && newPosition < 64 && newPosition/8 == peacePosition/8){
                if(board[newPosition]==0){
                    return newPosition;
                }
            }
        }else if (levelChangePeace == 1){
            byte newPosition = (byte)(enemyPosition + (enemyPosition - peacePosition));
            if(newPosition >0 && newPosition < 64 && Math.abs(newPosition/8 - peacePosition/8)==2){
                if(board[newPosition] == 0){
                    return newPosition;
                }
            }
        }
        return -1;
    }

    /**
     * return array of all the enemies that can be captured in form byte[]{enemyBox, boxAfterCapture, enemyBox, boxAfterCapture,...}
     * @return byte[]{enemyBox, boxAfterCapture, enemyBox, boxAfterCapture,...}
     */
    private byte[] getCaptureEnemies(byte[] enemyArray, byte position, byte[] board){
        byte[] capturedEnemies = new byte[enemyArray.length*2];
        int arrayPointer = 0;
        for(byte b: enemyArray){
            byte afterCapture = canBeCaptured(position, b, board);
            if(afterCapture >= 0){
                capturedEnemies[arrayPointer] = b;
                arrayPointer++;
                capturedEnemies[arrayPointer] = afterCapture;
                arrayPointer++;
            }
        }

        //concat array
        byte[] toReturn = new byte[arrayPointer];
        System.arraycopy(capturedEnemies, 0, toReturn, 0, arrayPointer);

        return  toReturn;
    }


    /**
     * Get all possible moves from given position for royalPeaces
     * @param position peace position
     * @param board play board
     * @param initialMove move that lead to this position - if non than null
     * @return List<byte> representing all possible moves</>
     */
    public List<byte[]> getMovesFromPositionRoyal(byte position, byte[] board, byte[] initialMove){
        //logging
        if(log) System.out.println("Starting get moves from position ROYAL");

        List<byte[]> moves = new ArrayList<>();

        boolean playerType;
        try{
            playerType = getPlayerType(position,board,initialMove);
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

    /**
     * return array containing position of all the enemies that can be captured -  modified for royal Figures
     * @param position peace position
     * @param board playBoard
     * @return byte[] with position of the enemies and directions in format {enemy_0, direction_0, enemy_1, direction_1,... enemy_n, direction_n}
     */
    public byte[] getCaptureEnemiesRoyal(byte position, byte[] board, byte activePeace) {
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
    public byte[] getLandingLocations(byte position, byte d, byte[] board){
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

        return toReturn;
    }



}
