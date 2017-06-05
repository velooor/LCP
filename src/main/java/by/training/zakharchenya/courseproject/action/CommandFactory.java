package by.training.zakharchenya.courseproject.action;

import by.training.zakharchenya.courseproject.action.command.*;
import by.training.zakharchenya.courseproject.action.command.admin.ChangeAdminParamCommand;
import by.training.zakharchenya.courseproject.action.command.admin.UpdateUserStatusCommand;
import by.training.zakharchenya.courseproject.action.command.change.*;
import by.training.zakharchenya.courseproject.action.command.game.*;
import by.training.zakharchenya.courseproject.action.command.mail.DeleteMessageCommand;
import by.training.zakharchenya.courseproject.action.command.mail.NewMessageCommand;
import by.training.zakharchenya.courseproject.action.command.mail.UpdateMessageCommand;
import by.training.zakharchenya.courseproject.action.command.sign.SignInCommand;
import by.training.zakharchenya.courseproject.action.command.sign.SignUpCommand;
import javax.servlet.http.HttpServletRequest;
import static by.training.zakharchenya.courseproject.action.CommandType.*;

/**Class, which interpret command param from client
 * and return corresponding command object
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class CommandFactory {
    /**Interpret command param from client
     * and return corresponding class
     * @param request from client
     * @return command object
     */
    public Command getCurrentCommand(HttpServletRequest request) {
        String command = request.getParameter(COMMAND_PARAM);
        switch (command) {
            case LOCALE:
                return new LocaleCommand();
            case LOGIN:
                return new SignInCommand();
            case LOGOUT:
                return new LogoutCommand();
            case NULL:
                return new ErrorCommand();
            case SINGUP:
                return new SignUpCommand();
            case LOAD_IMAGE:
                return new LoadImageCommand();
            case CHANGE_AVATAR:
                return new ChangeAvatarCommand();
            case RESET_AVATAR:
                return new ResetAvatarCommand();
            case NEW_MESSAGE:
                return new NewMessageCommand();
            case DELETE_MESSAGE:
                return new DeleteMessageCommand();
            case UPDATE_MESSAGE:
                return new UpdateMessageCommand();
            case CHANGE_NAME:
                return new ChangeNameCommand();
            case CHANGE_PASSWORD:
                return new ChangePasswordCommand();
            case UPDATE_USER_STATUS:
                return new UpdateUserStatusCommand();
            case CHANGE_ADMIN_PARAM:
                return new ChangeAdminParamCommand();
            case TOP_UP_ACCOUNT:
                return new TopUpAccountCommand();
            case PLAY:
                return new PlayCommand();
            case CREATE_GAME:
                return new CreateMultiGameCommand();
            case END_OF_SINGLE_ROUND:
                return new EndOfSingleRound();
            case DELETE_GAME:
                return new DeleteGameCommand();
            case START_MULTI_GAME:
                return new StartMultiGameCommand();
            case RETURN_MULTI_GAME:
                return new ReturnToMultiGameCommand();
            case MOVE_MULTI_GAME:
                return new MultiGameMoveCommand();
            case CHECK_MULTI_GAME:
                return new CheckMultiGameCommand();
        }
        return null;
    }
}

