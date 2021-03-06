package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;

import java.sql.*;

public class SQLHelper {

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
    }

    public static void cleanDB() {
        String auth_codes = "DELETE FROM auth_codes";
        String card_transactions = "DELETE FROM card_transactions";
        String cards = "DELETE FROM cards";
        String users = "DELETE FROM users";
        QueryRunner runner = new QueryRunner();
        try (Connection connection = getConnection()) {
            runner.update(connection, auth_codes);
            runner.update(connection, card_transactions);
            runner.update(connection, cards);
            runner.update(connection, users);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String getVerificationCode(DataHelper.AuthInfo info) {
        String login = info.getLogin();
        String verificationCodeQuery =
                "SELECT auth_codes.code" + " " +
                "FROM users INNER JOIN auth_codes ON users.id=auth_codes.user_id" + " " +
                "WHERE users.id =" + " " +
                    "(SELECT id" + " " +
                    "FROM users" + " " +
                    "WHERE login = ?)" + " " +
                "ORDER BY auth_codes.created DESC LIMIT 1;";
        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(verificationCodeQuery);
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getString("code");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
}