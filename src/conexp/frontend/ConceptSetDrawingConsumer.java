/*
 * User: sergey
 * Date: Nov 5, 2001
 * Time: 9:17:14 PM
 */
package conexp.frontend;

import conexp.frontend.latticeeditor.ConceptSetDrawing;

public interface ConceptSetDrawingConsumer {
    void setConceptSetDrawing(ConceptSetDrawing drawing);
    void clearConceptSetDrawing();
}
