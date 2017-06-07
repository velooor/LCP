package by.training.zakharchenya.courseproject.dao;

import by.training.zakharchenya.courseproject.exception.DAOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Class DAO, serves for working with finance data in database
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class MoneyDAO extends AbstractDAO {

    private static final String SQL_ADD_PAYMENT ="UPDATE `creditcardinfo` SET `creditCard`=?, `cardValid`=?, `secretCode`=?, `moneyAmount`=`moneyAmount`+? "
            +" WHERE `account` = ?";
    private static final String SQL_UPDATE_AMOUNT ="UPDATE `creditcardinfo` SET `moneyAmount`= ? "
            +" WHERE `account` = ?";
    private static final String SQL_DOWN_AMOUNT ="UPDATE `creditcardinfo` SET `moneyAmount`=`moneyAmount`-? "
            +" WHERE `account` = ?";
    private static final String SQL_DOWN_BLOCKED_AMOUNT ="UPDATE `creditcardinfo` SET `blockedMoney`=`blockedMoney`-? "
            +" WHERE `account` = ?";
    private static final String SQL_TOPUP_BLOCKED_AMOUNT ="UPDATE `creditcardinfo` SET `blockedMoney`=`blockedMoney`+ ? "
            +" WHERE `account` = ?";
    private static final String SQL_TOPUP_AMOUNT ="UPDATE `creditcardinfo` SET `moneyAmount`=`moneyAmount`+ ? "
            +" WHERE `account` = ?";
    private static final String SQL_COUNT_MONEY_BALANCE = "SELECT SUM(moneyAmount) AS MoneyBalance FROM `creditcardinfo` WHERE (`account` = ?)";
    private static final String SQL_FIND_CREDIT_CARD_INFO = "SELECT `creditCard`, `cardValid` FROM `creditcardinfo` WHERE (`account` = ?)";

    private static final String BALANCE = "MoneyBalance";
    private static final String CREDIT_CARD_COLUMN = "creditCard";
    private static final String CARD_VALID_COLUMN = "cardValid";

    public MoneyDAO(Connection connection) {
        super(connection);
    }

    /**Adds money to money account in database.
     * @param accountId account id
     * @param creditcard credit card number
     * @param ccv code CCV
     * @param month month
     * @param year year
     * @param amount account amount
     * @return true if everything is successful
     * @throws DAOException signals, that statement was not executed successfully
     */
    public boolean addPayment(int accountId, String creditcard, String ccv, int month, int year, int amount) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_PAYMENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, creditcard);
            statement.setString(2, "20" + year + "-" + month + "-" + "01");
            statement.setString(3, ccv);
            statement.setInt(4, amount);
            statement.setInt(5, accountId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Reduces money account in database.
     * @param accountId account id
     * @param amount account amount
     * @return true if everything is successful
     * @throws DAOException signals, that statement was not executed successfully
     */
    public boolean downAmount(int accountId, int amount) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DOWN_AMOUNT)) {
            statement.setInt(1, amount);
            statement.setInt(2, accountId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Reduces blocked money account in database.
     * @param accountId account id
     * @param amount account amount
     * @return true if everything is successful
     * @throws DAOException signals, that statement was not executed successfully
     */
    public boolean downBlockedAmount(int accountId, int amount) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_DOWN_BLOCKED_AMOUNT)) {
            statement.setInt(1, amount);
            statement.setInt(2, accountId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Top up blocked money account in database.
     * @param accountId account id
     * @param amount account amount
     * @return true if everything is successful
     * @throws DAOException signals, that statement was not executed successfully
     */
    public boolean topUpBlockedAmount(int accountId, int amount) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_TOPUP_BLOCKED_AMOUNT)) {
            statement.setInt(1, amount);
            statement.setInt(2, accountId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Gets money amount in database.
     * @param accountId account id
     * @return money amount
     * @throws DAOException signals, that statement was not executed successfully
     */
    public int countCreditBalance(int accountId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_COUNT_MONEY_BALANCE)) {
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(BALANCE);
            }
            else return 0;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Gets credit card info in database.
     * @param accountId account id
     * @return credit card info
     * @throws DAOException signals, that statement was not executed successfully
     */
    public List<String> showCreditCardInfo(int accountId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_CREDIT_CARD_INFO)) {
            statement.setInt(1, accountId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                List<String> res = new ArrayList<>();
                res.add(resultSet.getString(CREDIT_CARD_COLUMN));
                if(res.get(0) == null) return null;
                res.add(resultSet.getString(CARD_VALID_COLUMN).substring(5,7));
                res.add(resultSet.getString(CARD_VALID_COLUMN).substring(2,4));
                return res;
            }
            else return null;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Tops up money account in database.
     * @param accountId account id
     * @param amount money amount
     * @return true if everything is successful
     * @throws DAOException signals, that statement was not executed successfully
     */
    public boolean topUpAmount(int accountId, int amount) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_TOPUP_AMOUNT)) {
            statement.setInt(1, amount);
            statement.setInt(2, accountId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }


}