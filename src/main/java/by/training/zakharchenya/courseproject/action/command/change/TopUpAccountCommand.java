package by.training.zakharchenya.courseproject.action.command.change;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.CreditBalance;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.MailLogic;
import by.training.zakharchenya.courseproject.logic.MoneyInfoLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import by.training.zakharchenya.courseproject.validator.InputsValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/** Class serves to process the corresponding command: Top up current money account
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class TopUpAccountCommand implements Command {

    private static final Logger LOG = LogManager.getLogger();

    private static final String CREDIT_CARD_1_PARAM = "creditcard1";
    private static final String CREDIT_CARD_2_PARAM = "creditcard2";
    private static final String CREDIT_CARD_3_PARAM = "creditcard3";
    private static final String CREDIT_CARD_4_PARAM = "creditcard4";
    private static final String CCV_PARAM = "ccv";
    private static final String MONTH_PARAM = "validMonth";
    private static final String YEAR_PARAM = "validYear";
    private static final String AMOUNT_PARAM = "amount";

    /**@param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String creditCard =   request.getParameter(CREDIT_CARD_1_PARAM)
                            + request.getParameter(CREDIT_CARD_2_PARAM)
                            + request.getParameter(CREDIT_CARD_3_PARAM)
                            + request.getParameter(CREDIT_CARD_4_PARAM);
        String ccv = request.getParameter(CCV_PARAM);
        int month = Integer.valueOf(request.getParameter(MONTH_PARAM));
        int year = Integer.valueOf(request.getParameter(YEAR_PARAM));
        int amount = Integer.valueOf(request.getParameter(AMOUNT_PARAM));
        String resultData;
        if(!InputsValidator.validateCreditInfo(amount,month,year,ccv,creditCard)){
            LOG.log(Level.ERROR, "Errors while topping account up.");
            return ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
        }
        Visitor visitor = (Visitor)request.getSession().getAttribute(Constants.VISITOR_KEY);
        Account account = (Account)request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        try {
            MoneyInfoLogic.processPayment(account.getAccountId(), creditCard, ccv, month, year, amount);
            request.getSession().setAttribute(Constants.CREDIT_BALANCE_KEY, new CreditBalance(MoneyInfoLogic.countCreditBalance(account.getAccountId())));
            MailLogic.sendMessage(account.getLogin(),"Your money account was topped up!","Dear, "+ account.getName() + " " + account.getSurname()+", you've topped up your money account with " + amount + "$.  Thank you for your money! We hope, that you wil not see it any more :)",1,false);

            visitor.setCurrentPage(ConfigurationManager.getProperty(Constants.PAGE_SETTINGS));
            resultData = ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
        } catch (LogicException e) {
            LOG.log(Level.ERROR, "Errors while topping account up.", e);
            resultData = ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
        }
        return resultData;
    }
}


