package by.training.zakharchenya.courseproject.logic;

import by.training.zakharchenya.courseproject.dao.AccountDAO;
import by.training.zakharchenya.courseproject.dao.MailDAO;
import by.training.zakharchenya.courseproject.database.ConnectionPool;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.Message;
import by.training.zakharchenya.courseproject.exception.DAOException;
import by.training.zakharchenya.courseproject.validator.AccountValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Class of logic, that provides service functions, while working with messages commands.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class MailLogic {
    private static final Logger LOG = LogManager.getLogger();
    public enum Result {
        EXCEPTION, SUCCESS, WRONG_LOGIN, INCORRECT_LOGIN
    }

    /**Sents new message in database.
     * @param login addressee
     * @param theme theme of the message
     * @param message body of the message
     * @param creatorId account id of creator of the message
     * @param forAdmin true, if the message is for administrators
     * @return corresponding result enum
     */
    public static MailLogic.Result sendMessage(String login, String theme, String message, int creatorId, boolean forAdmin) {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            connection.setAutoCommit(false);
            MailLogic.Result res;
            MailDAO mailDAO = new MailDAO(connection);
            AccountDAO accountDAO = new AccountDAO(connection);
            if(!forAdmin){
                if (!AccountValidator.validateLogin(login)) {
                    res = MailLogic.Result.INCORRECT_LOGIN;
                } else if(accountDAO.checkLoginUniqueness(login)){
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
            LOG.log(Level.ERROR, "Problems with sendMessage operation.", e);
            return MailLogic.Result.EXCEPTION;
        }
    }

    /**Deletes message by id in database.
     * @param messageId message id
     * @return corresponding result enum
     */
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
            LOG.log(Level.ERROR, "Problems with deleteMessage operation.", e);
            return MailLogic.Result.EXCEPTION;
        }
    }

    /**Updates message status by id in database.
     * @param isRead true, if message read
     * @param messageId message id
     * @return corresponding result enum
     */
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
            LOG.log(Level.ERROR, "Problems with updateMessage operation.", e);
            return MailLogic.Result.EXCEPTION;
        }
    }

    /**Updates message status by id in database.
     * @param account true, if message read
     * @return list of messages
     */
    public static List<Message> loadAllUserIncomingMessages(Account account){
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            connection.setAutoCommit(false);
            MailDAO messageDAO = new MailDAO(connection);

            List<Message> result = messageDAO.findAllMessagesByAccountId(account.getAccountId());

            connection.commit();
            return result;
        } catch (SQLException | DAOException e) {
            LOG.log(Level.ERROR, "Problems with loadAllUserIncomingMessages operation.", e);
            return null;
        }
    }
}
