package main;

import java.util.ArrayList;

public class HeroCard extends Card {


    static int health = 30;

    public HeroCard(int mana, String description, ArrayList<String> colors, String name, int health) {
        super(mana, description, colors, name);
        HeroCard.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        HeroCard.health = 30;
    }
}
