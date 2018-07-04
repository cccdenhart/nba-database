import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class PlayerStats implements NbaObject {

  private Date date;
  private String lName;
  private String fName;
  private Double mp;
  private Integer fg;
  private Integer fga;
  private Integer threeP;
  private Integer threePA;
  private Integer ft;
  private Integer fta;
  private Integer orb;
  private Integer drb;
  private Integer trb;
  private Integer ast;
  private Integer stl;
  private Integer blk;
  private Integer tov;
  private Integer pf;
  private Integer pts;
  private Double gmScore;
  private Integer plusMinus;

  // empty constructor
  public PlayerStats() {
  }

  // sets each field using
  @Override
  public void setObject(ResultSet rs) {
    try {
      this.date = rs.getDate("gameDate");
      this.lName = rs.getString("lName");
      this.fName = rs.getString("fName");
      this.mp = rs.getDouble("mp");
      this.fg = rs.getInt("fg");
      this.fga = rs.getInt("fga");
      this.threeP = rs.getInt("threeP");
      this.threePA = rs.getInt("threePA");
      this.ft = rs.getInt("ft");
      this.fta = rs.getInt("fta");
      this.orb = rs.getInt("orb");
      this.drb = rs.getInt("drb");
      this.trb = rs.getInt("trb");
      this.ast = rs.getInt("ast");
      this.stl = rs.getInt("stl");
      this.blk = rs.getInt("blk");
      this.tov = rs.getInt("tov");
      this.pf = rs.getInt("pf");
      this.pts = rs.getInt("pts");
      this.gmScore = rs.getDouble("gmScore");
      this.plusMinus = rs.getInt("plusMinus");
    } catch (SQLException e) {
      throw new RuntimeException("rs setting failed");
    }
  }

  @Override
  public String toString() {
    String date = "Date: " + this.date.toString();
    String lname = ", Last Name: " + this.lName;
    String fname = ", First Name: " + this.fName;
    String mp = ", MP: " + this.mp.toString();
    String fg = ", FG: " + this.fg.toString();
    String fga = ", FGA: " + this.fga.toString();
    String threeP = ", 3P: " + this.threeP.toString();
    String threePA = ", 3PA" + this.threePA.toString();
    String ft = ", FT: " + this.ft.toString();
    String fta = ", FTA: " + this.fta.toString();
    String orb = ", ORB: " + this.orb.toString();
    String drb = ", DRB: " + this.drb.toString();
    String trb = ", TRB: " + this.trb.toString();
    String ast = ", AST: " + this.ast.toString();
    String stl = ", STL: " + this.stl.toString();
    String blk = ", BLK: " + this.blk.toString();
    String tov = ", TOV: " + this.tov.toString();
    String pf = ", PF: " + this.pf.toString();
    String pts = ", PTS: " + this.pts.toString();
    String gmScore = ", gmScore" + this.gmScore;
    String plusMinus = ", +/-: " + this.plusMinus.toString();

    return date + lname + fname + mp + fg + fga + threeP + threePA + ft + fta + orb + drb + trb
        + ast + stl + blk + tov + pf + pts + gmScore + plusMinus;
  }
}
