/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.framework;

import java.util.ArrayList;
import java.util.List;


public class ExperimentSet {
    protected List experiments;

    /**
     * ExperimentSet constructor comment.
     */
    public ExperimentSet() {
        super();
        experiments = new ArrayList();
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 13:48:42)
     *
     * @param experiment conexp.core.experimenter.NewExperiment
     */
    public void addExperiment(IExperiment experiment) {
        experiments.add(experiment);
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 13:50:50)
     *
     * @param i int
     */
    public IExperiment experimentAt(int i) {
        return (IExperiment) experiments.get(i);
    }


    /**
     * Insert the method's description here.
     * Creation date: (21.07.01 13:49:53)
     *
     * @return int
     */
    public int experimentCount() {
        return experiments.size();
    }
}
