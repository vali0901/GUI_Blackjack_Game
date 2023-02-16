package app;

public class Stats {
    private double totalBet, totalWin;
    private int nrHands, nrWins, nrBj;

    public Stats() {
        totalWin = totalBet = 0;
        nrHands = nrWins = nrBj = 0;
    }

    public Stats(Stats stats) {
        this.totalBet = stats.totalBet;
        this.totalWin = stats.totalWin;
        this.nrHands = stats.nrHands;
        this.nrWins = stats.nrWins;
        this.nrBj = stats.nrBj;
    }

    public double getTotalBet() {
        return totalBet;
    }

    public double getTotalWin() {
        return totalWin;
    }

    public int getNrHands() {
        return nrHands;
    }

    public int getNrWins() {
        return nrWins;
    }

    public int getNrBj() {
        return nrBj;
    }

    public void updateStats(double totalBet, double totalWin, int nrHands, int nrWins, int nrBj) {
        this.totalBet += totalBet;
        this.totalWin += totalWin;
        this.nrHands += nrHands;
        this.nrWins += nrWins;
        this.nrBj += nrBj;
    }

    private double getTotalBalance() {
        return - totalBet + totalWin;
    }

    private double getAvgBet() {
        return totalBet / (nrHands == 0 ? 1 : nrHands);
    }

    private double getWinPercentage() {
        return (float)nrWins / (nrHands == 0 ? 1 : nrHands) * 100;
    }

    @Override
    public String toString() {
        return "Money bet: " + totalBet + "\nMoney won: " + totalWin + "\nOverall balance: " + getTotalBalance() + "\nAverage bet per hand: " + String.format("%.2f", getAvgBet()) + "\n\nNumber of hands played: " + nrHands + "\nNumber of won hands: " + nrWins + "\nNumber of blackjacks: " + nrBj + "\nWin percentage: " +String.format("%.2f",  getWinPercentage() ) + "%";
    }
}
