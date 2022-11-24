package main;

import fileio.DecksInput;
import fileio.Input;

public class DeckSetter {
    DecksInput deck1;
    DecksInput deck2;

    public void DeckSetter(DecksInput deck1, DecksInput deck2, Input inputData){
        this.deck1.setDecks(inputData.getPlayerOneDecks().getDecks());
        this.deck1.setNrDecks((inputData.getPlayerOneDecks().getNrDecks()));
        this.deck1.setNrCardsInDeck(inputData.getPlayerOneDecks().getNrCardsInDeck());
        this.deck2.setDecks(inputData.getPlayerTwoDecks().getDecks());
        this.deck2.setNrDecks((inputData.getPlayerTwoDecks().getNrDecks()));
        this.deck2.setNrCardsInDeck(inputData.getPlayerTwoDecks().getNrCardsInDeck());
    }
}
