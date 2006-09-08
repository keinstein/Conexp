package canvas.figures;

import canvas.Figure;
import canvas.IFigurePredicate;

/*
 * This program is a part of the Darmstadt JSM Implementation.
 *
 * You can redistribute it (modify it, compile it, decompile it, whatever)
 * AMONG THE JSM COMMUNITY. If you plan to use this program outside the
 * community, please notify V.K.Finn (finn@viniti.ru) and the authors.
 *
 * Authors: Peter Grigoriev and Serhiy Yevtushenko
 * E-mail: {peter, sergey}@intellektik.informatik.tu-darmstadt.de
 * 
 * Date: 27/6/2003
 * Time: 15:48:06
 */

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
