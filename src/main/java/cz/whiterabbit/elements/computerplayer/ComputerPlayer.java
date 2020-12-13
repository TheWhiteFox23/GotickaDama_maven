package cz.whiterabbit.elements.computerplayer;

public interface ComputerPlayer {
    byte[] chooseMove(byte[] state, boolean player);

    void setDifficulty(int difficulty);
}
