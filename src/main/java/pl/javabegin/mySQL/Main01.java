package pl.javabegin.mySQL;

import java.sql.Connection;
import java.sql.SQLException;

public class Main01 {
    public static void main(String[] args) {
        try(Connection conn = DBUtil.connect()) {
            System.out.println("Connect successful");
        }catch (SQLException e){
            System.out.println("Oooopppssss");
        }
    }
}
