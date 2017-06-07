package by.training.zakharchenya.courseproject.logic;

import by.training.zakharchenya.courseproject.dao.AccountDAO;
import by.training.zakharchenya.courseproject.dao.GameDAO;
import by.training.zakharchenya.courseproject.database.ConnectionPool;
import by.training.zakharchenya.courseproject.entity.game.MultiGame;
import by.training.zakharchenya.courseproject.exception.DAOException;
import by.training.zakharchenya.courseproject.exception.LogicException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * Class of logic, that provides service functions, while working with game commands.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class GameLogic {
    private static final Logger LOG = LogManager.getLogger();

    /**Creates a multi game.
     * @param bet game rate
     * @param creatorId creator account id
     * @throws LogicException signals, that there are problems with dao
     */
    public static void createGame(int bet, int creatorId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){

            GameDAO gameDAO = new GameDAO(connection);
            gameDAO.addGame(bet, creatorId);

        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with game creation operation.", e);
        }
    }

    /**Starts multi game.
     * @param gameId game id
     * @param playerId player account id
     * @throws LogicException signals, that there are problems with dao
     */
    public static void createMultiGame(int gameId, int playerId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){

            GameDAO gameDAO = new GameDAO(connection);
            gameDAO.updateMultiGame(gameId, playerId);

        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with multigame creation operation.", e);
        }
    }

    /**Updates multi game.
     * @param game game for update
     * @throws LogicException signals, that there are problems with dao
     */
    public static void updateMultiGame(MultiGame game) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){

            GameDAO gameDAO = new GameDAO(connection);
            gameDAO.updateMultiGame(game);

        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with multi game updating operation.", e);
        }
    }

    /**Gives multi game by id.
     * @param gameId game id
     * @return multi game object
     * @throws LogicException signals, that there are problems with dao
     */
    public static MultiGame findMultiGame(int gameId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){

            GameDAO gameDAO = new GameDAO(connection);
            return gameDAO.findMultiGame(gameId);

        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with multi game finding operation.", e);
        }
    }

    /**Loads waiting games.
     * @return list of multi games
     */
    public static List<MultiGame> loadWaitingGames(){
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            GameDAO gameDAO = new GameDAO(connection);

            return gameDAO.findWaitingGames();

        } catch (SQLException | DAOException e) {
            LOG.log(Level.ERROR, "Problems with multi game loading operation.", e);
            return null;
        }
    }

    /**GLoads active user games.
     * @param accountId account id
     * @return list of multi games
     */
    public static List<MultiGame> loadMyActiveGames(int accountId){
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            GameDAO gameDAO = new GameDAO(connection);

            List<MultiGame> result = gameDAO.findMyActiveGames(accountId);
            List<MultiGame> resultSecond = gameDAO.findMyActiveGamesAlt(accountId);

            result.addAll(resultSecond);
            AccountDAO accountDAO = new AccountDAO(connection);
            for (MultiGame game : result) {
                game.setPlayer(accountDAO.findAccountByID(game.getPlayer().getAccountId()));
            }

            return result;

        } catch (SQLException | DAOException e) {
            LOG.log(Level.ERROR, "Problems with active multi game loading operation.", e);
            return null;
        }
    }
    /**Deletes multi game by id.
     * @param gameId game id
     */
    public static void deleteGame(int gameId){
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            GameDAO gameDAO = new GameDAO(connection);

            gameDAO.removeGameById(gameId);

        } catch (SQLException | DAOException e) {
            LOG.log(Level.ERROR, "Problems with multi game deleting operation.", e);

        }
    }

    /**Finishes multi game.
     * @param game multi game object
     */
    public static void processPlayerMove(MultiGame game){
        Random rm = new Random();
        int step = rm.nextInt(11)+2;
        game.setPlayerScore(game.getPlayerScore()+step);
        game.setLastPlayerResult(step);
        if(game.getPlayerScore() > 21) {
            creatorWon(game);
        } else if(game.getPlayerScore() == 21){
            playerWon(game);
        }
    }

    /**Finishes multi game.
     * @param game multi game object
     */
    public static void processCreatorMove(MultiGame game){
        Random rm = new Random();
        int step = rm.nextInt(11)+2;
        game.setCreatorScore(game.getCreatorScore()+step);
        game.setLastCreatorResult(step);
        if(game.getCreatorScore() > 21) {
            playerWon(game);
        } else if(game.getCreatorScore() == 21){
            creatorWon(game);
        }
    }

    /**Finishes multi game.
     * @param game multi game object
     */
    public static void processPlayerPass(MultiGame game){
        game.setLastPlayerResult(-1);
        if(game.getLastCreatorResult() == -1){
            if(game.getPlayerScore() > game.getCreatorScore()){
                playerWon(game);
            } else if(game.getPlayerScore() < game.getCreatorScore()){
                creatorWon(game);
            } else{
                draw(game);
            }
        }
    }

    /**Finishes multi game.
     * @param game multi game object
     */
    public static void processCreatorPass(MultiGame game){
        game.setLastCreatorResult(-1);
        if(game.getLastPlayerResult() == -1){
            if(game.getCreatorScore() > game.getPlayerScore()){
                creatorWon(game);
            } else if(game.getCreatorScore() < game.getPlayerScore()){
                playerWon(game);
            } else{
                draw(game);
            }
        }
    }

    /**Finishes multi game.
     * @param game multi game object
     */
    public static void playerWon(MultiGame game){
        game.setFinished(true);
        game.setLastPlayerResult(-2);
        try{
            MoneyInfoLogic.finishMultiGame(game.getPlayer().getAccountId(), game.getCreator().getAccountId(), game.getRate());
        }catch (LogicException e){
            LOG.log(Level.ERROR, "Problems with multi game finishing operation.", e);
        }
    }

    /**Finishes multi game.
     * @param game multi game object
     */
    public static void creatorWon(MultiGame game){
        game.setFinished(true);
        game.setLastCreatorResult(-2);
        try{
            MoneyInfoLogic.finishMultiGame(game.getCreator().getAccountId(), game.getPlayer().getAccountId(), game.getRate());
        }catch (LogicException e){
            LOG.log(Level.ERROR, "Problems with multi game finishing operation.", e);
        }
    }

    /**Finishes multi game.
     * @param game multi game object
     */
    public static void draw(MultiGame game){
        game.setFinished(true);
        game.setLastCreatorResult(-3);
        game.setLastPlayerResult(-3);
        try{
            MoneyInfoLogic.finishMultiGameDraw(game.getPlayer().getAccountId(), game.getCreator().getAccountId(), game.getRate());
        }catch (LogicException e){
            LOG.log(Level.ERROR, "Problems with multi game finishing operation.", e);
        }
    }
}
