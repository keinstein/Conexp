package conexp.frontend.latticeeditor;

/**
 * Insert the type's description here.
 * Creation date: (11.10.00 21:33:02)
 * @author Serhiy Yevtushenko
 */
public interface FigureDimensionCalcStrategy extends DimensionCalcStrategy {

    int calcNodeRadius(ConceptQuery query);
}