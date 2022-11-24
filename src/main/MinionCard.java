package main;

import java.util.ArrayList;

public class MinionCard extends Card {
    int attackdamge;
    int health;

    public MinionCard(int mana, int attackdamge, int health, String description, ArrayList<String> colors, String name) {
        super(mana, description, colors, name);
        this.attackdamge = attackdamge;
        this.health = health;
    }

    public int getAttackdamge() {
        return attackdamge;
    }

    public void setAttackdamge(int attackdamge) {
        this.attackdamge = attackdamge;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
