package conexp.frontend.latticeeditor.labelingstrategies;

import canvas.figures.BorderCalculatingFigure;
import canvas.figures.MultiLineTextFigure;
import conexp.core.ContextEntity;

import java.util.Iterator;

/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 * Date: 13/8/2003
 * Time: 14:53:30
 */

public abstract class MultiLineLabelingStrategyBase extends OneLabelConceptLabelingStrategy {
    public MultiLineLabelingStrategyBase() {
        super();
    }

    protected static BorderCalculatingFigure buildMultiLineFigureFromEntityIterator(Iterator iterator) {
        StringBuffer names = new StringBuffer();
        boolean first = true;
        for(;iterator.hasNext(); ){
            if(first){
                first = false;
            }else{
                names.append("\n");
            }
            names.append(((ContextEntity)iterator.next()).getName());
        }
        MultiLineTextFigure figure = new MultiLineTextFigure();
        figure.setText(names.toString());
        return figure;
    }
}
