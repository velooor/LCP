package by.training.zakharchenya.courseproject.action.command.admin;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.logic.AdminLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/** Class serves to process the corresponding command: Update User Status
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class UpdateUserStatusCommand implements Command {

    private static final String USER_ID_PARAM = "userid";
    private static final String USER_ISACTIVE_PARAM = "isActive";

    /**
     * @param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        String page = null;
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        int userID = Integer.valueOf(request.getParameter(USER_ID_PARAM));
        boolean isActive = Boolean.valueOf(request.getParameter(USER_ISACTIVE_PARAM));

        Visitor visitor = (Visitor)request.getSession().getAttribute(Constants.VISITOR_KEY);

        List<Account> allUsers = (List<Account>)request.getSession().getAttribute(Constants.USERS_LIST_KEY);
        for(Account acc : allUsers){
            if(acc.getAccountId() == userID && !acc.isAdmin()){
                AdminLogic.Result res = AdminLogic.updateUserStatus(isActive, userID);
                if (res == AdminLogic.Result.SUCCESS) {
                    visitor.setCurrentPage(ConfigurationManager.getProperty(Constants.PAGE_ADMINSETTINGS));
                }
            }
        }
        page = ConfigurationManager.getProperty(Constants.PAGE_ADMINSETTINGS);
        return page;
    }
}