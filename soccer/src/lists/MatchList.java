package lists;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import models.*;

public class MatchList implements Menu {
    private DataStore data;
    private Scanner sc = new Scanner(System.in);
    private Random rand = new Random();

    public MatchList(DataStore data) {
        this.data = data;
    }

    @Override
    public void showList() {
        boolean exit = false;
        do {
            System.out.println("\n================= Match List =================");
            System.out.println("1. Add new Match");
            System.out.println("2. Show matches schedule/results");
            System.out.println("3. Play or edit a match");
            System.out.println("4. Delete a match");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int option = getIntInput();
            switch (option) {
                case 1: add(); break;
                case 2: viewData(); break;
                case 3: editStats(); break;
                case 4: delete(); break;
                case 5: exit = true; break;
                default: System.out.println("Invalid choice. Please try again.");
            }
        } while (!exit);
    }

    @Override
    public void add() {
        if (data.teams.isEmpty()) {
            System.out.println("No teams available. Add teams first.");
            return;
        }
        if (data.leagues.isEmpty()) {
            System.out.println("No leagues available. Add league first.");
            return;
        }
        System.out.println("Choose a league:");
        for (int i = 0; i < data.leagues.size(); i++) {
            System.out.println((i + 1) + ". " + data.leagues.get(i).getName());
        }
        int leagueChoice = getIntInput();
        if (!isValidLeagueNumber(leagueChoice)) {
            System.out.println("Invalid League number. Match not added.");
            return;
        }
        League selectedLeague = data.leagues.get(leagueChoice - 1);

        System.out.println("\nAvailable Teams:");
        for (int i = 0; i < selectedLeague.getTeams().size(); i++) {
            System.out.println((i + 1) + ". " + selectedLeague.getTeams().get(i).getName());
        }
        System.out.print("Enter first team number: ");
        int numA = getIntInput();
        System.out.print("Enter second team number: ");
        int numB = getIntInput();

        if (!isValidTeamNumber(numA) || !isValidTeamNumber(numB)) {
            System.out.println("Invalid team number. Match not added.");
            return;
        }
        if (numA == numB) {
            System.out.println("A team cannot play against itself.");
            return;
        }

        Match m = new Match(selectedLeague.getTeams().get(numA - 1), selectedLeague.getTeams().get(numB - 1));
        selectedLeague.addMatch(m);
        data.matches.add(m);
        System.out.println("Match added successfully!");
    }

    @Override
    public void viewData() {
        if (data.matches.isEmpty()) {
            System.out.println("No matches available.");
            return;
        }
        System.out.println("\nMatch Schedule / Results:");
        for (int i = 0; i < data.matches.size(); i++) {
            System.out.println((i + 1) + ". " + data.matches.get(i).getResult());
        }
    }

    @Override
    public void editStats() {
        if (data.matches.isEmpty()) {
            System.out.println("No matches to edit.");
            return;
        }
        System.out.println("\n1. Play match automatically");
        System.out.println("2. Enter match result manually");
        System.out.print("Enter your choice: ");
        int choice = getIntInput();
        viewData();
        System.out.print("Choose match number: ");
        int numM = getIntInput();
        if (!isValidMatchNumber(numM)) {
            System.out.println("Invalid match number.");
            return;
        }
        Match match = data.matches.get(numM - 1);
        if (match.isPlayed()==true){
            System.out.println("The match is already played. The score cannot be changed.");
            return;
        }

        switch (choice) {
            case 1:
                match.playMatchAuto();
                int yellowA = (int)(Math.random() * 3);
                int redA = (int)(Math.random() * 2);
                int yellowB = (int)(Math.random() * 3);
                int redB = (int)(Math.random() * 2);
                autoAssignCards(match.getTeamA(), yellowA, redA);
                autoAssignCards(match.getTeamB(), yellowB, redB);
                System.out.println("Match played automatically: " + match.getResult());
                break;

            case 2:
                System.out.print("Enter first team score: ");
                int scoreA = getIntInput();
                System.out.print("Enter second team score: ");
                int scoreB = getIntInput();
                match.playMatchManual(scoreA, scoreB);

                assignCards(match.getTeamA(), "yellow");
                assignCards(match.getTeamA(), "red");
                assignCards(match.getTeamB(), "yellow");
                assignCards(match.getTeamB(), "red");

                System.out.println("Score updated successfully: " + match.getResult());
                break;

            default:
                System.out.println("Invalid choice.");
        }
    }

    @Override
    public void delete() {
        if (data.matches.isEmpty()) {
            System.out.println("No matches to delete.");
            return;
        }
        viewData();
        System.out.print("Choose match number: ");
        int option = getIntInput();
        if (isValidMatchNumber(option)) {
            data.matches.remove(option - 1);
            System.out.println("Match deleted successfully!");
        } else {
            System.out.println("Invalid match number.");
        }
    }

    private int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }

    private boolean isValidTeamNumber(int num) {
        return num >= 1 && num <= data.teams.size();
    }

    private boolean isValidLeagueNumber(int num) {
        return num >= 1 && num <= data.leagues.size();
    }

    private boolean isValidMatchNumber(int num) {
        return num >= 1 && num <= data.matches.size();
    }

    private void assignCards(Team team, String cardType) {
        System.out.print("Enter number of " + cardType + " cards for " + team.getName() + ": ");
        int cardCount = sc.nextInt();
        ArrayList<Player> players = team.getPlayer();
        if (cardCount > 0 && !players.isEmpty()) {
            for (int j = 0; j < players.size(); j++) {
                System.out.println((j + 1) + ". " + players.get(j).getName());
            }
            for (int i = 0; i < cardCount; i++) {
                System.out.print("Choose player number for " + cardType + " card #" + (i + 1) + ": ");
                int p = sc.nextInt();
                Player player = players.get(p - 1);
                if (cardType.equalsIgnoreCase("yellow")) {
                    player.addYellowCard();
                    team.addYellowCards();
                } else if (cardType.equalsIgnoreCase("red")) {
                    player.addRedCard();
                    team.addRedCards();
                }
            }
        }
    }

    private void autoAssignCards(Team team, int yellowCount, int redCount){
        ArrayList<Player> players = team.getPlayer();
        if (players.isEmpty()) return;
        for (int i = 0; i < yellowCount; i++) {
            Player p = players.get(rand.nextInt(players.size()));
            p.addYellowCard();
            team.addYellowCards();
        }
        for (int i = 0; i < redCount; i++) {
            Player p = players.get(rand.nextInt(players.size()));
            p.addRedCard();
            team.addRedCards();
        }
    }
}
