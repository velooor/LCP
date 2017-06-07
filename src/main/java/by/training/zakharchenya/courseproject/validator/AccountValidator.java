package by.training.zakharchenya.courseproject.validator;

public class AccountValidator {

    private static final String LOGIN_REGEXP = "^\\p{L}(\\p{L}|\\p{N}|[_])*$";
    private static final String EMAIL_REGEXP = "^.+@.+[.].+$";
    private static final String PASSWORD_REGEXP = "((?=.*\\d)(?=.*[A-z]).{6,})";

    private AccountValidator() {
    }

    public static boolean validateLogin(String login) {
        return login.matches(LOGIN_REGEXP);
    }

    public static boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEXP);
    }

    public static boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEXP);
    }

}