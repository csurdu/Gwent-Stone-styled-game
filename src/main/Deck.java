package main;

import java.util.ArrayList;

public class Deck {
    private int nrCardsInDeck;

    private ArrayList<Card> cards;

    public Deck(int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
        this.cards = new ArrayList<>();
    }

    public int getNrCardsInDeck() {
        return nrCardsInDeck;
    }

    public void setNrCardsInDeck(final int nrCardsInDeck) {
        this.nrCardsInDeck = nrCardsInDeck;
    }


    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(final ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void addMinionCard(MinionCard card) {
        this.cards.add(card);
    }

    @Override
    public String toString() {
        return "InfoInput{"
                + "nr_cards_in_deck="
                + nrCardsInDeck
                + ", decks="
                + cards
                + '}';
    }

}
