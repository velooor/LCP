package by.training.zakharchenya.courseproject.action.command.change;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.ChangeAccountLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/** Class serves to process the corresponding command: Reset avatar for current account
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class ResetAvatarCommand implements Command {

    private static final Logger LOG = LogManager.getLogger();

    /**@param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String resultData;
        Account account = (Account)request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        try {
            ChangeAccountLogic.updateAvatar(account.getAccountId(), null);
            resultData = ConfigurationManager.getProperty(Constants.PAGE_MAIN);
            account.setAvatar(null);
        } catch (LogicException e) {
            LOG.log(Level.ERROR, "Errors during changing account name.", e);
            resultData = ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
        }
        return resultData;
    }

}
