/*
 * Created by IntelliJ IDEA.
 * User: sergey
 * Date: Jan 24, 2002
 * Time: 1:12:28 AM
 * To change template for new interface use
 * Code Style | Class Templates options (Tools | IDE Options).
 */
package conexp.core.layout;

import conexp.core.ItemSet;

import java.awt.geom.Point2D;

public interface ConceptCoordinateMapper {
    void setCoordsForConcept(ItemSet c, Point2D coords);
}
