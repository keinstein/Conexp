package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.core.ContextEntity;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;

import java.util.Iterator;

/**
 * Author: Serhiy Yevtushenko
 * Date: Nov 29, 2002
 * Time: 11:24:28 PM
 */
public abstract class BaseFullLabelingStrategy extends SimpleConceptLabelingStrategy {
    public BaseFullLabelingStrategy(DrawParameters opt) {
        super(opt);
    }

    protected void describeContextEntitySet(StringBuffer ret, Iterator iter) {
        ret.append("{ ");
        boolean first = true;
        while (iter.hasNext()) {
            if (first) {
                first = false;
            } else {
                ret.append(" , ");
            }
            ret.append(((ContextEntity) iter.next()).getName());
        }
        ret.append(" }");
    }

    public boolean accept(ConceptQuery query) {
        return true;
    }
}
