package by.training.zakharchenya.courseproject.action.command.game;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.CreditBalance;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.entity.game.MultiGame;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.GameLogic;
import by.training.zakharchenya.courseproject.logic.LoginLogic;
import by.training.zakharchenya.courseproject.logic.MoneyInfoLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/** Class serves to process the corresponding command: Resume multi game
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class ReturnToMultiGameCommand  implements Command {
    private static final Logger LOG = LogManager.getLogger();
    private static final String GAME_ID_PARAM = "gameId";

    /**@param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        int gameId = Integer.valueOf(request.getParameter(GAME_ID_PARAM));

        Account currentAccount = (Account)request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        try{
            MultiGame mg =  GameLogic.findMultiGame(gameId);
            mg.setCreator(LoginLogic.getAccount( mg.getCreator().getAccountId()));
            mg.setPlayer(LoginLogic.getAccount( mg.getPlayer().getAccountId()));

            request.getSession().setAttribute(Constants.CREDIT_BALANCE_KEY, new CreditBalance(MoneyInfoLogic.countCreditBalance(currentAccount.getAccountId())));
            request.getSession().setAttribute(Constants.MULTI_GAME_KEY, mg);
            page = ConfigurationManager.getProperty(Constants.PAGE_MULTIGAME);
        } catch (LogicException e){
            LOG.log(Level.ERROR, "Errors during resuming multi game.", e);
            page = ConfigurationManager.getProperty(Constants.PAGE_WAITING);
        }
        return page;
    }
}