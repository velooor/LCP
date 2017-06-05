package by.training.zakharchenya.courseproject.action.command.game;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.CreditBalance;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.GameLogic;
import by.training.zakharchenya.courseproject.logic.MoneyInfoLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/** Class serves to process the corresponding command: Delete multi game
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class DeleteGameCommand implements Command {
    private static final Logger LOG = LogManager.getLogger();

    private static final String GAME_ID_PARAM = "gameId";
    private static final String GAME_RATE_PARAM = "rate";

    /**@param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        int gameID = Integer.valueOf(request.getParameter(GAME_ID_PARAM));
        int rate = Integer.valueOf(request.getParameter(GAME_RATE_PARAM));

        Account currentAccount = (Account)request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        try{
            GameLogic.deleteGame(gameID);
            MoneyInfoLogic.topUpAccount(currentAccount.getAccountId(), rate);
            request.getSession().setAttribute(Constants.CREDIT_BALANCE_KEY, new CreditBalance(MoneyInfoLogic.countCreditBalance(currentAccount.getAccountId())));
        } catch (LogicException e){
            LOG.log(Level.ERROR, "Error during multi game deleting", e);
        }
        page = ConfigurationManager.getProperty(Constants.PAGE_WAITING);
        return page;
    }
}