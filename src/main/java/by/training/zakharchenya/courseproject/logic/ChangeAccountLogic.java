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
 * Class of logic, that provides service functions, while working with setting commands.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class ChangeAccountLogic {
    public enum Result {
        EXCEPTION, SUCCESS, PASSWORD_NOT_EQUALS, INCORRECT_NEW_PASSWORD, INVALID_PASSWORD
    }
    /**Updates user avatar.
     * @param accountId user id
     * @param avatar user avatar for update
     * @throws LogicException signals, that there are problems with dao
     */
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

    /**Updates user name and surname.
     * @param accountId user id
     * @param name user name
     * @param surname user surname
     * @throws LogicException signals, that there are problems with dao
     */
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

    /**Updates admin params.
     * @param numOfPoints minimum number of points
     * @param rate minimum rate
     * @throws LogicException signals, that there are problems with dao
     */
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

    /**Checks and updates user password.
     * @param accountId account id
     * @param oldP old password
     * @param newP new password
     * @param repP repeated password
     * @return result object
     * @throws LogicException signals, that there are problems with dao
     */
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
                throw new LogicException("Problems with updating password.", e);
            }
        }else{
            res = Result.PASSWORD_NOT_EQUALS;
        }
        return res;

    }
}
