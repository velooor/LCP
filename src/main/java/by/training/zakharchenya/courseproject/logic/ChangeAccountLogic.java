package by.training.zakharchenya.courseproject.logic;

import by.training.zakharchenya.courseproject.dao.AccountDAO;
import by.training.zakharchenya.courseproject.dao.AdminDAO;
import by.training.zakharchenya.courseproject.database.ConnectionPool;
import by.training.zakharchenya.courseproject.exception.DAOException;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.validator.AccountValidator;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Lagarde on 28.03.2017.
 */
public class ChangeAccountLogic {
    public enum Result {
        EXCEPTION, SUCCESS, PASSWORD_NOT_EQUALS, INCORRECT_NEW_PASSWORD, INVALID_PASSWORD
    }
    public static void updateAvatar(int accountId, byte[] avatar) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            AccountDAO accountDAO = new AccountDAO(connection);
            accountDAO.updateAvatarByAccountId(accountId, avatar);
            connection.commit();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with updating account avatar.", e);
        }
    }
    public static void updateNameSurname(int accountId, String name, String surname) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            AccountDAO accountDAO = new AccountDAO(connection);
            accountDAO.updateNameSurnameByAccountId(accountId, name, surname);
            connection.commit();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with updating name and surname.", e);
        }
    }
    public static void updateAdminParams(int numOfPoints, int rate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            AdminDAO adminDAO = new AdminDAO(connection);
            adminDAO.updateAdminParams(numOfPoints, rate);
            connection.commit();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with updating admin params.", e);
        }
    }
    public static Result updatePassword(int accountId, String oldP, String newP, String repP) throws LogicException {
        Result res;
        if(newP.equals(repP)){
            try (Connection connection = ConnectionPool.getInstance().getConnection()) {
                connection.setAutoCommit(false);
                AccountDAO accountDAO = new AccountDAO(connection);
                if (!AccountValidator.validatePassword(newP)) {
                    res = Result.INCORRECT_NEW_PASSWORD;
                } else if(accountDAO.checkPassword(accountId, oldP)){
                    accountDAO.updatePasswordByAccountId(accountId, newP);
                    res = Result.SUCCESS;
                }else{
                    res = Result.INVALID_PASSWORD;
                }

                connection.commit();
            } catch (SQLException | DAOException e) {
                throw new LogicException("Problems with updating account avatar.", e);
            }
        }else{
            res = Result.PASSWORD_NOT_EQUALS;
        }
        return res;

    }
}
