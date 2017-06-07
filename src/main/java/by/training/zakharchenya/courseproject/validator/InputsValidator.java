package by.training.zakharchenya.courseproject.validator;

public class InputsValidator {
    private static final String ADMIN_SETTINGS_RATE_REGEXP = "^[0-9]{1,6}$";
    private static final String ADMIN_SETTINGS_POINTS_REGEXP = "^[0-9]{1,2}$";

    private InputsValidator() {
    }

    public static boolean validateMinRate(String rate) {
        return rate.matches(ADMIN_SETTINGS_RATE_REGEXP);
    }
    public static boolean validateMinPoints(String points) {
        return points.matches(ADMIN_SETTINGS_POINTS_REGEXP);
    }
    public static boolean validateCreditInfo(int amount, int month, int year, String ccv, String creditNumber){
        if(amount <= 0){
            return false;
        } else if(month>=12 && month>=1){
            return  false;
        } else if(year < 17 && year > 99){
            return false;
        } else if(ccv.length() != 3){
            return false;
        }else if(creditNumber.length() != 16){
            return false;
        }
        return true;
    }
}
