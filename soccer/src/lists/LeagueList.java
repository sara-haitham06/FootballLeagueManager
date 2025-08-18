package lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.*;

public class LeagueList implements Menu {
    private DataStore data;
    Scanner sc = new Scanner(System.in);

    public LeagueList(DataStore data) {
        this.data = data;
    }

    @Override
    public void showList(){
        boolean exit = false;
        do {
            System.out.println("=================League list=================");
            System.out.println("1. Create new League.");
            System.out.println("2. Show a league data.");
            System.out.println("3. Edit a league data.");
            System.out.println("4. Delete a league.");
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
        if (data.teams.isEmpty()) {
            System.out.println("There are no added teams. Please add teams first.");
            return;
        }
        sc.nextLine();
        System.out.print("Enter league name: ");
        String leagueName = sc.nextLine().toLowerCase();
        System.out.print("Enter league start date (yyyy-mm-dd): ");
        String startDate = sc.nextLine();
        System.out.print("Enter number of participating teams: ");
        int teamsNum = getIntInput();
        sc.nextLine();
        if (teamsNum < 2 || teamsNum > data.teams.size()) {
            System.out.println("Invalid number of teams.");
            return;
        }
        League league = new League(leagueName, startDate);
        System.out.println("Choose participating teams:");
        for (int i = 0; i < data.teams.size(); i++) {
            System.out.println((i + 1) + ". " + data.teams.get(i).getName());
        }
        for (int i = 0; i < teamsNum; i++) {
            System.out.print("Enter team number " + (i + 1) + ": ");
            int teamChoice = getIntInput();
            sc.nextLine();
            if (teamChoice >= 1 && teamChoice <= data.teams.size()) {
                league.addTeam(data.teams.get(teamChoice - 1));
            } else {
                System.out.println("Invalid team number, skipping...");
            }
        }
        league.setRoundsNo();
        data.leagues.add(league);
        System.out.println("League added successfully!");
    }

    @Override
    public void viewData() {
        if (data.leagues.isEmpty()) {
            System.out.println("There are no leagues available.");
            return;
        }
        for (int i = 0; i < data.leagues.size(); i++) {
            System.out.println((i + 1) + ". " + data.leagues.get(i).getName());
        }

        System.out.print("Choose league number: ");
        int choice = getIntInput();
        sc.nextLine();

        if (choice < 1 || choice > data.leagues.size()) {
            System.out.println("Invalid league number.");
            return;
        }
        League selectedLeague = data.leagues.get(choice - 1);
        System.out.println("________________________________________");
        System.out.println("1. Show league data");
        System.out.println("2. Show league fixtures");
        System.out.println("3. Show league standings");
        System.out.print("Choose a number: ");
        int subChoice = getIntInput();
        sc.nextLine();
        switch (subChoice) {
            case 1:
                selectedLeague.showLeagueData();
                break;
            case 2:
                if(selectedLeague.getMatches().isEmpty()){
                    System.out.println("There is no added matches.");
                    return;
                }
                selectedLeague.showFixtures();
                break;
            case 3:
                if(selectedLeague.getTeams().isEmpty()){
                    System.out.println("There is no added teams.");
                    return;
                }
                selectedLeague.showStandings();
                break;
            default:
                System.out.println("Invalid choice number.");
        }
    }

    @Override
    public void editStats() {
        if (data.leagues.isEmpty()) {
            System.out.println("There are no leagues available.");
            return;
        }
        for (int i = 0; i < data.leagues.size(); i++) {
            System.out.println((i + 1) + ". " + data.leagues.get(i).getName());
        }
        System.out.print("Choose league number: ");
        int choice = getIntInput();
        sc.nextLine();
        if (choice < 1 || choice > data.leagues.size()) {
            System.out.println("Invalid league number.");
            return;
        }
        League selectedLeague = data.leagues.get(choice - 1);

        System.out.println("________________________________________");
        System.out.println("1. Change name.");
        System.out.println("2. Change start date.");
        System.out.println("3. Add a new team to the league.");
        System.out.println("4.Remove a team from the league");
        System.out.print("Choose a number: ");
        int subChoice = getIntInput();
        sc.nextLine();
        switch (subChoice){
            case 1:
                System.out.print("Enter the new name: ");
                selectedLeague.setName(sc.nextLine());
                break;
            case 2:
                System.out.print("Enter the new start date (yyyy-mm-dd): ");
                selectedLeague.setStartDate(sc.nextLine());
                break;
            case 3:
                System.out.println("Participating teams:");
                for (int i = 0; i < selectedLeague.getTeams().size(); i++) {
                    System.out.println((i + 1) + ". " + selectedLeague.getTeams().get(i).getName());
                }
                ArrayList<Team> uncommonTeams = new ArrayList<>();
                for (Team t : data.teams) {
                    if (!selectedLeague.getTeams().contains(t)) {
                        uncommonTeams.add(t);
                    }
                }
                if (uncommonTeams.isEmpty()) {
                    System.out.println("All available teams are already participating in this league.");
                    break;
                }
                System.out.println("\nAvailable teams to add:");
                for (int i = 0; i < uncommonTeams.size(); i++) {
                    System.out.println((i + 1) + ". " + uncommonTeams.get(i).getName());
                }
                System.out.print("Choose team number to add: ");
                int teamChoice = getIntInput();
                sc.nextLine();
                if (teamChoice >= 1 && teamChoice <= uncommonTeams.size()) {
                    selectedLeague.addTeam(uncommonTeams.get(teamChoice - 1));
                    System.out.println("Team added successfully!");
                } else {
                    System.out.println("Invalid team number, skipping...");
                }
                break;
            case 4:
                System.out.println("Participated teams:");
                for (int i = 0; i < selectedLeague.getTeams().size(); i++) {
                    System.out.println((i + 1) + ". " + selectedLeague.getTeams().get(i).getName());
                }

                System.out.print("Enter the number of the team to remove: ");
                int removeChoice = getIntInput();
                sc.nextLine();

                if (removeChoice >= 1 && removeChoice <= selectedLeague.getTeams().size()) {
                    Team removedTeam = selectedLeague.getTeams().remove(removeChoice - 1);
                    removedTeam.setPts(0);
                    removedTeam.setGoalsAgainst(0);
                    removedTeam.setGoalsFor(0);
                    ArrayList<Match> matchesToRemove = new ArrayList<>();
                    for (Match match : data.matches) {
                        if ((match.getTeamA() != null && match.getTeamA().equals(removedTeam)) ||
                                (match.getTeamB() != null && match.getTeamB().equals(removedTeam))) {
                            matchesToRemove.add(match);
                        }
                    }
                    data.matches.removeAll(matchesToRemove);
                    System.out.println("Team '" + removedTeam.getName() + "' removed successfully!");
                } else {
                    System.out.println("Invalid team number.");
                }
                break;
            default: System.out.println("Invalid number.");
        }
    }

    @Override
    public void delete() {
        if (data.leagues.isEmpty()) {
            System.out.println("There are no leagues available.");
            return;
        }
        for (int i = 0; i < data.leagues.size(); i++) {
            System.out.println((i + 1) + ". " + data.leagues.get(i).getName());
        }
        System.out.print("Choose league number: ");
        int choice = getIntInput();
        sc.nextLine();
        if (choice < 1 || choice > data.leagues.size()) {
            System.out.println("Invalid league number.");
            return;
        }
        League selectedLeague = data.leagues.get(choice - 1);
        List<Match> matchesToRemove = new ArrayList<>();
        for (Match match : data.matches) {
            if (match.getLeague().equals(selectedLeague)) {
                matchesToRemove.add(match);
            }
        }
        data.matches.removeAll(matchesToRemove);
        System.out.println("League '" + selectedLeague.getName() + "' has been deleted.");
    }

    private int getIntInput() {
        while (!sc.hasNextInt()) {
            System.out.print("Please enter a valid number: ");
            sc.next();
        }
        return sc.nextInt();
    }
}
