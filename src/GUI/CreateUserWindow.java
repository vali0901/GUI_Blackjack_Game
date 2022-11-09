package GUI;

import app.Database;
import app.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUserWindow extends JFrame implements ActionListener {
    private final JButton create;
    private final JButton cancel;
    private final JTextField username;
    private final JPasswordField password;
    public CreateUserWindow() {
        this.setTitle("Create New User");
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
        create = new JButton("CREATE");
        create.addActionListener(this);
        cancel = new JButton("CANCEL");
        cancel.addActionListener(this);
        btnPanel.add(create);
        btnPanel.add(cancel);

        this.add(btnPanel, BorderLayout.SOUTH);

        this.setSize(400, 120);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == create) {
            String username = this.username.getText();
            String password = new String(this.password.getPassword());

            if(Database.getDatabase().hasKeyPasswd(username)) {
                JOptionPane.showMessageDialog(null, "Username already exists!", "Create user error", JOptionPane.WARNING_MESSAGE);
            } else {
                User new_user = new User(username);

                Database.getDatabase().addPasswd(username, String.valueOf(password.hashCode()));
                Database.getDatabase().addContent(username, new_user);

                this.dispose();
                JOptionPane.showMessageDialog(null, "User " + username + " created!", "Create user success", JOptionPane.PLAIN_MESSAGE);
            }

        } else if(e.getSource() == cancel) {
            this.dispose();
        }
    }

}
