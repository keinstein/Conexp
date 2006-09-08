/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.latticeeditor;

import conexp.util.exceptions.ConfigFatalError;
import conexp.util.gui.strategymodel.AbstractNonGrowingStrategyModel;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class XMLFileStrategyModel extends AbstractNonGrowingStrategyModel {
    static class ConfigInfo {
        final String label;
        final String className;

        ConfigInfo(String l, String clName) {
            label = StringUtil.safeTrim(l);
            className = StringUtil.safeTrim(clName);
        }
    }

    private static final String STRATEGY_MODEL_ELEMENT = "strategymodel";
    private static final String STRATEGY_ELEMENT_NAME = "strategy";
    private static final String LABEL_ATTRIBUTE = "label";
    private static final String CLASS_ATTRIBUTE = "class";

    private ArrayList configInfo = new ArrayList();

    protected java.lang.String[][] createInfo;

    /**
     * XMLFileStrategyModel constructor comment.
     */
    public XMLFileStrategyModel(String xmlFileUrl) {
        this(xmlFileUrl, true);
    }

    /**
     * Insert the method's description here.
     * Creation date: (14.06.01 21:46:49)
     *
     * @return java.lang.String[][]
     */
    public java.lang.String[][] getCreateInfo() {
        return createInfo;
    }

    /**
     * Insert the method's description here.
     * Creation date: (14.06.01 21:52:01)
     *
     * @param fileName java.lang.String
     */
    protected void parse(String fileName) {

        SAXBuilder builder = new SAXBuilder();

        Document doc;
        try {
            doc = builder.build(fileName);
        } catch (JDOMException e) {
            throw new ConfigFatalError("Error while parsing " + fileName, e);
        } catch (IOException e) {
            throw new ConfigFatalError("Error while parsing " + fileName, e);
        }
        Element root = doc.getRootElement();
        if (null == root ||
                !STRATEGY_MODEL_ELEMENT.equals(root.getName())) {
            throw new ConfigFatalError("Bad root element in xml file " + fileName);
        }

        configInfo.clear();
        readStrategies(root);
        convertConfigToCreateInfo();
    }

    private void readStrategies(Element root) {
        List strategies = root.getChildren(STRATEGY_ELEMENT_NAME);
        Iterator iter = strategies.iterator();
        while (iter.hasNext()) {
            Element strategyRecord = (Element) iter.next();
            configInfo.add(new ConfigInfo(strategyRecord.getAttributeValue(LABEL_ATTRIBUTE),
                    strategyRecord.getAttributeValue(CLASS_ATTRIBUTE)));
        }
    }


    /**
     * Insert the method's description here.
     * Creation date: (14.06.01 23:12:08)
     */
    protected void convertConfigToCreateInfo() {
        int size = configInfo.size();
        if (size <= 0) {
            throw new ConfigFatalError("Strategy model is empty");
        }
        createInfo = new String[size][3];

        for (int i = size; --i >= 0;) {
            ConfigInfo inf = (ConfigInfo) configInfo.get(i);
            if ("".equals(inf.label)) {
                throw new ConfigFatalError("Empty label in strategy model");
            }
            if ("".equals(inf.className)) {
                throw new ConfigFatalError("Empty className in strategy model");
            }

            createInfo[i][0] = inf.label;
            createInfo[i][1] = StringUtil.extractClassName(inf.className);
            createInfo[i][2] = inf.className;
        }
    }


    /**
     * XMLFileStrategyModel constructor comment.
     *
     * @test_public this constructor is for tests only
     */
    public XMLFileStrategyModel(String xmlFileUrl, boolean doCreateStrategies) {
        super(false); //not create strategies();
        parse(xmlFileUrl);
        if (doCreateStrategies) {
            createStrategies();
        }
    }
}
