package by.training.zakharchenya.courseproject.action.command;

import by.training.zakharchenya.courseproject.action.Command;
import by.training.zakharchenya.courseproject.entity.Account;
import by.training.zakharchenya.courseproject.entity.game.MultiGame;
import by.training.zakharchenya.courseproject.manager.ImageManager;
import by.training.zakharchenya.courseproject.servlet.Constants;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.List;

/** Class serves to process the corresponding command: Load certain image
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class LoadImageCommand implements Command {

    private enum ImageSource {
        ACCOUNT, USER, MULTIGAME
    }
    private static final String ID_PARAM = "id";
    private static final String SOURCE_PARAM = "src";
    private static final String EMPTY_IMAGE = "";

    /**@param request from client
     * @return image, encoded to string
     */
    @Override
    public String execute(HttpServletRequest request) {
        byte[] image;
        String idString = request.getParameter(ID_PARAM);
        int id = 0;
        String sourceString = request.getParameter(SOURCE_PARAM);
        ImageSource source;
        if (sourceString == null) {
            return EMPTY_IMAGE;
        }
        try {
            source = ImageSource.valueOf(sourceString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return EMPTY_IMAGE;
        }

        image = ImageManager.getDefaultAvatar();

        if (idString != null) {
            try {
                id = Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                return Base64.getEncoder().encodeToString(image);
            }
        }
        switch (source) {
            case ACCOUNT:
                Account account = (Account) request.getSession().getAttribute(Constants.ACCOUNT_KEY);
                image = account.getAvatar();
                break;
            case USER:
                List<Account> users = (List<Account>) request.getSession().getAttribute(Constants.USERS_LIST_KEY);
                for(Account user : users){
                    if(user.getAccountId() == id){
                       image = user.getAvatar();
                    }
                }
                break;
            case MULTIGAME:
                MultiGame mg = (MultiGame) request.getSession().getAttribute(Constants.MULTI_GAME_KEY);
                if(mg.getPlayer().getAccountId() == id){
                    image = mg.getPlayer().getAvatar();
                }else if(mg.getCreator().getAccountId() == id){
                    image = mg.getCreator().getAvatar();
                }

                break;

        }
        String resultData;
        if (image == null || image.length == 0) {
            image = ImageManager.getDefaultAvatar();
        }
        resultData = Base64.getEncoder().encodeToString(image);
        request.getSession().setAttribute(Constants.STATE_KEY, Constants.State.RESPONSE);
        return resultData;
    }

}
