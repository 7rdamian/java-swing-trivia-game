package service;

import model.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// Handles writing to, reading from and viewing the leaderboard

public class Leaderboard {
    private String path = "data/leaderboard.txt";
    private File file;
    private List<Player> players;
    public int numberOfPlayers;

    // Constructor; loads leaderboard into memory on initiation
    public Leaderboard() {
        this.players = new ArrayList<>();
        this.file = new File(path);
        loadPlayers();
        sortPlayers();
        numberOfPlayers = players.size();
    }

    // Reads player names and scores from the leaderboard file
    public void loadPlayers() {
        StringBuilder sb = new StringBuilder();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] parts = line.split(" ");
                if (parts.length < 2) continue;
                String username = parts[0];
                String score = parts[1];

                Player p =  new Player();
                p.setName(username);
                p.setScore(score);
                players.add(p);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find " + path);
            e.printStackTrace();
        }
    }

    // Sorts players in descending order
    private void sortPlayers() {
        players.sort((p1, p2) -> {
            String[] parts1 = p1.getScore().split("/");
            double score1 = Double.parseDouble(parts1[0]) / Double.parseDouble(parts1[1]);

            String[] parts2 = p2.getScore().split("/");
            double score2 = Double.parseDouble(parts2[0]) / Double.parseDouble(parts2[1]);

            return Double.compare(score2, score1); // descending
        });
    }

    // Writes a name and score to the leaderboard file
    public void savePlayer(Player player) {
        try (FileWriter writer = new FileWriter(path, true)) {
            writer.write(player.getName() + " " +  player.getScore() + "\n");
            System.out.println("Player and score added!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        players.add(player);
        sortPlayers();
    }

    public List<Player> getPlayers() {
        return players;
    }

    // Prints leaderboard and aligns it properly
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(" ---------- Leaderboard ----------\n");
        for (Player player : players) {
            sb.append(String.format("%-15s | %s\n", player.getName(), player.getScore()));
        }
        sb.append("-------------------------------\n");

        return sb.toString();
    }
}
