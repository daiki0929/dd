package slim3.exception;

public class MyException extends RuntimeException {
    /**
     * 異常を発生する元（throwable）情報を持つ異常を構築する
     * 
     * @param throwable
     *            Throwable
     */
    public MyException(Throwable throwable) {
        super(throwable);
    }
}
