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
import by.training.zakharchenya.courseproject.manager.MessageManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.training.zakharchenya.courseproject.manager.MessageConstants.MESSAGE_INCORRECT_LOGIN;
import static by.training.zakharchenya.courseproject.manager.MessageConstants.MESSAGE_NOT_ENOUGH_MONEY;
import static by.training.zakharchenya.courseproject.servlet.Constants.MULTIGAME_MESSAGE;
import static by.training.zakharchenya.courseproject.servlet.Constants.NEWMESSAGE_MESSAGE;

/** Class serves to process the corresponding command: Join and start multi game
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class StartMultiGameCommand implements Command {
    private static final Logger LOG = LogManager.getLogger();
    private static final String GAME_ID_PARAM = "gameId";
    private static final String RATE_PARAM = "rate";

    /**@param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        int gameId = Integer.valueOf(request.getParameter(GAME_ID_PARAM));
        int rate = Integer.valueOf(request.getParameter(RATE_PARAM));

        Account currentAccount = (Account)request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        CreditBalance cb = (CreditBalance)request.getSession().getAttribute(Constants.CREDIT_BALANCE_KEY);

        if(rate>cb.getMoneyAmount()){
            page = ConfigurationManager.getProperty(Constants.PAGE_WAITING);
            request.setAttribute(MULTIGAME_MESSAGE, MessageManager.getProperty(MESSAGE_NOT_ENOUGH_MONEY));
        } else{
            try{
                GameLogic.createMultiGame(gameId, currentAccount.getAccountId());
                MultiGame mg =  GameLogic.findMultiGame(gameId);
                mg.setCreator(LoginLogic.getAccount( mg.getCreator().getAccountId()));
                mg.setPlayer(LoginLogic.getAccount( mg.getPlayer().getAccountId()));

                MoneyInfoLogic.keepRate(currentAccount.getAccountId(), rate);
                request.getSession().setAttribute(Constants.CREDIT_BALANCE_KEY, new CreditBalance(MoneyInfoLogic.countCreditBalance(currentAccount.getAccountId())));
                request.getSession().setAttribute(Constants.MULTI_GAME_KEY, mg);

                page = ConfigurationManager.getProperty(Constants.PAGE_MULTIGAME);
            } catch (LogicException e){
                page = ConfigurationManager.getProperty(Constants.PAGE_WAITING);
                LOG.log(Level.ERROR, "Errors during joining multi game.", e);
            }
        }
        return page;
    }
}