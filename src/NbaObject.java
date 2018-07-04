import java.sql.ResultSet;

public interface NbaObject {

  // sets all fields in this NbaObject according to the data in the given ResultSet.
  void setObject(ResultSet rs);

}
