package by.training.zakharchenya.courseproject.servlet;

/**
 * Created by Lagarde on 23.03.2017.
 */
public class Constants {
    public enum State {FORWARD, REDIRECT, AJAX, RESPONSE}

    public static final String PAGE_ERROR = "path.page.error";
    public static final String PAGE_LOGIN = "path.page.login";
    public static final String PAGE_REGISTRATION = "path.page.registration";
    public static final String PAGE_MAIN = "path.page.main";
    public static final String PAGE_INDEX = "path.page.index";
    public static final String PAGE_WELCOME = "path.page.welcome";
    public static final String PAGE_SETTINGS = "path.page.settings";
    public static final String PAGE_MESSAGES = "path.page.messages";
    public static final String PAGE_ADMINSETTINGS = "path.page.adminsettings";
    public static final String PAGE_SINGLE_GAME = "path.page.singlegame";
    public static final String PAGE_WAITING = "path.page.waitng";
    public static final String PAGE_MULTIGAME = "path.page.multigame";

    public static final String LOCALE_ATTRIBUTE="sing";
    public static final String SINGIN_ATTRIBUTE="singin";

    public static final String ERROR_LOGIN_MESSAGE = "errorLoginPassMessage";
    public static final String ERROR_SIGNUP_MESSAGE = "errorSingUpPassMessage";
    public static final String ERROR_CHANGE_PASSWORD_MESSAGE="errorChangePasswordMessage";
    public static final String NEWMESSAGE_MESSAGE="newMessageMessage";
    public static final String MULTIGAME_MESSAGE="multiGameMessage";

    public static final String VISITOR_KEY = "visitor";
    public static final String ACCOUNT_KEY = "account";
    public static final String STATE_KEY = "state";

    public static final String READ_MESSAGES_KEY = "readMessages";
    public static final String UNREAD_MESSAGES_KEY = "unreadMessages";

    public static final String ACTIVE_USERS_KEY = "activeUsers";
    public static final String BANNED_USERS_KEY = "bannedUsers";
    public static final String USERS_LIST_KEY = "allUsers";

    public static final String MIN_RATE_KEY = "minRate";
    public static final String MIN_POINTS_KEY = "minPoints";
    public static final String USER_KEY = "user";

    public static final String CREDIT_BALANCE_KEY = "creditBalance";

    public static final String CARD_FIRST_KEY = "cardFirst";
    public static final String CARD_SECOND_KEY = "cardSecond";
    public static final String CARD_THIRD_KEY = "cardThird";
    public static final String CARD_FOURTH_KEY = "cardFourth";
    public static final String CARD_MONTH_KEY = "cardMonth";
    public static final String CARD_YEAR_KEY = "cardYear";

    public static final String SINGLE_GAME_KEY = "singleGame";
    public static final String MULTI_GAME_KEY = "multiGameParams";

    public static final String FINISHED_GAMES_KEY = "finished";
    public static final String NOT_FINISHED_GAMES_KEY = "notFinished";
    public static final String MY_NOT_ACTIVE_GAMES_KEY = "myNotActive";
    public static final String NOT_MY_NOT_ACTIVE_GAMES_KEY = "notMyNotActive";


    public static final int MAX_FILE_SIZE = 10_000_000;


}
