/*
 * User: sergey
 * Date: Nov 27, 2001
 * Time: 9:42:00 PM
 */
package conexp.frontend;

import util.DataFormatException;

import java.io.IOException;
import java.io.Reader;

public interface DocumentLoader {
    public ContextDocument loadDocument(Reader reader, DataFormatErrorHandler errorHandler) throws IOException, DataFormatException;
}
