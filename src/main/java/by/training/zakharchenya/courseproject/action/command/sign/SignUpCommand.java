package by.training.zakharchenya.courseproject.action.command.sign;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.CreditBalance;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.LoginLogic;
import by.training.zakharchenya.courseproject.logic.MailLogic;
import by.training.zakharchenya.courseproject.logic.MoneyInfoLogic;
import by.training.zakharchenya.courseproject.logic.SingUpLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.manager.MessageManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static by.training.zakharchenya.courseproject.manager.MessageConstants.*;
import static by.training.zakharchenya.courseproject.servlet.Constants.*;

/** Class serves to process the corresponding command: Create account and save it
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class SignUpCommand implements Command {
    private static final Logger LOG = LogManager.getLogger();

    private static final String FIRST_NAME_PARAM = "name";
    private static final String SECOND_NAME_PARAM = "surname";
    private static final String LOGIN_PARAM = "login";
    private static final String EMAIL_PARAM = "email";
    private static final String PASSWORD_PARAM = "password";
    private static final String REPEATED_PASSWORD_PARAM = "password2";
    private static final String BIRTHDATE_PARAM = "birthdate";

    /**@param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        String login = request.getParameter(LOGIN_PARAM);
        String pass = request.getParameter(PASSWORD_PARAM);
        String passrep = request.getParameter(REPEATED_PASSWORD_PARAM);
        String name = request.getParameter(FIRST_NAME_PARAM);
        String surname = request.getParameter(SECOND_NAME_PARAM);
        String email = request.getParameter(EMAIL_PARAM);
        String birthDate = request.getParameter(BIRTHDATE_PARAM);

        Visitor visitor = (Visitor)request.getSession().getAttribute(Constants.VISITOR_KEY);
        SingUpLogic.Result res = SingUpLogic.singup(name, surname, login, email, pass, passrep, birthDate);

        switch(res){
            case VALID:
                HttpSession session = request.getSession();
                session.setAttribute(FIRST_NAME_PARAM, name);
                session.setAttribute(SECOND_NAME_PARAM, surname);
                session.setAttribute(LOGIN_PARAM, login);
                session.setAttribute(EMAIL_PARAM, email);
                session.setAttribute(BIRTHDATE_PARAM, birthDate);

                Account currentAccount = LoginLogic.getAccount(login);
                visitor.setRole(Visitor.Role.USER);
                visitor.setCurrentPage(ConfigurationManager.getProperty(Constants.PAGE_MAIN));
                visitor.setStatus(Account.StatusEnum.ACTIVE);
                request.getSession().setAttribute(Constants.ACCOUNT_KEY, currentAccount);

                try {
                    MoneyInfoLogic.registerCreditAccount(currentAccount.getAccountId());
                    request.getSession().setAttribute(Constants.CREDIT_BALANCE_KEY, new CreditBalance(0));
                    MailLogic.sendMessage(login,"Welcome!","Hello, "+ name + " " + surname +"!  Thank you for registering. We are glad to see you in Elgrand Casino! We suppose, that luck will always be with you. Good luck! Administration.",1,false);
                    page = ConfigurationManager.getProperty(PAGE_MAIN);
                } catch (LogicException e) {
                    LOG.log(Level.ERROR, "Errors during changing account name.", e);
                    page = ConfigurationManager.getProperty(Constants.PAGE_INDEX);
                }
                break;
            case INVALID_EMAIL:
                request.setAttribute(ERROR_SIGNUP_MESSAGE, MessageManager.getProperty(MESSAGE_INVALID_EMAIL));
                page = ConfigurationManager.getProperty(PAGE_REGISTRATION);
                break;
            case PASSWORDS_NOT_EQUALS:
                request.setAttribute(ERROR_SIGNUP_MESSAGE, MessageManager.getProperty(MESSAGE_PASSWORD_NOT_EQUALS));
                page = ConfigurationManager.getProperty(PAGE_INDEX);
                break;
            case LOGIN_NOT_UNIQUE:
                request.setAttribute(ERROR_SIGNUP_MESSAGE, MessageManager.getProperty(MESSAGE_LOGIN_NOTUNIQUE));
                page = ConfigurationManager.getProperty(PAGE_INDEX);
                break;
            case INVALID_LOGIN:
                request.setAttribute(ERROR_SIGNUP_MESSAGE, MessageManager.getProperty(MESSAGE_INVALID_LOGIN));
                page = ConfigurationManager.getProperty(PAGE_INDEX);
                break;
            case INVALID_PASSWORD:
                request.setAttribute(ERROR_SIGNUP_MESSAGE, MessageManager.getProperty(MESSAGE_INVALID_PASSWORD));
                page = ConfigurationManager.getProperty(PAGE_INDEX);
                break;
            case EXCEPTION:
                request.setAttribute(ERROR_SIGNUP_MESSAGE, MessageManager.getProperty(MESSAGE_DATABASE_PROBLEM));
                page = ConfigurationManager.getProperty(PAGE_INDEX);
                break;
        }
        return page;
    }
}