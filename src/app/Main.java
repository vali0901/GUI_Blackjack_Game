package app;

import GUI.MainWindow;

public class Main {

    static private User currUser;

    public static User getCurrUser() {
        return currUser;
    }

    public static void setCurrUser(User currUser) {
        Main.currUser = currUser;
    }

    public static void main(String[] args) {
        new MainWindow();
    }

}
