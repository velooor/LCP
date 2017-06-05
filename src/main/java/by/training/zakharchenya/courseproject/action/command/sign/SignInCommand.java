package by.training.zakharchenya.courseproject.action.command.sign;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.CreditBalance;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.LoginLogic;
import by.training.zakharchenya.courseproject.logic.MoneyInfoLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.manager.MessageManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.training.zakharchenya.courseproject.manager.MessageConstants.ERROR_LOGIN;
import static by.training.zakharchenya.courseproject.servlet.Constants.*;

/** Class serves to process the corresponding command: Find account and put it to the session
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class SignInCommand implements Command {
    private static final Logger LOG = LogManager.getLogger();

    private static final String PARAM_NAME_LOGIN = "INlogin";
    private static final String PARAM_NAME_PASSWORD = "INpassword";

    /**@param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        Visitor visitor = (Visitor)request.getSession().getAttribute(Constants.VISITOR_KEY);
        request.getSession().setAttribute(Constants.STATE_KEY, State.FORWARD);
        if (LoginLogic.checkLogin(login, pass)) {
            Account currentAccount = LoginLogic.getAccount(login);
            request.getSession().setAttribute(Constants.ACCOUNT_KEY, currentAccount);
            try {
                request.getSession().setAttribute(Constants.CREDIT_BALANCE_KEY, new CreditBalance(MoneyInfoLogic.countCreditBalance(currentAccount.getAccountId())));
                if(currentAccount.isAdmin()){
                    visitor.setRole(Visitor.Role.ADMIN);
                }else{
                    visitor.setRole(Visitor.Role.USER);
                }
                visitor.setStatus(currentAccount.getStatus());
                visitor.setCurrentPage(ConfigurationManager.getProperty(Constants.PAGE_MAIN));
                page = ConfigurationManager.getProperty(PAGE_MAIN);

            } catch (LogicException e) {
                LOG.log(Level.ERROR, "Errors during changing account name.", e);
                page = ConfigurationManager.getProperty(Constants.PAGE_WELCOME);
            }
        } else {
            request.setAttribute(ERROR_LOGIN_MESSAGE, MessageManager.getProperty(ERROR_LOGIN));
            page = ConfigurationManager.getProperty(PAGE_WELCOME);
        }
        return page;
    }
}