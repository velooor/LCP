package by.training.zakharchenya.courseproject.exception;

/** Exception class, serves for signaling of bad situations during working with logic classes.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class LogicException extends Exception {

    public LogicException() {
    }

    public LogicException(String s) {
        super(s);
    }

    public LogicException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public LogicException(Throwable throwable) {
        super(throwable);
    }

}
