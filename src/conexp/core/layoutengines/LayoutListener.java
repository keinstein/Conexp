/*
 * User: Serhiy Yevtushenko
 * Date: Oct 23, 2002
 * Time: 10:32:34 PM
 */

package conexp.core.layoutengines;

import conexp.core.layout.ConceptCoordinateMapper;

import java.util.EventListener;

public interface LayoutListener extends EventListener
{
    void layoutChange(ConceptCoordinateMapper mapper);
}
