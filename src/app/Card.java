package app;

public class Card {
    private final int number;
    private final String symbol;

    public Card(int number, String symbol) {
        this.number = number;
        this.symbol = symbol;
    }

    public int getNumber() {
        return number;
    }

    public boolean isRedCard() {
        return this.getNumber() == 0;
    }
    public String toString() {
        return Integer.valueOf(number).toString() + " " + symbol;
    }
}
