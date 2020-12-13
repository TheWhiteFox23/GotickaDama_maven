package cz.whiterabbit.elements.computerplayer;

import cz.whiterabbit.elements.MoveChecker;

import java.util.List;

/**
 * Computer player implementation. Choose move based on the minimax algorithm. Variable difficulty is equal of algorithm search depth.
 */
public class MinimaxComputerPlayer implements ComputerPlayer {
    private MoveChecker moveChecker;
    private int difficulty;
    private MovePicker movePicker;
    private StateEvaluator stateEvaluator;


    public MinimaxComputerPlayer() {
        this.moveChecker = new MoveChecker();
        this.difficulty = 7;
        this.movePicker = new RandomMovePicker();
        this.stateEvaluator = new SimpleEvaluator();
    }

    /**
     * Choose move based on the minimax algorithm. Choose randomly from the moves with equal evaluation
     * @param state initial state of the board
     * @param player maximizing player. (true == positive player)
     * @return byte[] representing the chosen move
     */
    @Override
    public byte[] chooseMove(byte[] state, boolean player) {
        ValueSearchResult value = getValue(state, player, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return movePicker.pickMove(value.moves, value.evaluations, value.value);
    }


    //todo split in to few separated method so it won't do so many things - need refactoring
    /**
     * Apply minimax on the list of moves and return value of the best move.
     * @param state Initial state of the board
     * @param player maximizing player
     * @param alfa value of minimal result
     * @param beta value of maximal result
     * @return value of the chosen move
     */
    protected ValueSearchResult getValue(byte[] state, boolean player, int alfa, int beta) {
        //init values
        List<byte[]> moves = moveChecker.getAllValidMoves(state, player);
        int[] evaluations = new int[moves.size()];

        //initialize value
        int value = Integer.MAX_VALUE;
        if(player) value = Integer.MIN_VALUE;

        //search
        for(int i = 0; i< moves.size(); i++){
            int eval = minimax(moveChecker.applyMove(state, moves.get(i)), difficulty, alfa, beta, !player, stateEvaluator);
            evaluations[i] = eval;
            if(!player && eval < value){
                value = eval;
                beta = eval;
            }else if (player && eval>value){
                value = eval;
                alfa = eval;
            }
            if(beta <= alfa)break;
        }
        return new ValueSearchResult(value, evaluations, moves);
    }

    /**
     * Minimax search of the best value. Return evaluation of the position based on the given depth.
     * @param state State of the board
     * @param depth Depth of the search
     * @param alfa Minimal found value
     * @param beta Maximal found value
     * @param activePlayer maximizingPlayer
     * @return evaluation of the position
     */
    protected int minimax(byte[] state, int depth,int alfa, int beta, boolean activePlayer, StateEvaluator stateEvaluator){
        if(depth == 0 ) {
            return stateEvaluator.evaluate(state);
        }

        List<byte[]> moves = moveChecker.getAllValidMoves(state, activePlayer);

        if(activePlayer){
            int max = Integer.MIN_VALUE;
            for(byte[] by: moves){
                int eval = minimax(moveChecker.applyMove(state, by), depth-1,alfa, beta, !activePlayer, stateEvaluator);
                max = Integer.max(max, eval);
                alfa = Integer.max(max, eval);
                if(beta<=alfa)break;
            }
            return max;
        }else{
            int min = Integer.MAX_VALUE;
            for(byte[] by: moves){
                int eval = minimax(moveChecker.applyMove(state, by), depth-1,alfa, beta, !activePlayer, stateEvaluator);
                min = Integer.min(min, eval);
                beta =  Integer.min(min, eval);
                if(beta <= alfa)break;
            }
            return min;
        }
    }

    //GETTERS AND SETTERS

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public MovePicker getMovePicker() {
        return movePicker;
    }

    public void setMovePicker(MovePicker movePicker) {
        this.movePicker = movePicker;
    }

    public StateEvaluator getStateEvaluator() {
        return stateEvaluator;
    }

    public void setStateEvaluator(StateEvaluator stateEvaluator) {
        this.stateEvaluator = stateEvaluator;
    }


    /**
     * Crate for find value method
     */
    class ValueSearchResult{
        int value;
        int[] evaluations;
        List<byte[]> moves;

        public ValueSearchResult(int value, int[] evaluations, List<byte[]> moves){
            this.evaluations = evaluations;
            this.value = value;
            this.moves = moves;
        }

    }
}
