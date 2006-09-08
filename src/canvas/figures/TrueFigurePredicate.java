/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package canvas.figures;

import canvas.Figure;
import canvas.IFigurePredicate;



public class TrueFigurePredicate implements IFigurePredicate {

    private TrueFigurePredicate() {
    }

    static final IFigurePredicate g_Instance = new TrueFigurePredicate();

    public static IFigurePredicate getInstance() {
        return g_Instance;
    }

    public boolean accept(Figure figure) {
        return true;
    }
}
