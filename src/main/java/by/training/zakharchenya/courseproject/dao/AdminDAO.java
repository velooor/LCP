package by.training.zakharchenya.courseproject.dao;

import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Class DAO, serves for working with admin params in database
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class AdminDAO extends AbstractDAO {
    private enum Status{
        ACTIVE, BANNED
    }
    private static final String SQL_FIND_ALL_USERS = "SELECT `accountId`, `name`, `surname`, `login`, `email`, `avatar`, `birthDate`, `admin`, `status` FROM `account`";

    private static final String SQL_ADMIN_PARAMS = "SELECT `minNumOfPoints`, `minRate` FROM `adminstration`";
    private static final String SQL_UPDATE_USER_STATUS_BY_USER_ID = "UPDATE `account` SET `status` = ? WHERE `accountId` = ?";

    private static final String SQL_UPDATE_RATE = "UPDATE `adminstration` SET `minRate` = ?";
    private static final String SQL_UPDATE_POINTS = "UPDATE `adminstration` SET `minNumOfPoints` = ?";

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

    private static final String MINRATE_COLUMN = "minRate";
    private static final String MINPOINTS_COLUMN = "minNumOfPoints";

    public AdminDAO(Connection connection) {
        super(connection);
    }

    /**Updates account state by ID in database.
     * @param isActive value to update
     * @param id account ID
     * @throws DAOException signals, that statement was not executed successfully
     */
    public void updateUserById(boolean isActive, int id) throws DAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_USER_STATUS_BY_USER_ID);
            if(isActive){
                statement.setString(1, Status.ACTIVE.toString());
            } else{
                statement.setString(1, Status.BANNED.toString());
            }
            statement.setInt(2, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Problems with database(updateUserById).", e);
        }
    }
    /**Searches for all accounts in database.
     * @throws DAOException signals, that statement was not executed successfully
     * @return list of all accounts in database
     */
    public List<Account> findAllUsers() throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ALL_USERS)) {
            List<Account> accounts = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account acc = new Account();
                acc.setAccountId(resultSet.getInt(ACCOUNT_ID_COLUMN));
                acc.setName(resultSet.getString(NAME_COLUMN));
                acc.setSurname(resultSet.getString(SURNAME_COLUMN));
                acc.setLogin(resultSet.getString(LOGIN_COLUMN));
                acc.setEmail(resultSet.getString(EMAIL_COLUMN));
                Blob avatar = resultSet.getBlob(AVATAR_COLUMN);
                if (avatar == null) {
                    acc.setAvatar(null);
                } else {
                    acc.setAvatar(avatar.getBytes(1, (int)avatar.length()));
                }
                acc.setBirthDate(resultSet.getDate(BIRTHDATE_COLUMN));
                acc.setAdmin(resultSet.getBoolean(ADMIN_COLUMN));
                acc.setStatus(Account.StatusEnum.valueOf(resultSet.getString(STATUS_COLUMN).toUpperCase()));

                accounts.add(acc);
            }
            return accounts;
        } catch (SQLException e) {
            throw new DAOException("Problems with database(findAllUsers).", e);
        }
    }
    /**Updates admin params in database.
     * @param numOfPoints admin param
     * @param rate admin param
     * @throws DAOException signals, that statement was not executed successfully
     */
    public void updateAdminParams(int numOfPoints, int rate) throws DAOException {
        try {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_POINTS);
            PreparedStatement statement2 = connection.prepareStatement(SQL_UPDATE_RATE);
            statement.setInt(1, numOfPoints);
            statement.execute();
            statement2.setInt(1, rate);
            statement2.execute();
        } catch (SQLException e) {
            throw new DAOException("Problems with database(updateAdminParams).", e);
        }
    }
    /**Gets admin params from database.
     * @throws DAOException signals, that statement was not executed successfully
     * @return list of 2 admin params
     */
    public List<Integer> findAdminParams() throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADMIN_PARAMS)) {
            List<Integer> params = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                params.add(resultSet.getInt(MINPOINTS_COLUMN));
                params.add(resultSet.getInt(MINRATE_COLUMN));
            }
            return params;
        } catch (SQLException e) {
            throw new DAOException("Problems with database(findAdminParams).", e);
        }
    }
}
