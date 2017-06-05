package by.training.zakharchenya.courseproject.exception;

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
