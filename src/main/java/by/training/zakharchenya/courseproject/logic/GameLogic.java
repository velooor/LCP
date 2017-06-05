package by.training.zakharchenya.courseproject.logic;

import by.training.zakharchenya.courseproject.dao.AccountDAO;
import by.training.zakharchenya.courseproject.dao.GameDAO;
import by.training.zakharchenya.courseproject.database.ConnectionPool;
import by.training.zakharchenya.courseproject.entity.game.MultiGame;
import by.training.zakharchenya.courseproject.exception.DAOException;
import by.training.zakharchenya.courseproject.exception.LogicException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

/**
 * Created by Lagarde on 18.05.2017.
 */
public class GameLogic {

    public static void createGame(int bet, int creatorId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){

            GameDAO gameDAO = new GameDAO(connection);
            gameDAO.addGame(bet, creatorId);

        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with game creation operation.", e);
        }
    }
    public static void createMultiGame(int gameId, int playerId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){

            GameDAO gameDAO = new GameDAO(connection);
            gameDAO.updateMultiGame(gameId, playerId);

        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with game creation operation.", e);
        }
    }
    public static void updateMultiGame(MultiGame game) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){

            GameDAO gameDAO = new GameDAO(connection);
            gameDAO.updateMultiGame(game);

        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with game creation operation.", e);
        }
    }
    public static MultiGame findMultiGame(int gameId) throws LogicException {
        try (Connection connection = ConnectionPool.getInstance().getConnection()){

            GameDAO gameDAO = new GameDAO(connection);
            return gameDAO.findMultiGame(gameId);

        } catch (SQLException | DAOException e) {
            throw new LogicException("Problems with game creation operation.", e);
        }
    }


    public static List<MultiGame> loadWaitingGames(){
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            GameDAO gameDAO = new GameDAO(connection);

            return gameDAO.findWaitingGames();

        } catch (SQLException | DAOException e) {
            //throw new LogicException("Problems with signIn operation.", e);
            return null;
        }
    }
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
            //throw new LogicException("Problems with signIn operation.", e);
            return null;
        }
    }
    public static void deleteGame(int gameId){
        try (Connection connection = ConnectionPool.getInstance().getConnection()){
            GameDAO gameDAO = new GameDAO(connection);

            gameDAO.removeGameById(gameId);

        } catch (SQLException | DAOException e) {
            //throw new LogicException("Problems with signIn operation.", e);

        }
    }
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
    public static void playerWon(MultiGame game){
        game.setFinished(true);
        game.setLastPlayerResult(-2);
        try{
            MoneyInfoLogic.finishMultiGame(game.getPlayer().getAccountId(), game.getCreator().getAccountId(), game.getRate());
        }catch (LogicException e){

        }
    }
    public static void creatorWon(MultiGame game){
        game.setFinished(true);
        game.setLastCreatorResult(-2);
        try{
            MoneyInfoLogic.finishMultiGame(game.getCreator().getAccountId(), game.getPlayer().getAccountId(), game.getRate());
        }catch (LogicException e){

        }
    }
    public static void draw(MultiGame game){
        game.setFinished(true);
        game.setLastCreatorResult(-3);
        game.setLastPlayerResult(-3);
        try{
            MoneyInfoLogic.finishMultiGameDraw(game.getPlayer().getAccountId(), game.getCreator().getAccountId(), game.getRate());
        }catch (LogicException e){

        }
    }
}
