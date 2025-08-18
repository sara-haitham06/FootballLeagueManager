import lists.*;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        DataStore data = new DataStore();
        PlayerList pList = new PlayerList(data);
        TeamList tList = new TeamList(data);
        MatchList mList = new MatchList(data);
        LeagueList lList = new LeagueList(data);
        boolean exit = false;
        do{
            System.out.println("=================Menu=================");
            System.out.println("1. Manage players.");
            System.out.println("2. Manage teams.");
            System.out.println("3. Manage matches.");
            System.out.println("4. Manage leagues.");
            System.out.println("5. Exit");
            System.out.print("Enter number of your option: ");
            int option = sc.nextInt();
            switch(option){
                case 1:
                    pList.showList();
                    break;
                case 2:
                    tList.showList();
                    break;
                case 3:
                    mList.showList();
                    break;
                case 4:
                    lList.showList();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice number.");
            }

        }while (!exit);
    }
}