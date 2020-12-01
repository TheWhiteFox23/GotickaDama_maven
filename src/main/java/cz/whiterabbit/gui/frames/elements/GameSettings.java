package cz.whiterabbit.gui.frames.elements;

public class GameSettings {

    private String blackPlayerName = "player_2";
    private String whitePlayerName = "player_1";
    private PlayerOperator blackOperator = PlayerOperator.HUMAN_PLAYER;
    private PlayerOperator whiteOperator = PlayerOperator.COMPUTER_MINIMAX;
    private PlayerLevel blackPlayerLevel = PlayerLevel.MEDIUM;
    private PlayerLevel whitePlayerLevel = PlayerLevel.MEDIUM;

    public String getBlackPlayerName() {
        return blackPlayerName;
    }

    public void setBlackPlayerName(String blackPlayerName) {
        this.blackPlayerName = blackPlayerName;
    }

    public String getWhitePlayerName() {
        return whitePlayerName;
    }

    public void setWhitePlayerName(String whitePlayerName) {
        this.whitePlayerName = whitePlayerName;
    }

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
                "blackPlayerName='" + blackPlayerName + '\'' +
                ", whitePlayerName='" + whitePlayerName + '\'' +
                ", blackOperator=" + blackOperator +
                ", whiteOperator=" + whiteOperator +
                ", blackPlayerLevel=" + blackPlayerLevel +
                ", whitePlayerLevel=" + whitePlayerLevel +
                '}';
    }
}
