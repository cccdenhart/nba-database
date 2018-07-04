/*
 * Charlie Denhart
 * CS3200
 * HW7 -- 4/2/18
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

// contains information to connect and interact with a database
public class NbaApp {

  /**
   * The name of the MySQL account to use (or empty for anonymous)
   */
  private String userName = null;

  /**
   * The password for the MySQL account (or empty for anonymous)
   */
  private String password = null;

  /**
   * The name of the computer running MySQL
   */
  private String serverName = null;

  /**
   * The port of the MySQL server (default is 3306)
   */
  private int portNumber = 0;

  /**
   * The name of the database we are testing with (this default is installed with MySQL)
   */
  private String dbName = null;

  // DbConn constructor for default username/password
  public NbaApp() {
    this.userName = "root";
    this.password = "";
    this.dbName = "CelticsDB";
    this.serverName = "127.0.0.1";
    this.portNumber = 3306;
  }

  // DbConn constructor for user determined username/password
  public NbaApp(String userName, String password, String dbName) {
    this.userName = userName;
    this.password = password;
    this.dbName = dbName;
    this.serverName = "127.0.0.1";
    this.portNumber = 3306;
    try {
      Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      System.out.println(e);
    }
  }

  /**
   * Get a new database connection
   */
  public Connection getConnection() throws SQLException {
    Connection conn = null;
    Properties connectionProps = new Properties();
    connectionProps.put("user", this.userName);
    connectionProps.put("password", this.password);

    conn = DriverManager.getConnection("jdbc:mysql://"
            + this.serverName + ":" + this.portNumber + "/" + this.dbName
            + "?characterEncoding=UTF-8&useSSL=false",
        connectionProps);

    return conn;
  }

  /**
   * Run a SQL command which does not return a recordset: CREATE/INSERT/UPDATE/DELETE/DROP/etc.
   *
   * @throws SQLException If something goes wrong
   */
  public boolean executeUpdate(Connection conn, String command) throws SQLException {
    Statement stmt = null;
    try {
      stmt = conn.createStatement();
      stmt.executeUpdate(command); // This will throw a SQLException if it fails
      return true;
    } finally {

      // This will run whether we throw an exception or not
      if (stmt != null) {
        stmt.close();
      }
    }
  }

  /**
   * Run a SQL command which does return a recordset: SELECT.
   *
   * @throws SQLException If something goes wrong
   */
  public List<String> getCol(Connection conn, String command, String colName) throws SQLException {
    List<String> result = new ArrayList<>();

    // execute query
    try {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(command);
      while (rs.next()) {
        result.add(rs.getString(colName));
      }
    } catch (SQLException e) {
      System.out.println("ERROR: Could not create statement");
      e.printStackTrace();
    }

    return result;
  }

  /**
   * Run a SQL command which does return a recordset: SELECT.
   *
   * @throws SQLException If something goes wrong
   */
  public String getInsts(Connection conn, String command, ObjectType type) throws SQLException {
    String result = "";

    // execute query
    try {
      Statement st = conn.createStatement();
      ResultSet rs = st.executeQuery(command);
      while (rs.next()) {
        NbaObject ob = null;
        if (type.equals(ObjectType.PLAYER_STATS)) {
          ob = new PlayerStats();
        } else if (type.equals(ObjectType.GAME)) {
          ob = new Game();
        } else if (type.equals(ObjectType.PLAYER)) {
          ob = new Player();
        } else {
          ob = new Team();
        }
        ob.setObject(rs);
        result += ob.toString() + "\n";
      }
    } catch (SQLException e) {
      System.out.println("ERROR: Could not create statement");
      e.printStackTrace();
    }

    return result;
  }

  /**
   * Connect to MySQL and do some stuff.
   */
  private void run() {

    // Connect to MySQL
    Connection conn = null;
    try {
      conn = this.getConnection();
      System.out.println("Connected to database");
    } catch (SQLException e) {
      System.out.println("ERROR: Could not connect to the database");
      e.printStackTrace();
    }
  }

  /**
   * Connect to the DB and do some stuff
   */
  public static void main(String[] args) {
    // gather and store initial user input
    Scanner userInput = new Scanner(System.in);
    System.out.println("Enter a username: ");
    String username = userInput.next();
    System.out.println("Enter a password: ");
    String password = userInput.next();
    System.out.println("Enter a database name: ");
    String userDb = userInput.next();

    // set username and/or password to "" if 'empty' is entered by user
    if (username.equals("empty")) {
      username = "";
    } else if (password.equals("empty")) {
      password = "";
    }

    // connect to the database using user-provided username, password, and database
    NbaApp app = new NbaApp(username, password, userDb);
    app.run();

    // start application, continuing to prompt user for events until they hit 'done'
    Connection conn = null;
    try {
      conn = app.getConnection();

      // print out all possible queries for user
      System.out.println("\nType 'teams' to view all teams");
      System.out.println("Type 'roster' to view all players in the database");
      System.out.println("Type 'schedule' to view the Celtic's full schedule");
      System.out.println(
          "Type 'player' then the name of a player to view their game-by-game statistics");
      System.out.println("Type 'trade' then the name of a player to change the team of a player");
      System.out
          .println("Type 'retire' then the name of a player to remove them from the database");
      System.out.println("Type 'end' to end the application");

      System.out.println("\nEnter an event: ");

      boolean endApp = false;

      while (userInput.hasNext()) {
        String event = userInput.next();
        event = event.toLowerCase();

        // variables to be used in while loop
        String getPlayers = "select * from roster";
        List<String> allNames = app.getCol(conn, getPlayers, "lName");
        String getTeams = "select * from team";
        List<String> allTeams = app.getCol(conn, getTeams, "franchise");

        // process user input
        switch (event) {
          case "teams":
            System.out.println(app.getInsts(conn, "select * from team", ObjectType.TEAM));
            break;
          case "roster":
            System.out.println(app.getInsts(conn, "select * from roster", ObjectType.PLAYER));
            break;
          case "schedule":
            System.out.println(app.getInsts(conn, "select * from seasonSched", ObjectType.GAME));
            break;
          case "player":
            String name = userInput.next();
            if (allNames.contains(name)) {
              String nameQuery = "select * from playerGameStats where lName = " + "'" + name + "'";
              System.out.println(app.getInsts(conn, nameQuery, ObjectType.PLAYER_STATS));
            } else {
              System.out.println("Invalid player. Try again: ");
            }
            break;
          case "trade":
            String player = userInput.next();
            if (allNames.contains(player)) {
              System.out.println("To which team?");
              userInput.nextLine();
              String team = userInput.nextLine();
              System.out.println("TEAMS: " + allTeams);
              System.out.println("SELECTED TEAM: " + team);
              if (allTeams.contains(team)) {
                String update =
                    "update roster set team = " + "'" + team + "'" + " where lName = " + "'"
                        + player + "'";
                app.executeUpdate(conn, update);
                System.out.println(player + " has been traded to " + team + "!");
              } else {
                System.out.println("Invalid team. Try again: ");
              }
            } else {
              System.out.println("Invalid player. Try again: ");
            }
            break;
          case "retire":
            String retiredPlayer = userInput.next();
            if (allNames.contains(retiredPlayer)) {
              String delete = "delete from roster where lName = '" + retiredPlayer + "'";
              app.executeUpdate(conn, delete);
              System.out.println(retiredPlayer + " has retired!");
            }
            break;
          case "end":
            endApp = true;
            break;
          default:
            System.out.println("Invalid entry!");
        }

        // decide whether or not to end application
        if (endApp) {
          break;
        } else {
          System.out.println("Enter an event: ");
        }
      }

    } catch (SQLException e) {
      System.out.println("ERROR: Could not connect to database");
      e.printStackTrace();
    } finally {
      // close the database connection
      try {
        conn.close();
      } catch (Exception e) {
        System.out.println("ERROR: connection could not close");
        e.printStackTrace();
      }
    }
    // end the application program
    System.out.println("\nFINISHED");
  }
}



