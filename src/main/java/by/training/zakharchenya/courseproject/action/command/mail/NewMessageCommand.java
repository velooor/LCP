package by.training.zakharchenya.courseproject.action.command.mail;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.logic.MailLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.manager.MessageManager;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.http.HttpServletRequest;

import static by.training.zakharchenya.courseproject.manager.MessageConstants.MESSAGE_DATABASE_PROBLEM;
import static by.training.zakharchenya.courseproject.manager.MessageConstants.MESSAGE_INCORRECT_LOGIN;
import static by.training.zakharchenya.courseproject.manager.MessageConstants.MESSAGE_WRONG_LOGIN;
import static by.training.zakharchenya.courseproject.servlet.Constants.NEWMESSAGE_MESSAGE;

/** Class serves to process the corresponding command: Create and save message
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class NewMessageCommand implements Command {

    private static final String LOGIN_PARAM = "login";
    private static final String THEME_PARAM = "theme";
    private static final String MESSAGE_PARAM = "message";
    private static final String ADDRESS_PARAM = "address";
    private static final String ADMIN = "admin";

    /**@param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        String login = request.getParameter(LOGIN_PARAM);
        String theme = request.getParameter(THEME_PARAM);
        String message = request.getParameter(MESSAGE_PARAM);
        boolean forAdmin = false;
        if(request.getParameter(ADDRESS_PARAM).equals(ADMIN) || login.equals("")) {
            forAdmin=true;
        }
        Visitor visitor = (Visitor)request.getSession().getAttribute(Constants.VISITOR_KEY);
        Account currentAccount = (Account)request.getSession().getAttribute(Constants.ACCOUNT_KEY);

        MailLogic.Result res = MailLogic.sendMessage(login, theme, message, currentAccount.getAccountId(), forAdmin);
        switch(res){
            case SUCCESS:
                visitor.setCurrentPage(ConfigurationManager.getProperty(Constants.PAGE_MESSAGES));
                break;
            case INCORRECT_LOGIN:
                request.setAttribute(NEWMESSAGE_MESSAGE, MessageManager.getProperty(MESSAGE_INCORRECT_LOGIN));
                break;
            case WRONG_LOGIN:
                request.setAttribute(NEWMESSAGE_MESSAGE, MessageManager.getProperty(MESSAGE_WRONG_LOGIN));
                break;
            case EXCEPTION:
                request.setAttribute(NEWMESSAGE_MESSAGE, MessageManager.getProperty(MESSAGE_DATABASE_PROBLEM));
                break;
        }
        page = ConfigurationManager.getProperty(Constants.PAGE_MESSAGES);
        return page;
    }
}