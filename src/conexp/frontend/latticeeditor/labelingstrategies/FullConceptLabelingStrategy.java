package conexp.frontend.latticeeditor.labelingstrategies;

import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.DrawParameters;

public class FullConceptLabelingStrategy extends BaseFullLabelingStrategy{
    public FullConceptLabelingStrategy(DrawParameters opt) {
        super(opt);
    }

    protected String getDescriptionString(ConceptQuery conceptQuery) {
        StringBuffer ret = new StringBuffer();
        ret.append("(");
        describeContextEntitySet(ret, conceptQuery.extentIterator());
        ret.append(" , ");

        describeContextEntitySet(ret, conceptQuery.intentIterator());
        ret.append(" )");
        return ret.toString();
    }
}
