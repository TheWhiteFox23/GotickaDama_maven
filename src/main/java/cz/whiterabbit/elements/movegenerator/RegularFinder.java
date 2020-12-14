package cz.whiterabbit.elements.movegenerator;

import org.jetbrains.annotations.Nullable;

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
            System.out.println("unable to determinate player type from given values : " + e.getMessage());
            return null;
        }
        byte activePeace = 1;
        if(!positive)activePeace = -1;

        byte[] availableSurrounding = getAvailableSurroundings(position, board, positive);
        byte[] enemiesInSurrounding = getEnemies(availableSurrounding, board, positive);
        byte[] emptySpacesInSurrounding = getEmptySpaces(availableSurrounding, board);

        if(enemiesInSurrounding.length == 0){
            return manageNoCaptureEnemies(position, board, initialMove, moves, activePeace, emptySpacesInSurrounding);
        }else{
            byte[] capturedEnemies = getCaptureEnemies(enemiesInSurrounding, position, board);
            if(capturedEnemies.length == 0){
                return manageNoCaptureEnemies(position, board, initialMove, moves, activePeace, emptySpacesInSurrounding);
            }else{
                if(initialMove != null){
                    manageCaptureEnemies(position, board, initialMove, moves, activePeace, capturedEnemies);
                }else{
                    captureAllEnemies(position, board, moves, activePeace, capturedEnemies);
                }
            }
        }
        return moves;
    }

    /**
     * Helper method, add all capture enemies into the moves list
     * @param position
     * @param board
     * @param moves
     * @param activePeace
     * @param capturedEnemies
     */
    private void captureAllEnemies(byte position, byte[] board, List<byte[]> moves, byte activePeace, byte[] capturedEnemies) {
        for(int i = 0; i< capturedEnemies.length; i+=2){
            byte[] newMove = new byte[]{position, activePeace, 0, capturedEnemies[i], board[capturedEnemies[i]], 0, capturedEnemies[i+1], board[capturedEnemies[i+1]], activePeace};
            moves.addAll(gerMovesFromPositionRegular(capturedEnemies[i+1], board, newMove));
        }
    }

    /**
     * Merge the current capture with initial move and add into moves list
     * @param position
     * @param board
     * @param initialMove
     * @param moves
     * @param activePeace
     * @param capturedEnemies
     */
    private void manageCaptureEnemies(byte position, byte[] board, byte[] initialMove, List<byte[]> moves, byte activePeace, byte[] capturedEnemies) {
        boolean foundValidEnemy = false;
        for(int i = 0; i< capturedEnemies.length; i+=2){
            boolean alreadyCaptured = false;
            for(int j = 0; j< initialMove.length; j += 3) {
                if(capturedEnemies[i] == initialMove[j]){
                    alreadyCaptured = true;
                    break;
                }
            }
            if(!alreadyCaptured){
                foundValidEnemy = true;
                mergeNewMoveWithInitial(new byte[]{position, activePeace, 0, capturedEnemies[i], board[capturedEnemies[i]], 0, capturedEnemies[i + 1], board[capturedEnemies[i + 1]], activePeace}
                , board, initialMove, moves, capturedEnemies, i);
            }
        }
        if(!foundValidEnemy){
            moves.add(initialMove);
        }
    }

    private void mergeNewMoveWithInitial(byte[] newMove1, byte[] board, byte[] initialMove, List<byte[]> moves, byte[] capturedEnemies, int i) {
        byte[] newMove = newMove1;
        byte[] newInitialMove = new byte[initialMove.length + newMove.length];
        System.arraycopy(initialMove, 0, newInitialMove, 0, initialMove.length);
        System.arraycopy(newMove, 0, newInitialMove, initialMove.length, newMove.length);
        moves.addAll(gerMovesFromPositionRegular(capturedEnemies[i+1], board, newInitialMove));
    }

    /**
     * Add all simple moves into the moves list in case there are no enemies or non of them can be capture.
     * @param position
     * @param board
     * @param initialMove
     * @param moves
     * @param activePeace
     * @param emptySpacesInSurrounding
     * @return
     */
    @Nullable
    private List<byte[]> manageNoCaptureEnemies(byte position, byte[] board, byte[] initialMove, List<byte[]> moves, byte activePeace, byte[] emptySpacesInSurrounding) {
        if(emptySpacesInSurrounding.length == 0){
            if(initialMove == null){
                return null;
            }else{
                moves.add(initialMove);
                return moves;
            }
        }else {
            if (initialMove == null) {
                for (byte b : emptySpacesInSurrounding) {
                    moves.add(new byte[]{position, activePeace, 0, b, board[b], board[position]});
                }
            } else {
                moves.add(initialMove);
                return moves;
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
