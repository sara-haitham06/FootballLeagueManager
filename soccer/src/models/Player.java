package models;

public class Player {
    private String name;
    private Team team;
    private String position;
    private int yellowCard;
    private int redCard;

    public Player(String name, String position) {
        this.name = name;
        setPosition(position);
        this.team = null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;}

    public Team getTeam() {
        return team;}

    public void setTeam(Team team) {
        this.team = team;}

    public String getPosition() {
        return position;}

    public void setPosition(String p) {
        if (p == null) {
            System.out.println("Invalid input!");
            return;
        }
        p = p.trim().toLowerCase();
        if (!(p.equals("goalkeeper") || p.equals("defender") ||
                p.equals("midfielder") || p.equals("forward"))) {
            System.out.println("Invalid position! write the position correct.");
        } else {
            this.position = p;
        }
    }


    public int getYellowCard() {
        return yellowCard;}

    public void addYellowCard() {
        yellowCard++;
    }

    public int getRedCard() {
        return redCard;}

    public void addRedCard() {
        redCard++;
    }

    @Override
    public String toString() {
        return name + "\n team= " + team.getName() + "\n position= " + position +
                "\n yellowCard=" + yellowCard + " ,redCard=" + redCard;
    }

}
