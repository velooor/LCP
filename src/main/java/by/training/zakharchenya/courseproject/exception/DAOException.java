package by.training.zakharchenya.courseproject.exception;

/** Exception class, serves for signaling of bad situations during working with DAO classes.
 * @author Vadim Zakharchenya
 * @version 1.0
 */
public class DAOException extends Exception {

    public DAOException() {
    }
    public DAOException(String s) {
        super(s);
    }

    public DAOException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DAOException(Throwable throwable) {
        super(throwable);
    }

}
