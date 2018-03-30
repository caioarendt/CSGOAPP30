package ie.wit.csgoapp30.models;

/**
 * Created by caio_ on 2/20/2018.
 * Android tutorials for hassle-free android development and programming. (2018). Android SQLite Database Tutorial - CRUD Operations. [online] Available at: https://www.androidtutorialpoint.com/storage/android-sqlite-database-tutorial/ [Accessed 2 Mar. 2018].
 */

public class Match {

    private String team1;
    private String team2;
    private String date;
    private String time;
    private int matchID;

    public Match(){
    }

    public Match(int matchID, String team1, String team2, String date, String time){
        this.matchID = matchID;
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.time = time;
    }

    public Match(String team1, String team2, String date, String time){
        this.team1 = team1;
        this.team2 = team2;
        this.date = date;
        this.time = time;
    }

    public int getMatchID() {
        return matchID;
    }

    public void setMatchID(int matchID) {
        this.matchID = matchID;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String toString(){
        return "Match ID : " +getMatchID()+ "\n"+
                "Team1 : " +getTeam1()+ "\n" +
                "Team2 : "+getTeam2() + "\n" +
                "Date : "+getDate() + "\n" +
                "Time : "+getTime();


    }
}
