package pl.javabegin.mySQL;

import java.sql.*;

public class DBUtil {

    private static final String DB_USER = "root";
    private static final String DB_PASS = "coderslab";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/workshop2?useSSL=false&characterEncoding=utf8&serverTimezone=UTC";


    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static void insert(Connection conn, String query, String... params) {
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                statement.setString(i + 1, params[i]);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printData(Connection conn, String query, String... columnNames) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery();) {
            while (resultSet.next()) {
                for (String columnNam : columnNames) {
                    System.out.println(resultSet.getString(columnNam));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printDataVersion(Connection conn, String query, String...columnNames){
        try(PreparedStatement preStmt = conn.prepareStatement(query);
            ResultSet resultSet = preStmt.executeQuery()){
            while (resultSet.next()){
                for (int i =0; i< columnNames.length; i++){
                    System.out.print(columnNames[i]+": "+resultSet.getString(columnNames[i])+" ");
                }
                System.out.println();
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private static final String DELETE_QUERY = "DELETE FROM tableName where id = ?";

    public static void remove(Connection conn, String tableName, int id) {
        try (PreparedStatement statement =
                     conn.prepareStatement(DELETE_QUERY.replace("tableName", tableName));) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}