package database;
import java.util.*;
import java.sql.*;

public class RestaurantDatabase {
    private String url = "jdbc:mysql://localhost:3306/resturant";
    private String user = "root";
    private String password = "suchiSQL";
    private Connection conn;

    public RestaurantDatabase() {
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return conn;
    }


public List<Map<String, String>> executeQuery(String sql) {
    List<Map<String, String>> resultList = new ArrayList<>();

    try {
        Statement stmt = conn.createStatement();
        boolean result = stmt.execute(sql);

        if (result) {
            // The result is a ResultSet object
            ResultSet rs = stmt.getResultSet();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();

            while(rs.next()){
                Map<String, String> rowMap = new HashMap<>();
                for (int i = 1; i <= columnsNumber; i++) {
                    String columnName = rsmd.getColumnName(i);
                    String columnValue = rs.getString(i);
                    rowMap.put(columnName, columnValue);
                }
                resultList.add(rowMap);
            }

            rs.close();
        } else {
            // The result is an update count
            int updateCount = stmt.getUpdateCount();
            System.out.println("Update count: " + updateCount);
        }

        stmt.close();
    } catch(SQLException e) {
        e.printStackTrace();
    }

    return resultList;
}

}
