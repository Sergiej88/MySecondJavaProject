package pl.javabegin.entity;


import org.mindrot.jbcrypt.BCrypt;
import pl.javabegin.mySQL.DBUtil;

import java.sql.*;
import java.util.Arrays;

public class UserDao {

    private static final String CREATE_USER_QUERY = "INSERT INTO users (email, username, password) VALUES (?, ?, ?)";
    private static final String READ_USER_QUERY = "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY = "UPDATE users SET email = ?, username = ?, password = ? WHERE id= ?";
    private static final String DELETE_USER_QUERY = "DELETE FROM users WHERE id = ?";
    private static final String FIND_ALL = "SELECT * FROM users";
    private static User[] users = new User[0];

    public User create(User user){
        ResultSet rs = null;
        try(Connection conn = DBUtil.connect();
            PreparedStatement prstmt = conn.prepareStatement(CREATE_USER_QUERY, PreparedStatement.RETURN_GENERATED_KEYS)) {
            prstmt.setString(1, user.getEmail());
            prstmt.setString(2, user.getUserName());
            prstmt.setString(3, hashPassword(user.getPassword()));
            prstmt.execute();
            rs = prstmt.getGeneratedKeys();
            if(rs.next()){
                long id = rs.getLong(1);
                user.setId(id);
            }
            return user;
        }catch (SQLException e){
        e.printStackTrace();}
        finally {
            if(rs!=null){
                try {
                    rs.close();
                    System.out.println("Ya zakryl ResultSet");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public User getById(int userId){
        ResultSet rs = null;
        try(Connection conn = DBUtil.connect();
        PreparedStatement prstmt = conn.prepareStatement(READ_USER_QUERY)){
            prstmt.setString(1, Integer.toString(userId));
           rs = prstmt.executeQuery();
           if (rs.next()){
               User user = new User();
               user.setId(rs.getLong(1));
               user.setEmail(rs.getString(2));
               user.setUserName(rs.getString(3));
               user.setPassword(rs.getString(4));
               return user;
           }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            if(rs!=null){
                try {
                    rs.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void update(User user) {
        try (Connection conn = DBUtil.connect();
        PreparedStatement prstmt = conn.prepareStatement(UPDATE_USER_QUERY)) {
            prstmt.setString(1, user.getEmail());
            prstmt.setString(2, user.getUserName());
            prstmt.setString(3, hashPassword(user.getPassword()));
            prstmt.setLong(4, user.getId());
            prstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(long userId){
        try(Connection conn = DBUtil.connect();
            PreparedStatement prstmt = conn.prepareStatement(DELETE_USER_QUERY)) {
            prstmt.setLong(1, userId);
            prstmt.executeUpdate();
        }catch (SQLException e){e.printStackTrace();}
    }

    public User[] findAll(){
        try(Connection conn = DBUtil.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(FIND_ALL)) {
            while (rs.next()){
              users = addToArray(getById(rs.getInt(1)), users);
            }
        }catch (SQLException e){e.printStackTrace();}
        return users;
    }


        public String hashPassword (String password){
            return BCrypt.hashpw(password, BCrypt.gensalt());
        }

        private User[] addToArray(User u, User[] users){
        User[] tmpUsers = Arrays.copyOf(users, users.length+1);
        tmpUsers[users.length] = u;
        return tmpUsers;
        }

}
