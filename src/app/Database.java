package app;

import java.io.*;
import java.util.*;

public class Database {
    static final Database database = new Database();
    private HashMap<String, String> passwd;
    private HashMap<String , User> content;

    public void addPasswd(String key, String value) {
        passwd.put(key, value);
    }

    public void addContent(String key, User value) {
        content.put(key, value);
    }

    public String getPasswdValue(String key) {
        return this.passwd.get(key);
    }

    public User getContentValue(String key) {
        return this.content.get(key);
    }

    public boolean hasKeyPasswd(String key) {
        return passwd.containsKey(key);
    }

    private Database() {
        this.loadDatabase();
    }

    public static Database getDatabase() {
        return database;
    }

    private void loadDatabase() {
        this.content = new HashMap<>();
        this.passwd = new HashMap<>();

        BufferedReader myReader;

        try {
            myReader = new BufferedReader(new FileReader(".passwd"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String line;
        try {
            line = myReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(line != null) {
            //line = line.replace('\n', '\0');
            String[] group = line.split(":");
            passwd.put(group[0], group[1]);

            try {
                line = myReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            myReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            myReader = new BufferedReader(new FileReader(".database"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            line = myReader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(line != null) {
            String[] group = line.split(":");
            User helper = new User(group[0]);
            helper.setBalance(Double.parseDouble(group[1]));
            helper.setUserStats(Double.parseDouble(group[2]), Double.parseDouble(group[3]), Integer.parseInt(group[4]), Integer.parseInt(group[5]), Integer.parseInt(group[6]));
            content.put(group[0], helper);

            try {
                line = myReader.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            myReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeToDiskDatabase() {
        FileWriter myWriter;
        try {
            myWriter = new FileWriter(".passwd");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AbstractSet<String> users = (AbstractSet<String>) passwd.keySet();

        for(String user : users) {
            try {
                myWriter.write(user + ":" + passwd.get(user) + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            myWriter = new FileWriter(".database");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(String user : users) {
            try {
                Stats helper = content.get(user).getUserStats();
                myWriter.write(user + ":" + content.get(user).getBalance() + ":" + helper.getTotalBet() + ":" + helper.getTotalWin() + ":" + helper.getNrHands() + ":" + helper.getNrWins() + ":" + helper.getNrBj() + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
