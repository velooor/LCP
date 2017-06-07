package by.training.zakharchenya.courseproject.logic;

import by.training.zakharchenya.courseproject.dao.AccountDAO;
import by.training.zakharchenya.courseproject.database.ConnectionPool;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.exception.DAOException;
import by.training.zakharchenya.courseproject.exception.LogicException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginLogic {
    private static final Logger LOG = LogManager.getLogger();
    public static boolean checkLogin(String emailOrLogin, String password) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            connection.setAutoCommit(false);
            boolean result = false;
            AccountDAO accountDAO = new AccountDAO(connection);
            result = accountDAO.checkPassword(emailOrLogin, password);
            connection.commit();
            return result;
        } catch (SQLException | DAOException e) {
            LOG.log(Level.ERROR, "Problems with checkLogin operation.", e);
            return false;
        }
    }
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