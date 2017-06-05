package by.training.zakharchenya.courseproject.logic;

import by.training.zakharchenya.courseproject.dao.AdminDAO;
import by.training.zakharchenya.courseproject.dao.MailDAO;
import by.training.zakharchenya.courseproject.database.ConnectionPool;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.Message;
import by.training.zakharchenya.courseproject.exception.DAOException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lagarde on 13.04.2017.
 */
public class AdminLogic {
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
            //throw new LogicException("Problems with signIn operation.", e);
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
            //throw new LogicException("Problems with signIn operation.", e);
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
            //throw new LogicException("Problems with signIn operation.", e);
            return null;
        }
    }
}
