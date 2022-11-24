package main;

import java.util.ArrayList;

public class Game {

    int playerId1;
    int playerId2;
    int shuffleSeed;
    int startingPlayer;
    HeroCard hero1;
    HeroCard hero2;
    ArrayList<Actions> actiuni;


    public Game(int playerId1, int playerId2, int shuffleSeed, int startingPlayer, HeroCard hero1, HeroCard hero2, ArrayList<Actions> actiuni) {
        this.playerId1 = playerId1;
        this.playerId2 = playerId2;
        this.shuffleSeed = shuffleSeed;
        this.startingPlayer = startingPlayer;
        this.hero1 = hero1;
        this.hero2 = hero2;
        this.actiuni = actiuni;
    }


    public int getPlayerId1() {
        return playerId1;
    }

    public void setPlayerId1(int playerId1) {
        this.playerId1 = playerId1;
    }

    public int getPlayerId2() {
        return playerId2;
    }

    public void setPlayerId2(int playerId2) {
        this.playerId2 = playerId2;
    }

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setShuffleSeed(int shuffleSeed) {
        this.shuffleSeed = shuffleSeed;
    }

    public ArrayList<Actions> getActiuni() {
        return actiuni;
    }

    public int getStartingPlayer() {
        return startingPlayer;
    }

    public void setStartingPlayer(int startingPlayer) {
        this.startingPlayer = startingPlayer;
    }
}
