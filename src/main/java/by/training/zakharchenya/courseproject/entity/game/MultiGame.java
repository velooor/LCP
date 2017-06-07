package by.training.zakharchenya.courseproject.entity.game;

import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.CreditBalance;

import java.time.LocalDateTime;

/** Entity class, serves for processing relative object Game from database.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class MultiGame {
    private int gameId;
    private LocalDateTime time;
    private int rate;
    private Account creator;
    private Account player;
    private int creatorScore;
    private int playerScore;
    private int lastPlayerResult;
    private int lastCreatorResult;
    private boolean finished;
    private boolean playerMove;

    public MultiGame(int gameId, LocalDateTime time, int rate, Account creator, Account player, int creatorScore, int playerScore, int lastPlayerResult, int lastCreatorResult, boolean finished) {
        this.gameId = gameId;
        this.time = time;
        this.rate = rate;
        this.creator = creator;
        this.player = player;
        this.creatorScore = creatorScore;
        this.playerScore = playerScore;
        this.lastPlayerResult = lastPlayerResult;
        this.lastCreatorResult = lastCreatorResult;
        this.finished = finished;
    }

    public MultiGame(int gameId, LocalDateTime time, int rate, Account creator, Account player, int creatorScore, int playerScore, int lastPlayerResult, int lastCreatorResult, boolean finished, boolean playerMove) {
        this.gameId = gameId;
        this.time = time;
        this.rate = rate;
        this.creator = creator;
        this.player = player;
        this.creatorScore = creatorScore;
        this.playerScore = playerScore;
        this.lastPlayerResult = lastPlayerResult;
        this.lastCreatorResult = lastCreatorResult;
        this.finished = finished;
        this.playerMove = playerMove;
    }

    public MultiGame(int gameId, LocalDateTime time, int rate, Account creator, boolean finished) {
        this.gameId = gameId;
        this.time = time;
        this.rate = rate;
        this.creator = creator;
        this.finished = finished;
    }


    public String toXml(int moneyPlayer, int moneyCreator) {
        StringBuffer xml = new StringBuffer();
        xml.append("<MultiGame>");

        xml.append("<gameId>"+gameId+"</gameId>");
        xml.append("<rate>"+rate+"</rate>");
        xml.append("<creatorId>"+creator.getAccountId()+"</creatorId>");
        xml.append("<playerId>"+player.getAccountId()+"</playerId>");
        xml.append("<creatorScore>"+creatorScore+"</creatorScore>");
        xml.append("<playerScore>"+playerScore+"</playerScore>");
        xml.append("<lastPlayerResult>"+lastPlayerResult+"</lastPlayerResult>");
        xml.append("<lastCreatorResult>"+lastCreatorResult+"</lastCreatorResult>");
        xml.append("<finished>"+finished+"</finished>");
        xml.append("<playerMove>"+playerMove+"</playerMove>");
        xml.append("<moneyPlayer>"+moneyPlayer+"</moneyPlayer>");
        xml.append("<moneyCreator>"+moneyCreator+"</moneyCreator>");


        xml.append("</MultiGame>");
        return xml.toString();
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public Account getPlayer() {
        return player;
    }

    public void setPlayer(Account player) {
        this.player = player;
    }

    public int getCreatorScore() {
        return creatorScore;
    }

    public void setCreatorScore(int creatorScore) {
        this.creatorScore = creatorScore;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getLastPlayerResult() {
        return lastPlayerResult;
    }

    public void setLastPlayerResult(int lastPlayerResult) {
        this.lastPlayerResult = lastPlayerResult;
    }

    public int getLastCreatorResult() {
        return lastCreatorResult;
    }

    public void setLastCreatorResult(int lastCreatorResult) {
        this.lastCreatorResult = lastCreatorResult;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isPlayerMove() {
        return playerMove;
    }

    public void setPlayerMove(boolean playerMove) {
        this.playerMove = playerMove;
    }


}
