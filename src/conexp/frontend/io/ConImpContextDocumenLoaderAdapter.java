/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Nov 27, 2001
 * Time: 9:46:43 PM
 * To change template for new class use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.frontend.io;

import conexp.frontend.ContextDocument;
import conexp.frontend.DataFormatErrorHandler;
import conexp.frontend.DocumentLoader;
import util.DataFormatException;

import java.io.IOException;
import java.io.Reader;

public class ConImpContextDocumenLoaderAdapter implements DocumentLoader {

    public ContextDocument loadDocument(Reader r, DataFormatErrorHandler errorHandler) throws IOException, DataFormatException {
        ConImpContextLoader loader = new ConImpContextLoader();
        return new ContextDocument(loader.loadContext(r));
    }
}
