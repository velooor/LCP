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
 * Created by Lagarde on 30.04.2017.
 */
public class MoneyInfoLogic {
    public static int processPayment(int accountId, String creditcard, String ccv, int month, int year, int amount) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.addPayment(accountId, creditcard, ccv, month, year, amount);
            return amount;
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with updating name and surname.", e);
        }
    }
    public static void keepRate(int accountId, int rate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.downAmount(accountId, rate);
            moneyDAO.topUpBlockedAmount(accountId, rate);
            connection.commit();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with updating name and surname.", e);
        }
    }
    public static void registerCreditAccount(int accountId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            AccountDAO accountDAO = new AccountDAO(connection);
            accountDAO.addMoneyAccount(accountId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with updating name and surname.", e);
        }
    }
    public static int countCreditBalance(int accountId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            return moneyDAO.countCreditBalance(accountId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with updating name and surname.", e);
        }
    }

    public static List<String> showCreditCardInfo(int accountId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            return moneyDAO.showCreditCardInfo(accountId);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with updating name and surname.", e);
        }
    }

    public static void casinoWon(int accountId, int rate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.downBlockedAmount(accountId, rate);
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with updating name and surname.", e);
        }
    }
    public static void playerWon(int accountId, int rate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.downBlockedAmount(accountId, rate);
            moneyDAO.topUpAmount(accountId, 2*rate);
            connection.commit();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with updating name and surname.", e);
        }
    }
    public static void finishMultiGame(int winnerId, int loserId, int rate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.downBlockedAmount(winnerId, rate);
            moneyDAO.downBlockedAmount(loserId, rate);
            moneyDAO.topUpAmount(winnerId, 2*rate);
            connection.commit();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with updating name and surname.", e);
        }
    }
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
            throw new LogicException("Problems with updating name and surname.", e);
        }
    }
    public static void topUpAccount(int accountId, int rate) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            MoneyDAO moneyDAO = new MoneyDAO(connection);
            moneyDAO.downBlockedAmount(accountId, rate);
            moneyDAO.topUpAmount(accountId, rate);
            connection.commit();
        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with updating name and surname.", e);
        }
    }

}
