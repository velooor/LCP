package by.training.zakharchenya.courseproject.logic;

import by.training.zakharchenya.courseproject.dao.AccountDAO;
import by.training.zakharchenya.courseproject.database.ConnectionPool;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class of logic, that provides service functions, while working with sign in commands.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class LoginLogic {
    private static final Logger LOG = LogManager.getLogger();

    /**Checks user login and password.
     * @param emailOrLogin account id
     * @param password old password
     * @return true, if login and password are right
     */
    public static boolean checkLogin(String emailOrLogin, String password) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            connection.setAutoCommit(false);
            boolean result;
            AccountDAO accountDAO = new AccountDAO(connection);
            result = accountDAO.checkPassword(emailOrLogin, password);
            connection.commit();
            return result;
        } catch (SQLException | DAOException e) {
            LOG.log(Level.ERROR, "Problems with checkLogin operation.", e);
            return false;
        }
    }

    /**REturn account by login.
     * @param login account login
     * @return account object
     */
    public static Account getAccount(String login) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            AccountDAO accountDAO = new AccountDAO(connection);
            Account result = accountDAO.findAccountByLogin(login);
            return result;
        } catch (SQLException | DAOException e) {
            LOG.log(Level.ERROR, "Problems with getAccount operation.", e);
            return null;
        }
    }

    /**REturn account by id.
     * @param id account id
     * @return account object
     */
    public static Account getAccount(int id) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            AccountDAO accountDAO = new AccountDAO(connection);
            return accountDAO.findAccountByID(id);
        } catch (SQLException | DAOException e) {
            LOG.log(Level.ERROR, "Problems with getAccount operation.", e);
            return null;
        }
    }
}