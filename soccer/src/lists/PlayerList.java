package lists;
import java.util.Scanner;
import models.*;

public class PlayerList implements Menu {
    private DataStore data;
    Scanner sc = new Scanner(System.in);

    public PlayerList(DataStore data) {
        this.data = data;
    }

    @Override
    public void showList() {
        boolean exit = false;
        do {
            System.out.println("=================Player list=================");
            System.out.println("1. Add new player.");
            System.out.println("2. View a player stats.");
            System.out.println("3. Edit data of a player.");
            System.out.println("4. Delete a player.");
            System.out.println("5. Exit");
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
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice number.");
            }
        } while (!exit);
    }

    @Override
    public void add() {
        sc.nextLine();
        System.out.print("Enter player name (letters only): ");
        String pName = null;
        do {
            pName = sc.nextLine().toLowerCase().trim();
            if (pName.matches("[a-zA-Z ]+")){
                break;
            }else{
                System.out.println("Invalid name! Please use letters only.");
            }
        }while(true);
        System.out.print("Enter player position (goalkeeper, defender, midfielder, forward): ");
        String pPosition=null;
        do {
            pPosition = sc.next().toLowerCase().trim();
            if (pPosition.equals("goalkeeper") || pPosition.equals("defender") ||
                    pPosition.equals("midfielder") || pPosition.equals("forward")) {
                System.out.println(pName + " position added successfully!");
                break;
            }else {
                System.out.println("Invalid position! write the position correct: ");
            }
        }while (true);
        Player p = new Player(pName, pPosition);

        System.out.print("The player is assigned to team ? (y/n): ");
        char answer = sc.next().toLowerCase().charAt(0);

        if (answer == 'y') {
            if (data.teams.isEmpty()) {
                System.out.println("No teams available. Please add a team first.");
            } else {
                for (int i = 0; i < data.teams.size(); i++) {
                    System.out.println((i + 1) + ". " + data.teams.get(i).getName());
                }
                System.out.print("Choose a team number: ");
                int teamChoice = getIntInput();
                if (teamChoice >= 1 && teamChoice <= data.teams.size()) {
                    Team chosenTeam = data.teams.get(teamChoice - 1);
                    chosenTeam.addPlayer(p);
                    System.out.println("Player assigned to " + chosenTeam.getName());
                } else {
                    System.out.println("Invalid team number. Player not assigned.");
                }
            }
        } else if (answer=='n') {
            System.out.println("Player not assigned to any team.");
        }else {
            System.out.println("Invalid input!");
        }
        data.players.add(p);
        System.out.println("Player added successfully!");
    }

    @Override
    public void viewData() {
        if (data.players.isEmpty()) {
            System.out.println("There is no added players.");
            return;
        }
        for (int i = 0; i < data.players.size(); i++) {
            System.out.println((i + 1) + ". " + data.players.get(i).getName());
        }
        System.out.print("Choose player number: ");
        int option = getIntInput();
        if (option >= 1 && option <= data.players.size()) {
            Player p = data.players.get(option - 1);
            System.out.println(p);
        } else {
            System.out.println("Invalid player number.");
        }
    }

    @Override
    public void editStats() {
        if (data.players.isEmpty()) {
            System.out.println("There is no added players.");
            return;
        }
        for (int i = 0; i < data.players.size(); i++) {
            System.out.println((i + 1) + ". " + data.players.get(i).getName());
        }
        System.out.print("Choose player number: ");
        int option = getIntInput();
        if (option >= 1 && option <= data.players.size()) {
            Player p = data.players.get(option - 1);
            System.out.println("1. Change team\n2. Change position");
            System.out.print("Choose data option: ");
            switch (getIntInput()) {
                case 1:
                    if (data.teams.isEmpty()) {
                        System.out.println("No teams available. Please add a team first.");
                        return;
                    }else {
                        if (p.getTeam() != null) {
                            p.getTeam().removePlayer(p);
                        }
                        for (int i = 0; i < data.teams.size(); i++) {
                            if (data.teams.get(i) == p.getTeam()) {
                                continue;
                            }
                            System.out.println((i + 1) + ". " + data.teams.get(i).getName());
                        }
                    }
                    System.out.print("Choose a team number: ");
                    int teamChoice = getIntInput();
                    if (teamChoice >= 1 && teamChoice <= data.teams.size()) {
                        Team chosenTeam = data.teams.get(teamChoice - 1);
                        chosenTeam.addPlayer(p);
                        System.out.println("Player assigned to " + chosenTeam.getName());
                    } else {
                        System.out.println("Invalid team number. Player not assigned.");
                    }
                    break;
                case 2:
                    System.out.print("Enter new position: ");
                    String newPosition = sc.next();
                    p.setPosition(newPosition);
                    System.out.println(p.getName() + " position changed successfully!");
                    break;
                default:
                    System.out.println("Invalid choice number.");
            }
        }
    }

    @Override
    public void delete() {
        if (data.players.isEmpty()) {
            System.out.println("There is no added players.");
            return;
        }
        for (int i = 0; i < data.players.size(); i++) {
            System.out.println((i + 1) + ". " + data.players.get(i));
        }
        System.out.print("Choose player number to delete: ");
        int option = getIntInput();
        if (option >= 1 && option <= data.players.size()) {
            if (data.players.get(option - 1).getTeam() != null) {
                data.players.get(option - 1).getTeam().removePlayer(data.players.get(option - 1));
            }
            data.players.remove(option - 1);
            System.out.println("Player deleted successfully!");
        } else {
            System.out.println("Invalid player number.");
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
