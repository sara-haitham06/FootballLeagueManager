package models;
import java.util.ArrayList;

public class Team {
    private String name;
    private ArrayList<Player> player= new ArrayList<>();
    private int matchPlayed;
    private int goalsFor;
    private  int goalsAgainst;
    private int pts;
    private int yellowCards;
    private int redCards;

    public Team(String name) {
        this.name = name;
        this.matchPlayed = 0;
        this.goalsFor = 0;
        this.goalsAgainst = 0;
        this.pts = 0;
    }

    public String getName() {
        return name;}

    public void setName(String name) {
        this.name = name;
    }

    public void setMatchPlayed(int matchPlayed) {
        if (matchPlayed < 0) {
            throw new IllegalArgumentException("Played matches cannot be negative");
        }}

    public int getMatchPlayed() {
        return matchPlayed;}

    public int getGoalsFor() {
        return goalsFor;}

    public int getGoalsAgainst() {
        return goalsAgainst;}

    public int getGoalDiff() {
        return goalsFor-goalsAgainst;}

    public int getPts() {
        return pts;}

    public int getYellowCards() {
        return yellowCards;
    }

    public void addYellowCards() {
        yellowCards++;
    }

    public int getRedCards() {
        return redCards;
    }

    public void addRedCards() {
        redCards++;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public void setPts(int pts) {
        if (pts < 0) {
            throw new IllegalArgumentException("Points cannot be negative");
        } else{
            this.pts = pts;
        }
    }

    public ArrayList<Player> getPlayer() {
        return player;}

    public void setPlayer(ArrayList<Player> player) {
        this.player = player;
    }

    public void addPlayer(Player player) {
        if (!this.player.contains(player)) {
            this.player.add(player);
            player.setTeam(this);
        }
    }

    public void removePlayer(Player player) {
        this.player.remove(player);
        if (player.getTeam() == this) {
            player.setTeam(null);
        }
    }

    public void updateStats(int gf, int ga){
        if (gf < 0 || ga < 0) {
            throw new IllegalArgumentException("Goals cannot be negative");
        }
        this.matchPlayed++;
        this.goalsFor += gf;
        this.goalsAgainst += ga;
        if(gf>ga){
            this.pts+=3;
        } else if (gf==ga) {
            this.pts+=1;
        }
    }

    @Override
    public String toString() {
        return name+" | Matched played "+ matchPlayed+" | Goals For= "+goalsFor+" | Goals Against= "+
                goalsAgainst+" | Points="+pts;
    }

}
