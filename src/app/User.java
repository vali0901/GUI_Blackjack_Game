package app;

public class User {

    private final String username;
    private double balance;
    private final Stats userStats;

    public Stats getUserStats() {
        return userStats;
    }
    public User(String username) {
        this.username = username;
        this.userStats = new Stats();
        this.balance = 0;
    }

    public void setUserStats(double totalBet, double totalWin, int nrHands, int nrWins, int nrBj) {
        userStats.updateStats(totalBet, totalWin, nrHands, nrWins, nrBj);
    }

    public String getUsername() {
        return username;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance += balance;
    }
}
