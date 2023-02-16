package GUI;

import javax.swing.*;
import app.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame implements ActionListener {
    private final JButton startGame, logIn, createNewUser, logOut, exitGame, refresh, seeStats, deposit;
    private final JLabel userMsg;
    public MainWindow() {
        this.setTitle("Blackjack");
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // creating buttons panel
        startGame = new JButton("START GAME");
        logIn = new JButton("LOG IN");
        createNewUser = new JButton("CREATE NEW USER");
        deposit = new JButton("DEPOSIT");
        seeStats = new JButton("SEE STATS");
        logOut = new JButton("LOG OUT");
        exitGame = new JButton(("EXIT GAME"));

        startGame.setFocusable(false);
        logIn.setFocusable(false);
        createNewUser.setFocusable(false);
        deposit.setFocusable(false);
        seeStats.setFocusable(false);
        logOut.setFocusable(false);
        exitGame.setFocusable(false);


        startGame.addActionListener(this);
        logIn.addActionListener(this);
        createNewUser.addActionListener(this);
        deposit.addActionListener(this);
        seeStats.addActionListener(this);
        logOut.addActionListener(this);
        exitGame.addActionListener(this);

        startGame.setLayout(null);
        logIn.setLayout(null);
        createNewUser.setLayout(null);
        deposit.setLayout(null);
        seeStats.setLayout((null));
        logOut.setLayout(null);
        exitGame.setLayout(null);

        JPanel btnPanel = new JPanel(new GridLayout(5, 1, 0, 10));

        if(Main.getCurrUser() == null) {
            btnPanel.add(logIn);
            btnPanel.add(createNewUser);
        } else {
            btnPanel.add(startGame);
            btnPanel.add(deposit);
            btnPanel.add(seeStats);
            btnPanel.add(logOut);
        }
        if(Main.getCurrUser() == null)
            btnPanel.add(exitGame);

        btnPanel.setPreferredSize(new Dimension(300, 300));

        JPanel extra = new JPanel(new FlowLayout(FlowLayout.CENTER));
        extra.add(btnPanel);

        this.add(extra, BorderLayout.SOUTH);
        //creating another panel

        JPanel mainPanel = new JPanel(new FlowLayout());

        refresh = new JButton("Refresh");
        refresh.setFocusable(false);
        refresh.addActionListener(this);
        mainPanel.add(refresh);

        userMsg = new JLabel();
        setUserMsg();
        mainPanel.add(userMsg);

        this.add(mainPanel, BorderLayout.CENTER);

        this.setSize(1280, 720);
        this.setResizable(true);
        this.setVisible(true);
        this.setResizable(false);
    }


    private void setUserMsg() {
        if(Main.getCurrUser() == null)
            userMsg.setText("You need to log in!");
        else
            userMsg.setText("Hello, " + Main.getCurrUser().getUsername() + "!");
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startGame) {
            if(Main.getCurrUser() == null) {
                JOptionPane.showMessageDialog(null, "Not logged in! Please refresh page and log in ...", "Start game error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new GameWindow();
        } else if (e.getSource() == logIn) {
            if(Main.getCurrUser() != null) {
                JOptionPane.showMessageDialog(null, "Already logged in as " + Main.getCurrUser().getUsername() + "! Please refresh page ...", "Log in error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            new LogInWindow();
        } else if (e.getSource() == createNewUser) {
            if(Main.getCurrUser() != null) {
                JOptionPane.showMessageDialog(null, "You are logged in as " + Main.getCurrUser().getUsername() + "! In order to be able to create an account you have to be logged out ...", "Create new user error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new CreateUserWindow();
        } else if(e.getSource() == seeStats) {
            if(Main.getCurrUser() == null) {
                JOptionPane.showMessageDialog(null, "Not logged in! Please refresh page and log in ...", "See stats error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "Username: " + Main.getCurrUser().getUsername() + "\nCurrent balance: " + Main.getCurrUser().getBalance() + "\n" + Main.getCurrUser().getUserStats(), Main.getCurrUser().getUsername() + " stats", JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == logOut) {
            if(Main.getCurrUser() == null) {
                JOptionPane.showMessageDialog(null, "Not logged in! Please refresh page ...", "Log out error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Main.setCurrUser(null);
            JOptionPane.showMessageDialog(null, "Log out successful", "Log out success", JOptionPane.PLAIN_MESSAGE);
        } else if (e.getSource() == exitGame) {
            this.dispose();
            // load database to disk
            Database.getDatabase().writeToDiskDatabase();
        } else if(e.getSource() == deposit) {
            if(Main.getCurrUser() == null) {
                JOptionPane.showMessageDialog(null, "Not logged in! Please refresh page ...", "Deposit error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String sum = JOptionPane.showInputDialog("Insert how much money you want to deposit:");
            if(sum == null || sum.isEmpty()) {
                return;
            }

            try {
                Main.getCurrUser().setBalance(Double.parseDouble(sum));
            } catch (NumberFormatException f) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Deposit error", JOptionPane.WARNING_MESSAGE);
            }

        } else if (e.getSource() == refresh) {
            this.dispose();
            new MainWindow();
        }

    }
}
