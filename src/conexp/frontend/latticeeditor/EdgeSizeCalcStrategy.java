package conexp.frontend.latticeeditor;

import conexp.core.ItemSet;

/**
 * Insert the type's description here.
 * Creation date: (18.01.01 23:13:08)
 * @author Serhiy Yevtushenko
 */
public interface EdgeSizeCalcStrategy extends DimensionCalcStrategy {
    float edgeThickness(ItemSet start, ItemSet end);
}