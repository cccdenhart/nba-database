import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Game implements NbaObject {

  Date date;
  String celtics;
  String opponent;
  Integer celtPoints;
  Integer oppPoints;
  Boolean isHome;
  Boolean isWin;

  @Override
  public void setObject(ResultSet rs) {
    try {
      this.date = rs.getDate("gameDate");
      this.celtics = rs.getString("celtics");
      this.opponent = rs.getString("opponent");
      this.celtPoints = rs.getInt("celtPoints");
      this.oppPoints = rs.getInt("oppPoints");
      this.isHome = rs.getInt("isHome") == 1;
      this.isWin = rs.getInt("isWin") == 1;

    } catch(SQLException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public String toString() {
    String date = "Date: " + this.date.toString();
    String celtics = ", Celtics: " + this.celtics;
    String opponent = ", Opponent: " + this.opponent;
    String celtPoints = ", Celtics Points: " + this.celtPoints.toString();
    String oppPoints = ", Oppenent Points: " + this.oppPoints.toString();
    String isHome = ", IsHome: " + this.isHome.toString();
    String isWin = ", IsWin: " + this.isWin.toString();

    return date + celtics + opponent + celtPoints + oppPoints + isHome + isWin;
  }
}
