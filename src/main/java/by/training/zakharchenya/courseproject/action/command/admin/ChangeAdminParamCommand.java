package by.training.zakharchenya.courseproject.action.command.admin;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Visitor;
import by.training.zakharchenya.courseproject.exception.LogicException;
import by.training.zakharchenya.courseproject.logic.ChangeAccountLogic;
import by.training.zakharchenya.courseproject.manager.ConfigurationManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/** Class serves to process the corresponding command: Change Admin Params
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class ChangeAdminParamCommand implements Command {

    private static final Logger LOG = LogManager.getLogger();

    private static final String MIN_POINTS_PARAM = "minNumOfPoints";
    private static final String MIN_RATE_PARAM = "minRate";

    /**
     * @param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        int numOfPoints = Integer.valueOf(request.getParameter(MIN_POINTS_PARAM));
        int rate = Integer.valueOf(request.getParameter(MIN_RATE_PARAM));
        String resultData;
        if(rate>=0 && rate<=999999 && numOfPoints>=0 && numOfPoints<=21){
            Visitor visitor = (Visitor)request.getSession().getAttribute(Constants.VISITOR_KEY);
            request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
            try {
                ChangeAccountLogic.updateAdminParams(numOfPoints, rate);
                visitor.setCurrentPage(ConfigurationManager.getProperty(Constants.PAGE_ADMINSETTINGS));
                resultData = ConfigurationManager.getProperty(Constants.PAGE_ADMINSETTINGS);
            } catch (LogicException e) {
                LOG.log(Level.ERROR, "Errors during changing admin params.", e);
                resultData = ConfigurationManager.getProperty(Constants.PAGE_ADMINSETTINGS);
            }
        } else{
            LOG.log(Level.ERROR, "Errors during changing admin params.");
            resultData = ConfigurationManager.getProperty(Constants.PAGE_ADMINSETTINGS);
        }
        return resultData;

    }
}
