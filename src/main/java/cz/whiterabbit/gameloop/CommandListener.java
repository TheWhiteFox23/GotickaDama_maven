package cz.whiterabbit.gameloop;

public interface CommandListener {
    void onChangeSettings();
    void onPrintAllMoves();
    void onPrintBestMove();
    void onGameResume();
    void onGameRestart();
}
