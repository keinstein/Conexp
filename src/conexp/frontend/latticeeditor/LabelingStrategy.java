package conexp.frontend.latticeeditor;


public abstract class LabelingStrategy implements ILabelingStrategy {
    public LabelingStrategy() {
        super();
    }

    public void shutdown(ConceptSetDrawing drawing) {
        drawing.visitFigures(makeShutDownVisitor(drawing));
//        drawing.applyChanges();
    }

    public void init(ConceptSetDrawing drawing) {
        drawing.visitFiguresAndApplyChanges(makeInitStrategyVisitor(drawing));
    }

    public abstract canvas.BaseFigureVisitor makeInitStrategyVisitor(ConceptSetDrawing fd);

    public abstract canvas.BaseFigureVisitor makeShutDownVisitor(ConceptSetDrawing fd);
}