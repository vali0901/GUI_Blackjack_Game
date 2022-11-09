package GUI;

import app.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import static GUI.MainWindow.currUser;

public class GameWindow extends JFrame implements ActionListener {

    private final User currUser;
    public Deck deck;
    private JButton doubleBtn, hit, stay, startNewRound, dealerControl, exit;
    private JTextArea playerTextArea, dealerTextArea;
    JPanel playerPanel, dealerPanel, buttonPanel;
    private Hand playerHand, dealerHand;

    public GameWindow() {
        currUser = Main.getCurrUser();

        this.setTitle("Blackjack");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(1280, 720);

        if(currUser.getBalance() <= 0) {
            JOptionPane.showMessageDialog(null, "Not enough balance! Current balance: " + currUser.getBalance(), "Betting error", JOptionPane.WARNING_MESSAGE);
            this.dispose();
            return;
        }

        Integer[] options = {1, 2, 4, 8};
        int option = JOptionPane.showOptionDialog(null, "Insert the number of decks you want to play with: ", "Start game",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, String.valueOf(1));
        if(option < 0 || option > 3)
            return;
        deck = new Deck(options[option]);

        String bet = JOptionPane.showInputDialog("Place your bet: (current balance: " + currUser.getBalance() + ")");
        if(bet == null || bet.isEmpty()) {
            this.dispose();
            return;
        }

        if(Float.parseFloat(bet) <= 0) {
            JOptionPane.showMessageDialog(null, "Bet must be positive!", "Betting error", JOptionPane.WARNING_MESSAGE);
            this.dispose();
            return;
        }

        if(Float.parseFloat(bet) > currUser.getBalance()) {
            JOptionPane.showMessageDialog(null, "Not enough balance! Current balance: " + currUser.getBalance(), "Betting error", JOptionPane.WARNING_MESSAGE);
            this.dispose();
            return;
        }

        try {
            playerHand = new Hand(Float.parseFloat(bet), deck);
        } catch (NumberFormatException f) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Betting error", JOptionPane.WARNING_MESSAGE);
            this.dispose();
            return;
        }

        dealerHand = new Hand(0, deck);

        currUser.setBalance(-Float.parseFloat(bet));

        JPanel table = new JPanel(new BorderLayout());

        dealerTextArea = new JTextArea();
        dealerTextArea.setText(dealerHand.getDealerCardsBeforeShowdown());
        dealerTextArea.setLineWrap(true);
        dealerTextArea.setEditable(false);
        dealerTextArea.setName("Dealer's Cards");
        dealerTextArea.setBackground(Color.LIGHT_GRAY);
        dealerTextArea.setPreferredSize(new Dimension(200, 200));
        dealerTextArea.setLocation(540, 100);

        dealerPanel = new JPanel();
        dealerPanel.add(dealerTextArea);

        playerTextArea = new JTextArea();
        playerTextArea.setText("Current user: " + currUser.getUsername() + "\nCurrent balance: " + currUser.getBalance() + "\n\n" + playerHand.getScoreToString() + playerHand.getCards());
        playerTextArea.setLineWrap(true);
        playerTextArea.setEditable(false);
        playerTextArea.setName("Player's Cards");
        playerTextArea.setBackground(Color.LIGHT_GRAY);
        playerTextArea.setPreferredSize(new Dimension(200, 300));
        playerTextArea.setLocation(540, 420);

        playerPanel = new JPanel();
        playerPanel.add(playerTextArea);

        doubleBtn = new JButton("DOUBLE");
        hit = new JButton("HIT");
        stay = new JButton("STAY");
        startNewRound = new JButton(("NEW ROUND"));
        dealerControl = new JButton("SEE DEALER'S CARDS");
        exit = new JButton("EXIT GAME");

        doubleBtn.setFocusable(false);
        hit.setFocusable(false);
        stay.setFocusable(false);
        startNewRound.setFocusable(false);
        dealerControl.setFocusable(false);
        exit.setFocusable(false);

        doubleBtn.addActionListener(this);
        hit.addActionListener(this);
        stay.addActionListener(this);
        startNewRound.addActionListener(this);
        dealerControl.addActionListener(this);
        exit.addActionListener(this);

        buttonPanel = new JPanel();

        buttonPanel.add(doubleBtn);
        buttonPanel.add(hit);
        buttonPanel.add(stay);
        buttonPanel.add(startNewRound);
        buttonPanel.add(dealerControl);

