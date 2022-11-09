package app;

import java.util.ArrayList;

public class Hand {
    private double bet;
    public ArrayList<Card> cards;

    public Hand(float bet, Deck deck) {
        cards = new ArrayList<>(2);
        cards.add(deck.dealCard());
        cards.add(deck.dealCard());
        this.bet = bet;
    }

    public double getBet() {
        return this.bet;
    }

    public void setBet(double bet) {
        this.bet += bet;
    }
    public boolean isBlackJack() {
        return this.cards.size() == 2 && this.getScore() == 21;
    }
    public void dealCard(Deck deck) {
        this.cards.add(deck.dealCard());
    }

    public int getScore() {
        int sum = 0;
        boolean has_ace = false;
        for(Card card : cards) {
            if(card.getNumber() == 1)
                has_ace = true;
            sum += Math.min(card.getNumber(), 10);
        }
        return has_ace && sum <= 11 ? sum + 10 : sum;
    }

    public String getScoreToString() {
        if(getScore() <= 21) {
            if(cards.size() == 2 && getScore() == 21)
                return "Score: 21 (Blackjack!)\n";
            return "Score: " + getScore() + "\n";
        }
        return "Score: " + getScore() + " (Bust)\n";
    }

    public String getDealerCardsBeforeShowdown() {
        return  cards.get(0) + "\nunknown\n";
    }

    public String getCards() {
        StringBuilder aux = new StringBuilder();
        for (Card card : this.cards) aux.append(card).append("\n");
        return aux.toString();
    }
}
