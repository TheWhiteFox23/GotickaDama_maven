package cz.whiterabbit.elements.movegenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegularFinder implements Finder{
    private boolean log = false;

    @Override
    public List<byte[]> find(int position, byte[] board) {
        List<byte[]> moves = gerMovesFromPositionRegular((byte)position, board, null);
        return moves;
    }

    /**
     * Get moves from given position for regular peaces
     * @param position
     * @param board
     * @param initialMove
     * @return
     */
    protected List<byte[]> gerMovesFromPositionRegular(byte position, byte[] board, byte[] initialMove){
        List<byte[]> moves = new ArrayList<>();

        boolean positive;
        try{
            positive = MoveGeneratorUtilities.moveGeneratorUtilities().getPlayerType(position, board, initialMove);
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
                            List<byte[]> queryResult = gerMovesFromPositionRegular(capturedEnemies[i+1], board, newInitialMove);
                            //System.out.println(queryResult);
                            moves.addAll(gerMovesFromPositionRegular(capturedEnemies[i+1], board, newInitialMove));
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
                        moves.addAll(gerMovesFromPositionRegular(capturedEnemies[i+1], board, newMove));
                    }
                }
            }
        }
        return moves;
    }


    /**
     * Return array of the available surrounding (regular peace)
     * @param position
     * @param board
     * @param playerType
     * @return
     */
    protected byte[] getAvailableSurroundings(byte position, byte[] board, boolean playerType) {
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

        Arrays.sort(toReturn);

        return toReturn;
    }

    /**
     * Return array of all the enemies in the surrounding
     * @param availableSurroundings
     * @param board
     * @param positive
     * @return
     */
    protected byte[] getEnemies(byte[] availableSurroundings, byte[] board, boolean positive){
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

        Arrays.sort(toReturn);
        return  toReturn;
    }

    /**
     * Return only empty spaces in available surroundings
     * @param availableSurroundings
     * @param board
     * @return
     */
    protected byte[] getEmptySpaces(byte[] availableSurroundings, byte[] board){
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

    /**
     * Return position after capturing if enemy can be captured (valid for regular moves)
     * @param peacePosition
     * @param enemyPosition
     * @param board
     * @return
     */
    protected byte canBeCaptured(byte peacePosition, byte enemyPosition, byte[] board){
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
}
