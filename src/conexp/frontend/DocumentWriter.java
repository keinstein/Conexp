/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 27, 2001
 * Time: 11:45:07 PM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend;

import java.io.IOException;
import java.io.Writer;

public interface DocumentWriter {
    void storeDocument(Document document, Writer writer) throws IOException;
}
