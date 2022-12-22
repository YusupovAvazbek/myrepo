package service;

import model.Result;
import model.User;

import java.sql.*;

public class DbService {
    String url="jdbc:postgresql://localhost:5432/app-auth";
    String dbUser="postgres";
    String dbPassword="1111";
    public Result registerUser(User user){
        Connection connection=null;
        try {
            Class.forName("org.postgresql.Driver");
            connection= DriverManager.getConnection(url,dbUser,dbPassword);

            String checkUsernameQuery="select count(*) from users where username='"+user.getUsername()+"'";
            Statement statement=connection.createStatement();

            ResultSet resultSetUsername = statement.executeQuery(checkUsernameQuery);

            int countUsername=0;
            while(resultSetUsername.next()){
                countUsername=resultSetUsername.getInt(1);
            }
            if(countUsername>0){
                return new Result("username already exist",false);
            }

            String insertQuery="insert into users(username,password) values('"+user.getUsername()+"','"+user.getPassword()+"');";
            statement.execute(insertQuery);
            return new Result("Successfully registred",true);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Result("error server",false);
    }

    public User LoginUser(String username, String password){
        Connection connection=null;

        try {
            Class.forName("org.postgresql.Driver");
            connection=DriverManager.getConnection(url,dbUser,dbPassword);
            String query="select * from users where username=? and password=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String userName=resultSet.getString(2);
                String passwordd=resultSet.getString(3);
                User user=new User(userName,passwordd);
                return user;
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public User LoadUser(String username){
        Connection connection=null;

        try {
            Class.forName("org.postgresql.Driver");
            connection=DriverManager.getConnection(url,dbUser,dbPassword);
            String query="select * from users where username=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String userName=resultSet.getString(2);
                String passwordd=resultSet.getString(3);
                User user=new User(userName,passwordd);
                return user;
            }
            return null;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
