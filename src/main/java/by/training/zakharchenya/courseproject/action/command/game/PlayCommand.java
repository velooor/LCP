package by.training.zakharchenya.courseproject.action.command.game;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.CreditBalance;
import by.training.zakharchenya.courseproject.entity.game.SingleGame;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.MoneyInfoLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Class serves to process the corresponding command: Create and start single game
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class PlayCommand implements Command {
    private static final Logger LOG = LogManager.getLogger();
    private static final String BET_PARAM = "bet";

    /**@param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        int bet = Integer.valueOf(request.getParameter(BET_PARAM));
        Account currentAccount = (Account)request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        int minRate = (Integer) request.getSession().getAttribute(Constants.MIN_RATE_KEY);
        try{
            MoneyInfoLogic.keepRate(currentAccount.getAccountId(), bet);
            request.getSession().setAttribute(Constants.CREDIT_BALANCE_KEY, new CreditBalance(MoneyInfoLogic.countCreditBalance(currentAccount.getAccountId())));

            List<Integer> playerResults = new ArrayList<>();
            List<Integer> casinoResults = new ArrayList<>();
            int numOfSteps = process(playerResults, casinoResults, minRate);
            request.getSession().setAttribute(Constants.SINGLE_GAME_KEY, new SingleGame(currentAccount, bet, playerResults, casinoResults, numOfSteps));
            page = ConfigurationManager.getProperty(Constants.PAGE_SINGLE_GAME);

        }catch (LogicException e){
            LOG.log(Level.ERROR, "Errors during creating single game.", e);
            page = ConfigurationManager.getProperty(Constants.PAGE_MAIN);
        }
        return page;
    }

    public int process(List<Integer> playerResults, List<Integer> casinoResults, int minRate){
        Random rand = new Random();
        int casinoPoints = 0, playerPoints = 0, k = 0;
        boolean stop = false;
        for(int i=0; i<21; i++){
            playerResults.add(rand.nextInt(11)+2);
            if(!stop){
                casinoResults.add(rand.nextInt(11)+2);
                casinoPoints += casinoResults.get(i);
                playerPoints += playerResults.get(i);
                k++;
                if(casinoPoints > 19){
                    stop = true;
                    continue;
                }
                if(playerPoints <= casinoPoints && casinoPoints >= minRate){
                    switch(casinoPoints){
                        case 16:
                            stop = !threeQuoter();
                            break;
                        case 17:
                            stop = !mid();
                            break;
                        case 18:
                            stop = !small();
                            break;
                        case 19:
                            stop = !minimum();
                            break;
                        case 20:
                        case 21:
                            stop = true;
                            break;
                    }
                }
            }
        }
        return k;
    }
    public boolean threeQuoter(){
        Random rand = new Random();
        if(rand.nextInt(3)==3)return false;
        else return true;
    }
    public boolean mid(){
        Random rand = new Random();
        if(rand.nextInt(2)==2)return false;
        else return true;
    }
    public boolean small(){
        Random rand = new Random();
        if(rand.nextInt(10)==10)return true;
        else return false;
    }
    public boolean minimum(){
        Random rand = new Random();
        if(rand.nextInt(25)==25)return true;
        else return false;
    }
}