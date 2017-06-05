package by.training.zakharchenya.courseproject.action.command.mail;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.logic.MailLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.http.HttpServletRequest;

/** Class serves to process the corresponding command: Delete the message
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class DeleteMessageCommand implements Command {

    private static final String MESSAGE_ID_PARAM = "messageid";

    /**@param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page ;
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        int messageID = Integer.valueOf(request.getParameter(MESSAGE_ID_PARAM));

        Visitor visitor = (Visitor)request.getSession().getAttribute(Constants.VISITOR_KEY);

        MailLogic.Result res = MailLogic.deleteMessage(messageID);

        if (res == MailLogic.Result.SUCCESS) {
            visitor.setCurrentPage(ConfigurationManager.getProperty(Constants.PAGE_MESSAGES));
        }
        page = ConfigurationManager.getProperty(Constants.PAGE_MESSAGES);
        return page;
    }
}