package by.training.zakharchenya.courseproject.dao;

import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.exception.DAOException;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Class DAO, serves for working with accounts in database
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class AccountDAO extends AbstractDAO {

    private static final String SQL_ADD_ACCOUNT ="INSERT INTO `account` " +"(`name`, `surname`, `login`, `email`, `password`, `birthDate`, `admin`, `status`) " +"VALUES (?, ?, ?, ?, ?, ?, FALSE, 'active')";
    private static final String SQL_ADD_MONEY_ACCOUNT ="INSERT INTO `creditcardinfo` SET  `account` = ?, `moneyAmount` = 0, `blockedMoney` = 0";
    private static final String SQL_CHECK_PASSWORD_BY_EMAIL_OR_LOGIN = "SELECT 1 FROM `account` WHERE `login` = ? AND `password` = ?";

    private static final String SQL_CHECK_LOGIN_UNIQUENESS = "SELECT 1 FROM `account` WHERE `login` = ?";

    private static final String SQL_FIND_ACCOUNT_BY_LOGIN = "SELECT `accountId`, `name`, `surname`, `login`, `email`, `avatar`, `birthDate`, `admin`, `status` FROM `account` WHERE (`login` = ?)";
    private static final String SQL_UPDATE_AVATAR_BY_ACCOUNT_ID = "UPDATE `account` SET `avatar` = ? WHERE `accountId` = ?";
    private static final String SQL_ADMIN_LIST = "SELECT `accountId`, `name`, `surname`, `login`, `email`, `avatar`, `birthDate`, `admin`, `status` FROM `account` WHERE (`admin` = TRUE)";

    private static final String SQL_FIND_ACCOUNT_BY_ID = "SELECT `accountId`, `name`, `surname`, `login`, `email`, `avatar`, `birthDate`, `admin`, `status` FROM `account` WHERE (`accountId` = ?)";


    private static final String SQL_UPDATE_NAME_BY_ACCOUNT_ID = "UPDATE `account` SET `name` = ? WHERE `accountId` = ?";
    private static final String SQL_UPDATE_SURNAME_BY_ACCOUNT_ID = "UPDATE `account` SET `surname` = ? WHERE `accountId` = ?";
    private static final String SQL_CHECK_PASSWORD_BY_ID = "SELECT 1 FROM `account` WHERE `accountId` = ?  AND `password` = ?";
    private static final String SQL_UPDATE_PASSWORD_BY_ACCOUNT_ID = "UPDATE `account` SET `password` = ? WHERE `accountId` = ?";

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

    public AccountDAO(Connection connection) {
        super(connection);
    }

    /**Create account in database.
     * @param firstName user name
     * @param secondName user surname
     * @param login user login
     * @param email user email
     * @param password user password
     * @param birthDate user birthDate
     * @throws DAOException signals, that statement was not executed successfully
     * @return true, if statement was executed successfully
     */
    public boolean addAccount(String firstName, String secondName, String login, String email, String password, String birthDate) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, firstName);
            statement.setString(2, secondName);
            statement.setString(3, login);
            statement.setString(4, email);
            statement.setString(5, password);
            statement.setString(6, birthDate);
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Problems with adding account to database.", e);
        }
    }
    /**Create money account in database.
     * @param accountId account ID
     * @throws DAOException signals, that statement was not executed successfully
     * @return true, if statement executed successfully
     */
    public boolean addMoneyAccount(int accountId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_MONEY_ACCOUNT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, accountId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Problems with adding mondey account to database.", e);
        }
    }

    /**Check password during singing in.
     * @param login user login
     * @param password user password
     * @throws DAOException signals, that statement was not executed successfully
     * @return true, if password is right
     */
    public boolean checkPassword(String login, String password) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CHECK_PASSWORD_BY_EMAIL_OR_LOGIN)) {
            statement.setString(1, login);
            statement.setString(2, password);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new DAOException("Problems with database(checkPassword).", e);
        }
    }
    /**Check password by account ID.
     * @param accountId account ID
     * @param password user password
     * @throws DAOException signals, that statement was not executed successfully
     * @return true, if password is right
     */
    public boolean checkPassword(int accountId, String password) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CHECK_PASSWORD_BY_ID)) {
            statement.setInt(1, accountId);
            statement.setString(2, password);
            return statement.executeQuery().next();
        } catch (SQLException e) {
            throw new DAOException("Problems with database(checkPassword by ID).", e);
        }
    }
    /**Check whether login is unique.
     * @param login user login
     * @throws DAOException signals, that statement was not executed successfully
     * @return true, if login is unique
     */
    public boolean checkLoginUniqueness(String login) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_CHECK_LOGIN_UNIQUENESS)) {
            statement.setString(1, login);
            return !statement.executeQuery().next();
        } catch (SQLException e) {
            throw new DAOException("Problems with database(checkLoginUniqueness).", e);
        }
    }
    /**Searches account by user login in database.
     * @param login user login
     * @throws DAOException signals, that statement was not executed successfully
     * @return account
     */
    public Account findAccountByLogin(String login) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Account result = new Account();
                result.setAccountId(resultSet.getInt(ACCOUNT_ID_COLUMN));
                result.setName(resultSet.getString(NAME_COLUMN));
                result.setSurname(resultSet.getString(SURNAME_COLUMN));
                result.setLogin(resultSet.getString(LOGIN_COLUMN));
                result.setEmail(resultSet.getString(EMAIL_COLUMN));
                Blob avatar = resultSet.getBlob(AVATAR_COLUMN);
                if (avatar == null) {
                    result.setAvatar(null);
                } else {
                    result.setAvatar(avatar.getBytes(1, (int)avatar.length()));
                }
                result.setBirthDate(resultSet.getDate(BIRTHDATE_COLUMN));
                result.setAdmin(resultSet.getBoolean(ADMIN_COLUMN));
                result.setStatus(Account.StatusEnum.valueOf(resultSet.getString(STATUS_COLUMN).toUpperCase()));
                return result;
            }
            else {
                throw new DAOException("Can't find account by email or login in database.");
            }
        } catch (SQLException e) {
            throw new DAOException("Problems with finding account by email or login in database.", e);
        }
    }
    /**Searches account by user ID in database.
     * @param accountId user accountId
     * @throws DAOException signals, that statement was not executed successfully
     * @return account
     */
    public Account findAccountByID(int accountId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ACCOUNT_BY_ID)) {
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Account result = new Account();
                result.setAccountId(resultSet.getInt(ACCOUNT_ID_COLUMN));
                result.setName(resultSet.getString(NAME_COLUMN));
                result.setSurname(resultSet.getString(SURNAME_COLUMN));
                result.setLogin(resultSet.getString(LOGIN_COLUMN));
                result.setEmail(resultSet.getString(EMAIL_COLUMN));
                Blob avatar = resultSet.getBlob(AVATAR_COLUMN);
                if (avatar == null) {
                    result.setAvatar(null);
                } else {
                    result.setAvatar(avatar.getBytes(1, (int)avatar.length()));
                }
                result.setBirthDate(resultSet.getDate(BIRTHDATE_COLUMN));
                result.setAdmin(resultSet.getBoolean(ADMIN_COLUMN));
                result.setStatus(Account.StatusEnum.valueOf(resultSet.getString(STATUS_COLUMN).toUpperCase()));
                return result;
            }
            else {
                throw new DAOException("Can't find account by id in database.");
            }

        } catch (SQLException e) {
            throw new DAOException("Problems with finding account by id in database.", e);
        }
    }
    /**Searches all administrator accounts.
     * @throws DAOException signals, that statement was not executed successfully
     * @return list of admin accounts
     */
    public List<Account> findAdmins() throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADMIN_LIST)) {
            List<Account> accounts = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account result = new Account();
                result.setAccountId(resultSet.getInt(ACCOUNT_ID_COLUMN));
                result.setName(resultSet.getString(NAME_COLUMN));
                result.setSurname(resultSet.getString(SURNAME_COLUMN));
                result.setLogin(resultSet.getString(LOGIN_COLUMN));
                result.setEmail(resultSet.getString(EMAIL_COLUMN));
                Blob avatar = resultSet.getBlob(AVATAR_COLUMN);
                if (avatar == null) {
                    result.setAvatar(null);
                } else {
                    result.setAvatar(avatar.getBytes(1, (int)avatar.length()));
                }
                result.setBirthDate(resultSet.getDate(BIRTHDATE_COLUMN));
                result.setAdmin(resultSet.getBoolean(ADMIN_COLUMN));
                result.setStatus(Account.StatusEnum.valueOf(resultSet.getString(STATUS_COLUMN).toUpperCase()));
                accounts.add(result);
            }
            return accounts;
        } catch (SQLException e) {
            throw new DAOException("Problems with database(findAdmins).", e);
        }
    }
    /**Updates avatar by account ID.
     * @param accountId user accountId
     * @param avatar avatar for updating
     * @throws DAOException signals, that statement was not executed successfully
     */
    public void updateAvatarByAccountId(int accountId, byte[] avatar) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_AVATAR_BY_ACCOUNT_ID)) {
            if (avatar != null && avatar.length != 0) {
                statement.setBlob(1, new ByteArrayInputStream(avatar));
            } else {
                statement.setNull(1, Types.BLOB);
            }
            statement.setInt(2, accountId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Problems with database(updateAvatarByAccountId).", e);
        }
    }
    /**Updates name and surname by account ID.
     * @param accountId user accountId
     * @param name user name
     * @param surname user surname
     * @throws DAOException signals, that statement was not executed successfully
     */
    public void updateNameSurnameByAccountId(int accountId, String name, String surname) throws DAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_NAME_BY_ACCOUNT_ID);
            PreparedStatement statement2 = connection.prepareStatement(SQL_UPDATE_SURNAME_BY_ACCOUNT_ID);
            statement.setString(1, name);
            statement.setInt(2, accountId);
            statement.execute();
            statement2.setString(1, surname);
            statement2.setInt(2, accountId);
            statement2.execute();
        } catch (SQLException e) {
            throw new DAOException("Problems with database(updateNameSurnameByAccountId).", e);
        }
    }
    /**Updates password by account ID.
     * @param accountId user accountId
     * @param password user password
     * @throws DAOException signals, that statement was not executed successfully
     */
    public void updatePasswordByAccountId(int accountId, String password) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD_BY_ACCOUNT_ID)) {
            statement.setString(1, password);
            statement.setInt(2, accountId);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Problems with database(updatePasswordByAccountId).", e);
        }
    }
}
