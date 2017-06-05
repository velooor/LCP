package by.training.zakharchenya.courseproject.action.command;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.http.HttpServletRequest;
/** Class serves to process the corresponding command: Exit from the system
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class LogoutCommand implements Command {
    /**@param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        Visitor visitor = (Visitor)request.getSession().getAttribute(Constants.VISITOR_KEY);
        visitor.setRole(Visitor.Role.GUEST);
        visitor.setCurrentPage(ConfigurationManager.getProperty(Constants.PAGE_INDEX));
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        return request.getContextPath() + ConfigurationManager.getProperty(Constants.PAGE_INDEX);
    }
}