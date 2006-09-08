/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/
package conexp.experimenter.framework;

import conexp.core.BinaryRelation;
import conexp.core.BinaryRelationUtils;
import util.Assert;
import util.MemoryUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class ExperimentRunner {
    List protocol;

    protected PrintWriter outStream;
    protected PrintWriter screenStream;
    protected ExperimentSet experimentSet;

    protected IMeasurementProtocol expRunnerMeasurementProtocol;
    MeasurementSetChecker validatingParamsChecker = new MeasurementSetChecker();

    public static final String RUNTIME = "RunTime";
    protected static final String MEMORY_USAGE = "MemoryUsage";
    public static final String OPERATION_COUNT = "OperationCountStatistics";
    private ExperimentContextFactory setFactory;


    protected MeasurementProtocol makeExpRunnerMeasurementProtocol() {
        if (!isStoreOperationCount()) {
            return MeasurementProtocol.buildMeasurementProtocolFromStrings(
                    new String[][]{
                            {RUNTIME, "false"},
                            {MEMORY_USAGE, "false"}
                    }
            );
        } else {
            return MeasurementProtocol.buildMeasurementProtocolFromStrings(
                    new String[][]{
                            {RUNTIME, "false"},
                            {MEMORY_USAGE, "false"},
                            {OPERATION_COUNT, "false"}
                    }
            );
        }
    }

    protected IMeasurementProtocol getExpRunnerMeasurementProtocol() {
        if (null == expRunnerMeasurementProtocol) {
            expRunnerMeasurementProtocol = makeExpRunnerMeasurementProtocol();
        }
        return expRunnerMeasurementProtocol;
    }


    public ExperimentRunner() {
        this(null);
    }

    public ExperimentRunner(ExperimentContextFactory setFactory) {
        setOutStream(new PrintWriter(new StringWriter()));
        setScreenStream(new PrintWriter(new StringWriter()));
        this.setFactory = setFactory;
    }

    public void performExperiment(RelationSequence relSeq, int no) throws ExperimentException {
        MeasurementSet relationParams = new MeasurementSet();
        relationParams.setMeasurementProtocol(relSeq.getMeasurementProtocol());
        relSeq.fillInMeasurementSet(no, relationParams);
        ExperimentRunResults currRunResults = new ExperimentRunResults(relationParams, experimentSet.experimentCount());
        protocol.add(currRunResults);

        BinaryRelation rel = relSeq.getRelation(no);

        onStartExperimentStep(no, rel);

        try {
            for (int i = 0; i < experimentSet.experimentCount(); i++) {
                IExperiment experiment = experimentSet.experimentAt(i);
                MeasurementSet expResults = doPerformExperiment(experiment, rel);
                currRunResults.storeResultsForExperiment(i, expResults);
                validatingParamsChecker.checkValidatingParamsValues(expResults, experiment);
            }
        } catch (ExperimentException ex) {
            logFailureInformation(ex, rel);
            validatingParamsChecker.clear();
            throw ex;
        }
        validatingParamsChecker.clear();
        onFinishedExperimentStep(no);
    }

    protected static void checkValidityStatistics(ValidityStatistics prev, ValidityStatistics current) throws ExperimentException {
        if (!current.equals(prev)) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            current.printOn(pw);
            prev.printOn(pw);
            throw new ExperimentException(sw.toString());
        }
    }

    protected MeasurementSet doPerformExperiment(IExperiment experiment, BinaryRelation rel) throws ExperimentException {

        MeasurementSet res = new MeasurementSet();

        try {
            experiment.setUp(rel);

            if (isStoreOperationCount()) {
                setFactory.resetStatictics();
            }
            long startMemory = MemoryUtil.freeMemory();
            long start = System.currentTimeMillis();
            experiment.perform();

            long end = System.currentTimeMillis();
            long timeOfExecution = end - start;

            long endMemory = MemoryUtil.freeMemory();
            long memoryUsed = startMemory - endMemory;

            res.setMeasurementProtocol(getExpRunnerMeasurementProtocol());
            res.setMeasurement(RUNTIME, new Long(timeOfExecution));
            res.setMeasurement(MEMORY_USAGE, new Long(memoryUsed));
            if (isStoreOperationCount()) {
                res.setMeasurement(OPERATION_COUNT, setFactory.getSnapshot());
            }


            res.setMeasurementProtocol(experiment.getMeasurementProtocol());
            experiment.saveResults(res);
            res.clearMeasurementProtocol();

            logOneExperimentsResults(experiment.getMeasurementProtocol(), res);
        } finally {
            experiment.tearDown();
            MemoryUtil.fullGc();
        }
        return res;
    }

    public static String formatDouble(double temp) {
        return DecimalFormat.getNumberInstance().format(temp);
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.07.01 7:48:44)
     */
    protected static void interrupt() {
        System.exit(-1);
    }


    public int getNumberOfExperimentsRuns() {
        return protocol.size();
    }

    public ExperimentRunResults getRunResults(int runNo) {
        return (ExperimentRunResults) protocol.get(runNo);
    }

    interface IMeasurementSupplier {
        MeasurementSet getMeasurementParams(ExperimentRunResults res);
    }


    private void logMeasurementFromMeasurementSupplier(String measurementName, IMeasurementSupplier supplier) {
        for (int i = 0; i < getNumberOfExperimentsRuns(); i++) {
            MeasurementSet params = supplier.getMeasurementParams(getRunResults(i));
            if (null == params) {
                outStream.print("Wasn't performed");
            } else {
                outStream.print(params.getMeasurementValue(measurementName));
            }
            outStream.print(";");
        }
    }


    private void printOneParamWithHeaderFromSupplier(String header, String currParamName, IMeasurementSupplier supplier) {
        outStream.print(header);
        outStream.print(";");

        logMeasurementFromMeasurementSupplier(currParamName, supplier);

        outStream.println();
    }

    protected void logResultsFromMeasurementSupplier(IMeasurementProtocol protocol, IMeasurementSupplier supplier) {
        Iterator measurementsIter = protocol.measurementsIterator();

        while (measurementsIter.hasNext()) {
            IMeasurementDescription measurement = (IMeasurementDescription) measurementsIter.next();
            printOneParamWithHeaderFromSupplier(measurement.getName(), measurement.getName(), supplier);
        }
    }


    static class ExperimentMeasurementSupplier implements IMeasurementSupplier {
        final int expNo;

        public ExperimentMeasurementSupplier(int expNo) {
            this.expNo = expNo;
        }

        public MeasurementSet getMeasurementParams(ExperimentRunResults res) {
            return res.resultsOnExperimentSet[expNo];
        }
    }

    protected void logExperimentResults(RelationSequence relSeq, ExperimentSet experimentSet) {
        logRelationSequenceParams(relSeq);
        outStream.println();

        for (int i = 0; i < experimentSet.experimentCount(); i++) {
            IExperiment currExperiment = experimentSet.experimentAt(i);
            outStream.println(currExperiment.getDescription());
            IMeasurementSupplier expSupplier = new ExperimentMeasurementSupplier(i);

            logResultsFromMeasurementSupplier(getExpRunnerMeasurementProtocol(), expSupplier);
            logResultsFromMeasurementSupplier(currExperiment.getMeasurementProtocol(), expSupplier);

            outStream.println();
        }
        outStream.println();

        logIndividualParamsInParamTables(relSeq, experimentSet);

        outStream.flush();
    }

    protected void logIndividualParamsInParamTables(RelationSequence relSeq, ExperimentSet experimentSet) {
        Iterator experimentRunnerParamIter = getExpRunnerMeasurementProtocol().measurementsIterator();
        while (experimentRunnerParamIter.hasNext()) {
            String paramName = ((IMeasurementDescription) experimentRunnerParamIter.next()).getName();
            logParamForAllExperiments(relSeq, paramName, experimentSet);
        }

        Set commonParams = findNamesOfCommonParamsOfExperiments(experimentSet);


        Iterator iter = commonParams.iterator();
        while (iter.hasNext()) {
            String paramName = (String) iter.next();
            logParamForAllExperimentsHavingThisParam(relSeq, paramName, experimentSet);
        }

    }

    private void logValidatingParamsForExperimentsHavingParam(String paramName, ExperimentSet experimentSet) {
        Set validatingParams = new HashSet();


        for (int i = 0; i < experimentSet.experimentCount(); i++) {
            IMeasurementProtocol protocol = experimentSet.experimentAt(i).getMeasurementProtocol();
            if (!parameterIsSupportedByExperiment(protocol, paramName)) {
                continue;
            }
            Iterator iter = protocol.validatingMeasurementIterator();
            while (iter.hasNext()) {
                IMeasurementDescription description = (IMeasurementDescription) iter.next();
                Assert.isTrue(description.isValidating());
                if (!validatingParams.contains(description.getName())) {
                    validatingParams.add(description.getName());

                    printOneParamWithHeaderFromSupplier(description.getName(), description.getName(), new ExperimentMeasurementSupplier(i));

                }
            }
        }


    }

    private boolean parameterIsSupportedByExperiment(IMeasurementProtocol protocol, String paramName) {
        if (getExpRunnerMeasurementProtocol().hasMeasurementWithName(paramName)) {
            return true;
        }
        return protocol.hasMeasurementWithName(paramName);
    }


    private void logParamForAllExperiments(RelationSequence relSeq, String paramName, ExperimentSet experimentSet) {
        outStream.print(paramName);
        outStream.print(";");
        outStream.println();

        logSequenceAndValidatingParamsForExperimentsHavingParam(relSeq, paramName, experimentSet);

        for (int i = 0; i < experimentSet.experimentCount(); i++) {
            IExperiment currExperiment = experimentSet.experimentAt(i);
            printOneParamWithHeaderFromSupplier(currExperiment.getDescription(), paramName, new ExperimentMeasurementSupplier(i));
        }

        outStream.println();
    }

    private void logSequenceAndValidatingParamsForExperimentsHavingParam(RelationSequence relSeq, String paramName, ExperimentSet experimentSet) {
        logRelationSequenceParams(relSeq);
        logValidatingParamsForExperimentsHavingParam(paramName, experimentSet);
    }

    private void logParamForAllExperimentsHavingThisParam(RelationSequence relSeq, String paramName, ExperimentSet experimentSet) {
        outStream.print(paramName);
        outStream.print(";");
        outStream.println();

        logSequenceAndValidatingParamsForExperimentsHavingParam(relSeq, paramName, experimentSet);

        for (int i = 0; i < experimentSet.experimentCount(); i++) {
            IExperiment currExperiment = experimentSet.experimentAt(i);
            IMeasurementProtocol protocol = currExperiment.getMeasurementProtocol();
            if (!protocol.hasMeasurementWithName(paramName)) {
                continue;
            }
            printOneParamWithHeaderFromSupplier(currExperiment.getDescription(), paramName, new ExperimentMeasurementSupplier(i));
        }

        outStream.println();
    }

    private static Set findNamesOfCommonParamsOfExperiments(ExperimentSet experimentSet) {
        Set measuredParams = new HashSet();
        Set commonParams = new HashSet();
        for (int i = 0; i < experimentSet.experimentCount(); i++) {
            IMeasurementProtocol protocol = experimentSet.experimentAt(i).getMeasurementProtocol();
            Iterator iter = protocol.measurementsIterator();
            while (iter.hasNext()) {
                IMeasurementDescription description = (IMeasurementDescription) iter.next();
                if (description.isValidating()) {
                    continue; // validating params are same for all experiments
                }
                if (measuredParams.add(description.getName())) {
                    commonParams.add(description.getName());
                }
            }
        }
        return commonParams;
    }


    private void logRelationSequenceParams(RelationSequence relSeq) {
        outStream.println(relSeq.describeStrategy());
        logResultsFromMeasurementSupplier(relSeq.getMeasurementProtocol(), new IMeasurementSupplier() {
            public MeasurementSet getMeasurementParams(ExperimentRunResults res) {
                return res.relationParams;
            }
        });
    }


    /**
     * Insert the method's description here.
     * Creation date: (26.07.01 20:49:17)
     */
    protected void logFailureInformation(ExperimentException ex, BinaryRelation rel) {
        outStream.println("\n\nStart failure info");
        outStream.println(ex.toString());
        BinaryRelationUtils.logRelation(rel, outStream);
        outStream.println("End failure info \n\n");
    }


    /**
     * Insert the method's description here.
     * Creation date: (27.07.01 0:43:07)
     */
    protected void logOneExperimentsResults(IMeasurementProtocol protocol, MeasurementSet measurementSet) {
        Iterator iter = protocol.measurementsIterator();
        while (iter.hasNext()) {
            IMeasurementDescription measurement = (IMeasurementDescription) iter.next();
            screenStream.print(measurement.getName());
            screenStream.print(";");
            screenStream.print(measurementSet.getMeasurementValue(measurement.getName()));
            screenStream.print(";");
        }
        screenStream.println();
    }


    /**
     * Insert the method's description here.
     * Creation date: (26.07.01 20:48:35)
     */
    protected void onExperimentFailure() {
        outStream.println("-------------------------------------------------------");
        outStream.println("!!!!!!!Experiment failed");
        outStream.println("-------------------------------------------------------");
        outStream.flush();
    }


    /**
     * Insert the method's description here.
     * Creation date: (26.07.01 20:45:23)
     */
    protected void onFinishedExperimentStep(int no) {
        screenStream.println("Performed " + no + "th step");
        screenStream.println("Free memory " + MemoryUtil.freeMemory());
    }

    protected void onStartExperimentStep(int no, BinaryRelation rel) {
        screenStream.println("Step no " + no + " Relation stat " + BinaryRelationUtils.describeRelation(rel));
    }

    protected void printRelations(RelationSequence relGen) {
        int count = relGen.getRelationCount();
        for (int i = 0; i < count; i++) {
            BinaryRelationUtils.logRelation(relGen.getRelation(i), outStream);
        }
    }

    public void runExperiment(RelationSequence relGen) {
        Assert.isTrue(relGen != null);
        protocol = new ArrayList();
        try {
            final int relationCount = relGen.getRelationCount();
            for (int i = 0; i < relationCount; i++) {
                performExperiment(relGen, i);
            }
        } catch (ExperimentException ex) {
            onExperimentFailure();
        } finally {
            logExperimentResults(relGen, experimentSet);
        }
    }

    public void setExperimentSet(ExperimentSet newExperimentSet) {
        experimentSet = newExperimentSet;
    }

    public void setOutStream(PrintWriter newOutStream) {
        outStream = newOutStream;
    }

    public void setScreenStream(PrintWriter newScreenStream) {
        screenStream = newScreenStream;
    }

    protected boolean isStoreOperationCount() {
        return setFactory != null;
    }
}
