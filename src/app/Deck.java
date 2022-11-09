package app;

import java.util.*;

public class Deck {
    private final List<Card> deck;
    public boolean needsShuffle = false;

    public Deck(int numberOfDecks) {
        deck = new ArrayList<>(52);
        String[] symbols = new String[4];
        symbols[0] = "HEARTS";
        symbols[1] = "DIAMONDS";
        symbols[2] = "SPADES";
        symbols[3] = "CLUB";
        for(int k = 0; k < numberOfDecks; k++)
            for(int i = 0; i < 4; i ++)
                for(int j = 1; j <= 14; j++) {
                    if(j == 11)
                        continue;
                    deck.add(new Card(j, symbols[i]));
                }
        this.shuffleDeck();
    }

    private void shuffleDeck() {
        Random myRand = new Random();
        Collections.shuffle(deck, myRand);
        deck.add(deck.size() / 2 - deck.size() / 20 + myRand.nextInt(deck.size() / 10), new Card(0, "RED") );
    }

    public Card dealCard() {
        Card aux = this.deck.remove(0);
        if(aux.isRedCard()) {
            this.needsShuffle = true;
            return this.deck.remove(0);
        }
        return aux;
    }
}
