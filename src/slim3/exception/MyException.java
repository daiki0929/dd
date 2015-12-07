package slim3.exception;
@SuppressWarnings({ "unused", "serial" })
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
    
    /** 表示用のメッセージ */
    private String dispMessage;
    
    public void setDispMessage(String dispMessage) {
        this.dispMessage = dispMessage;
    }
    
    /**
     * メッセージ付け異常を構築する
     * 
     * @param message
     *            エラーメッセージ
     */
    public MyException(String message) {
        super(message);
        this.setDispMessage(message);
    }
    
}
