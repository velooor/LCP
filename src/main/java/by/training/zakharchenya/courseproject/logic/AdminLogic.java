package by.training.zakharchenya.courseproject.logic;

import by.training.zakharchenya.courseproject.dao.AdminDAO;
import by.training.zakharchenya.courseproject.dao.MailDAO;
import by.training.zakharchenya.courseproject.database.ConnectionPool;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.Message;
import by.training.zakharchenya.courseproject.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminLogic {

    private static final Logger LOG = LogManager.getLogger();
    public enum Result {
        EXCEPTION, SUCCESS, WRONG_LOGIN, INCORRECT_LOGIN
    }
    public static Result updateUserStatus(boolean isActive, int userId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            connection.setAutoCommit(false);
            Result res;
            AdminDAO adminDAO = new AdminDAO(connection);

            adminDAO.updateUserById(isActive, userId);
            res = Result.SUCCESS;

            connection.commit();
            return res;
        } catch (SQLException | DAOException e) {
            LOG.log(Level.ERROR, "Errors during updateUserStatus.", e);
            return Result.EXCEPTION;
        }
    }
    public static List<Account> loadAllUsers(){
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            connection.setAutoCommit(false);
            AdminDAO adminDAO = new AdminDAO(connection);

            List<Account> result = adminDAO.findAllUsers();

            connection.commit();
            return result;
        } catch (SQLException | DAOException e) {
            LOG.log(Level.ERROR, "Errors during loadAllUsers.", e);
            return null;
        }
    }
    public static List<Integer> loadParams(){
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            connection.setAutoCommit(false);
            AdminDAO adminDAO = new AdminDAO(connection);

            List<Integer> result = adminDAO.findAdminParams();

            connection.commit();
            return result;
        } catch (SQLException | DAOException e) {
            LOG.log(Level.ERROR, "Errors during loadParams.", e);
            return null;
        }
    }
}
