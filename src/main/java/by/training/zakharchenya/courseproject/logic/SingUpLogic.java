package by.training.zakharchenya.courseproject.logic;

import by.training.zakharchenya.courseproject.dao.AccountDAO;
import by.training.zakharchenya.courseproject.database.ConnectionPool;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.exception.DAOException;
import by.training.zakharchenya.courseproject.validator.AccountValidator;

import java.sql.Connection;
import java.sql.SQLException;

public class SingUpLogic {
    public enum Result {
        EXCEPTION, VALID, INVALID_LOGIN, INVALID_EMAIL, INVALID_PASSWORD, PASSWORDS_NOT_EQUALS, LOGIN_NOT_UNIQUE
    }
    public static Result singup(String name, String surname, String login, String email, String password, String repPassword, String birthDate) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            connection.setAutoCommit(false);
            Result res;
            AccountDAO accountDAO = new AccountDAO(connection);
            if (!AccountValidator.validateLogin(login)) {
                res = Result.INVALID_LOGIN;
            } else if (!AccountValidator.validateEmail(email)) {
                res = Result.INVALID_EMAIL;
            } else if (!AccountValidator.validatePassword(password)) {
                res = Result.INVALID_PASSWORD;
            } else if (!password.equals(repPassword)) {
                res = Result.PASSWORDS_NOT_EQUALS;
            } else if (!accountDAO.checkLoginUniqueness(login)) {
                res = Result.LOGIN_NOT_UNIQUE;
            } else {
                accountDAO.addAccount(name,surname,login,email,password, birthDate);
                res = Result.VALID;
            }
            connection.commit();
            return res;
        } catch (SQLException | DAOException e) {
            //throw new LogicException("Problems with signIn operation.", e);
            return Result.EXCEPTION;
        }
    }
}
