package conexp.frontend;

/**
 * User: sergey
 * Date: 8/8/2006
 * Time: 12:56:26
 */
public interface AppErrorHandler {
    /**
     * This method is used for reporting expected ("usual") error situations
     * to the user.
     *
     * @param messageKey Key of the message which includes format
     * @param exception  Exception, which has occured
     */
    void reportAppErrorMessage(String messageKey, Throwable exception);

    /**
     * This methos is used for reporting unexpected ("Internal Error")
     * situations to the user.
     *
     * @param messageKey Property key for the message
     * @param exception  Exception data
     */
    void reportInternalError(String messageKey, Throwable exception);
}
