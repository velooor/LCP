package by.training.zakharchenya.courseproject.action.command.game;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.CreditBalance;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.entity.game.SingleGame;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.MoneyInfoLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/** Class serves to process the corresponding command: End of single game and update money accounts
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class EndOfSingleRound implements Command {
    private static final Logger LOG = LogManager.getLogger();
    private static final String RESULT_PARAM = "result";
    private static final String RATE_PARAM = "rate";

    /**@param request from client
     * @return xml file to be forwarded to client or path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String resData;
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.AJAX);

        int result = Integer.valueOf(request.getParameter(RESULT_PARAM));
        int rate = Integer.valueOf(request.getParameter(RATE_PARAM));
        Account currentAccount = (Account)request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        try{
            switch (result){
                case 0:
                    MoneyInfoLogic.topUpAccount(currentAccount.getAccountId(), rate);
                    break;
                case 1:
                    MoneyInfoLogic.playerWon(currentAccount.getAccountId(), rate);
                    break;
                case 2:
                    MoneyInfoLogic.casinoWon(currentAccount.getAccountId(), rate);
                    break;
            }
            CreditBalance creditBalance = new CreditBalance(MoneyInfoLogic.countCreditBalance(currentAccount.getAccountId()));
            request.getSession().setAttribute(Constants.CREDIT_BALANCE_KEY, creditBalance);

            resData = creditBalance.toXml();
        }catch (LogicException e){
            LOG.log(Level.ERROR, "Errors during finishing single game.", e);
            request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
            resData = ConfigurationManager.getProperty(Constants.PAGE_MAIN);
        }
        return resData;
    }


}