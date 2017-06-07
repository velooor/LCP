package by.training.zakharchenya.courseproject.entity.game;

import by.training.zakharchenya.courseproject.entity.Account;

import java.util.List;

/** Entity class, serves for processing relative object Game from database.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class SingleGame {
    private Account player;
    private int rate;
    private List<Integer> playerResults;
    private List<Integer> casinoResults;
    private boolean playerWon;
    private int numOfCasinoSteps;

    public SingleGame(Account player, int rate, List<Integer> playerResults, List<Integer> casinoResults, int numOfCasinoSteps) {
        this.player = player;
        this.rate = rate;
        this.playerResults = playerResults;
        this.casinoResults = casinoResults;
        this.numOfCasinoSteps = numOfCasinoSteps;
    }


    public Account getPlayer() {
        return player;
    }

    public void setPlayer(Account player) {
        this.player = player;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public List<Integer> getPlayerResults() {
        return playerResults;
    }

    public void setPlayerResults(List<Integer> playerResults) {
        this.playerResults = playerResults;
    }

    public List<Integer> getCasinoResults() {
        return casinoResults;
    }

    public void setCasinoResults(List<Integer> casinoResults) {
        this.casinoResults = casinoResults;
    }

    public boolean isPlayerWon() {
        return playerWon;
    }

    public void setPlayerWon(boolean playerWon) {
        this.playerWon = playerWon;
    }

    public int getNumOfCasinoSteps() {
        return numOfCasinoSteps;
    }

    public void setNumOfCasinoSteps(int numOfCasinoSteps) {
        this.numOfCasinoSteps = numOfCasinoSteps;
    }
}
