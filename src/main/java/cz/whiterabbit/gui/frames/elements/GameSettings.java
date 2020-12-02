package cz.whiterabbit.gui.frames.elements;

public class GameSettings {

    private PlayerOperator blackOperator = PlayerOperator.HUMAN_PLAYER;
    private PlayerOperator whiteOperator = PlayerOperator.HUMAN_PLAYER;
    private PlayerLevel blackPlayerLevel = PlayerLevel.EASY;
    private PlayerLevel whitePlayerLevel = PlayerLevel.EASY;

    public PlayerOperator getBlackOperator() {
        return blackOperator;
    }

    public void setBlackOperator(PlayerOperator blackOperator) {
        this.blackOperator = blackOperator;
    }

    public PlayerOperator getWhiteOperator() {
        return whiteOperator;
    }

    public void setWhiteOperator(PlayerOperator whiteOperator) {
        this.whiteOperator = whiteOperator;
    }

    public PlayerLevel getBlackPlayerLevel() {
        return blackPlayerLevel;
    }

    public void setBlackPlayerLevel(PlayerLevel blackPlayerLevel) {
        this.blackPlayerLevel = blackPlayerLevel;
    }

    public PlayerLevel getWhitePlayerLevel() {
        return whitePlayerLevel;
    }

    public void setWhitePlayerLevel(PlayerLevel whitePlayerLevel) {
        this.whitePlayerLevel = whitePlayerLevel;
    }

    @Override
    public String toString() {
        return "GameSettings{" +
                ", blackOperator=" + blackOperator +
                ", whiteOperator=" + whiteOperator +
                ", blackPlayerLevel=" + blackPlayerLevel +
                ", whitePlayerLevel=" + whitePlayerLevel +
                '}';
    }
}
