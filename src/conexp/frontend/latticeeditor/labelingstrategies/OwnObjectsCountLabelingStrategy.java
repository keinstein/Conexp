package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;
import util.StringUtil;

/**
 * Insert the type's description here.
 * Creation date: (26.12.00 1:09:16)
 * @author
 */
public class OwnObjectsCountLabelingStrategy extends SimpleConceptLabelingStrategy {
    /**
     * OwnObjectsCountLabelingStrategy constructor comment.
     * @param opt conexp.frontend.latticeeditor.LatticePainterOptions
     */
    public OwnObjectsCountLabelingStrategy(DrawParameters opt) {
        super(opt);
    }

    /**
     * accept method comment.
     */
    public boolean accept(ConceptQuery concept) {
        return concept.isInnermost() && concept.hasOwnObjects();
    }

    /**
     * getDescriptionString method comment.
     */
    protected String getDescriptionString(ConceptQuery conceptQuery) {
        return conceptQuery.getOwnObjectsCount() + " ( " + StringUtil.formatPercents((double) conceptQuery.getOwnObjectsCount() / cxt.getObjectCount()) + " )";
    }
}