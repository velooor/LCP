package by.training.zakharchenya.courseproject.logic;

import by.training.zakharchenya.courseproject.dao.AccountDAO;
import by.training.zakharchenya.courseproject.dao.MoneyDAO;
import by.training.zakharchenya.courseproject.database.ConnectionPool;
import by.training.zakharchenya.courseproject.exception.DAOException;
import by.training.zakharchenya.courseproject.exception.LogicException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Class of logic, that provides service functions, while working with money accounts commands.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class MoneyInfoLogic {

    /**Processes payment.
     * @param accountId account id
     * @param creditcard credit card number
     * @param ccv ccv code
     * @param month month
     * @param year year
     * @param amount money amount to add to money account
     * @return money amount
     * @throws LogicException signals, that there are problems with dao
     */
    public static int processPayment(int accountId, String creditcard, String ccv, int month, int year, int amount) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.addPayment(accountId, creditcard, ccv, month, year, amount);
            return amount;
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with processPayment operation.", e);
        }
    }

    /**Keeps amount of money.
     * @param accountId account id
     * @param rate credit card number
     * @throws LogicException signals, that there are problems with dao
     */
    public static void keepRate(int accountId, int rate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.downAmount(accountId, rate);
            moneyDAO.topUpBlockedAmount(accountId, rate);
            connection.commit();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with keepRate operation.", e);
        }
    }

    /**Creates empty money account.
     * @param accountId account id
     * @throws LogicException signals, that there are problems with dao
     */
    public static void registerCreditAccount(int accountId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            accountDAO.addMoneyAccount(accountId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with registerCreditAccount operation.", e);
        }
    }

    /**Return money amount.
     * @param accountId account id
     * @return money amount
     * @throws LogicException signals, that there are problems with dao
     */
    public static int countCreditBalance(int accountId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            return moneyDAO.countCreditBalance(accountId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with countCreditBalance operation.", e);
        }
    }

    /**Return credit card info.
     * @param accountId account id
     * @return list of data
     * @throws LogicException signals, that there are problems with dao
     */
    public static List<String> showCreditCardInfo(int accountId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            return moneyDAO.showCreditCardInfo(accountId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with showCreditCardInfo operation.", e);
        }
    }

    /**Finishes multi game.
     * @param accountId account id
     * @param rate account id
     * @throws LogicException signals, that there are problems with dao
     */
    public static void casinoWon(int accountId, int rate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.downBlockedAmount(accountId, rate);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with casinoWon operation.", e);
        }
    }

    /**Finishes multi game.
     * @param accountId account id
     * @param rate account id
     * @throws LogicException signals, that there are problems with dao
     */
    public static void playerWon(int accountId, int rate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.downBlockedAmount(accountId, rate);
            moneyDAO.topUpAmount(accountId, 2*rate);
            connection.commit();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with playerWon operation.", e);
        }
    }

    /**Finishes multi game.
     * @param winnerId winner account id
     * @param loserId loser account id
     * @param rate account id
     * @throws LogicException signals, that there are problems with dao
     */
    public static void finishMultiGame(int winnerId, int loserId, int rate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.downBlockedAmount(winnerId, rate);
            moneyDAO.downBlockedAmount(loserId, rate);
            moneyDAO.topUpAmount(winnerId, 2*rate);
            connection.commit();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with finishMultiGame operation.", e);
        }
    }

    /**Finishes multi game as draw.
     * @param playerId player account id
     * @param creatorId creator account id
     * @param rate rate
     * @throws LogicException signals, that there are problems with dao
     */
    public static void finishMultiGameDraw(int playerId, int creatorId, int rate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.downBlockedAmount(playerId, rate);
            moneyDAO.downBlockedAmount(creatorId, rate);
            moneyDAO.topUpAmount(playerId, rate);
            moneyDAO.topUpAmount(creatorId, rate);
            connection.commit();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with finishMultiGameDraw operation.", e);
        }
    }

    /**Tops up money account.
     * @param accountId account id
     * @param rate rate
     * @throws LogicException signals, that there are problems with dao
     */
    public static void topUpAccount(int accountId, int rate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.downBlockedAmount(accountId, rate);
            moneyDAO.topUpAmount(accountId, rate);
            connection.commit();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with topUpAccount operation.", e);
        }
    }

}
