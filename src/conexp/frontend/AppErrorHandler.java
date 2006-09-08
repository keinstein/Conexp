/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.frontend;


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
