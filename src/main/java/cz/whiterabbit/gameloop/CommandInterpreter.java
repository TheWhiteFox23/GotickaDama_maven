package cz.whiterabbit.gameloop;

import java.util.Comparator;

@Deprecated
public class CommandInterpreter {
    CommandListener CommandListener;

    public CommandInterpreter(){

    }

    public void InterpretCommand(String Command){

    }

    public cz.whiterabbit.gameloop.CommandListener getCommandListener() {
        return CommandListener;
    }

    public void setCommandListener(cz.whiterabbit.gameloop.CommandListener commandListener) {
        CommandListener = commandListener;
    }
}
