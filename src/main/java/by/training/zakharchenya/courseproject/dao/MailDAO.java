package by.training.zakharchenya.courseproject.dao;

import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.Message;
import by.training.zakharchenya.courseproject.exception.DAOException;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Class DAO, serves for working with mail data in database
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class MailDAO extends AbstractDAO {

    private static final String SQL_ADD_MESSAGE ="INSERT INTO `message` (`creator`, `addressee`, `isRead`, `creationTime`, `message`, `theme`) "
                                                                    +"VALUES (?, ?, FALSE, ?, ?, ?)";

    private static final String SQL_FIND_INCOME_MESSAGES_BY_ACCOUNT_ID =
            "SELECT `messageId`,`theme`, `message`, `creationTime`, `isRead`, `creator`, `name`, `surname`, `login`, `admin`"+
            "FROM (SELECT `messageId`, `theme`, `message`, `creationTime`, `isRead`, `creator` FROM `message` WHERE `addressee` = ?) AS `mess` " +
            "JOIN `account` ON `account`.`accountId`=`mess`.`creator`";

    private static final String SQL_REMOVE_MESSAGE_BY_ID = "DELETE FROM `message` WHERE `messageId` = ?";

    private static final String SQL_UPDATE_ISREAD_BY_MESSAGE_ID = "UPDATE `message` SET `isRead` = ? WHERE `messageId` = ?";

    private static final String MESSAGE_ID_COLUMN = "messageId";
    private static final String MESSAGE_CREATOR_COLUMN = "creator";
    private static final String MESSAGE_ADDRESSEE_COLUMN = "addressee";
    private static final String MESSAGE_ISREAD_COLUMN = "isRead";
    private static final String MESSAGE_TIME_COLUMN = "creationTime";
    private static final String MESSAGE_MESSAGE_COLUMN = "message";
    private static final String MESSAGE_THEME_COLUMN = "theme";


    private static final String ACCOUNT_ID_COLUMN = "accountId";
    private static final String NAME_COLUMN = "name";
    private static final String SURNAME_COLUMN = "surname";
    private static final String LOGIN_COLUMN = "login";
    private static final String EMAIL_COLUMN = "email";
    private static final String PASSWORD_COLUMN = "password";
    private static final String AVATAR_COLUMN = "avatar";
    private static final String BIRTHDATE_COLUMN = "birthDate";
    private static final String ADMIN_COLUMN = "admin";
    private static final String STATUS_COLUMN = "status";

    public MailDAO(Connection connection) {
        super(connection);
    }

    /**Adds new message in database.
     * @param login addressee
     * @param theme theme of the message
     * @param message body of the message
     * @param creatorId account id of creator of the message
     * @param forAdmin true, if the message is for administrators
     * @param accountDAO AccountDAO object
     * @return true if everything is successful
     * @throws DAOException signals, that statement was not executed successfully
     */
    public boolean addMessage(String login, String theme, String message, int creatorId, boolean forAdmin, AccountDAO accountDAO) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_MESSAGE, Statement.RETURN_GENERATED_KEYS)) {
            if(forAdmin){
                statement.setInt(1, creatorId);
                LocalDateTime localDateTime = LocalDateTime.now();
                statement.setTimestamp(3, Timestamp.valueOf(localDateTime));
                statement.setString(4, message);
                statement.setString(5, theme);
                List<Account> admins = accountDAO.findAdmins();
                for(int i = 0; i < admins.size(); i++){
                    statement.setInt(2, admins.get(i).getAccountId());
                    statement.execute();
                }
            }else{
                statement.setInt(1, creatorId);
                Account addressee = accountDAO.findAccountByLogin(login);
                statement.setInt(2, addressee.getAccountId());
                LocalDateTime localDateTime = LocalDateTime.now();
                statement.setTimestamp(3, Timestamp.valueOf(localDateTime));
                statement.setString(4, message);
                statement.setString(5, theme);
                statement.execute();
            }

            return true;
        } catch (SQLException e) {
            throw new DAOException("Problems with adding account to database.", e);
        }
    }

    /**Finds all messages by account id in database.
     * @param accountId account id of addressee
     * @return list of messages
     * @throws DAOException signals, that statement was not executed successfully
     */
    public List<Message> findAllMessagesByAccountId(int accountId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_INCOME_MESSAGES_BY_ACCOUNT_ID)) {
            List<Message> messages = new ArrayList<>();
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account creator = new Account(  resultSet.getString(NAME_COLUMN),
                                                resultSet.getString(SURNAME_COLUMN),
                                                resultSet.getString(LOGIN_COLUMN),
                                                resultSet.getBoolean(ADMIN_COLUMN));

                Message mess = new Message( creator,
                                            resultSet.getBoolean(MESSAGE_ISREAD_COLUMN),
                                            resultSet.getTimestamp(MESSAGE_TIME_COLUMN).toLocalDateTime(),
                                            resultSet.getString(MESSAGE_MESSAGE_COLUMN),
                                            resultSet.getString(MESSAGE_THEME_COLUMN),
                                            resultSet.getInt(MESSAGE_ID_COLUMN));
                messages.add(mess);
            }
            return messages;
        } catch (SQLException e) {
            throw new DAOException("Problems with finding messages by account id and in database.", e);
        }
    }

    /**Removes message in database.
     * @param id game id
     * @throws DAOException signals, that statement was not executed successfully
     */
    public void removeMessageById(int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_MESSAGE_BY_ID)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Updates message status by id in database.
     * @param isRead account id of addressee
     * @param id game id
     * @throws DAOException signals, that statement was not executed successfully
     */
    public void updateMessageById(boolean isRead, int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_ISREAD_BY_MESSAGE_ID)) {
            statement.setBoolean(1, isRead);
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

}