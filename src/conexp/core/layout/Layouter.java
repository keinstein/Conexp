package conexp.core.layout;

import conexp.frontend.latticeeditor.DrawParameters;
import conexp.util.GenericStrategy;
import conexp.util.gui.paramseditor.ParamsProvider;

import java.beans.PropertyChangeListener;


public interface Layouter extends GenericStrategy, ConceptCoordinateMapper, ParamsProvider {
    static final String LAYOUT_CHANGE = "layoutChanged";
    static final String LAYOUT_PARAMS_CHANGE = "layoutParamsChanged";

    void initLayout(conexp.core.Lattice l, DrawParameters drawParams);

    void addLayoutChangeListener(PropertyChangeListener listener);

    void removeLayoutChangeListener(PropertyChangeListener listener);

    boolean isDone();

    boolean isIncremental();

    void calcInitialPlacement();

    void improveOnce();

    void performLayout();
}