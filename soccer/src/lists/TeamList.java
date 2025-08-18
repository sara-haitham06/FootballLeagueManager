package lists;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import models.*;

public class TeamList implements Menu {
    private DataStore data;
    Scanner sc = new Scanner(System.in);

    public TeamList(DataStore data) {
        this.data = data;
    }

    @Override
    public void showList(){
        boolean exit = false;
        do {
            System.out.println("=================Team list=================");
            System.out.println("1. Add new team.");
            System.out.println("2. View a team stats.");
            System.out.println("3. Edit data of a team.");
            System.out.println("4. Delete a team.");
            System.out.println("5. Show players of a team.");
            System.out.println("6. Exit");
            System.out.print("Enter number of your option: ");
            int option = getIntInput();

            switch (option) {
                case 1:
                    add();
                    break;
                case 2:
                    viewData();
                    break;
                case 3:
                    editStats();
                    break;
                case 4:
                    delete();
                    break;
                case 5:
                    showPlayers();
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice number.");
            }
        } while (!exit);
    }

    @Override
    public void add(){
        sc.nextLine();
        System.out.print("Enter Team name (letters only): ");
        String tName = null;
        do {
            tName = sc.nextLine().toLowerCase().trim();
            if (tName.matches("[a-zA-Z ]+")){
                break;
            }else{
                System.out.println("Invalid name! Please use letters only.");
            }
        }while(true);
        Team t = new Team(tName);
        data.teams.add(t);
        System.out.println(tName+" team added successfully!");
    }

    @Override
    public void viewData() {
        if (data.teams.isEmpty()) {
            System.out.println("There is no added teams.");
            return;
        }
        for (int i = 0; i < data.teams.size(); i++) {
            System.out.println((i + 1) + ". " + data.teams.get(i).getName());
        }
        System.out.print("Choose team number: ");
        int option = getIntInput();
        if (option >= 1 && option <= data.teams.size()) {
            Team t = data.teams.get(option - 1);
            System.out.println(t);
        } else {
            System.out.println("Invalid team number.");
        }
    }

    @Override
    public void editStats() {
        if (data.teams.isEmpty()) {
            System.out.println("There are no added teams.");
            return;
        }
        for (int i = 0; i < data.teams.size(); i++) {
            System.out.println((i + 1) + ". " + data.teams.get(i).getName());
        }
        System.out.print("Choose team number: ");
        int option = getIntInput();
        sc.nextLine();
        if (option >= 1 && option <= data.teams.size()) {
            Team team = data.teams.get(option - 1);
            System.out.print("Change team name? (y/n): ");
            char choice = sc.nextLine().trim().toLowerCase().charAt(0);
            if (choice == 'y') {
                System.out.print("Enter new name: ");
                String newName = sc.nextLine();
                team.setName(newName);
                System.out.println("Team changed to " + team.getName());
            } else if(choice=='n') {
                System.out.println("There are no changes.");
            }else {
                System.out.println("Invalid input!");
            }

        } else {
            System.out.println("Invalid team number.");
        }
    }


    public void delete() {
        if (data.teams.isEmpty()) {
            System.out.println("There are no added teams.");
            return;
        }

        for (int i = 0; i < data.teams.size(); i++) {
            System.out.println((i + 1) + ". " + data.teams.get(i).getName());
        }
        System.out.print("Choose team number to delete: ");
        int option = getIntInput();
        sc.nextLine();
        if (option >= 1 && option <= data.teams.size()) {
            Team selectedTeam = data.teams.get(option - 1);
            for (League league : data.leagues) {
                if (league.getTeams().contains(selectedTeam)) {
                    league.getTeams().remove(selectedTeam);
                }
            }
            for (Player player : data.players) {
                if (player.getTeam() != null && player.getTeam().equals(selectedTeam)) {
                    player.setTeam(null);
                }
            }
            List<Match> matchesToRemove = new ArrayList<>();
            for (Match match : data.matches) {
                if ((match.getTeamA() != null && match.getTeamA().equals(selectedTeam)) ||
                        (match.getTeamB() != null && match.getTeamB().equals(selectedTeam))) {
                    matchesToRemove.add(match);
                }
            }
            data.matches.removeAll(matchesToRemove);
            data.teams.remove(selectedTeam);
            System.out.println("Team deleted successfully!");
        } else {
            System.out.println("Invalid team number.");
        }
    }


    public void showPlayers() {
        if (data.teams.isEmpty()) {
            System.out.println("There are no added teams.");
            return;
        }
        for (int i = 0; i < data.teams.size(); i++) {
            System.out.println((i + 1) + ". " + data.teams.get(i).getName());
        }
        System.out.print("Choose team number: ");
        int option = getIntInput();
        sc.nextLine();
        if (option >= 1 && option <= data.teams.size()) {
            Team team = data.teams.get(option - 1);
            List<Player> players = team.getPlayer();
            if (players.isEmpty()) {
                System.out.println("No players in this team.");
            } else {
                System.out.println("Players in " + team.getName() + ":");
                for (Player p : players) {
                    System.out.println("- " + p.getName());
                }
            }
        } else {
            System.out.println("Invalid team number.");
        }
    }

    private int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }
}
