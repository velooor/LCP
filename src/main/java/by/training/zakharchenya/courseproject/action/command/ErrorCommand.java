package by.training.zakharchenya.courseproject.action.command;


import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.http.HttpServletRequest;

import static by.training.zakharchenya.courseproject.servlet.Constants.PAGE_ERROR;

/** Class serves to process the corresponding command: Forward to error page
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class ErrorCommand implements Command {
    /**@param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        return ConfigurationManager.getProperty(PAGE_ERROR);
    }
}
