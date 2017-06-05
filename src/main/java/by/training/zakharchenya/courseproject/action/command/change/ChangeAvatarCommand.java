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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;

/** Class serves to process the corresponding command: Change avatar for current account
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class ChangeAvatarCommand implements Command {

    private static final Logger LOG = LogManager.getLogger();
    private static final String AVATAR_PARAM = "avatar";

    /**
     * @param request from client
     * @return path for jsp file to be forwarded to
     */
    @Override
    public String execute(HttpServletRequest request) {
        boolean formValid = true;
        Part avatarPart = null;
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.FORWARD);
        try {
            avatarPart = request.getPart(AVATAR_PARAM);
        } catch (IOException | ServletException e) {
            formValid = false;
        }
        if (avatarPart == null || !formValid) {
            LOG.log(Level.WARN, "Change avatar form is invalid.");
            return ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
        }
        byte[] empty = {};
        byte[] avatar = empty;

        int fileSize = (int)avatarPart.getSize();
        if (fileSize != 0 && fileSize <= Constants.MAX_FILE_SIZE) {
            avatar = new byte[fileSize];
            try {
                int bytesAmount = avatarPart.getInputStream().read(avatar, 0, fileSize);
                if (bytesAmount != fileSize) {
                    avatar = empty;
                }
            } catch (IOException e) {
                avatar = empty;
            }
        }
        String resultData;
        Account account = (Account)request.getSession().getAttribute(Constants.ACCOUNT_KEY);
        try {
            if (avatar.length != 0) {
                ChangeAccountLogic.updateAvatar(account.getAccountId(), avatar);
            }
            resultData = ConfigurationManager.getProperty(Constants.PAGE_MAIN);
            account.setAvatar(avatar);
        } catch (LogicException e) {
            LOG.log(Level.ERROR, "Errors during changing account avatar.", e);
            resultData = ConfigurationManager.getProperty(Constants.PAGE_SETTINGS);
        }
        return resultData;
    }

}
