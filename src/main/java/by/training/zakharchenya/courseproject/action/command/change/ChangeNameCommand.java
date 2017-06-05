package by.training.zakharchenya.courseproject.action.command.change;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.ChangeAccountLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/** Class serves to process the corresponding command: Change name for current account
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class ChangeNameCommand implements Command {

    private static final Logger LOG = LogManager.getLogger();

    private static final String FIRST_NAME_PARAM = "firstName";
    private static final String SECOND_NAME_PARAM = "secondName";

    /**
     * @param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter(FIRST_NAME_PARAM);
        String surname = request.getParameter(SECOND_NAME_PARAM);
        String resultData;
        if(name.equals("") || surname.equals("")){
            resultData = ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
            return resultData;
        }
        Visitor visitor = (Visitor)request.getSession().getAttribute(Constants.VISITOR_KEY);
        Account account = (Account)request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        try {
            ChangeAccountLogic.updateNameSurname(account.getAccountId(), name, surname);

            account.setName(name);
            account.setSurname(surname);
            visitor.setCurrentPage(ConfigurationManager.getProperty(Constants.PAGE_MAIN));
            resultData = ConfigurationManager.getProperty(Constants.PAGE_MAIN);
        } catch (LogicException e) {
            LOG.log(Level.ERROR, "Errors during changing account name.", e);
            resultData = ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
        }
        return resultData;
    }
}
