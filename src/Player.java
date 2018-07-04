import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Player implements NbaObject {

  Integer playerNum;
  String lName;
  String fName;
  String pos;
  Integer height;
  Integer weight;
  Date birthday;
  String college;
  String team;

  // empty constructor
  public Player() {

  }

  @Override
  public void setObject(ResultSet rs) {
    try {
      this.playerNum = rs.getInt("playerNum");
      this.lName = rs.getString("lName");
      this.fName = rs.getString("fName");
      this.pos = rs.getString("pos");
      this.height = rs.getInt("height");
      this.weight = rs.getInt("weight");
      this.birthday = rs.getDate("birthday");
      this.college = rs.getString("college");
      this.team = rs.getString("team");

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public String toString() {
    String playerNum = "No.: " + this.playerNum.toString();
    String lName = ", Last Name: " + this.lName;
    String fName = ", First Name: " + this.fName;
    String pos = ", Position: " + this.pos;
    String height = ", Height: " + this.height.toString();
    String weight = ", Weight: " + this.weight.toString();
    String birthday = ", Birthday: " + this.birthday.toString();
    String college = ", College: " + this.college;
    String team = ", Team: " + this.team;

    return playerNum + lName + fName + pos + height + weight + birthday + college + team;
  }
}
