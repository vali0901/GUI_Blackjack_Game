package GUI;

import app.Database;
import app.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LogInWindow extends JFrame implements ActionListener {
    private final JButton submit;
    private final JButton cancel;
    private final JTextField username;
    private final JPasswordField password;
    public LogInWindow() {
        this.setTitle("Log in");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // text panel
        JPanel textPanel = new JPanel(new GridLayout(2, 2));

        JLabel nameLabel = new JLabel("Username:");
        nameLabel.setHorizontalAlignment(JLabel.LEFT);
        nameLabel.setVerticalAlignment(JLabel.CENTER);
        JLabel passLabel = new JLabel("Password:");
        passLabel.setHorizontalAlignment(JLabel.LEFT);
        passLabel.setVerticalAlignment(JLabel.CENTER);

        username = new JTextField("username");
        password = new JPasswordField("password");

        textPanel.add(nameLabel);
        textPanel.add(username);
        textPanel.add(passLabel);
        textPanel.add(password);

        this.add(textPanel, BorderLayout.CENTER);

        // buttons panel
        JPanel btnPanel = new JPanel(new GridLayout(1, 2));
        submit = new JButton("SUBMIT");
        submit.addActionListener(this);
        cancel = new JButton("CANCEL");
        cancel.addActionListener(this);
        btnPanel.add(submit);
        btnPanel.add(cancel);

        this.add(btnPanel, BorderLayout.SOUTH);

        this.setSize(400, 120);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == submit) {
            String username = this.username.getText();
            String password = new String(this.password.getPassword());

            if(Database.getDatabase().hasKeyPasswd(username)) {
                int helper = password.hashCode();
                if(String.valueOf(helper).equals(Database.getDatabase().getPasswdValue(username))) {
                    //log in successful
                    Main.setCurrUser(Database.getDatabase().getContentValue(username));
                    this.dispose();
                    JOptionPane.showMessageDialog(null, "Log in successful!", "Log in success", JOptionPane.PLAIN_MESSAGE);
                } else {
                    // try again!
                    JOptionPane.showMessageDialog(null, "Wrong password/username! Please try again!", "Log in error", JOptionPane.WARNING_MESSAGE);

                }
            } else {
                // user does not exist! create it first!
                JOptionPane.showMessageDialog(null, "User does not exists!", "Log in error", JOptionPane.WARNING_MESSAGE);
            }

        } else if(e.getSource() == cancel) {
            this.dispose();
        }
    }
}