        if(playerHand.getBet() > currUser.getBalance())
            doubleBtn.setEnabled(false);

        startNewRound.setEnabled(false);
        dealerControl.setEnabled(false);
        exit.setEnabled(false);

        JPanel exitPanel = new JPanel();
        exitPanel.add(exit);

        table.add(dealerPanel, BorderLayout.NORTH);
        table.add(playerPanel, BorderLayout.SOUTH);
        table.add(buttonPanel, BorderLayout.CENTER);
        table.add(exitPanel, BorderLayout.WEST);

        this.add(table);
        this.setResizable(false);
        this.setVisible(true);

        if(playerHand.isBlackJack())
            dealerShowdown();
    }

    private void newRound() {
        if(currUser.getBalance() <= 0) {
            JOptionPane.showMessageDialog(null, "Not enough balance! Current balance: " + currUser.getBalance(), "Betting error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(deck.needsShuffle) {
            JOptionPane.showMessageDialog(null, "The deck needs to be shuffled! Press Ok to continue", "Shuffle needed", JOptionPane.PLAIN_MESSAGE);
            Integer[] options = {1, 2, 4, 8};
            int option = JOptionPane.showOptionDialog(null, "Insert the number of decks you want to play with: ", "Start game",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, String.valueOf(1));
            if(option >= 0 && option <= 3)
                deck = new Deck(options[option]);
        }

        String bet = JOptionPane.showInputDialog("Place your bet: (current balance: " + currUser.getBalance() + ")");
        if(bet == null || bet.isEmpty()) {
            return;
        }

        if(Float.parseFloat(bet) <= 0) {
            JOptionPane.showMessageDialog(null, "Bet must be positive!", "Betting error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(Float.parseFloat(bet) > currUser.getBalance()) {
            JOptionPane.showMessageDialog(null, "Not enough balance! Current balance: " + currUser.getBalance(), "Betting error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            playerHand = new Hand(Float.parseFloat(bet), deck);
        } catch (NumberFormatException f) {
            JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Betting error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        currUser.setBalance(-Float.parseFloat(bet));

        dealerHand = new Hand(0, deck);

        dealerTextArea.setText(dealerHand.getDealerCardsBeforeShowdown());
        playerTextArea.setText("Current user: " + currUser.getUsername() + "\nCurrent balance: " + currUser.getBalance() + "\n\n" + playerHand.getScoreToString() + playerHand.getCards());



        doubleBtn.setEnabled(true);
        hit.setEnabled(true);
        stay.setEnabled(true);

        startNewRound.setEnabled(false);
        dealerControl.setText("SEE DEALER'S CARDS");
        dealerControl.setEnabled(false);
        exit.setEnabled(false);

        if(playerHand.getBet() > currUser.getBalance())
            doubleBtn.setEnabled(false);

        if(playerHand.isBlackJack())
            dealerShowdown();
    }

    private void dealerShowdown(){
        stay.setEnabled(false);
        hit.setEnabled(false);
        doubleBtn.setEnabled(false);

       dealerControl.setEnabled(true);
    }

    private void getConclusion() {
        if(dealerHand.isBlackJack() && playerHand.isBlackJack()) {
            JOptionPane.showMessageDialog(null, "Tie! Dealer had blackjack, money back ...", "Round ended", JOptionPane.PLAIN_MESSAGE);
            currUser.setBalance(playerHand.getBet());
            currUser.setUserStats(playerHand.getBet(), playerHand.getBet(), 1, 0, 0);
        } else if(dealerHand.isBlackJack() && !playerHand.isBlackJack()  && playerHand.getScore() < 22) {
            JOptionPane.showMessageDialog(null, "Lose! Dealer had blackjack ...", "Round ended", JOptionPane.PLAIN_MESSAGE);
            currUser.setUserStats(playerHand.getBet(), 0, 1, 0, 0);
        } else if(playerHand.isBlackJack()) {
            JOptionPane.showMessageDialog(null, "Blackjack! Payed 3 to 2 ...", "Round ended", JOptionPane.PLAIN_MESSAGE);
            currUser.setBalance(playerHand.getBet() * 5 / 2);
            currUser.setUserStats(playerHand.getBet(), playerHand.getBet() * 5 / 2, 1, 1, 1);
        } else if(playerHand.getScore() > 21) {
            JOptionPane.showMessageDialog(null, "Lose! You busted ...", "Round ended", JOptionPane.PLAIN_MESSAGE);
            currUser.setUserStats(playerHand.getBet(), 0, 1, 0, 0);
        } else if(playerHand.getScore() <= 21) {
            if(playerHand.getScore() > dealerHand.getScore() && dealerHand.getScore() <= 21) {
                JOptionPane.showMessageDialog(null, "Win! Payed 2 to 1 ...", "Round ended", JOptionPane.PLAIN_MESSAGE);
                currUser.setBalance(playerHand.getBet() * 2);
                currUser.setUserStats(playerHand.getBet(), playerHand.getBet() * 2, 1, 1, 0);
            } else if(playerHand.getScore() == dealerHand.getScore() && dealerHand.getScore() <= 21) {
                JOptionPane.showMessageDialog(null, "Tie! Money back ...", "Round ended", JOptionPane.PLAIN_MESSAGE);
                currUser.setBalance(playerHand.getBet());
                currUser.setUserStats(playerHand.getBet(), playerHand.getBet(), 1, 0, 0);
            } else if(playerHand.getScore() < dealerHand.getScore() && dealerHand.getScore() <= 21) {
                JOptionPane.showMessageDialog(null, "Lose! Dealer wins ...", "Round ended", JOptionPane.PLAIN_MESSAGE);
                currUser.setUserStats(playerHand.getBet(), 0, 1, 0, 0);
            } else if(dealerHand.getScore() > 21) {
                JOptionPane.showMessageDialog(null, "Win! Dealer busted, payed 2 to 1 ...", "Round ended", JOptionPane.PLAIN_MESSAGE);
                currUser.setBalance(playerHand.getBet() * 2);
                currUser.setUserStats(playerHand.getBet(), playerHand.getBet() * 2, 1, 1, 0);
            }
        }
        playerTextArea.setText("Current user: " + currUser.getUsername() + "\nCurrent balance: " + currUser.getBalance() + "\n\n" + playerHand.getScoreToString() + playerHand.getCards());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == doubleBtn) {
            currUser.setBalance(-playerHand.getBet());
            playerHand.setBet(playerHand.getBet());
            playerHand.dealCard(deck);
            playerTextArea.setText("Current user: " + currUser.getUsername() + "\nCurrent balance: " + currUser.getBalance() + "\n\n" + playerHand.getScoreToString() + playerHand.getCards());
            dealerShowdown();
        } else if(e.getSource() == hit){
            doubleBtn.setEnabled(false);
            playerHand.dealCard(deck);
            playerTextArea.setText("Current user: " + currUser.getUsername() + "\nCurrent balance: " + currUser.getBalance() + "\n\n" + playerHand.getScoreToString() + playerHand.getCards());
            if(playerHand.getScore() >= 21) {
                dealerShowdown();
            }
        } else if(e.getSource() == stay) {
            dealerShowdown();
        } else if(e.getSource() == startNewRound) {
            newRound();
        } else if(e.getSource() == dealerControl) {
            if(dealerControl.getText().equals("SEE DEALER'S CARDS")) {
                dealerTextArea.setText(dealerHand.getScoreToString() + dealerHand.getCards());
                if(playerHand.isBlackJack() || dealerHand.isBlackJack()) {
                    dealerControl.setEnabled(false);
                    startNewRound.setEnabled(true);
                    exit.setEnabled(true);
                    getConclusion();
                } else if(dealerHand.getScore() < 17 && playerHand.getScore() < 22)
                    dealerControl.setText("DEALER HITTING...");
                else {
                    dealerControl.setEnabled(false);
                    startNewRound.setEnabled(true);
                    exit.setEnabled(true);
                    getConclusion();
                }
            } else if(dealerHand.getScore() < 17){
                dealerHand.dealCard(deck);
                dealerTextArea.setText(dealerHand.getScoreToString() + dealerHand.getCards());
                if(dealerHand.getScore() >= 17) {
                    dealerControl.setEnabled(false);
                    startNewRound.setEnabled(true);
                    exit.setEnabled(true);
                    getConclusion();
                }
            }

        } else if(e.getSource() == exit) {
            this.dispose();
            Database.getDatabase().addContent(currUser.getUsername(), currUser);
            Database.getDatabase().writeToDiskDatabase();
            //load into database
        }
    }
}
