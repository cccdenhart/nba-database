import java.sql.ResultSet;
import java.sql.SQLException;

public class Team implements NbaObject {

  String franchise;
  Integer fromYear;
  Integer toYear;
  Integer wins;
  Integer losses;
  Integer numPlyfs;
  Integer numConf;
  Integer numChamp;

  // empty constructor
  public Team() {

  }

  @Override
  public void setObject(ResultSet rs) {
    try {
      this.franchise = rs.getString("franchise");
      this.fromYear = rs.getInt("fromYear");
      this.toYear = rs.getInt("toYear");
      this.wins = rs.getInt("wins");
      this.losses = rs.getInt("losses");
      this.numPlyfs = rs.getInt("numPlyfs");
      this.numConf = rs.getInt("numConf");
      this.numChamp = rs.getInt("numChamp");
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    String franchise = "Franchise: " + this.franchise;
    String fromYear = ", Began: " + this.fromYear.toString();
    String toYear = ", Ended: " + this.toYear.toString();
    String wins = ", Wins: " + this.wins.toString();
    String losses = ", Losses: " + this.losses.toString();
    String numPlyfs = ", Playoffs: " + this.numPlyfs.toString();
    String numConf = ", Conference: " + this.numConf.toString();
    String numChamp = ", Championships: " + this.numChamp;

    return franchise + fromYear + toYear + wins + losses + numPlyfs + numConf + numChamp;
  }

}
