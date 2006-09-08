/**
 * Copyright (c) 2000-2006, Serhiy Yevtushenko
 * All rights reserved.
 * Please read license.txt for licensing issues.
 **/



package conexp.frontend.attributeexploration;

import conexp.core.AttributeExplorationError;
import conexp.core.AttributeExplorer;
import conexp.core.AttributeInformationSupplier;
import conexp.core.AttributeInformationSupplierUtil;
import conexp.core.ContextEntity;
import conexp.core.LocalizedMessageSupplier;
import conexp.core.ModifiableSet;
import conexp.core.Set;
import conexp.core.attrexplorationimpl.AttributeExplorerImplementation;
import conexp.frontend.ResourceLoader;
import util.Assert;

import javax.swing.JOptionPane;
import java.awt.Component;
import java.text.MessageFormat;
import java.util.ResourceBundle;

public class AttributeExplorationUserCallbackImplementation implements AttributeExplorerImplementation.AttributeExplorerUserCallback {
    Component parentComponent;

    private static ResourceBundle resources;

    static {
        resources = ResourceLoader.getResourceBundle("conexp/frontend/resources/AttributeExplorerImplementation");  //$NON-NLS-1$
    }
    //------------------------------------------------------------

    static ResourceBundle getResources() {
        return resources;
    }

    LocalizedMessageSupplier messageSupplier = new LocalizedMessageSupplier() {
        public String getMessage(String key) {
            return getResources().getString(key);
        }
    };

    public LocalizedMessageSupplier getLocalizedMessageSupplier() {
        return messageSupplier;
    }

    protected String getLocalizedMessage(String key) {
        return getLocalizedMessageSupplier().getMessage(key);
    }

    public AttributeExplorationUserCallbackImplementation(Component parent) {
        this.parentComponent = parent;
    }

    AttributeExplorer attrExplorer;

    AttributeInformationSupplier attrInfo;

    public void setAttributeInformationSupplier(AttributeInformationSupplier attrInfo) {
        this.attrInfo = attrInfo;
    }

    final static String separator = " , "; //TODO - change for correct localization support
    final static String emptySetDescriptor = "";

    protected void describeSet(StringBuffer buffer, Set set) {
        AttributeInformationSupplierUtil.describeSet(buffer, attrInfo, set, separator, emptySetDescriptor);
    }


    protected String formAttributeExplorationQuestion(Set premise, Set conclusion) {
        StringBuffer temp = new StringBuffer();
        describeSet(temp, premise);
        String premiseAttributes = temp.toString();

        temp.setLength(0);
        describeSet(temp, conclusion);
        String conclusionAttributes = temp.toString();

        String formatString;
        if (premise.isEmpty()) {
            formatString = getLocalizedMessage("AttributeExplorerUserCallbackImplementation.EmptyPremiseQuestion");
        } else {
            formatString = getLocalizedMessage("AttributeExplorerUserCallbackImplementation.NotEmptyPremiseQuestion");
        }

        return MessageFormat.format(formatString, new Object[]{premiseAttributes, conclusionAttributes});
    }

    public int isTrue(Set premise, Set conclusion) {
        Object[] options = {getLocalizedMessage("Global.YesMessage"),
                getLocalizedMessage("Global.NoMessage"),
                getLocalizedMessage("AttributeExplorerUserCallbackImplementation.StopAttributeExplorationCaption")};


        int ret = JOptionPane.showOptionDialog(JOptionPane.getFrameForComponent(parentComponent),
                formAttributeExplorationQuestion(premise, conclusion),
                getLocalizedMessage("AttributeExplorerUserCallbackImplementation.ConfirmImplicationMessage"),
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        switch (ret) {
            case JOptionPane.CANCEL_OPTION:  //FALLTHROUGH !!!
            case JOptionPane.CLOSED_OPTION: //fix for case, when option dialog is closed with button
                return STOP;
            case JOptionPane.YES_OPTION:
                return ACCEPT_IMPLICATION;
            case JOptionPane.NO_OPTION:
                return REJECT_IMPLICATION;
            default:
                Assert.shouldNotGetHere();
                return STOP;
        }
    }

    public int queryContrExample(ContextEntity obj, ModifiableSet contrExample) {
        ContrExampleInputDialog dlg = new ContrExampleInputDialog(JOptionPane.getFrameForComponent(parentComponent),
                getLocalizedMessage("AttributeExplorerUserCallbackImplementation.CounterExampleCaption"), getLocalizedMessageSupplier());
        return dlg.queryContrExample(attrInfo, obj, contrExample);
    }

    public void error(AttributeExplorationError error) {
        JOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(parentComponent),
                error.formatMessage(getLocalizedMessageSupplier()),
                getLocalizedMessage("Global.ErrorMessage"),
                JOptionPane.ERROR_MESSAGE);

    }

}
