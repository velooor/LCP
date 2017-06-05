package by.training.zakharchenya.courseproject.logic;

import by.training.zakharchenya.courseproject.dao.AccountDAO;
import by.training.zakharchenya.courseproject.dao.MailDAO;
import by.training.zakharchenya.courseproject.database.ConnectionPool;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.Message;
import by.training.zakharchenya.courseproject.exception.DAOException;
import by.training.zakharchenya.courseproject.validator.AccountValidator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Lagarde on 02.04.2017.
 */
public class MailLogic {
    public enum Result {
        EXCEPTION, SUCCESS, WRONG_LOGIN, INCORRECT_LOGIN
    }
    public static MailLogic.Result sendMessage(String login, String theme, String message, int creatorId, boolean forAdmin) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            connection.setAutoCommit(false);
            MailLogic.Result res;
            MailDAO mailDAO = new MailDAO(connection);
            AccountDAO accountDAO = new AccountDAO(connection);
            if(!forAdmin){
                if (!AccountValidator.validateLogin(login)) {
                    res = MailLogic.Result.INCORRECT_LOGIN;
                } else if(!accountDAO.checkLoginUniqueness(login)){
                    res = MailLogic.Result.WRONG_LOGIN;
                } else {
                    mailDAO.addMessage(login, theme, message, creatorId, false, accountDAO);
                    res = MailLogic.Result.SUCCESS;
                }
            } else {
                mailDAO.addMessage(login, theme, message, creatorId, true, accountDAO);
                res = MailLogic.Result.SUCCESS;
            }
            connection.commit();
            return res;
        } catch (SQLException | DAOException e) {
            //throw new LogicException("Problems with signIn operation.", e);
            return MailLogic.Result.EXCEPTION;
        }
    }

    public static MailLogic.Result deleteMessage(int messageId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            connection.setAutoCommit(false);
            MailLogic.Result res;
            MailDAO mailDAO = new MailDAO(connection);

            mailDAO.removeMessageById(messageId);
            res = MailLogic.Result.SUCCESS;

            connection.commit();
            return res;
        } catch (SQLException | DAOException e) {
            //throw new LogicException("Problems with signIn operation.", e);
            return MailLogic.Result.EXCEPTION;
        }
    }
    public static MailLogic.Result updateMessage(boolean isRead, int messageId) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            connection.setAutoCommit(false);
            MailLogic.Result res;
            MailDAO mailDAO = new MailDAO(connection);

            mailDAO.updateMessageById(isRead, messageId);
            res = MailLogic.Result.SUCCESS;

            connection.commit();
            return res;
        } catch (SQLException | DAOException e) {
            //throw new LogicException("Problems with signIn operation.", e);
            return MailLogic.Result.EXCEPTION;
        }
    }
    public static List<Message> loadAllUserIncomingMessages(Account account){
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            connection.setAutoCommit(false);
            MailDAO messageDAO = new MailDAO(connection);

            List<Message> result = messageDAO.findAllMessagesByAccountId(account.getAccountId());

            connection.commit();
            return result;
        } catch (SQLException | DAOException e) {
            //throw new LogicException("Problems with signIn operation.", e);
            return null;
        }
    }
}
