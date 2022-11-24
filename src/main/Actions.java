package main;

public class Actions {
    String command;
    int playerId;

    public Actions(String command, int playerId) {
        this.command = command;
        this.playerId = playerId;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
}
