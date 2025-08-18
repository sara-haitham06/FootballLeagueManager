package models;
import java.util.Comparator;
import java.util.ArrayList;

public class League {
    private String name;
    private String startDate;
    private int roundsNo;
    private ArrayList<Match> matches = new ArrayList<>();
    private ArrayList<Team> teams = new ArrayList<>();

    public League(String name, String startDate ) {
        this.name = name;
        this.startDate = startDate;
    }

    public String getName() {
        return name;}

    public void setName(String name) {
        this.name = name;}

    public String getStartDate() {
        return startDate;}

    public void setStartDate(String startDate) {
        this.startDate = startDate;}

    public int getRoundsNo() {
        return roundsNo;}

    public void setRoundsNo() {
        this.roundsNo = 2*(teams.size()-1);}

    public ArrayList<Match> getMatches() {
        return matches;}

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;}

    public ArrayList<Team> getTeams() {
        return teams;}

    public Team getTeamByName(String n) {
        for (Team t : teams) {
            if (t.getName().equalsIgnoreCase(n.trim())) {
                return t;
            }
        }
        System.out.println("Team is not found");
        return null;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;}

    public void addTeam(Team t){
        boolean found=false;
        for (Team i : teams){
            if(t.getName().equalsIgnoreCase(i.getName())){
                System.out.println("Team is already exist.");
                found=true;
                break;
            }
        }if(!found){
            teams.add(t);
        }
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    Comparator<Team> ptsComparator = new Comparator<Team>() {
        @Override
        public int compare(Team t1, Team t2) {
            if (t1.getPts() != t2.getPts()) {
                return Integer.compare(t2.getPts(), t1.getPts());
            }else if (t1.getGoalDiff() != t2.getGoalDiff()) {
                return Integer.compare(t2.getGoalDiff(), t1.getGoalDiff());
            } else {
                return Integer.compare(t2.getGoalsFor(), t1.getGoalsFor());
            }

        }
    };

    public void showStandings() {
        teams.sort(ptsComparator);
        System.out.println("================== Standings ==================");
        System.out.printf("%-15s | %3s | %3s | %3s | %3s | %3s%n",
                "Club", "MP", "GF", "GA", "GD", "Pts");
        System.out.println("------------------------------------------------");
        for (Team t : teams) {
            System.out.printf("%-15s | %3d | %3d | %3d | %3d | %3d%n",
                    t.getName(),
                    t.getMatchPlayed(),
                    t.getGoalsFor(),
                    t.getGoalsAgainst(),
                    t.getGoalDiff(),
                    t.getPts());
        }
        System.out.println("================================================");
    }

    public void showFixtures(){
        System.out.println("==================Fixtures==================");
        for (Match m : matches){
            System.out.println(m.getTeamA().getName()+" vs "+m.getTeamB().getName());
            System.out.println("_________________________________________________");
        }
    }

    public void showLeagueData(){
        System.out.println("==================League data==================");
        System.out.println(getName()+":");
        System.out.println("Start Date: " + getStartDate());
        System.out.println("Number of rounds for each team: " + getRoundsNo());
        System.out.println("Participated Teams:");
        for (int i = 0; i < teams.size(); i++) {
            System.out.println((i + 1) + ". " + teams.get(i).getName());
        }
    }
}

