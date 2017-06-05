package by.training.zakharchenya.courseproject.action.command.change;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.action.command.LogoutCommand;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.ChangeAccountLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.manager.MessageManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.training.zakharchenya.courseproject.manager.MessageConstants.*;
import static by.training.zakharchenya.courseproject.servlet.Constants.ERROR_CHANGE_PASSWORD_MESSAGE;
import static by.training.zakharchenya.courseproject.servlet.Constants.ERROR_LOGIN_MESSAGE;


/** Class serves to process the corresponding command: Change password for current account
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class ChangePasswordCommand  implements Command {

    private static final Logger LOG = LogManager.getLogger();

    private static final String OLD_PASSWORD_PARAM = "oldPassword";
    private static final String NEW_PASSWORD_PARAM = "newPassword";
    private static final String REPEATED_NEW_PASSWORD_PARAM = "repeatedNewPassword";

    /**
     * @param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String oldPassword = request.getParameter(OLD_PASSWORD_PARAM);
        String newPassword = request.getParameter(NEW_PASSWORD_PARAM);
        String repeatedNewPassword = request.getParameter(REPEATED_NEW_PASSWORD_PARAM);
        String resultData= null;
        Account account = (Account)request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        try {
            ChangeAccountLogic.Result res= ChangeAccountLogic.updatePassword(account.getAccountId(), oldPassword, newPassword, repeatedNewPassword);
            switch(res){
                case SUCCESS:
                    resultData = new LogoutCommand().execute(request);
                    request.setAttribute(ERROR_LOGIN_MESSAGE, MESSAGE_CHANGE_PASSWORD_SUCCESS);
                    break;
                case PASSWORD_NOT_EQUALS:
                    request.setAttribute(ERROR_CHANGE_PASSWORD_MESSAGE, MessageManager.getProperty(MESSAGE_PASSWORD_NOT_EQUALS));
                    resultData = ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
                    break;
                case INVALID_PASSWORD:
                    request.setAttribute(ERROR_CHANGE_PASSWORD_MESSAGE, MessageManager.getProperty(MESSAGE_INVALID_PASSWORD));
                    resultData = ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
                    break;
                case INCORRECT_NEW_PASSWORD:
                    request.setAttribute(ERROR_CHANGE_PASSWORD_MESSAGE, MessageManager.getProperty(MESSAGE_NEW_PASSWORD_INCORRECT));
                    resultData = ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
                    break;
                case EXCEPTION:
                    request.setAttribute(ERROR_CHANGE_PASSWORD_MESSAGE, MessageManager.getProperty(MESSAGE_DATABASE_PROBLEM));
                    resultData = ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
                    break;
            }
        } catch (LogicException e) {
            LOG.log(Level.ERROR, "Errors during changing account password.", e);
            resultData = ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
        }
        return resultData;
    }
}
