/*
 * User: sergey
 * Date: Jan 24, 2002
 * Time: 2:43:05 AM
 */
package conexp.frontend.latticeeditor;



public interface DrawStrategiesContext extends conexp.frontend.latticeeditor.FigureDimensionCalcStrategyProvider {
    void setupStrategiesParams(conexp.core.ConceptsCollection conceptSet);

    EdgeSizeCalcStrategy getEdgeSizeCalcStrategy();

    HighlightStrategy getHighlightStrategy();
}
