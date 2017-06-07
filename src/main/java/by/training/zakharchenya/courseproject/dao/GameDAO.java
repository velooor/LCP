package by.training.zakharchenya.courseproject.dao;

import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.game.MultiGame;
import by.training.zakharchenya.courseproject.exception.DAOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/** Class DAO, serves for working with game data in database
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class GameDAO  extends AbstractDAO {

    private static final String SQL_ADD_GAME ="INSERT INTO `game` (`time`, `rate`, `creator`, `finished`) "
            +"VALUES (?, ?, ?, FALSE)";
    private static final String SQL_UPDATE_MULTI_GAME ="UPDATE `game` SET `player` = ? WHERE `gameId` = ?";
    private static final String SQL_UPDATE_MULTI_GAME_BY_ID ="UPDATE `game` SET `playerScore` = ?, `creatorScore` = ?, `lastPlayerResult` = ?, `lastCreatorResult` = ?," +
            " `finished` = ?, `playerMove` = ?  WHERE `gameId` = ?";

    private static final String SQL_FIND_WAITING_GAMES =
            "SELECT `gameId`,`time`, `rate`, `creator`, `finished`, `accountId`, `name`, `surname`, `login`"+
                    "FROM (SELECT `gameId`,`time`, `rate`, `creator`, `player`, `finished` FROM `game` WHERE `player` IS NULL) AS `ggame` " +
                    "JOIN `account` ON `account`.`accountId`=`ggame`.`creator`";

    private static final String SQL_FIND_MULTI_GAME_BY_ID =
            "SELECT `gameId`,`time`, `rate`, `creator`, `player`, `creatorScore`, `playerScore`, `lastPlayerResult`, `lastCreatorResult`, `finished`, `playerMove` FROM `game` WHERE `gameId`=?";

    private static final String SQL_FIND_MY_ACTIVE_GAMES =
            "SELECT `gameId`,`time`, `rate`, `creator`, `player`, `creatorScore`, `playerScore`, `lastPlayerResult`, `lastCreatorResult`, `finished`, `accountId`, `name`, `surname`, `login`"+
                    "FROM (SELECT `gameId`,`time`, `rate`, `creator`, `player`, `creatorScore`, `playerScore`, `lastPlayerResult`, `lastCreatorResult`, `finished` FROM `game` WHERE `player` = ?  ) AS `ggame` " +
                    "JOIN `account` ON `account`.`accountId`=`ggame`.`creator`";
    private static final String SQL_FIND_MY_ACTIVE_GAMES_ALT =
            "SELECT `gameId`,`time`, `rate`, `creator`, `player`, `creatorScore`, `playerScore`, `lastPlayerResult`, `lastCreatorResult`, `finished`, `accountId`, `name`, `surname`, `login`"+
                    "FROM (SELECT `gameId`,`time`, `rate`, `creator`, `player`, `creatorScore`, `playerScore`, `lastPlayerResult`, `lastCreatorResult`, `finished` FROM `game` WHERE `creator` = ? AND `player` IS NOT NULL  ) AS `ggame` " +
                    "JOIN `account` ON `account`.`accountId`=`ggame`.`creator`";

    private static final String SQL_REMOVE_GAME_BY_ID = "DELETE FROM `game` WHERE `gameId` = ?";

    private static final String ACCOUNT_ID_COLUMN = "accountId";
    private static final String NAME_COLUMN = "name";
    private static final String SURNAME_COLUMN = "surname";
    private static final String LOGIN_COLUMN = "login";

    private static final String GAME_ID_COLUMN = "gameId";
    private static final String GAME_TIME_COLUMN = "time";
    private static final String GAME_RATE_COLUMN = "rate";
    private static final String GAME_CREATOR_COLUMN = "creator";
    private static final String GAME_PLAYER_COLUMN = "player";
    private static final String GAME_CREATOR_SCORE_COLUMN = "creatorScore";
    private static final String GAME_PLAYER_SCORE_COLUMN = "playerScore";
    private static final String GAME_LAST_PLAYER_RESULT_COLUMN = "lastPlayerResult";
    private static final String GAME_LAST_CREATOR_RESULT_COLUMN = "lastCreatorResult";
    private static final String GAME_FINISHED_COLUMN = "finished";
    private static final String GAME_PLYERMOVE_COLUMN = "playerMove";

    public GameDAO(Connection connection) {
        super(connection);
    }

    /**Adds not active game in database.
     * @param bet value to add
     * @param creatorId account id of creator
     * @return true if everything is successful
     * @throws DAOException signals, that statement was not executed successfully
     */
    public boolean addGame(int bet, int creatorId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_ADD_GAME)) {
            LocalDateTime localDateTime = LocalDateTime.now();
            statement.setTimestamp(1, Timestamp.valueOf(localDateTime));
            statement.setInt(2, bet);
            statement.setInt(3, creatorId);

            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Updates multi game in database.
     * @param gameId id of current game
     * @param playerId account id of player
     * @return true if everything is successful
     * @throws DAOException signals, that statement was not executed successfully
     */
    public boolean updateMultiGame(int gameId, int playerId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MULTI_GAME)) {
            statement.setInt(1, playerId);
            statement.setInt(2, gameId);
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Updates multi game in database.
     * @param game game which replace one in database
     * @return true if everything is successful
     * @throws DAOException signals, that statement was not executed successfully
     */
    public boolean updateMultiGame(MultiGame game) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_MULTI_GAME_BY_ID)) {
            statement.setInt(1, game.getPlayerScore());
            statement.setInt(2, game.getCreatorScore());
            statement.setInt(3, game.getLastPlayerResult());
            statement.setInt(4, game.getLastCreatorResult());
            statement.setBoolean(5, game.isFinished());
            statement.setBoolean(6, game.isPlayerMove());
            statement.setInt(7, game.getGameId());
            statement.execute();
            return true;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Look for waiting games in database.
     * @return list of waiting games
     * @throws DAOException signals, that statement was not executed successfully
     */
    public List<MultiGame> findWaitingGames() throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_WAITING_GAMES)) {
            List<MultiGame> multiGames = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account creator = new Account(
                        resultSet.getInt(ACCOUNT_ID_COLUMN),
                        resultSet.getString(NAME_COLUMN),
                        resultSet.getString(SURNAME_COLUMN),
                        resultSet.getString(LOGIN_COLUMN));

                MultiGame multiGame = new MultiGame(
                        resultSet.getInt(GAME_ID_COLUMN),
                        resultSet.getTimestamp(GAME_TIME_COLUMN).toLocalDateTime(),
                        resultSet.getInt(GAME_RATE_COLUMN),
                        creator,
                        resultSet.getBoolean(GAME_FINISHED_COLUMN));
                multiGames.add(multiGame);
            }
            return multiGames;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Look for active games in database.
     * @param accountId account id of creator of the games
     * @return list of active creator's games
     * @throws DAOException signals, that statement was not executed successfully
     */
    public List<MultiGame> findMyActiveGames(int accountId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_MY_ACTIVE_GAMES)) {
            statement.setInt(1, accountId);
            List<MultiGame> multiGames = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account creator = new Account(
                        resultSet.getInt(ACCOUNT_ID_COLUMN),
                        resultSet.getString(NAME_COLUMN),
                        resultSet.getString(SURNAME_COLUMN),
                        resultSet.getString(LOGIN_COLUMN));
                Account player = new Account(
                        resultSet.getInt(GAME_PLAYER_COLUMN));

                MultiGame multiGame = new MultiGame(
                        resultSet.getInt(GAME_ID_COLUMN),
                        resultSet.getTimestamp(GAME_TIME_COLUMN).toLocalDateTime(),
                        resultSet.getInt(GAME_RATE_COLUMN),
                        creator,
                        player,
                        resultSet.getInt(GAME_CREATOR_SCORE_COLUMN),
                        resultSet.getInt(GAME_PLAYER_SCORE_COLUMN),
                        resultSet.getInt(GAME_LAST_PLAYER_RESULT_COLUMN),
                        resultSet.getInt(GAME_LAST_CREATOR_RESULT_COLUMN),
                        resultSet.getBoolean(GAME_FINISHED_COLUMN));
                multiGames.add(multiGame);
            }
            return multiGames;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Look for active games in database.
     * @param accountId account id of creator of the games
     * @return list of active creator's games
     * @throws DAOException signals, that statement was not executed successfully
     */
    public List<MultiGame> findMyActiveGamesAlt(int accountId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_MY_ACTIVE_GAMES_ALT)) {
            statement.setInt(1, accountId);
            List<MultiGame> multiGames = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Account creator = new Account(
                        resultSet.getInt(ACCOUNT_ID_COLUMN),
                        resultSet.getString(NAME_COLUMN),
                        resultSet.getString(SURNAME_COLUMN),
                        resultSet.getString(LOGIN_COLUMN));
                Account player = new Account(
                        resultSet.getInt(GAME_PLAYER_COLUMN));

                MultiGame multiGame = new MultiGame(
                        resultSet.getInt(GAME_ID_COLUMN),
                        resultSet.getTimestamp(GAME_TIME_COLUMN).toLocalDateTime(),
                        resultSet.getInt(GAME_RATE_COLUMN),
                        creator,
                        player,
                        resultSet.getInt(GAME_CREATOR_SCORE_COLUMN),
                        resultSet.getInt(GAME_PLAYER_SCORE_COLUMN),
                        resultSet.getInt(GAME_LAST_PLAYER_RESULT_COLUMN),
                        resultSet.getInt(GAME_LAST_CREATOR_RESULT_COLUMN),
                        resultSet.getBoolean(GAME_FINISHED_COLUMN));
                multiGames.add(multiGame);
            }
            return multiGames;
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Removes game with its id in database.
     * @param id game id
     * @throws DAOException signals, that statement was not executed successfully
     */
    public void removeGameById(int id) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_REMOVE_GAME_BY_ID)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException("Problems with database.", e);
        }
    }

    /**Look for game in database.
     * @param gameId game id
     * @return found game
     * @throws DAOException signals, that statement was not executed successfully
     */
    public MultiGame findMultiGame(int gameId) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_MULTI_GAME_BY_ID)) {
            statement.setInt(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new MultiGame(
                        resultSet.getInt(GAME_ID_COLUMN),
                        resultSet.getTimestamp(GAME_TIME_COLUMN).toLocalDateTime(),
                        resultSet.getInt(GAME_RATE_COLUMN),
                        new Account(resultSet.getInt(GAME_CREATOR_COLUMN)),
                        new Account(resultSet.getInt(GAME_PLAYER_COLUMN)),
                        resultSet.getInt(GAME_CREATOR_SCORE_COLUMN),
                        resultSet.getInt(GAME_PLAYER_SCORE_COLUMN),
                        resultSet.getInt(GAME_LAST_PLAYER_RESULT_COLUMN),
                        resultSet.getInt(GAME_LAST_CREATOR_RESULT_COLUMN),
                        resultSet.getBoolean(GAME_FINISHED_COLUMN),
                        resultSet.getBoolean(GAME_PLYERMOVE_COLUMN));
            }
            else {
                throw new DAOException("Can't find account by id in database.");
            }
        } catch (SQLException e) {
            throw new DAOException("Problems with finding account by id in database.", e);
        }
    }

}