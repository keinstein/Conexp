package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.figures.BorderCalculatingFigure;
import canvas.figures.MultiLineTextFigure;
import conexp.core.ContextEntity;
import conexp.frontend.latticeeditor.ConceptQuery;
import conexp.frontend.latticeeditor.figures.AbstractConceptCorrespondingFigure;

import java.util.Iterator;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 30/7/2003
 * Time: 22:19:52
 */

public class AllAttribsMultiLineLabelingStrategy extends OneLabelConceptLabelingStrategy{
    public AllAttribsMultiLineLabelingStrategy() {
        super();
    }

    protected BorderCalculatingFigure makeLabelForConceptCorrespondingFigure(AbstractConceptCorrespondingFigure f) {
        StringBuffer attribsNames = new StringBuffer();
        boolean first = true;
        for(Iterator attribsIterator = f.getConcept().ownAttribsIterator();attribsIterator.hasNext(); ){
            if(first){
                first = false;
            }else{
                attribsNames.append("\n");
            }
            attribsNames.append(((ContextEntity)attribsIterator.next()).getName());
        }
        MultiLineTextFigure figure = new MultiLineTextFigure();
        figure.setText(attribsNames.toString());
        return figure;
    }

    public boolean accept(ConceptQuery query) {
        return query.hasOwnAttribs();
    }
}
