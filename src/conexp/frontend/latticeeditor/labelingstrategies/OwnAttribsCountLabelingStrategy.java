package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;
import util.StringUtil;

public class OwnAttribsCountLabelingStrategy extends OneLabelConceptLabelingStrategy  {

    public OwnAttribsCountLabelingStrategy() {
        super(UP_LABEL_LOCATION_STRATEGY);
    }

    public boolean accept(ConceptQuery concept) {
        return concept.isInnermost() && concept.hasOwnAttribs();
    }

    protected String getDescriptionString(ConceptQuery conceptQuery) {
        return conceptQuery.getOwnAttribsCount() + " ( " + StringUtil.formatPercents(
                ((double) conceptQuery.getOwnAttribsCount()) / cxt.getAttributeCount()) + " )";

    }

}
