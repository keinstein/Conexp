/**
 * Copyright (c) 2000-2003, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;


public class NestedLineDiagramCanvas extends BaseConceptSetCanvas {
    public NestedLineDiagramCanvas(NestedLineDiagramDrawing drawing) {
        //todo: fix it correctly, when NestedLineDiagramDrawing will be used
        super(new LatticePainterOptions());
        setConceptSetDrawing(drawing);
        init();
    }


}


